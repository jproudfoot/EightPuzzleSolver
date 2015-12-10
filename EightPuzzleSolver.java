import java.util.PriorityQueue;

import linkedList.LinkedList;


public class EightPuzzleSolver {
	public static void main (String [] args) {
		int [][] board  = {{7,8,6}, {2,5,3}, {0,1,4}};
		
		long startTime = System.currentTimeMillis();
		Board solvedBoard = manhattanSolver(new Board(board));
		long stopTime = System.currentTimeMillis();
		
		
		System.out.println("METHOD: MANHATTAN")
		System.out.println("TIME: " + (stopTime-startTime));
		System.out.println("MOVES: " + solvedBoard.getMovesMade().size());
		System.out.println("SOLVED BOARD:\n" + solvedBoard);


		long startTime = System.currentTimeMillis();
		Board solvedBoard = hammingSolver(new Board(board));
		long stopTime = System.currentTimeMillis();
		
		
		System.out.println("METHOD: HAMMING")
		System.out.println("TIME: " + (stopTime-startTime));
		System.out.println("MOVES: " + solvedBoard.getMovesMade().size());
		System.out.println("SOLVED BOARD:\n" + solvedBoard);
		
	}
	
	public static Board manhattanSolver (Board parentBoard) {
		Board.scoringMethod = false;
		return solver(parentBoard);
	}
	
	public static Board hammingSolver (Board parentBoard) {
		Board.scoringMethod = true;
		return solver(parentBoard);
	}
	
	private static Board solver (Board parentBoard) {
		PriorityQueue<Board> unchecked = new PriorityQueue<Board>();
		LinkedList<Board> checked = new LinkedList<Board>();
		
		unchecked.add(parentBoard);
		
		while (true) {
			Board bestBoard = unchecked.poll();
			
			if (bestBoard.isGoal()) return bestBoard; //Checks to see if the board is solved.
			
			if (checked.contains(bestBoard)) continue; //Checks to see if the board has already been tested.
			
			Iterable<Board> children = bestBoard.neighbors(); //Generates all of the possible board states from the best board
			for (Board child : children) unchecked.add(child); //Adds the child boards to the unchecked list.
			
			checked.add(bestBoard); //Adds the current best board to the checkedlist
		}
	}
	
	
}
