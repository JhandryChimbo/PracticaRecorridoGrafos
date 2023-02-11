/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.grafo;

import controlador.Listas.ListaEnlazada;
import controlador.cola.Cola;
import controlador.pilas.Pila;
import java.util.Objects;
import vista.FrmGrafo;

/**
 *
 * @author DEEPIN
 */
public abstract class Grafo {

    private static final Double Infinito = 10000000.0;

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
                Double peso = Infinito;
                int T = -1;
                for (int i = 0; i < adycencias.getSize(); i++) {
                    Adyacencia ad = adycencias.obtener(i);
                    if (!estaEnCamino(camino, destino)) {
                        Double pesoArista = ad.getPeso();
                        if (destino.intValue() == ad.getDestino().intValue()) {
                            T = ad.getDestino();
                            peso = pesoArista;
                            break;
                        } else if (pesoArista < peso) {
                            T = ad.getDestino();
                            peso = pesoArista;
                        }
                    }
                }
                pesos.insertar(peso);
                camino.insertar(T);
                inicial = T;
                if (destino.intValue() == inicial.intValue()) {
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
        for (int i = 0; i < lista.getSize(); i++) {
            if (lista.obtener(i).intValue() == vertice.intValue()) {
                band = true;
                break;
            }
        }
        return band;
    }

