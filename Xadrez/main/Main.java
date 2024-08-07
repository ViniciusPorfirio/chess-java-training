package main;

import chessLayer.ChessMatch;

public class Main {

	public static void main(String[] args) {
		ChessMatch chessmatch = new ChessMatch();
		
		UI.printBoard(chessmatch.getPieces());

	}

}
