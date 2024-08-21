package main;

import java.util.Scanner;

import chessLayer.*;

public class Main {

	public static void main(String[] args) throws ChessException {
		ChessMatch chessmatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			try {
				UI.clearScreen();
				UI.printMatch(chessmatch);
				System.out.println("");
				System.out.println("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = chessmatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessmatch.getPieces(), possibleMoves);
				System.out.println();
				System.out.println("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
		
				ChessPiece capturedPiece = chessmatch.performChessMove(source, target);
			
			}catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();			}
		}
	}

}