    //METODO DE FLOYD
    public Double[][] algoritmoFloyd() throws Exception {

        Integer vertices = this.numVertices();
        Integer camino[][] = new Integer[vertices][vertices];
        Double caminoAux[][] = new Double[vertices][vertices];
        Double pesos[][] = pesoGrafo(this);

        //Inicializando peso, camino y caminosAux
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                caminoAux[i][j] = pesos[i][j];
                camino[i][j] = -1;
            }
        }

        for (int i = 0; i < vertices; i++) {
            caminoAux[i][i] = 0.0;
        }

        for (int k = 0; k < vertices; k++) {
            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    if ((caminoAux[i][k] + caminoAux[k][j]) < caminoAux[i][j]) {
                        caminoAux[i][j] = caminoAux[i][k] + caminoAux[k][j];
                        camino[i][j] = k;
                    }
                }
            }
        }

        return caminoAux;
    }

    private Double[][] pesoGrafo(Grafo grafo) throws Exception {
        Integer vertices = grafo.numVertices();
        Double[][] adyacencia = new Double[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                Double peso = grafo.pesoArista(i + 1, j + 1);
                adyacencia[i][j] = (peso != 0) ? peso : Infinito;
            }
        }
        return adyacencia;
    }

    //METODO DIJKSTRA
    public ListaEnlazada algoritmoDijkstra(Integer origen) throws Exception {

        ListaEnlazada caminoDijkstra = new ListaEnlazada();

        origen -= 1;
        Integer s = origen;
        Integer vertices = this.numVertices();
        Integer ultimo[] = new Integer[vertices];//ultmo vertice antes de llegar al destino

        Boolean visitados[] = new Boolean[vertices];// vertices visitados

        Double minimos[] = new Double[vertices];
        Double pesos[][] = pesoGrafo(this);

        for (int i = 0; i < vertices; i++) {
            visitados[i] = false;
            minimos[i] = pesos[s][i];
            ultimo[i] = s;
        }

        visitados[s] = true;
        minimos[s] = 0.0;
        for (int i = 0; i < vertices; i++) {
            Integer v = minimo(vertices, visitados, minimos);
            visitados[v] = true;

            for (int w = 0; w < vertices; w++) {
                if (!visitados[w] && ((minimos[v] + pesos[v][w]) < minimos[w])) {
                    minimos[w] = minimos[v] + pesos[v][w];
                    ultimo[w] = v;
                }
            }
            caminoDijkstra.insertar(minimos[i]);
        }
        return caminoDijkstra;
    }

    private Integer minimo(Integer n, Boolean[] F, Double[] D) {
        Double maximo = Double.MAX_VALUE;
        Integer indiceMinimo = 1;
        for (int j = 0; j < n; j++) {
            if (!F[j] && (D[j] < maximo)) {
                maximo = D[j];
                indiceMinimo = j;
            }
        }
        return indiceMinimo;
    }

    //BPA
    public ListaEnlazada busquedaAnchura(Integer origen) throws Exception {

        ListaEnlazada busqueda = new ListaEnlazada<>();

        Integer matrizAdyacentes[][] = matrizAdyacencia();
        Integer vertices = this.numVertices();
        Boolean visitados[] = new Boolean[vertices];// vertices visitados

        for (int i = 0; i < vertices; i++) {
            visitados[i] = false;
        }
        origen -= 1;
        visitados[origen] = true;
        Cola cola = new Cola(vertices);
        busqueda.insertar(origen + 1);
        cola.queue(origen);

        while (!cola.estaVacia()) {
            Integer j = (Integer) cola.dequeue();
            for (int i = 0; i < vertices; i++) {
                if (matrizAdyacentes[j][i] == 2 && !visitados[i]) {
                    cola.queue(i);
                    visitados[i] = true;
                    busqueda.insertar(i + 1);
                }
            }
        }
        return busqueda;
    }

    //BPP
    public ListaEnlazada busquedaProfundidad(Integer origen) throws Exception {

        ListaEnlazada busqueda = new ListaEnlazada<>();

        Integer vertices = this.numVertices();
        Boolean[] visitados = new Boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            visitados[i] = false;
        }

        origen -= 1;
        visitados[origen] = true;
        Pila pila = new Pila();
        pila.push(origen);

        while (!pila.estaVacia()) {
            Integer temporal = (Integer) pila.pop();
            busqueda.insertar(temporal + 1);
            ListaEnlazada<Adyacencia> adyacencias = adyacentes(temporal + 1);
            for (int i = 0; i < adyacencias.getSize(); i++) {
                Adyacencia adyacencia = adyacencias.obtener(i);
                Integer aux = adyacencia.getDestino() - 1;
                if (!visitados[aux]) {
                    pila.push(aux);
                    visitados[aux] = true;
                }
            }
        }
        return busqueda;
    }

    private Integer[][] matrizAdyacencia() {
        Integer vertices = this.numVertices();
        Integer matriz[][] = new Integer[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                Double peso = this.pesoArista(i + 1, j + 1);
                if (peso != 0) {
                    matriz[i][j] = 2; // Existe
                    matriz[j][i] = 2; // Existe
                } else {
                    matriz[i][j] = 1; // No existe
                    matriz[j][i] = 1; // No existe
                }
            }
        }
        return matriz;
    }

    public static void main(String[] args) {
        GrafoNoDirigidoEtiquetado gde = new GrafoNoDirigidoEtiquetado(5, String.class);
        gde.etiquetarVertice(1, "Mayuri");//
        gde.etiquetarVertice(2, "Alice");
        gde.etiquetarVertice(3, "Vanessa");
        gde.etiquetarVertice(4, "Letty");//
        gde.etiquetarVertice(5, "Cobos");

        try {
            gde.insertarAristaE(gde.obtenerEtiqueta(5), gde.obtenerEtiqueta(4), 6.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(5), gde.obtenerEtiqueta(2), 4.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(5), gde.obtenerEtiqueta(1), 3.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(1), gde.obtenerEtiqueta(2), 5.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(1), gde.obtenerEtiqueta(3), 8.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(2), gde.obtenerEtiqueta(4), 1.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(3), gde.obtenerEtiqueta(2), 9.0);
//            gde.algoritmoFloyd();
            Integer nodo = 2;
//            gde.algoritmoDijkstra(nodo).imprimir();
            gde.busquedaAnchura(nodo).imprimir();
            gde.busquedaProfundidad(nodo).imprimir();

            new FrmGrafo(null, true, gde, 1).setVisible(true);
        } catch (Exception e) {
        }
    }
}
