package main;

import java.util.Scanner;

import chessLayer.*;

public class Main {

	public static void main(String[] args) throws ChessException {
		ChessMatch chessmatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
		UI.printBoard(chessmatch.getPieces());
		System.out.println("");
		System.out.println("Source: ");
		ChessPosition source = UI.readChessPosition(sc);
		System.out.println();
		System.out.println("Target: ");
		ChessPosition target = UI.readChessPosition(sc);

		ChessPiece capturedPiece = chessmatch.performChessMove(source, target);
		System.out.println("\033[H\33[2J");
		System.out.flush();
		}
	}

}
