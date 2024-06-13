package backend;

import cores.StringColorida;
import mecanicas.Carta;

public class CartaConnect extends Carta {
    private StringColorida frente;

    public CartaConnect(StringColorida frente) {
        super(frente);
        this.frente = frente;
    }

 

    public StringColorida getFrente() {
        return frente;
    }
    
}
