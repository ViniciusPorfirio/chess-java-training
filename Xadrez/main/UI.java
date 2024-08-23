package main;

import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import chessLayer.ChessException;
import chessLayer.ChessMatch;
import chessLayer.ChessPiece;
import chessLayer.ChessPosition;
import chessLayer.Color;

public class UI {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static ChessPosition readChessPosition (Scanner sc) throws ChessException {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		}catch(RuntimeException e){
			throw new InputMismatchException("Erro ao ler o input");
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List <ChessPiece> capturedPieces) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(capturedPieces, chessMatch);
		if(!chessMatch.getCheckMate()) {
			System.out.println("Turno: "+ chessMatch.getTurn());
			System.out.println("Esperando jogador da cor: "+ chessMatch.getCurrentPlayer());
			if(chessMatch.getCheck() == true) {
				System.out.println("Seu rei esta em CHEQUE");
			}
		}else if(chessMatch.getCheckMate()){
			System.out.println("CHEQUE-MATE!");
			System.out.println("Jogador das peças "+chessMatch.getCurrentPlayer()+" ganhou!");
		}
	}
	
	public static void printBoard(ChessPiece[][] pieces) {
		for(int i=0 ; i<pieces.length; i++) {
			System.out.print((8 - i)+" ");
			for(int j=0; j<pieces.length;j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}
	
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for(int i=0 ; i<pieces.length; i++) {
			System.out.print((8 - i)+" ");
			for(int j=0; j<pieces.length;j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.print("  a b c d e f g h");
	}
	
	
	private static void printPiece(ChessPiece piece, boolean background) {
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if(piece == null) {
			System.out.print("-");
		}else{
			if(piece.getColor() == Color.BRANCA) {
				System.out.print(ANSI_WHITE+piece+ANSI_RESET);
			}else {
				System.out.print(ANSI_YELLOW+piece+ANSI_RESET);
			}
			
		}
			System.out.print(" ");
	}
	
	public static void printCapturedPieces(List <ChessPiece> captured, ChessMatch chessMatch){
		System.out.println("Peças capturadas: ");
		if(chessMatch.getCurrentPlayer() == Color.BRANCA) {
			List <ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.PRETA).collect(Collectors.toList());
			System.out.println(ANSI_YELLOW);
			System.out.println(black);
			System.out.println(ANSI_RESET);
		}else if(chessMatch.getCurrentPlayer() == Color.PRETA) {
			List <ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.BRANCA).collect(Collectors.toList());
			System.out.println(ANSI_WHITE);
			System.out.println(white);
			System.out.println(ANSI_RESET);
		}
	}
	
	public static void clearScreen() {
		System.out.println("\033[H\33[2J");
		System.out.flush();
	}
}
