package boardLayer;

public abstract class Piece extends Board{
	protected Position position;
	private Board board;
	
	public abstract boolean possibleMoves();
	
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}
	
	public boolean possibleMove(Position position) {
		return true;
	}

	protected Board getBoard() {
		return board;
	}

	public boolean isThereAnyPossibleMove() {
		return true;
	}
}
