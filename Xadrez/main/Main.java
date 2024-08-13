package main;

import chessLayer.ChessException;
import chessLayer.ChessMatch;

public class Main {

	public static void main(String[] args) throws ChessException {
		ChessMatch chessmatch = new ChessMatch();
		
		UI.printBoard(chessmatch.getPieces());

	}

}
