import java.util.Random;
import java.util.Scanner;

public class DotsAndBoxes {
	public static void main(String[] args)
	{   int myTime = 5;//waiting time not working
		Scanner s = new Scanner(System.in);
		int boardWidth = 0;
		int boardHeight = 0;
		int playsNumber = 0;
		int playerSelection = 0;
		Player starting;
		Node node;
		int[] move = new int[2];
		String[] moveStr;
		Error result;
        if (args.length == 2) {
            if (args[0].equals("-d")) {
                try {
                    playsNumber = Integer.parseUnsignedInt(args[1]);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid number");
                    System.exit(1);
                }
            }
            else {
                System.out.println("Incorrect command, command should be -d <N>");
                System.exit(1);
            }
        }
        else {
            if (args.length > 2) {
                System.out.println("Too many arguments use it as -d <N>");
                System.exit(1);
            }
            else {
                System.out.println("Too few arguments use it as -d <N>");
                System.exit(1);
            }
        }
		while(boardWidth == 0)
		{
			System.out.print("Enter the width of the grid: ");
			try {
				boardWidth = Integer.parseInt(s.nextLine());
			} catch(NumberFormatException e)
			{
				System.out.print("\nInvalid width. Try again.");
			}
		}
		while(boardHeight == 0)
		{
			System.out.print("\nEnter the height of the grid: ");
			try {
				boardHeight = Integer.parseInt(s.nextLine());
			} catch(NumberFormatException e)
			{
				System.out.print("\nInvalid height. Try again.");
			}
		}
		node = new Node(boardHeight, boardWidth, Player.MIN);
		System.out.println("Enter the coordinates of your moves in <column>,<row> format. Example 0,1\n");		
		node.printNode();
        int prevPlayerScore = 0;
        int prevComputerScore = 0;
		while(!node.isOver())
		{
			do {
				System.out.print("Your move: ");
				moveStr = s.nextLine().split(",");
				result = Error.SUCCESS;
				if(moveStr.length < 2)
				{
					System.out.println("Invalid move, format is <column>,<row> .");
					result = Error.INVALID_NUMBER;
				} else
				{
					for(int i = 0; i < 2; i++)
					{
						try {
							move[i] = Integer.parseInt(moveStr[i]);
						} catch (NumberFormatException e)
						{
							System.out.println("Invalid move, format is <column>,<row>.");
							result = Error.INVALID_NUMBER;
						}
					}
				}
                
				if(result != Error.INVALID_NUMBER)
					result = node.makeMove(move[1], move[0]);
				if(result == Error.INVALID_SPACE)
					System.out.println("You can't draw a line there.");
				if(result == Error.OUT_OF_BOUNDS)
					System.out.println("That coordinate is outside the boundaries of the grid.");
				if(result == Error.SPACE_FILLED)
					System.out.println("That space already has a line drawn in it.");
			} while (result != Error.SUCCESS);
			System.out.print("\n");
			node.printNode();
           
            if(prevPlayerScore != node.getMinScore() && node.getMinScore() != 0){            
                prevPlayerScore = node.getMinScore();
                node.type = Player.MIN;
                continue;
            }
                
			if(!node.isOver())
			{
				System.out.print("Computer's move: ");
                final long start = System.nanoTime();
				move = MinMax.makeMove(node, playsNumber,myTime,start);
				System.out.println(move[1] + "," + move[0]);
				node.makeMove(move[0], move[1]);
				node.printNode();
                
                
                while(prevComputerScore != node.getMaxScore() && node.getMaxScore() != 0){
                    node.type = Player.MAX;
                    prevComputerScore = node.getMaxScore();
                    System.out.print("Computer's move: ");
                    move = MinMax.makeMove(node, playsNumber,myTime,start);
                    System.out.println(move[1] + "," + move[0]);
                    node.makeMove(move[0], move[1]);
                    node.printNode();
                }
			}
            
		}
		
		System.out.println("*************\nFinal results\n*************");
		System.out.println("Player score: " + node.getMinScore());
		System.out.println("Computer score: " + node.getMaxScore());
		if(node.getMaxScore() > node.getMinScore())
			System.out.println("Winner is the Computer");
		else if(node.getMinScore() > node.getMaxScore())
			System.out.println("Winner is the Player");
		else
			System.out.println("The game is a draw.");
		
		
		s.close();
	}
}