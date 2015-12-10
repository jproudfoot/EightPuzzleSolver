import linkedList.LinkedList;


public class Board implements Comparable<Board>{
	private int [][] board;
	public static boolean scoringMethod = false; //false - Manhattan; 1 - Hamming
	
	private LinkedList<Board> movesMade;
	
	public Board (int [][] board, LinkedList<Board> movesMade) {
		this.board = board;
		this.movesMade = movesMade;
	}
	
	public Board (int [][] board) {
		this.board = board;
		this.movesMade = new LinkedList<Board>();
	}
	
	/**
	 * Returns an iterable containing all of the possible child boards.
	 * @return Iterable<Board> child boards
	 */
	public Iterable<Board> neighbors() {
		int[] emptySpace = emptySpace();
	
		if (emptySpace[0] == -1) return null;
		else {
			LinkedList<Board> children = new LinkedList<Board>();
			movesMade.add(this);
			
			//Is the empty space at the left of the board
			if (emptySpace[1] > 0) {
				int [][] newBoard = copyBoard();
				
				newBoard[emptySpace[0]][emptySpace[1]] = newBoard[emptySpace[0]][emptySpace[1]-1];
				newBoard[emptySpace[0]][emptySpace[1]-1] = 0;
				
				children.add(new Board(newBoard, movesMade));
			}
			
			//Is the empty space at the right of the board?
			if (emptySpace[1] < board[0].length-1) {
				int [][] newBoard = copyBoard();
				
				newBoard[emptySpace[0]][emptySpace[1]] = newBoard[emptySpace[0]][emptySpace[1]+1];
				newBoard[emptySpace[0]][emptySpace[1]+1] = 0;
				
				children.add(new Board(newBoard, movesMade));
			}
			
			//Is the empty space at the top of the board?
			if (emptySpace[0] > 0) {
				int [][] newBoard = copyBoard();
				
				newBoard[emptySpace[0]][emptySpace[1]] = newBoard[emptySpace[0]-1][emptySpace[1]];
				newBoard[emptySpace[0]-1][emptySpace[1]] = 0;
				
				children.add(new Board(newBoard, movesMade));
			}
			
			//Is the empty space at the bottom of the board?
			if (emptySpace[0] < board.length-1) {
				int [][] newBoard = copyBoard();
				
				newBoard[emptySpace[0]][emptySpace[1]] = newBoard[emptySpace[0]+1][emptySpace[1]];
				newBoard[emptySpace[0]+1][emptySpace[1]] = 0;
				
				children.add(new Board(newBoard, movesMade));
			}
			
			return children;
		}
	}
	
	/**
	 * Returns a copy of the board array
	 * @return board copy
	 */
	private int[][] copyBoard () {
		int [][] copyBoard = new int [board.length][board[0].length];
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				copyBoard[x][y] = board[x][y];
			}
		}
		
		return copyBoard;
	}
	
	/**
	 * Returns the location of the empty space
	 * @return int location
	 */
	private int[] emptySpace () {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] == 0) {
					int[] coords = {x, y};
					return coords;
				}
			}
		}
		
		int[] coords = {-1, -1};
		return coords;
	}
	
	/**
	 * Returns the size of one side of the board.
	 * @return int size
	 */
	public int size() {
		return board.length;
	}
	
	/**
	 * Checks to see if the board is solved. If so returns true, else returns false.
	 * @return is the board the goal board
	 */
	public boolean isGoal () {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] != (y + (x*board.length) + 1) % 9) return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns the string representation of the board
	 * @return String board
	 */
	public String toString() {
		String str = "";
		
		for (int [] r : board) {
			for (int i : r) str += i +" ";
			str += "\n";
		}
		
		return str;
	}

	/**
	 * Return the int array that holds the values for the board.
	 * @return board array
	 */
	public int[][] getBoard () {
		return board;
	}
	
	/**
	 * Returns whether two Boards hold the same values in the same positions.
	 * @return boolean are the boards equal
	 */
	public boolean equals (Object object) {
		if (this.toString().equals(object.toString())) return true;
		else return false;
	}
	
	/**
	 * Calculates the Hamming Score of the current board state. The Hamming score is found by adding the number of tiles out of position and the number of moves it took to get to the current board.
	 * @return int Hamming score of the board
	 */
	public int hamming () {
		int numberOfTilesOutOfPosition = 0;
		
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] != ((1 + y*board.length) + x) % 9) numberOfTilesOutOfPosition++;
			}
		}
		
		return numberOfTilesOutOfPosition + movesMade.size();
	}
	
	/**
	 * Calculates the Manhattan score of the current board state. The Manhattan score is found by adding the distance of each misplaces tile from its correct position and the number of moves it took to reach the current position.
	 * @return int Manhattan score of the board
	 */
	public int manhattan () {
		int tilesOutOfPositionScore = 0;
		
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] != ((1 + y*board.length) + x) % 9 && board[y][x] != 0) {
					int tileY = (board[y][x]-1) / 3;
					int tileX = (board[y][x]-1) % 3;
					
					tilesOutOfPositionScore += Math.abs(y-tileY) + Math.abs(x - tileX);
				}
			}
		}
		
		return tilesOutOfPositionScore + movesMade.size();
	}

	
	/**
	 * Compares the two board's score and returns their relationship
	 * @return int less than, equal to, or greater than
	 */
	public int compareTo(Board b) {
		if (!scoringMethod) {
			return manhattan() - b.manhattan();
		}
		else {
			return hamming() - b.hamming();
		}
	}

	
	/**
	 * Returns a linked list with all of the previous board states of the moves made
	 * @return LinkedList<Board> linked list of all previous board states
	 */
	public LinkedList<Board> getMovesMade() {
		return movesMade;
	}

}
