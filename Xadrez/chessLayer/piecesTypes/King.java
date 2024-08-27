package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessMatch;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class King extends ChessPiece {

	private ChessMatch chessmatch;

	public King(Board board, Color color, ChessMatch chessmatch) {
		super(board, color);
		this.chessmatch = chessmatch;
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Tower && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	private boolean kingCanMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// acima
		p.setPositionValues(position.getRow() - 1, position.getColumn());
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// abaixo
		p.setPositionValues(position.getRow() + 1, position.getColumn());
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// esquerda
		p.setPositionValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// direita
		p.setPositionValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// nordeste
		p.setPositionValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// noroeste
		p.setPositionValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sudoeste
		p.setPositionValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// sudeste
		p.setPositionValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Movimento especial
		// Roque lado do rei
		if (getMoveCount() == 0 && !chessmatch.getCheck()) {
			Position posTower1 = new Position(position.getRow(), position.getColumn() + 3);
			if (testRookCastling(posTower1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			// Roque lado da rainha
			Position posTower2 = new Position(position.getRow(), position.getColumn() - 4);
			if (testRookCastling(posTower2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}

		return mat;
	}
}
