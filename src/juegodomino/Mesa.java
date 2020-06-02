package juegodomino;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Mesa del juego (Contiene las fichas jugadas y el montón para robar)
 *
 * @author Rubén Vilas Martinez
 * @author Irene Fernández Mariño
 */
public final class Mesa {

    private static final Scanner TECLADO = new Scanner(System.in);

    private final Jugador[] jugadores;
    private final LinkedList<Ficha> fichasTablero;
    private final Monton monton;
    private final int[] extremosJugados;

    /**
     * Crea una mesa con una serie de jugadores.
     *
     * @param numJugadores Los jugadores de la mesa.
     */
    public Mesa(int numJugadores) {
        this.jugadores = new Jugador[numJugadores];
        this.fichasTablero = new LinkedList<>();
        this.monton = new Monton();
        this.extremosJugados = new int[7];

    }

    /**
     * Obtiene los jugadores de la mesa.
     *
     * @return Los jugadores de la mesa.
     */
    public Jugador[] getJugadores() {
        return jugadores;
    }

    /**
     * Obtiene el montón de fichas de la mesa.
     *
     * @return El montón de fichas de la mesa.
     */
    public Monton getMonton() {
        return monton;
    }

    /**
     * Obtiene las fichas del tablero de la mesa.
     *
     * @return Las fichas del tablero de la mesa.
     */
    public LinkedList<Ficha> getFichasTablero() {
        return fichasTablero;
    }

    public Ficha getPrimera() {
        return fichasTablero.peekFirst();
    }

    public Ficha getUltima() {
        return fichasTablero.peekLast();
    }

    /**
     * Obtiene los extremos jugados en la mesa de dominó.
     *
     * @return Un array con información de cuántas veces se ha jugado cada
     * número en un extremo, donde el índice del array es cada número posible
     * (0-6).
     */
    public int[] obtenerExtremosJugados() {
        return extremosJugados;
    }

    /**
     * Añade jugadores hasta completar el array que los contiene en la mesa,
     * pidiendo datos por teclado.
     */
    public void añadirJugadores() {
        String nombre;
        int manoElegida = Integer.MIN_VALUE;
        Jugador aux;

        for (int i = 0; i < jugadores.length; ++i) {
            do {
                System.out.println("\nJugador " + (i + 1));
                System.out.println("Introduce tu nombre: ");
                nombre = TECLADO.nextLine();
            } while (nombre.length() == 0);
            jugadores[i] = new Jugador(nombre);
            monton.repartirFichas(jugadores[i]);//Reparte una vez creado el jugador.
        }

        do {
            try {
                int i = 0;
                System.out.println("\nIntroduzca qué jugador será mano [1-" + jugadores.length + "]: ");
                while (i < jugadores.length) {
                    System.out.println("[" + (i + 1) + "] " + jugadores[i].getNombre());
                    i++;
                }

                manoElegida = Integer.parseUnsignedInt(TECLADO.nextLine());
            } catch (NumberFormatException exc) {
                System.err.println("Error de formato. Solo números del 1 - " + jugadores.length);

            }
        } while (manoElegida < 1 || manoElegida > jugadores.length);

        aux = jugadores[0];
        jugadores[0] = jugadores[manoElegida - 1];
        jugadores[manoElegida - 1] = aux;

    }

