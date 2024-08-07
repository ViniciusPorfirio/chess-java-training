package chessLayer;

import boardLayer.Board;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promote;
	
	public ChessMatch() {
		board = new Board(8,8);
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColunm()];
		for(int i=0 ; i<board.getColunm();i++) {
			for(int j=0 ; j<board.getColunm();j++) {
				mat [i][j] = (ChessPiece) board.piece(i,j);
			}
		}
		return mat;
	}
	
	
}
