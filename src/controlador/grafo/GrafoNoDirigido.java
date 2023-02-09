/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.grafo;

import controlador.Listas.ListaEnlazada;
import controlador.grafo.exception.VerticeOfSizeException;

/**
 *
 * @author DEEPIN
 */
public class GrafoNoDirigido extends GrafoDirigido {

    public GrafoNoDirigido(Integer numV) {
        super(numV);
    }

    @Override
    public void insertarArista(Integer o, Integer d, Double peso) throws Exception {

        if (o.intValue() <= getNumVertices() && d.intValue() <= getNumVertices()) {
            if (!existeArista(o, d)) {
                setNumAristas(getNumAristas() + 1);
                getListaAdyacente()[o].insertar(new Adyacencia(d, peso));
                getListaAdyacente()[d].insertar(new Adyacencia(o, peso));
            }
        } else {
            throw new VerticeOfSizeException();

        }
    }
}
