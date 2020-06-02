package juegodomino;

/**
 * Fichas de dominó
 *
 * @author Rubén Vilas Martinez
 * @author Irene Fernández Mariño
 */
public final class Ficha {

    private int numero1;
    private int numero2;

    private int code;

    /**
     * Crea una ficha con dos números.
     *
     * @param numero1 El primer número de la ficha, como {@code int}.
     * @param numero2 El segundo número de la ficha, como {@code int}.
     */
    public Ficha(int numero1, int numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
        code = -1;

    }

    /**
     * Devuelve el primer número de la ficha.
     *
     * @return El primer número de la ficha, como {@code int}.
     */
    public int getNumero1() {
        return numero1;
    }

    /**
     * Devuelve el segundo número de la ficha.
     *
     * @return El segundo número de la ficha, como {@code int}.
     */
    public int getNumero2() {
        return numero2;
    }

    /**
     * Transforma los nºs pasados por parametro en los nºs de la ficha
     *
     * @param n1 numero 1
     * @param n2 numero 2
     */
    public void getNumeros(int n1, int n2) {
        n1 = this.numero1;
        n2 = this.numero2;
    }

    /**
     * Comprueba que la ficha actual puede juntarse con otra ficha. Actualiza
     * {@code Int} Code de la ficha para saber que estilo de unión hay que
     * realizar
     *
     * @param f Ficha con la que debe unirse
     * @return True si puede unirse, false si no.
     */
    public boolean encajaConFicha(Ficha f) {
        boolean toret = false;

        if (this.equals(Juego.getMesa().getPrimera()) && this.equals(Juego.getMesa().getUltima())) { //SI HAY UNA FICHA EN LA MESA

            if (getNumero2() == f.getNumero1() && getNumero2() != f.getNumero2()) { //si dcha mesa == izq ficha != dcha ficha
                toret = true;
                f.setCode(0); //addLast
            }
            if (getNumero2() == f.getNumero2() && getNumero2() != f.getNumero1()) { //si dcha mesa == dcha ficha != izq ficha
                toret = true;
                f.setCode(1); // addLast & girar
            }
            if (getNumero1() == f.getNumero2() && getNumero1() != f.getNumero1()) { //si izq mesa == dcha ficha != izq ficha
                toret = true;
                f.setCode(2); //addFirst
            }
            if (getNumero1() == f.getNumero1() && getNumero1() != f.getNumero2()) { // si izq mesa == izqd ficha != dcha ficha
                toret = true;
                f.setCode(3); //addFirst & girar
            }
            if (getNumero1() == f.getNumero1() && getNumero1() == f.getNumero2()) { //si izq mesa == izq ficha == dcha ficha
                toret = true;
                f.setCode(4); //addFirst || addLast
            }
            if (getNumero2() == f.getNumero1() && getNumero2() == f.getNumero2()) { //si izq dcha == izq ficha == dcha ficha
                toret = true;
                f.setCode(5); //addFirst || addLast
            }
            if (getNumero1() == getNumero2()) { // si dcha mesa == izq mesa

                if (getNumero1() == f.getNumero1()) { //si dcha/izq mesa == izq ficha
                    toret = true;
                    f.setCode(6); // addLast || (addFirst & girar)
                }
                if (getNumero1() == f.getNumero2()) { //si dcha/izq mesa == dcha ficha
                    toret = true;
                    f.setCode(7); //addFirst || (addLast & girar)
                }
            }
        }

        if (this.equals(Juego.getMesa().getPrimera()) && !this.equals(Juego.getMesa().getUltima())) {//SI ENCAJA PRIMERA DE LA MESA

            if (getNumero1() == f.getNumero2() && getNumero1() != f.getNumero1()) { //si izq mesa == dcha ficha != izq ficha
                toret = true;
                f.setCode(2); //addFirst
            } else if (getNumero1() == f.getNumero1() && getNumero1() != f.getNumero2()) { // si izq mesa == izqd ficha != dcha ficha
                toret = true;
                f.setCode(3); //addFirst & girar
            } else if (getNumero1() == f.getNumero1() && getNumero1() == f.getNumero2()) { //si izq mesa == dcha ficha == izq ficha
                toret = true;
                f.setCode(4); //addFirst || addLast
            }
        }

        if (this.equals(Juego.getMesa().getUltima()) && !this.equals(Juego.getMesa().getPrimera())) { //SI ENCAJA ULTIMA DE LA MESA

            if (getNumero2() == f.getNumero1()) {
                toret = true;
                f.setCode(0); //addLast
            } else if (getNumero2() == f.getNumero2()) {
                toret = true;
                f.setCode(1); // addLast & girar
            } else if (getNumero2() == f.getNumero1() && getNumero2() == f.getNumero2()) {
                toret = true;
                f.setCode(5); //addFirst || addLast
            }
        }

        return toret;
    }

    /**
     * Devuelve el código de la ficha (comprueba el caso para unirla con otra
     * ficha)
     *
     * @return código de unión
     */
    public int getCode() {
        return code;
    }

    /**
     * Modifica el código de unión
     *
     * @param c nuevo código de unión
     */
    public void setCode(int c) {
        code = c;
    }

    /**
     * Gira la ficha actual
     */
    public void girarFicha() {
        int aux = numero1;
        numero1 = numero2;
        numero2 = aux;
    }

    /**
     * Representa la ficha en formato textual.
     *
     * @return La ficha como {@code String}, en formato
     * {@code "[ numero1 | numero2 ]"}.
     */
    @Override
    public String toString() {
        StringBuilder toret = new StringBuilder(" [");
        toret.append(getNumero1());
        toret.append("|");
        toret.append(getNumero2());
        toret.append("] ");
        return toret.toString();
    }
}
