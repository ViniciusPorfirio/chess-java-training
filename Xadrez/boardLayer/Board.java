package boardLayer;

public class Board {
	private int row;
	private int colunm;
	private Piece[][] pieces;
	
	public Board(int row, int colunm) {
		this.row = row;
		this.colunm = colunm;
		pieces = new Piece[row][colunm];
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColunm() {
		return colunm;
	}

	public void setColunm(int colunm) {
		this.colunm = colunm;
	}
	
	
}
