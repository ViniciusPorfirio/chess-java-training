package chessLayer;
import boardLayer.Position;
import chessLayer.ChessException;

public class ChessPosition {
	private char column;
	private int row;
	
	public ChessPosition(char column, int row) throws ChessException  {
		if(column <'a' || column >'h'|| row < 0|| row > 8) {
			throw new ChessException("Posição "+column+row+" extrapola os limites do tabuleiro");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) throws ChessException {
		return new ChessPosition((char)('a' - position.getColumn()), '8' - position.getRow());
	}
	
	@Override
	public String toString() {
		return ""+column + row;
	}
}
