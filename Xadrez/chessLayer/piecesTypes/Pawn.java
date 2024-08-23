package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class Pawn extends ChessPiece {
	public Pawn(Board board, Color color) {
		super(board, color);
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
			p.setPositionValues(position.getRow()-1,position.getColumn() );
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
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
		}else if(getColor() == Color.BRANCA) {
			
			p.setPositionValues(position.getRow()+1,position.getColumn() );
			if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
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
		}
		return mat;
	}
}
