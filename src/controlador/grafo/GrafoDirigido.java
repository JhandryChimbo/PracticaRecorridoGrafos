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
public class GrafoDirigido extends Grafo {

    private Integer numVertices;
    private Integer numAristas;
    private ListaEnlazada<Adyacencia> listaAdyacente[];

    public GrafoDirigido(Integer numVertices) {
        this.numVertices = numVertices;
        numAristas = 0;
        listaAdyacente = new ListaEnlazada[numVertices + 1];
        for (int i = 1; i <= this.numVertices; i++) {
            listaAdyacente[i] = new ListaEnlazada<>();
        }
    }

    @Override
    public Integer numVertices() {
        return numVertices;
    }

    @Override
    public Integer numAristas() {
        return numAristas;
    }

    @Override
    public Boolean existeArista(Integer o, Integer d) throws Exception {
        Boolean existe = false;
        if (o.intValue() <= numVertices && d.intValue() <= numVertices) {
            ListaEnlazada<Adyacencia> lista = listaAdyacente[o];
            for (int i = 0; i < lista.getSize(); i++) {
                Adyacencia a = lista.obtener(i);
                if (a.getDestino().intValue() == d.intValue()) {
                    existe = true;
                    break;
                }
            }
        } else // Todo exception VerticeOFsize{
        {
            throw new VerticeOfSizeException();
        }
        return existe;
    }

    @Override
    public Double pesoArista(Integer o, Integer d) {
        Double peso = Double.NaN; //NaN--->No es un valor numerico
        try {
            if (existeArista(o, d)) {
                ListaEnlazada<Adyacencia> adyacentes = listaAdyacente[o];
                for (int i = 0; i < adyacentes.getSize(); i++) {
                    Adyacencia a = adyacentes.obtener(i);
                    if (a.getDestino().intValue() == d.intValue()) {
                        peso = a.getPeso();
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return peso;
    }

    @Override
    public void insertarArista(Integer o, Integer d, Double peso) throws Exception {
        try {
            if (o.intValue() <= numVertices && d.intValue() <= numVertices) {
                if (!existeArista(o, d)) {
                    numAristas++;
                    listaAdyacente[o].insertar(new Adyacencia(d, peso));
                }
            } else {
                throw new VerticeOfSizeException();

            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }

    }

    @Override
    public void insertarArista(Integer o, Integer d) throws Exception {
        insertarArista(o, d, Double.NaN);
    }

    @Override
    public ListaEnlazada<Adyacencia> adyacentes(Integer v) {

        return listaAdyacente[v];
    }

    public Integer getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(Integer numVertices) {
        this.numVertices = numVertices;
    }

    public Integer getNumAristas() {
        return numAristas;
    }

    public void setNumAristas(Integer numAristas) {
        this.numAristas = numAristas;
    }

    public ListaEnlazada<Adyacencia>[] getListaAdyacente() {
        return listaAdyacente;
    }

    public void setListaAdyacente(ListaEnlazada<Adyacencia>[] listaAdyacente) {
        this.listaAdyacente = listaAdyacente;
    }

}
