package juegodomino;

import java.util.ArrayList;

/**
 * Jugador del dominó
 *
 * @author Rubén Vilas Martinez
 * @author Irene Fernández Mariño
 */
public final class Jugador {

    private final String nombre;
    private final ArrayList<Ficha> fichasMano;

    /**
     * Inicializa un jugador, con un nombre y mesa determinados, sin fichas en
     * la mano.
     *
     * @param nombre El nombre del jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.fichasMano = new ArrayList<>(21); // No puede tener más de 21 fichas (28-7)
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene las fichas en la mano del jugador.
     *
     * @return Las fichas en la mano del jugador.
     */
    public ArrayList<Ficha> getFichasMano() {
        return fichasMano;
    }

    /**
     * Devuelve el número de fichas que el jugador tiene en la mano.
     *
     * @return El número de fichas en posesión del jugador.
     */
    public int getNumFichasMano() {
        return fichasMano.size();
    }

    @Override
    public String toString() {
        StringBuilder toret = new StringBuilder(getNombre());

        toret.append("\nFichas: ");
        for (Ficha f : fichasMano) {
            toret.append(f);
        }

        return toret.toString();
    }
}
