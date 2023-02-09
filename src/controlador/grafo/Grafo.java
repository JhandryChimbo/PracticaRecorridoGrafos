/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.grafo;

import controlador.Listas.ListaEnlazada;

/**
 *
 * @author DEEPIN
 */
public abstract class Grafo {

    //vertices -----> aristas

    public abstract Integer numVertices();

    public abstract Integer numAristas();

    public abstract Boolean existeArista(Integer o, Integer d) throws Exception;

    public abstract Double pesoArista(Integer o, Integer d);

    public abstract void insertarArista(Integer o, Integer d) throws Exception;

    public abstract void insertarArista(Integer o, Integer d, Double peso) throws Exception;

    public abstract ListaEnlazada<Adyacencia> adyacentes(Integer v);

    @Override
    public String toString() {
        StringBuffer grafo = new StringBuffer("");
        try {
            for (int i = 1; i < numVertices(); i++) {
                grafo.append("vertice " + String.valueOf(i));
                ListaEnlazada<Adyacencia> lista = adyacentes(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adyacencia a = lista.obtener(j);
                    if (a.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                        grafo.append("-- Vertice Destino " + a.getDestino() + "-- SP");
                    } else {
                        grafo.append("-- Vertice Destino " + a.getDestino() + "-- Peso" + a.getPeso());
                    }
                }
                grafo.append("\n");

            }
        } catch (Exception e) {
            grafo.append(e.getMessage());
        }
        return grafo.toString();
    }

    public ListaEnlazada caminoMinimo(Integer origen, Integer destino) throws Exception {
        ListaEnlazada camino = new ListaEnlazada();
        if (estaConectado()) {
            ListaEnlazada pesos = new ListaEnlazada();
            Boolean finalizar = false;
            Integer inicial = origen;
            camino.insertar(inicial);
            while (!finalizar) {
                ListaEnlazada<Adyacencia> adycencias = adyacentes(inicial);
                Double peso = 10000000.0;
                int T = -1;
                for (int i = 0; i < adycencias.getSize(); i++) {
                    Adyacencia ad = adycencias.obtener(i);
                    if(!estaEnCamino(camino, destino)) {
                        Double pesoArista = ad.getPeso();
                        if(destino.intValue() == ad.getDestino().intValue()) {
                            T = ad.getDestino();
                            peso = pesoArista;
                            break;
                        } else if(pesoArista < peso) {
                            T = ad.getDestino();
                            peso = pesoArista;
                        }
                    }
                }
                pesos.insertar(peso);
                camino.insertar(T);
                inicial = T;
                if(destino.intValue() == inicial.intValue()) {
                    finalizar = true;
                }
            }
        } else {
            throw new Exception("Gafo no conectado");
        }
        return camino;
    }
    
        public Boolean estaConectado() {
        Boolean band = true;
        for (int i = 1; i <= numVertices(); i++) {
            ListaEnlazada<Adyacencia> lista = adyacentes(i);
            if (lista.estaVacia() || lista.getSize() == 0) {
                band = false;
                break;
            }
        }
        return band;
    }
    
    public Boolean estaEnCamino(ListaEnlazada<Integer> lista, Integer vertice) throws Exception {
        Boolean band = false;
        for(int i = 0; i < lista.getSize(); i++) {
            if(lista.obtener(i).intValue() == vertice.intValue()) {
                band = true;
                break;
            }
        }
        return band;        
    }

}
