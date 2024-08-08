package chessLayer.piecesTypes;

import boardLayer.Board;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class Tower extends ChessPiece {
	public Tower(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "T";
	}
}
