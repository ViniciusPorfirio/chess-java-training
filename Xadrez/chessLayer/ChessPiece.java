package chessLayer;
import boardLayer.Board;
import boardLayer.Piece;

public class ChessPiece extends Piece {
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	@Override
	public boolean possibleMoves() {
		// TODO Auto-generated method stub
		return false;
	}

	public Color getColor() {
		return color;
	}
	
	
}
