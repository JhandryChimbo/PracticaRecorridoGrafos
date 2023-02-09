/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.grafo;

import controlador.grafo.exception.VerticeOfSizeException;

/**
 *
 * @author LENOVO
 * @param <E>
 */
public class GrafoNoDirigidoEtiquetado<E> extends GrafoDirigidoEtiquetado<E> {

    public GrafoNoDirigidoEtiquetado(Integer numVertices, Class clazz) {
        super(numVertices, clazz);
    }

    /**
     *
     * @param o Origen
     * @param d Destino
     * @param peso La longitud de las aristas del grafo
     * @throws Exception
     */
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
