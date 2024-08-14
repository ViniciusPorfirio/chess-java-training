package boardLayer;

public abstract class Piece {
	protected Position position;
	private Board board;
	
	public abstract boolean[][] possibleMoves();
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}
	
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	protected Board getBoard() {
		return board;
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] matri = possibleMoves();
		for(int i = 0; i < matri.length ; i++){
			for(int j = 0 ; j<matri.length; j++) {
				if(matri[i][j]) {
					return true;
				}
			}
		}
		
		return false;
	}
}
