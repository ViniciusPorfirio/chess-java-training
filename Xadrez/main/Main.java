package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chessLayer.ChessException;
import chessLayer.ChessMatch;
import chessLayer.ChessPiece;
import chessLayer.ChessPosition;

public class Main {

	public static void main(String[] args) throws ChessException {
		ChessMatch chessmatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);

		List<ChessPiece> capturedPieces = new ArrayList<>();

		while (!chessmatch.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessmatch, capturedPieces);
				System.out.println("");
				System.out.println("Casa de origem: ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = chessmatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessmatch.getPieces(), possibleMoves);
				System.out.println();
				System.out.println("Casa de alvo: ");
				ChessPosition target = UI.readChessPosition(sc);

				ChessPiece capturedPiece = chessmatch.performChessMove(source, target);

				if (capturedPiece != null) {
					capturedPieces.add(capturedPiece);
				}
				
				if (chessmatch.getPromote() != null) {
					System.out.println("Escolha para qual peça deseja promover o peão:");
					System.out.println("P(Bispo)/N(Cavalo)/T(Torre)/Q(Rainha):");
					String pieceType = sc.nextLine();
					chessmatch.replacePromotedPiece(pieceType);
				}

			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessmatch, capturedPieces);
	}

}
