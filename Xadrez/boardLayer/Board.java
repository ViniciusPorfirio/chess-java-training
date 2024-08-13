package boardLayer;

public class Board {
	protected int rows;
	protected int columns;
	private Piece[][] pieces;
	
	public Board(int row, int column) {
		if(row < 1 || column < 1) {
			throw new BoardException("Erro ao criar tabulerio, o numero de linhas e de colunas devem ser maior do que 0");
		}
		this.rows = row;
		this.columns = column;
		pieces = new Piece[row][column];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece (int row, int column) {
		return pieces[row][column];
	}
	
	public Piece piece (Position position) {
		if(!positionExists(position.getRow(), position.getColumn())) {
			throw new BoardException("Posição fora dos limites do tabulerio");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece,Position position) {
		if(ThereIsAPiece(position)) {
			throw new BoardException("Posição já esta ocupada: "+position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean PositionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean ThereIsAPiece(Position position) {
		if(!PositionExists(position)) {
			throw new BoardException("Posição fora dos limites do tabulerio");
		}
		return piece(position) != null;
	}
	
	public Piece removePiece(Position position) {
		if(!PositionExists(position)) {
			throw new BoardException("Posição inexistente");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
}
