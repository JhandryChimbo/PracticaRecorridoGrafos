/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.pilas;

import controlador.Listas.ListaEnlazada;
import controlador.Listas.exception.ListaNullException;
import controlador.Listas.exception.PosicionNoEncontradaExcepcion;
import controlador.pilas.exception.PilaVaciaExcepcion;
import controlador.pilas.exception.TopeException;

/**
 *
 * @author ivangonzalez
 */
public class Pila<E> extends ListaEnlazada<E> {

    public Pila() {
    }

    private Integer tope;

    public Pila(Integer tope) {
        this.tope = tope;
    }

    public Boolean estaLleno() {
        return tope == getSize();
    }

    public void push(E dato) throws TopeException {
        if (!estaLleno()) {
            insertarCabecera(dato);
        } else {
            throw new TopeException();
        }
    }

    public E pop() throws PilaVaciaExcepcion, ListaNullException, PosicionNoEncontradaExcepcion {
        if (!estaVacia()) {
            E dato = eliminar(0);

            return dato;
        } else {
            throw new PilaVaciaExcepcion();
        }
    }

    public Integer getTope() {
        return tope;
    }

    public void setTope(Integer tope) {
        this.tope = tope;
    }

}
