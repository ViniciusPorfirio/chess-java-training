package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class Knight extends ChessPiece{
	
	public Knight(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "N";
	}
	
	@Override
	public boolean[][] possibleMoves(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//sudeste baixo
		p.setPositionValues(position.getRow() +2, position.getColumn()+1);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudeste direita
		p.setPositionValues(position.getRow() +1, position.getColumn()+2);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudoeste baixo
		p.setPositionValues(position.getRow() +2, position.getColumn()-1);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudoeste esquerda
		p.setPositionValues(position.getRow() +1, position.getColumn()-2);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//noroeste esquerda
		p.setPositionValues(position.getRow() -1, position.getColumn()-2);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//noroeste cima
		p.setPositionValues(position.getRow() -2, position.getColumn()-1);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//nordeste cima
		p.setPositionValues(position.getRow() -2, position.getColumn()+1);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//nordeste direita 
		p.setPositionValues(position.getRow() -1, position.getColumn()+2);
		if(getBoard().PositionExists(p) && !getBoard().ThereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
}
