package backend;

import cores.Cor;
import cores.StringColorida;
import console.Console;
import java.io.*;

public class Connect4Game {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final String EMPTY = "O";
    private static final String VERMELHO = "⨁";
    private static final String AZUL = "⨂";

    private TabuConnect tabuleiro;
    private int currentPlayer;
    private boolean gameWon;
    private boolean gameDraw;

    public Connect4Game() {
        StringColorida frenteVazia = new StringColorida(EMPTY, Cor.VERDE);
        CartaConnect cartaInicial = new CartaConnect(frenteVazia);
        tabuleiro = new TabuConnect(ROWS, COLS, cartaInicial);
        currentPlayer = 0;
        gameWon = false;
	gameDraw = false;
    }

    public boolean makeMove(int col) {
        if (col < 0 || col >= COLS) {
            Console.println("Coluna inválida!");
            return false;
        }

        for (int row = ROWS - 1; row >= 0; row--) {
            String carta = tabuleiro.getFundo(row, col).toString().trim();
            if (carta.contains(EMPTY)) {
                StringColorida frenteNova = new StringColorida(
                    currentPlayer == 0 ? VERMELHO : AZUL,
                    currentPlayer == 0 ? Cor.VERMELHO : Cor.AZUL
                );
                CartaConnect novaCarta = new CartaConnect(frenteNova);
                tabuleiro.setFundo(row, col, novaCarta);
                checkWin(row, col);
		checkDraw();
                currentPlayer = (currentPlayer == 0) ? 1 : 0;
                return true;
            }
        }

        Console.println("Coluna cheia!");
        return false;
    }

    private void checkWin(int lastRow, int lastCol) { //Checa Vitoria
        String currentPlayerColor = currentPlayer == 0 ? VERMELHO : AZUL;
        if (checkDirection(lastRow, lastCol, 1, 0, currentPlayerColor) ||
            checkDirection(lastRow, lastCol, 0, 1, currentPlayerColor) ||
            checkDirection(lastRow, lastCol, 1, 1, currentPlayerColor) ||
            checkDirection(lastRow, lastCol, 1, -1, currentPlayerColor)) {
            gameWon = true;
        }
    }

    private void checkDraw() {
	for(int i = 0; i < ROWS; i++){
		for(int j = 0; j < COLS; j++){
			String carta = tabuleiro.getFundo(i, j).toString().trim();
			if (carta.contains(EMPTY)){
				return;
			}
		}
	}

	gameDraw = true;

}

    private boolean checkDirection(int row, int col, int rowDir, int colDir, String color) {
        int count = 1;
        count += countConsecutive(row, col, rowDir, colDir, color);
        count += countConsecutive(row, col, -rowDir, -colDir, color);
        return count >= 4;
    }

    private int countConsecutive(int row, int col, int rowDir, int colDir, String color) {
        int count = 0;
        int r = row + rowDir;
        int c = col + colDir;
        while (r >= 0 && r < ROWS && c >= 0 && c < COLS && tabuleiro.getFundo(r, c).toString().contains(color)) {
            count++;
            r += rowDir;
            c += colDir;
        }
        return count;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameDraw() {
	return gameDraw;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public TabuConnect getTabuleiro(){
        return tabuleiro;
    }

    public void saveGame() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.dat"))) {
            writer.write(currentPlayer + "\n");
            writer.write(gameWon + "\n");
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    String carta = tabuleiro.getFundo(row, col).toString().trim();
                    writer.write(carta + " ");
                }
                writer.write("\n");
            }
        }
    }

    public static Connect4Game loadGame() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("save.dat"))) {
            int currentPlayer = Integer.parseInt(reader.readLine());
            boolean gameWon = Boolean.parseBoolean(reader.readLine());
            Connect4Game game = new Connect4Game();
            game.currentPlayer = currentPlayer;
            game.gameWon = gameWon;
            for (int row = 0; row < ROWS; row++) {
                String[] cartas = reader.readLine().split(" ");
                for (int col = 0; col < COLS; col++) {
                    String carta = cartas[col];
                    Cor cor = carta.contains("⨁") ? Cor.VERMELHO : (carta.contains("⨂") ? Cor.AZUL : Cor.VERDE);
                    String texto = carta.contains("⨁") ? VERMELHO : (carta.contains("⨂") ? AZUL : EMPTY);
                    StringColorida frente = new StringColorida(texto, cor);
                    CartaConnect novaCarta = new CartaConnect(frente);
                    game.tabuleiro.setFundo(row, col, novaCarta);
                }
            }
            return game;
        }
    }
    
}