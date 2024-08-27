package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessMatch;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessmatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessmatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] possibleMoves(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		if(getColor() == Color.PRETA) {
			//Em frente
			p.setPositionValues(position.getRow()-1,position.getColumn() );
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//Em baixo
			p.setPositionValues(position.getRow()-2,position.getColumn() );
			Position auxPosition = new Position(p.getRow()-1,p.getColumn());
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p) && getBoard().PositionExists(auxPosition) && !getBoard().ThereIsAPiece(auxPosition) && getMoveCount()== 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//ataque sudeste
			p.setPositionValues(position.getRow()-1,position.getColumn() +1);
			if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//ataque suldoeste
			p.setPositionValues(position.getRow()-1,position.getColumn() -1);
			if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//En Passant - Peça Preta
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn()-1);
				if(getBoard().PositionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessmatch.getEnPassantVulnarable()) {
					mat[left.getRow()-1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn()+1);
				if(getBoard().PositionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessmatch.getEnPassantVulnarable()) {
					mat[right.getRow()-1][right.getColumn()] = true;
				}
			}
		}else if(getColor() == Color.BRANCA) {
			//Em baixo
			p.setPositionValues(position.getRow()+1,position.getColumn() );
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//Em frente
			p.setPositionValues(position.getRow()+2,position.getColumn() );
			Position auxPosition = new Position(p.getRow()+1,p.getColumn());
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p) && getBoard().PositionExists(auxPosition) && !getBoard().ThereIsAPiece(auxPosition) && getMoveCount()== 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//ataque nordeste
			p.setPositionValues(position.getRow()+1,position.getColumn() +1);
			if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//ataque noroeste
			p.setPositionValues(position.getRow()+1,position.getColumn() -1);
			if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//En Passant - Peça Branca
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn()-1);
				if(getBoard().PositionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessmatch.getEnPassantVulnarable()) {
					mat[left.getRow()+1][left.getColumn()] = true;
				}
				
				Position right = new Position(position.getRow(), position.getColumn()+1);
				if(getBoard().PositionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessmatch.getEnPassantVulnarable()) {
					mat[right.getRow()+1][right.getColumn()] = true;
				}
			}
		}
		return mat;
	}
}
