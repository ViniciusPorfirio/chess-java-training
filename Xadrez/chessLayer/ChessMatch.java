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
	
	public ChessMatch() throws ChessException {
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
	
	private void placeNewPiece (char column, int row, ChessPiece piece) throws ChessException {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	public Piece makeMove(Position source,Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	public ChessPiece performChessMove(ChessPosition originalPosition, ChessPosition targetPosition) {
		Position originalP = originalPosition.toPosition();
		Position target = targetPosition.toPosition();
		validateOriginalPosition(originalP);
		validateTargetPosition(originalP, target);
		Piece capturedPiece = makeMove(originalP,target);
		return (ChessPiece)capturedPiece;
	}
	
	private void validateOriginalPosition(Position source) throws ChessException {
		if(!board.ThereIsAPiece(source)) {
			throw new ChessException("Não há peças na posição de origem");
		}
		if(!board.piece(source).isThereAnyPossibleMove()) {
			throw new ChessException("Não há movimentos possiveis");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("Não é possivel mover peça para posição "+target);
		}
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateOriginalPosition(position);
		return board.piece(position).possibleMoves();
	}
	
	private void initialSetup() throws ChessException {
		placeNewPiece('d', 1,new King(board ,Color.BLACK));
		placeNewPiece('a', 1,new Tower(board ,Color.BLACK));
		//placeNewPiece('a', 2,new Rook(board ,Color.BLACK));
		placeNewPiece('b', 2,new Rook(board ,Color.BLACK));
		//placeNewPiece('c', 2,new Rook(board ,Color.BLACK));
		placeNewPiece('d', 2,new Rook(board ,Color.BLACK));
		//placeNewPiece('e', 2,new Rook(board ,Color.BLACK));
		placeNewPiece('f', 2,new Rook(board ,Color.BLACK));
		placeNewPiece('g', 2,new Rook(board ,Color.BLACK));
		placeNewPiece('h', 2,new Rook(board ,Color.BLACK));
		

		placeNewPiece('e', 8,new King(board ,Color.WHITE));
		placeNewPiece('a', 8,new Tower(board ,Color.WHITE));
		placeNewPiece('a', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('b', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('c', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('d', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('e', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('f', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('g', 7,new Rook(board ,Color.WHITE));
		placeNewPiece('h', 7,new Rook(board ,Color.WHITE));
	}
}
