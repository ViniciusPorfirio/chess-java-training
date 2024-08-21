package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class King extends ChessPiece{
	
	public King(Board board, Color color) {
		super(board, color);
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
	public boolean[][] possibleMoves(){
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0,0);
		
		//acima
		p.setPositionValues(position.getRow() -1, position.getColumn());
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//abaixo
		p.setPositionValues(position.getRow() +1, position.getColumn());
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//esquerda
		p.setPositionValues(position.getRow(), position.getColumn()-1);
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//direita
		p.setPositionValues(position.getRow(), position.getColumn()+1);
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//nordeste
		p.setPositionValues(position.getRow()+1, position.getColumn()+1);
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//noroeste
		p.setPositionValues(position.getRow()+1, position.getColumn()-1);
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudoeste
		p.setPositionValues(position.getRow()-1, position.getColumn()-1);
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//sudeste
		p.setPositionValues(position.getRow()-1, position.getColumn()+1);
		if(getBoard().PositionExists(p) && kingCanMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		return mat;
	}
}
