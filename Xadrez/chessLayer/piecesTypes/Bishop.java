package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class Bishop extends ChessPiece {
	public Bishop(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "B";
	}
	
	@Override
	public boolean[][] possibleMoves(){
		
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		
		//sudeste
		p.setPositionValues(position.getRow() -1, position.getColumn()+1);
		while(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setPositionValues(p.getRow() - 1,p.getColumn() +1);
		}
		if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()]=true;
		}
		
		//nordeste
		p.setPositionValues(position.getRow()+1, position.getColumn()+1);
		while(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setPositionValues(p.getRow() +1,p.getColumn() +1);
		}
		if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()]=true;
		}
		
		//noroeste
		p.setPositionValues(position.getRow()+1, position.getColumn()-1);
		while(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setPositionValues(p.getRow() +1,p.getColumn() -1);
		}
		if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()]=true;
		}
		
		//sudoeste
		p.setPositionValues(position.getRow() -1, position.getColumn()-1);
		while(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setPositionValues(p.getRow() -1,p.getColumn() -1);
		}
		if(getBoard().PositionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()]=true;
		}
		
		return mat;
	}
}
