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

        Integer vertices = this.numVertices();
        Integer camino[][] = new Integer[vertices][vertices];
        Double caminoAux[][] = new Double[vertices][vertices];
        Double pesos[][] = pesoGrafo(this);

        //Inicializando las matrices caminos y caminosAuxiliares
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
                        caminoAux[i][j] = caminoAux[j][k] + caminoAux[k][j];
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

    public static void main(String[] args) {
        GrafoNoDirigidoEtiquetado gde = new GrafoNoDirigidoEtiquetado(5, String.class);
        gde.etiquetarVertice(1, "Mayuri");//
        gde.etiquetarVertice(2, "Alice");
        gde.etiquetarVertice(3, "Vanessa");
        gde.etiquetarVertice(4, "Letty");//
        gde.etiquetarVertice(5, "Cobos");
        try {
            gde.insertarAristaE(gde.obtenerEtiqueta(5), gde.obtenerEtiqueta(2), 10.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(1), gde.obtenerEtiqueta(2), 25.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(3), gde.obtenerEtiqueta(5), 1.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(3), gde.obtenerEtiqueta(4), -10.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(2), gde.obtenerEtiqueta(3), 55.0);
            gde.insertarAristaE(gde.obtenerEtiqueta(1), gde.obtenerEtiqueta(4), 1000.0);
            //System.out.println(gde.caminiMinimo(1, 4));
//            gde.caminoMinimo(1, 2).imprimir();
            gde.algoritmoFloyd();
            //System.out.println(gde.toString());
            new FrmGrafo(null, true, gde, 1).setVisible(true);
            //new UbicacionController().listar().imprimir();
        } catch (Exception e) {
        }
    }

}
