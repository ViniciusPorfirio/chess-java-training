package chessLayer;

import java.security.InvalidParameterException;
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

	List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
	List<ChessPiece> capturedPieces = new ArrayList<>();

	public ChessMatch() throws ChessException {
		board = new Board(8, 8);
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

	public ChessPiece getEnPassantVulnarable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromote() {
		return promote;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getColumns(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) throws ChessException {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	public Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece) capturedPiece);
		}

		// Roque lado do rei
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceTower = new Position(source.getRow(), source.getColumn() + 3);
			Position targetTower = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece Tower = (ChessPiece) board.removePiece(sourceTower);
			board.placePiece(Tower, targetTower);
			Tower.increaseMoveCount();
		}
		// Roque lado da rainha
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceTower = new Position(source.getRow(), source.getColumn() - 4);
			Position targetTower = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece Tower = (ChessPiece) board.removePiece(sourceTower);
			board.placePiece(Tower, targetTower);
			Tower.increaseMoveCount();
		}

		// Movimento Especial - En Passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position PawnPosition = null;
				if (p.getColor() == Color.BRANCA) {
					PawnPosition = new Position(target.getRow() - 1, target.getColumn());
				} else if (p.getColor() == Color.PRETA) {
					PawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				capturedPiece = board.removePiece(PawnPosition);
				capturedPieces.add((ChessPiece) capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	public void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}

		// Roque lado do rei
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceTower = new Position(source.getRow(), source.getColumn() + 3);
			Position targetTower = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece Tower = (ChessPiece) board.removePiece(targetTower);
			board.placePiece(Tower, sourceTower);
			Tower.decreaseMoveCount();
		}
		// Roque lado da rainha
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceTower = new Position(source.getRow(), source.getColumn() - 4);
			Position targetTower = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece Tower = (ChessPiece) board.removePiece(targetTower);
			board.placePiece(Tower, sourceTower);
			Tower.decreaseMoveCount();
		}

		// Movimento Especial - En Passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece returnedPawn = (ChessPiece) board.removePiece(target);
				Position PawnPosition = null;
				if (p.getColor() == Color.BRANCA) {
					PawnPosition = new Position(4, target.getColumn());
				} else if (p.getColor() == Color.PRETA) {
					PawnPosition = new Position(3, target.getColumn());
				}

				board.placePiece(returnedPawn, PawnPosition);
				capturedPiece = board.removePiece(PawnPosition);
				capturedPieces.add((ChessPiece) capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

	}

	public ChessPiece performChessMove(ChessPosition originalPosition, ChessPosition targetPosition) {
		Position originalP = originalPosition.toPosition();
		Position target = targetPosition.toPosition();
		validateOriginalPosition(originalP);
		validateTargetPosition(originalP, target);
		Piece capturedPiece = makeMove(originalP, target);
		if (testCheck(currentPlayer)) {
			undoMove(originalPosition.toPosition(), targetPosition.toPosition(), capturedPiece);
			throw new ChessException("Você não pode colocar seu rei em check");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);
		
		//Movimento especial - Promoção
		promote=null;
		if(movedPiece instanceof Pawn) {
			if((movedPiece.getColor() == Color.BRANCA && target.getRow()==7) || (movedPiece.getColor() == Color.PRETA && target.getRow()==0)) {
				promote = (ChessPiece)board.piece(target);
				promote = replacePromotedPiece("Q");
				
			}
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}

		// Movimento especial - EnPassant
		if (movedPiece instanceof Pawn
				&& (target.getRow() == originalP.getRow() - 2 || target.getRow() == originalP.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else /*
				 * if(movedPiece instanceof Pawn == false && !(target.getRow() ==
				 * originalP.getRow() - 2 || target.getRow() == originalP.getRow() + 2 ) )
				 */ {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece;
	}

	private void validateOriginalPosition(Position source) throws ChessException {
		if (!board.ThereIsAPiece(source)) {
			throw new ChessException("Não há peças na posição de origem");
		}
		if (currentPlayer != ((ChessPiece) board.piece(source)).getColor()) {
			throw new ChessException("Essa peça não é sua");
		}
		if (!board.piece(source).isThereAnyPossibleMove()) {
			throw new ChessException("Não há movimentos possiveis");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("Não é possivel mover peça para posição " + target);
		}
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateOriginalPosition(position);
		return board.piece(position).possibleMoves();
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}

		return false;
	}

	public boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<ChessPiece> pieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : pieces) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public ChessPiece replacePromotedPiece(String pieceType) {
		if(promote == null) {
			throw new IllegalStateException("Não há peça para ser promovida");
		}
		if(!pieceType.equals("B") && !pieceType.equals("N") && !pieceType.equals("R") && !pieceType.equals("Q")) {
			throw new InvalidParameterException(pieceType+" não está disponivel para promoção!");
		}
		
		Position position = promote.getChessPosition().toPosition();
		Piece p = board.removePiece(position);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPromotePiece(pieceType, promote.getColor());
		board.placePiece(newPiece, position);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPromotePiece(String newPiece, Color color) {
		if(newPiece.equals("B")) return new Bishop(board, color);
		if(newPiece.equals("N")) return new Knight(board, color);
		if(newPiece.equals("Q")) return new Queen(board, color);
		if(newPiece.equals("B")) return new Bishop(board, color);
		return new Tower(board, color);
	}
	
	private Color opponent(Color color) {
		return (color == Color.BRANCA) ? Color.PRETA : Color.BRANCA;
	}

	private ChessPiece king(Color color) {
		List<ChessPiece> pieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : pieces) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Não há rei da cor " + color + " no tabuleiro");
	}

	private void initialSetup() throws ChessException {
		placeNewPiece('a', 1, new Tower(board, Color.PRETA));
		// placeNewPiece('b', 1,new Knight(board ,Color.PRETA));
		// placeNewPiece('c', 1, new Bishop(board, Color.PRETA));
		placeNewPiece('d', 1, new Queen(board, Color.PRETA));
		placeNewPiece('e', 1, new King(board, Color.PRETA, this));
		placeNewPiece('a', 2, new Pawn(board, Color.PRETA, this));
		placeNewPiece('b', 2, new Pawn(board, Color.PRETA, this));
		placeNewPiece('c', 2, new Pawn(board, Color.PRETA, this));
		// placeNewPiece('d', 2,new Pawn(board ,Color.PRETA,this));
		placeNewPiece('e', 2, new Pawn(board, Color.PRETA, this));
		placeNewPiece('f', 2, new Pawn(board, Color.PRETA, this));
		placeNewPiece('g', 2, new Pawn(board, Color.PRETA, this));
		placeNewPiece('h', 2, new Pawn(board, Color.PRETA, this));

		placeNewPiece('a', 8, new Tower(board, Color.BRANCA));
		placeNewPiece('b', 8, new Knight(board, Color.BRANCA));
		placeNewPiece('c', 8, new Bishop(board, Color.BRANCA));
		placeNewPiece('d', 8, new Queen(board, Color.BRANCA));
		placeNewPiece('e', 8, new King(board, Color.BRANCA, this));
		// placeNewPiece('a', 7,new Rook(board ,Color.BRANCA));
		placeNewPiece('b', 7, new Pawn(board, Color.BRANCA, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BRANCA, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BRANCA, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BRANCA, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BRANCA, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BRANCA, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BRANCA, this));
	}
}
