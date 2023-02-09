/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.grafo;

import controlador.Listas.ListaEnlazada;
import java.lang.reflect.Array;
import java.util.HashMap;

/**
 *
 * @author LENOVO
 */
public class GrafoDirigidoEtiquetado<E> extends GrafoDirigido {

    protected E etiquetas[];
    protected HashMap<E, Integer> dicVertices;
    private Class<E> clazz;

    public GrafoDirigidoEtiquetado(Integer numVertices, Class clazz) {
        super(numVertices);
        this.clazz = clazz;
        etiquetas = (E[]) Array.newInstance(clazz, numVertices + 1);//Instancias de un arreglo
        dicVertices = new HashMap(numVertices);

    }

    public Boolean existeAristaE(E origen, E destino) throws Exception {
        return this.existeArista(obtenerCodigoE(origen), obtenerCodigoE(destino));
    }

    public void insertarAristaE(E origen, E destino, Double peso) throws Exception {
        insertarArista(obtenerCodigoE(origen), obtenerCodigoE(destino), peso);
    }

    public void insertarAristaE(E origen, E destino) throws Exception {
        insertarArista(obtenerCodigoE(origen), obtenerCodigoE(destino));
    }

    public ListaEnlazada<Adyacencia> adyacentesE(E origen) {
        return adyacentes(obtenerCodigoE(origen));

    }

    private Integer obtenerCodigoE(E etiqueta) {
        return dicVertices.get(etiqueta);
    }

    public E obtenerEtiqueta(Integer codigo) {
        return etiquetas[codigo];
    }

    public void etiquetarVertice(Integer codigo, E etiqueta) {
        etiquetas[codigo] = etiqueta;
        dicVertices.put(etiqueta, codigo);
    }

    @Override
    public String toString() {
        StringBuffer grafo = new StringBuffer("");
        try {
            for (int i = 1; i <= numVertices(); i++) {
                grafo.append("Vertice: " + obtenerEtiqueta(i).toString());
                ListaEnlazada<Adyacencia> lista = adyacentes(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adyacencia a = lista.obtener(j);
                    if (a.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                        grafo.append("---- Vertice etiquetado destino: ---- " + obtenerEtiqueta(a.getDestino()).toString() + "-- SP");
                    } else {
                        grafo.append("--------Vertice destino " + obtenerEtiqueta(a.getDestino()).toString() + "-- Peso" + a.getPeso());
                    }
                }
                grafo.append("\n");
            }
        } catch (Exception e) {
            grafo.append(e.getMessage());
        }
        return grafo.toString();
    }
}
