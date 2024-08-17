package chessLayer.piecesTypes;

import boardLayer.Board;
import boardLayer.Position;
import chessLayer.ChessPiece;
import chessLayer.Color;

public class Tower extends ChessPiece {
	public Tower(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "T";
	}
	
	@Override
	public boolean[][] possibleMoves(){
		
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position aux = new Position(0,0);
		
		//acima
		aux.setPositionValues(position.getRow() -1, position.getColumn());
		while(getBoard().PositionExists(aux) && !getBoard().ThereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() - 1);
		}
		if(getBoard().PositionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()]=true;
		}
		
		//esquerda
		aux.setPositionValues(position.getRow(), position.getColumn()-1);
		while(getBoard().PositionExists(aux) && !getBoard().ThereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() - 1);
		}
		if(getBoard().PositionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()]=true;
		}
		
		//direita
		aux.setPositionValues(position.getRow(), position.getColumn()+1);
		while(getBoard().PositionExists(aux) && !getBoard().ThereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setColumn(aux.getColumn() + 1);
		}
		if(getBoard().PositionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()]=true;
		}
		
		
		//baixo
		aux.setPositionValues(position.getRow() +1, position.getColumn());
		while(getBoard().PositionExists(aux) && !getBoard().ThereIsAPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()] = true;
			aux.setRow(aux.getRow() + 1);
		}
		if(getBoard().PositionExists(aux) && isThereOpponentPiece(aux)) {
			mat[aux.getRow()][aux.getColumn()]=true;
		}
		
		return mat;
	}
}