    /**
     * Coloca una ficha en el tablero de juego de la mesa.
     *
     * @param j el jugador de turno
     * @param f La ficha a colocar.
     * @return Verdadero si se puede colocar, falso en caso contrario.
     */
    public boolean ponerFicha(Ficha f, Jugador j) {
        Ficha primeraFicha = fichasTablero.peekFirst();
        Ficha ultimaFicha = fichasTablero.peekLast();
        boolean toret;
        int code;

        if (ultimaFicha == null) { // No hay fichas en el tablero
            fichasTablero.addLast(f);
            toret = true;   // Si no hay fichas, la que metemos encajará siempre
            if (f.getNumero1() == f.getNumero2()) {
                ++extremosJugados[f.getNumero1()];
                j.getFichasMano().remove(f);

            } else {
                ++extremosJugados[f.getNumero1()];
                ++extremosJugados[f.getNumero2()];
                j.getFichasMano().remove(f);

            }
        } else { // Hay fichas en el tablero

            boolean encajaPrincipio = primeraFicha.encajaConFicha(f);
            boolean encajaFinal = ultimaFicha.encajaConFicha(f);
            toret = encajaPrincipio || encajaFinal;
            int op = 0;

            if (fichasTablero.size() == 1) { //Si es la unica ficha de la mesa
                code = f.getCode(); //Coge codigo de la ficha a colocar para saber el movimiento

                if (code == 0 || code == 5) {
                    insertarDerecha(f, j);
                }else{
                if (code == 1) {
                    f.girarFicha();
                    insertarDerecha(f, j);
                }else{
                if (code == 2 || code == 4) {
                    insertarIzquierda(f, j);
                }else{
                if (code == 3) {
                    f.girarFicha();
                    insertarIzquierda(f, j);
                }else{
                if (code == 6) {
                    do {
                        try {
                            System.out.println("Elige donde quieres colocarla:\n[1] Izquierda\n[2] Derecha");
                            op = Integer.parseInt(TECLADO.nextLine());
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato.");
                        }
                    } while (op < 1 || op > 2);

                    switch (op) {
                        case 1: { //codigo 3 
                            f.girarFicha();
                            insertarIzquierda(f, j);
                        }
                        break;
                        case 2: { // codigo 0 
                            insertarDerecha(f, j);
                        }
                        break;
                    }
                }else{
                if (code == 7) {
                    do {
                        try {
                            System.out.println("Elige donde quieres colocarla:\n[1] Izquierda\n[2] Derecha");
                            op = Integer.parseInt(TECLADO.nextLine());
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato.");
                        }
                    } while (op < 1 || op > 2);

                    switch (op) {
                        case 1: { //codigo 2 
                            insertarIzquierda(f, j);
                        }
                        break;
                        case 2: { // código 1
                            f.girarFicha();
                            insertarDerecha(f, j);
                        }
                        break;
                    }
                }}}}}}

            } else if (encajaPrincipio && encajaFinal) {
                do {
                    try {
                        System.out.println("Elige donde quieres colocarla:\n[1] Izquierda\n[2] Derecha");
                        op = Integer.parseInt(TECLADO.nextLine());
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato.");
                    }
                } while (op < 1 || op > 2);

                switch (op) {
                    case 1: {
                        primeraFicha.encajaConFicha(f);
                        code = f.getCode();

                        if (code == 2 || code == 4) {
                            insertarIzquierda(f, j);
                        }else{
                        if (code == 3) {
                            f.girarFicha();
                            insertarIzquierda(f, j);
                        }}
                    }
                    break;
                    case 2: {
                        ultimaFicha.encajaConFicha(f);
                        code = f.getCode();
                    }
                    if (code == 0 || code == 5) {
                        insertarDerecha(f, j);
                    }else{
                    if (code == 1) {
                        f.girarFicha();
                        insertarDerecha(f, j);
                    }}
                    break;
                }

            } else if (encajaPrincipio && !encajaFinal) {
                primeraFicha.encajaConFicha(f);
                code = f.getCode();

                if (code == 2 || code == 4) {
                    insertarIzquierda(f, j);
                }else{
                if (code == 3) {
                    f.girarFicha();
                    insertarIzquierda(f, j);
                }}

            } else if (encajaFinal && !encajaPrincipio) {
                ultimaFicha.encajaConFicha(f);
                code = f.getCode();

                if (code == 0 || code == 5) {
                    insertarDerecha(f, j);
                }else{
                if (code == 1) {
                    f.girarFicha();
                    insertarDerecha(f, j);
                }}
            }
        }
        return toret;
    }

    /**
     * Inserta una ficha por el lado izquierdo
     *
     * @param f ficha a insertar
     * @param j jugador que inserta la ficha
     */
    public void insertarIzquierda(Ficha f, Jugador j) {
        fichasTablero.addFirst(f);
        if (f.getNumero1()==f.getNumero2()) {
            ++extremosJugados[f.getNumero1()];
        } else {
            ++extremosJugados[f.getNumero1()];
            ++extremosJugados[f.getNumero2()];
        }

        j.getFichasMano().remove(f);
    }

    /**
     * Inserta una ficha por el lado derecho
     *
     * @param f ficha a insertar
     * @param j jugador que inserta la ficha
     */
    public void insertarDerecha(Ficha f, Jugador j) {
        fichasTablero.addLast(f);
        if (f.getNumero1()==f.getNumero2()) {
            ++extremosJugados[f.getNumero2()];
        } else {
            ++extremosJugados[f.getNumero2()];
            ++extremosJugados[f.getNumero1()];
        }
        j.getFichasMano().remove(f);
    }

    /**
     * Obtiene si una ficha se puede colocar en la mesa.
     *
     * @param j El jugador a comprobar.
     * @return Verdadero si se puede colocar, falso en caso contrario.
     */
    public ArrayList<Ficha> obtenerFichasJugables(Jugador j) {
        ArrayList<Ficha> toret = new ArrayList<>();
        Ficha primeraFicha = fichasTablero.peekFirst();
        Ficha ultimaFicha = fichasTablero.peekLast();

        if (ultimaFicha != null) {
            for (Ficha f : j.getFichasMano()) {
                if (primeraFicha.encajaConFicha(f) || ultimaFicha.encajaConFicha(f)) {
                    toret.add(f);
                }
            }
        } else {
            toret = j.getFichasMano();
        }

        return toret;
    }

    /**
     * Roba una ficha del montón, si todavía quedan fichas
     *
     * @param j Jugador que roba la ficha
     * @return True si puede robar, false si no
     */
    public boolean robarFicha(Jugador j) {
        Ficha robada = monton.sacarFicha();
        if (robada != null) {
            j.getFichasMano().add(robada);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Pone una ficha en la mesa, si es posible
     *
     * @param j Jugador que pone ficha
     * @param pos Posición de la ficha jugable en la mano del jugador (Se
     * selecciona del ArrayList de fichas jugables)
     * @return True si puede colocarla, false si no
     */
    public boolean ponerEnMesa(Jugador j, int pos) {
        Ficha f, f2;
        boolean toret = false;
        f = j.getFichasMano().get(pos);
        f2 = new Ficha(f.getNumero2(), f.getNumero1());

        if (ponerFicha(f, j)) {
            toret = true;
        } else if (ponerFicha(f2, j)) {
            toret = true;
        }
        return toret;
    }

}
