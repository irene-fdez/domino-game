package juegodomino;

import java.util.Collections;
import java.util.Stack;

/**
 * Monton de fichas para robar y repartir al inicio del juego
 *
 * @author Rubén Vilas Martinez
 * @author Irene Fernández Mariño
 */
public final class Monton {

    private final static int FICHAS_JUGADOR = 7;
    private final Stack<Ficha> fichas;

    /**
     * Crea un montón con todas las fichas de dominó posibles.
     */
    public Monton() {
        this.fichas = new Stack<>();

        // Generar todas las combinaciones de fichas posibles
        for (int i = 0; i < FICHAS_JUGADOR; ++i) {
            for (int j = i; j < FICHAS_JUGADOR; ++j) {
                fichas.add(new Ficha(i, j));
            }
        }
        
        // Hacer aleatorio el orden de las fichas en el montón
        Collections.shuffle(fichas);

    }

    /**
     * reparte 7 fichas al jugador referenciado.
     *
     * @param j jugador a recibir fichas
     */
    public void repartirFichas(Jugador j) {
        while (j.getFichasMano().size() < FICHAS_JUGADOR) {
            j.getFichasMano().add(fichas.pop());
        }

    }

    /**
     * Saca la ficha que está en la cima del montón.
     *
     * @return La ficha que se ha sacado.
     */
    public Ficha sacarFicha() {
        if (getNumFichas() == 0) {
            System.out.println("El monton está vacío");
            return null;
        }
        return fichas.pop();
    }

    /**
     * Obtiene el número de fichas que hay en el montón.
     *
     * @return El número de fichas en el montón.
     */
    public int getNumFichas() {
        return fichas.size();
    }
}
