
public class MinMax {
	public static int[] makeMove(Node node, int playsNumber, int myTime, final long start)
	{
		int coordinates[] = {0, 0};
		Node newNode = new Node(node);
		int best = minMax(newNode, playsNumber, Integer.MIN_VALUE, Integer.MAX_VALUE,myTime,start);
                
		for(Node n : newNode.children)
		{
			if(n.val == best)
			{
				coordinates[0] = n.getChangedRow();
				coordinates[1] = n.getChangedCol();
				return coordinates;
			}     
		}
		return coordinates;
	}
	
	private static int minMax(Node node, int plys, int alpha, int beta,int myTime, final long start)
	{
		node.addChildren();
		if(node.depth >= plys || node.children.isEmpty())
		{      	node.eval();
			return node.val;
		}
		if(node.getType() == Player.MAX)
		{
			int bestVal = Integer.MIN_VALUE;
			int value;
			for(Node n : node.children )
			{   value = minMax(n, plys - 1, alpha, beta,myTime,start);
				if(value > bestVal)
					bestVal = value;
				if(bestVal > alpha)
					alpha = bestVal;
				if(beta <= alpha)
					break;
			}
			node.val = bestVal;
			return bestVal;
		}
		else
		{
			int bestVal = Integer.MAX_VALUE;
			int value;
			for(Node n : node.children)
			{	value = minMax(n, plys - 1, alpha, beta,myTime,start);
				if(value < bestVal)
					bestVal = value;
				if(bestVal < beta)
					beta = bestVal;
				if(beta <= alpha)
					break;
			}
			node.val = bestVal;
			return bestVal;
		}
	
        }
}

