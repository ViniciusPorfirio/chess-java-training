package chessLayer;

import boardLayer.*;
import chessLayer.piecesTypes.*;

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
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0 ; i<board.getColumns();i++) {
			for(int j=0 ; j<board.getColumns();j++) {
				mat [i][j] = (ChessPiece) board.piece(i,j);
			}
		}
		return mat;
	}
	
	private void initialSetup() {
		board.placePiece(new Tower(board ,Color.WHITE), new Position(1, 1));
		board.placePiece(new King(board ,Color.BLACK), new Position(7, 3));
		board.placePiece(new King(board ,Color.WHITE), new Position(1, 4));
	}
}
