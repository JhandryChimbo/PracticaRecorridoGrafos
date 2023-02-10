/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.grafo;

import controlador.Listas.ListaEnlazada;
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
    public void algoritmoFloyd() throws Exception {

        ListaEnlazada ListaFloyd = new ListaEnlazada();

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

        System.out.println("\t1\t2\t3\t4\t5");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + 1 + "\t");
            for (int j = 0; j < vertices; j++) {
                System.out.print(caminoAux[i][j] + "\t");
            }
            System.out.println("\n");
        }
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

        visitados[s] = true;
        minimos[s] = 0.0;

        for (int i = 0; i < vertices; i++) {
            if (i == origen) {
                visitados[i] = false;
                minimos[i] = pesos[s][i];
                ultimo[i] = s;
            }

        }

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
        public ListaEnlazada Dijkstra(Integer origen) throws Exception {
        
        origen = origen -1;
        ListaEnlazada caminoDijkstra = new ListaEnlazada();
        Integer Origen = origen;
        Integer NumeroDeVertices = this.numVertices();
        
        Double[] AuxiliarDouble = new Double[NumeroDeVertices];
        Boolean[] Marca = new Boolean[NumeroDeVertices];
        Integer[] Ultimo = new Integer[NumeroDeVertices];
        Double[][] MatrizPesos = pesoGrafo(this);
        
        Marca[Origen] = true;
        AuxiliarDouble[Origen] = 0.0;

        for (int i = 0; i < NumeroDeVertices; i++) {
            Marca[i] = false;
            AuxiliarDouble[i] = MatrizPesos[Origen][i];
            Ultimo[i] = Origen;
        }

        for (int i = 0; i < NumeroDeVertices; i++) {
            Integer v = minimo(NumeroDeVertices, Marca, AuxiliarDouble);
            Marca[v] = true;
            
            for (int w = 0; w < NumeroDeVertices; w++) {
                if (!Marca[w] && ((AuxiliarDouble[v] + MatrizPesos[v][w]) < AuxiliarDouble[w])) {
                    AuxiliarDouble[w] = AuxiliarDouble[v] + MatrizPesos[v][w];
                    Ultimo[w] = v;
                }
            }
            caminoDijkstra.insertar(AuxiliarDouble[i]);
        }
        return caminoDijkstra;
    }

    private Integer minimo(Integer n, Boolean[] F, Double[] D) {

        Double maximo = Infinito;
        Integer v = 1;

        for (int j = 0; j < n; j++) {
            if (!F[j] && (D[j] < maximo)) {
                maximo = D[j];
                v = j;
            }
        }
        return v;
    }

    

}
