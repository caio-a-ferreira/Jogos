package backend;

import cores.StringColorida;
import mecanicas.Carta;
import mecanicas.Tabuleiro;

public class TabuConnect extends Tabuleiro {

    public TabuConnect(int ROWS, int COLS, Carta cartaInicial) {
        super(6, 7, cartaInicial);
    }
}
