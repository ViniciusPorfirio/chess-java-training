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
		turn = 1;
		currentPlayer = Color.BRANCA;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.BRANCA) ? Color.PRETA : Color.BRANCA;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	
	private void validateOriginalPosition(Position source) throws ChessException {
		if(!board.ThereIsAPiece(source)) {
			throw new ChessException("Não há peças na posição de origem");
		}
		if(currentPlayer != ((ChessPiece)board.piece(source)).getColor()) {
			throw new ChessException("Essa peça não é sua");
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
		placeNewPiece('d', 1,new King(board ,Color.PRETA));
		placeNewPiece('a', 1,new Tower(board ,Color.PRETA));
		placeNewPiece('a', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('b', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('c', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('d', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('e', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('f', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('g', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('h', 2,new Rook(board ,Color.PRETA));
		

		placeNewPiece('e', 8,new King(board ,Color.BRANCA));
		placeNewPiece('a', 8,new Tower(board ,Color.BRANCA));
		placeNewPiece('a', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('b', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('c', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('d', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('e', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('f', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('g', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('h', 7,new Rook(board ,Color.BRANCA));
	}
}
