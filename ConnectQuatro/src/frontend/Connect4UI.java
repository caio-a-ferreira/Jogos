package frontend;

import backend.Connect4Game;
import backend.TabuConnect;
import console.Console;
import console.Tecla;
import java.io.IOException;
import java.util.Scanner;
import cores.StringColorida;

public class Connect4UI{
    private Connect4Game game;
    private Scanner scanner;
    

    public Connect4UI() {
        game = new Connect4Game();
        scanner = new Scanner(System.in);
    }

    public void start() {
        Tecla atual;
        while (true) {
            printMenu();
            atual = Console.getTecla();
            try{
                Console.limpaTela();
                if(atual == Tecla.NUM_1) {
                    newGame();

                }else if (atual == Tecla.NUM_2){
                    loadGame();

                }else if (atual == Tecla.NUM_3){
                    Console.println("Saindo do jogo...");
                    Console.saiDoPrograma();

                }else{
                    Console.println("Opção inválida. Tente novamente.");
                }

                }catch(Exception e){
                    Console.println("Por favor insira um número e tente novamente:)");
                }

           
        }
    }
    public void pause() {
        Tecla atual;
        while (true) {
            printPause();
            atual = Console.getTecla();
            try{
                Console.limpaTela();
                if(atual == Tecla.NUM_1) {
                newGame();
                
                }else if (atual == Tecla.NUM_2){
                    loadGame();
                }else if (atual == Tecla.NUM_3){
                    saveGame();
                }else if (atual == Tecla.NUM_4){
                    playGame();
                }else if (atual == Tecla.NUM_5){
                    start();
                }else if (atual == Tecla.NUM_6){
                    Console.println("Saindo do jogo...");
                    Console.saiDoPrograma();

                }else{
                    Console.println("Opção inválida. Tente novamente.");
                }
            }catch(Exception e){
                Console.println("Por favor insira um número e tente novamente:)");
            }

           
        }
    }
    
    private void printMenu() {
        Console.println("  __   _   _  _  _  _  __   __  __   _  _   ");
        Console.println(" / _| / _ \\ |  \\| ||  \\| || __| / __||   _| | || | ");
        Console.println("| |    | | | || . ` || . ` ||  |  | |      | |   | || | ");
        Console.println("| |_ | || || |\\  || |\\  || |_ | |_   | |   |_   _|");
        Console.println(" \\| \\/ || \\||| \\||| \\|  ||      ||   ");
        Console.println("1. Iniciar Novo Jogo");
        Console.println("2. Carregar Partida de um Arquivo");
        Console.println("3. Sair do Jogo");
        Console.println("Escolha uma opção: ");
    }
    
    private void printPause() {
        Console.println("Menu De Pause:");
        Console.println("1. Iniciar Novo Jogo");
        Console.println("2. Carregar Partida de um Arquivo");
        Console.println("3. Salvar Partida em um Arquivo");
        Console.println("4. Voltar para o Jogo");
        Console.println("5. Voltar ao Menu Principal");
        Console.println("6. Sair do Jogo");
        Console.println("Escolha uma opção: ");
    }




    private void newGame() {
        game = new Connect4Game();
        Console.println("Novo jogo iniciado!");
        playGame();
    }

    private void loadGame() {
        try {
            game = Connect4Game.loadGame();
            Console.println("Jogo carregado com sucesso!");
            playGame();
        } catch (IOException e) {
            Console.println("Erro ao carregar o jogo: " + e.getMessage());
        }
    }

     private void saveGame() {
        try {
            game.saveGame();
            Console.println("Jogo salvo com sucesso!!!");
        } catch (IOException e) {
            Console.println("Erro ao salvar o jogo: " + e);
        }
    }
     
    private void imprimeTabuleiro(){
        TabuConnect tabuleiro = game.getTabuleiro();
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                Console.print("|" + tabuleiro.getFundo(i, j));
            }
            Console.println("|");
            
        }
        Console.println("߅-+-+-+-+-+-+-˧");
        Console.println("|0|1|2|3|4|5|6|");
    }

    private void playGame() {
        Tecla atual;
        while (!game.isGameWon() || !game.isGameDraw()) {
            imprimeTabuleiro();
            Console.println("Jogador " + (game.getCurrentPlayer() + 1) + ", escolha uma coluna (0-6) ou pressione ESC para Pausar: ");
            atual = Console.getTecla();
            try{
                Console.limpaTela();
                if (atual == Tecla.ESC){ 
                    pause();
                }else{
                    boolean moveMade = false;
                    if(atual == Tecla.NUM_0)moveMade = game.makeMove(0);
                    else if (atual == Tecla.NUM_1) moveMade = game.makeMove(1);
                    else if (atual == Tecla.NUM_2) moveMade = game.makeMove(2);
                    else if (atual == Tecla.NUM_3) moveMade = game.makeMove(3);
                    else if (atual == Tecla.NUM_4) moveMade = game.makeMove(4);
                    else if (atual == Tecla.NUM_5) moveMade = game.makeMove(5);
                    else if (atual == Tecla.NUM_6) moveMade = game.makeMove(6);
                    if (!moveMade) {
                        Console.println("Movimento inválido, tente novamente.");
                    } else {
			saveGame();
		    }
                    if (game.isGameWon()) {
                        Console.println("Jogador " + (game.getCurrentPlayer() == 0 ? 2 : 1) + " venceu!");
                        break;
                    }
		    if (game.isGameDraw()) {
			Console.println("Empate!");
			break;
		    }
                }
                
            }catch(Exception e){
                Console.println("Por favor insira um valor válido e tente novamente:)");
            }
        }
    }

    public static void main(String[] args) {
        Connect4UI ui = new Connect4UI();
        ui.start();
    }
}