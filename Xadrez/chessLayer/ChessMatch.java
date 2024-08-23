package chessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardLayer.Board;
import boardLayer.Piece;
import boardLayer.Position;
import chessLayer.piecesTypes.*;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promote;
	
	List <ChessPiece> piecesOnTheBoard = new ArrayList<>();
	List <ChessPiece> capturedPieces = new ArrayList<>();
	
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
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		piecesOnTheBoard.add(piece);
	}
	
	public Piece makeMove(Position source,Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();;
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece)capturedPiece);
		}
		return capturedPiece;
	}
	
	public void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece)capturedPiece);
		}
	}
	
	public ChessPiece performChessMove(ChessPosition originalPosition, ChessPosition targetPosition) {
		Position originalP = originalPosition.toPosition();
		Position target = targetPosition.toPosition();
		validateOriginalPosition(originalP);
		validateTargetPosition(originalP, target);
		Piece capturedPiece = makeMove(originalP,target);
		if(testCheck(currentPlayer)) {
			undoMove(originalPosition.toPosition(), targetPosition.toPosition(), capturedPiece);
			throw new ChessException("Você não pode colocar seu rei em check");
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();
		}
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
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List <Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List <ChessPiece> pieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : pieces) {
			boolean[][] mat = p.possibleMoves();
			for(int i = 0; i<board.getRows();i++) {
				for(int j = 0; j <board.getColumns();j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source,target);
						boolean testCheck = testCheck(color);
						undoMove(source,target,capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private Color opponent(Color color) {
		return (color == Color.BRANCA) ? Color.PRETA : Color.BRANCA;
	}
	
	private ChessPiece king(Color color) {
		List <ChessPiece> pieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : pieces) {
			if(p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Não há rei da cor "+color+" no tabuleiro");
	}
	
	private void initialSetup() throws ChessException {
		placeNewPiece('a', 1,new Tower(board ,Color.PRETA));
		//placeNewPiece('b', 1,new Knight(board ,Color.PRETA));
		placeNewPiece('c', 1,new Bishop(board ,Color.PRETA));
		placeNewPiece('d', 1,new King(board ,Color.PRETA));
		//placeNewPiece('e', 1,new Queen(board ,Color.PRETA));
		placeNewPiece('a', 2,new Pawn(board ,Color.PRETA));
		placeNewPiece('b', 2,new Pawn(board ,Color.PRETA));
		placeNewPiece('c', 2,new Pawn(board ,Color.PRETA));
		//placeNewPiece('d', 2,new Rook(board ,Color.PRETA));
		placeNewPiece('e', 2,new Pawn(board ,Color.PRETA));
		placeNewPiece('f', 2,new Pawn(board ,Color.PRETA));
		placeNewPiece('g', 2,new Pawn(board ,Color.PRETA));
		placeNewPiece('h', 2,new Pawn(board ,Color.PRETA));
		

		placeNewPiece('a', 8,new Tower(board ,Color.BRANCA));
		placeNewPiece('b', 8,new Knight(board ,Color.BRANCA));
		placeNewPiece('c', 8,new Bishop(board ,Color.BRANCA));
		//placeNewPiece('d', 8,new Queen(board ,Color.BRANCA));
		placeNewPiece('e', 8,new King(board ,Color.BRANCA));
		//placeNewPiece('a', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('b', 7,new Pawn(board ,Color.BRANCA));
		placeNewPiece('c', 7,new Pawn(board ,Color.BRANCA));
		placeNewPiece('d', 7,new Pawn(board ,Color.BRANCA));
		placeNewPiece('e', 7,new Pawn(board ,Color.BRANCA));
		placeNewPiece('f', 7,new Pawn(board ,Color.BRANCA));
		placeNewPiece('g', 7,new Pawn(board ,Color.BRANCA));
		placeNewPiece('h', 7,new Pawn(board ,Color.BRANCA));
	}
}
