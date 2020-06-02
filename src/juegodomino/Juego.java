package juegodomino;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase del juego dominó y de sus reglas
 *
 * @author Rubén Vilas Martinez
 * @author Irene Fernández Mariño
 */
public class Juego {

    private static final Scanner TECLADO = new Scanner(System.in);
    private static final int CIERRE = 7;

    private static Mesa mesa;
    private static int[] puntuaciones;

    /**
     * Controla la ejecución de la partida
     */
    public static void iniciarJuego() {

        int numJug = 0;
        do {
            try {
                System.out.print("Indica el número de jugadores [2-4]: ");
                numJug = Integer.parseInt(TECLADO.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Error de formato.");
            }
            if (numJug < 2 || numJug > 4) {
                System.err.println("Introduzca un numero de jugadores valido\n");
            }

        } while (numJug < 2 || numJug > 4);

        mesa = new Mesa(numJug);
        mesa.añadirJugadores();
        puntuaciones = new int[numJug];
        int i = 0;
        ArrayList<Ficha> fichasJugables;
        Jugador j;
        do {
            int op = -1;
            j = mesa.getJugadores()[i];
            System.out.println("\n\t\t* * * MESA * * *\n");
            System.out.println(mesa.getFichasTablero().toString());
            System.out.println("\nNº fichas en montón: " + mesa.getMonton().getNumFichas());
            System.out.println("\n\n\tTURNO DE " + j.getNombre() + "\n");
            System.out.println("Tiene " + j.getNumFichasMano() + " fichas: \n" + j.getFichasMano());

            fichasJugables = mesa.obtenerFichasJugables(j);

            System.out.println("\nPuede jugar con: \n" + fichasJugables.toString() + "\n");

            char turno = decidirTurno(fichasJugables.size() > 0);
            if (turno == 'r' && !(mesa.getMonton().getNumFichas() == 0)) {
                if (mesa.robarFicha(j)) {
                    Ficha robada = j.getFichasMano().get(j.getFichasMano().size() - 1);
                    if (robada != null) {
                        System.out.println("\nHas robado la ficha " + robada);
                    } else {
                        System.out.println("No se pueden robar fichas.");
                    }
                }
                fichasJugables = mesa.obtenerFichasJugables(j);
                if (!fichasJugables.isEmpty()) {
                    System.out.println("\n\t\t* * * MESA * * *\n");
                    System.out.println(mesa.getFichasTablero().toString());
                    System.out.println("\nTiene " + j.getNumFichasMano() + " fichas: \n" + j.getFichasMano());
                    do {
                        try {

                            System.out.println("\nAhora puede jugar con: \n" + fichasJugables.toString());
                            System.out.println("\nQue quieres hacer? \n   [1] Jugar \n   [2] Pasar Turno");
                            op = Integer.parseInt(TECLADO.nextLine());
                        } catch (NumberFormatException e) {
                            System.err.println("Error de formato.");
                        }
                    } while (op < 1 || op > 2);

                } else {
                    try {
                        do {
                            System.out.println("\nSolo puedes pasar turno, pulsa cualquier tecla...");
                            TECLADO.nextLine();// para que no pase turno automaticamente
                            op = 2;
                        }while(op<0 || op>2);
                    
                    }catch(NumberFormatException ex){
                        System.err.println("Error de formato : "+ex.getMessage());
                    }

                }

            }
            if (turno != 'r' || op == 1) {
                mesa.ponerFicha(elegirFicha(fichasJugables), j);
            }

            ++i;
            if (i > numJug - 1) {
                i = 0;
            }
        } while (!esCierre() && !esDomino(j));

        if (esDomino(j)) {
            System.out.println("\n\n\n\t\t\t\t* * * FIN DE LA PARTIDA * * *\n MESA:\n\n" + mesa.getFichasTablero().toString());
            for (i = 0; i < numJug; i++) {
                System.out.println("\n" + mesa.getJugadores()[i].getNombre()
                        + " se ha quedado con " + mesa.getJugadores()[i].getNumFichasMano()
                        + " fichas: \n" + mesa.getJugadores()[i].getFichasMano());
            }
            System.out.println("\n " + j.getNombre() + " se ha quedado sin fichas.\n\n   ¡¡ENHORABUENA " + j.getNombre() + ", HAS GANADO!!\n\n");

        } else if (esCierre()) {

            System.out.println("\n\n\n\t\t* * * FIN DE LA PARTIDA * * *\n MESA:\n\n" + mesa.getFichasTablero().toString());
            for (i = 0; i < numJug; i++) {
                puntuaciones[i] = puntuacion(mesa.getJugadores()[i]);

                System.out.println("\n" + mesa.getJugadores()[i].getNombre()
                        + " se ha quedado con " + mesa.getJugadores()[i].getNumFichasMano()
                        + " fichas: \n" + mesa.getJugadores()[i].getFichasMano());
                System.out.println("PUNTUACIÓN: " + puntuaciones[i]);
            }

            System.out.print("GANADOR: ");
            if (ganadorCierre() >= 0) {
                System.out.print(mesa.getJugadores()[ganadorCierre()].getNombre());
                System.out.println(" Por tener la menor puntuación " + puntuaciones[ganadorCierre()]);

            } else {
                System.out.print(mesa.getJugadores()[0].getNombre());
                System.out.println(" Debido a empate de puntuaciones.");

            }
            System.out.println("¡¡ENHORABUENA!!\n ");
        }

    }

    /**
     * Devuelve la mesa
     *
     * @return devuelve la mesa
     */
    public static Mesa getMesa() {
        return mesa;
    }

    /**
     * Decide la acción que el jugador hará en su turno
     *
     * @param puedePoner Comprueba que tiene fichas para jugar
     * @return p si pone ficha, r si pasa turno y roba (si es posible)
     */
    private static char decidirTurno(boolean puedePoner) {
        int i = 1;
        int decision;

        System.out.println("\nSelecciona una opción de juego:");

        if (puedePoner) {
            System.out.println("   [" + i + "] Poner una ficha");
            ++i;
        } else {
            System.out.println("No puede poner fichas.");
        }
        if (mesa.getMonton().getNumFichas() == 0) {
            System.out.println("   [" + i + "] Pasar turno");
        } else {
            System.out.println("   [" + i + "] Robar ficha");
        }

        do {
            try {
                decision = Integer.parseInt(TECLADO.nextLine());
            } catch (NumberFormatException exc) {
                decision = Integer.MIN_VALUE;
            }
        } while (decision < 1 || decision > i);

        return decision == 1 && puedePoner ? 'p' : 'r';
    }

    /**
     * Elije una ficha para jugar
     *
     * @param fichasJugables fichas que puede jugar el jugador
     * @return Ficha seleccionada
     */
    private static Ficha elegirFicha(ArrayList<Ficha> fichasJugables) {
        int i = 0;
        do {
            try {
                System.out.println("\nIntroduzca el numero de ficha a poner [1-" + (fichasJugables.size()) + "]:");
                i = Integer.parseInt(TECLADO.nextLine()) - 1;
            } catch (NumberFormatException exc) {
                i = Integer.MIN_VALUE;
            }
        } while (i < 0 || i > (fichasJugables.size()) - 1);

        return fichasJugables.get(i);
    }

    /**
     * Comprueba si el juego finaliza por cierre.
     *
     * @return true si cierre, false si no
     */
    private static boolean esCierre() {
        boolean toret = false;
        if (!mesa.getFichasTablero().isEmpty()) {
            if ((mesa.getPrimera().getNumero1() == mesa.getUltima().getNumero2())
                    && (mesa.obtenerExtremosJugados()[mesa.getPrimera().getNumero1()] >= CIERRE)) {
                toret = true;
            }
            if ((mesa.obtenerExtremosJugados()[mesa.getPrimera().getNumero1()] >= CIERRE)
                    && (mesa.obtenerExtremosJugados()[mesa.getUltima().getNumero2()] >= CIERRE)) {
                toret = true;
            }
        }
//        for (int i = 0; i < CIERRE; i++) { // ver cuantas veces se juega cada numero
//            System.out.println(i + " jugada " + mesa.obtenerExtremosJugados()[i] + " veces");
//        }
        return toret;

    }

    /**
     * Comprueba si el juego finaliza por dominó
     *
     * @param j Jugador al cual se comprueba si ha acabado sus fichas.
     * @return true si el jugador acaba sus fichas, false si no
     */
    private static boolean esDomino(Jugador j) {
        return j.getFichasMano().isEmpty();
    }

    /**
     * Devuelve la puntuación del jugador (igual a la suma de todos los nºs de
     * las fichas)
     *
     * @param j jugador a comprobar
     * @return puntuación del jugador
     */
    private static int puntuacion(Jugador j) {
        int toret = 0;
        int n1 = 0;
        int n2 = 0;

        for (Ficha f : j.getFichasMano()) {
            n1 = f.getNumero1();
            n2 = f.getNumero2();
            toret += n1;
            toret += n2;
        }
        return toret;
    }

    /**
     * Comprueba quien es el ganador en caso de cierre
     *
     * @return posición del jugador ganador. Si hay empate, devuelve -1
     */
    private static int ganadorCierre() {
        int v1 = puntuaciones[0];
        int pos1 = 0;
        int v2 = puntuaciones[1];
        int pos2 = 1;
        int toret;
        int i = 2;
        while (i < mesa.getJugadores().length) {
            if (puntuacion(mesa.getJugadores()[i]) < v1) {
                v1 = puntuacion(mesa.getJugadores()[i]);
                pos1 = i;
            } else if (puntuacion(mesa.getJugadores()[i]) < v2) {
                v2 = puntuacion(mesa.getJugadores()[i]);
                pos2 = i;

            }
            i++;
        }
        if (v1 < v2) {
            toret = pos1;
        } else if (v2 < v1) {
            toret = pos2;
        } else {
            toret = -1;
        }
        return toret;
    }
}
