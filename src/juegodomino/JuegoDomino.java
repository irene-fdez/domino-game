package juegodomino;

import java.util.Scanner;

/**
 * Main (contiene un mensaje de bienvenida y despedida, e inicializa el juego)
 *
 * @author Rubén Vilas Martinez
 * @author Irene Fernández Mariño
 */
public class JuegoDomino {

    static Scanner TECLADO = new Scanner(System.in);

    public static void main(String[] args) {

        int op = 0;

        System.out.println("* * * Juego Dominó * * *");
        do {
            try {
                System.out.println("\nSeleccione una opción:\n   [1] Para jugar.\n   [2] Para salir.");
                op = Integer.parseInt(TECLADO.nextLine());
                if (op != 1 && op != 2) {
                    System.out.println("\nPorfavor seleccione la opción 1 o 2.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error de formato.");
            }
        } while (op < 1 || op > 2);

        if (op == 1) {
            Juego.iniciarJuego();
        } else {
            System.out.println("\nEsperamos verle pronto!\n\n");
        }

    }
}
