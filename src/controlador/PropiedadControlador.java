/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import com.google.gson.Gson;
import controlador.Listas.ListaEnlazada;
import controlador.grafo.Adyacencia;
import controlador.grafo.GrafoNoDirigidoEtiquetado;
import java.io.File;
import java.util.HashMap;
import java.io.FileWriter;
import modelo.Propiedad;

/**
 *
 * @author LENOVO
 */
public class PropiedadControlador {

    private GrafoNoDirigidoEtiquetado<Propiedad> grafo;

    private ListaEnlazada<Propiedad> propiedades = new ListaEnlazada<>();

    public void crearGrafo() {
        grafo = new GrafoNoDirigidoEtiquetado<>(propiedades.getSize(), Propiedad.class);
        try {
            for (int i = 0; i < propiedades.getSize(); i++) {
                grafo.etiquetarVertice((i + 1), propiedades.obtener(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String URL = "data";
    
    public void guardarGrafo() throws Exception {
        Gson gson = new Gson();
        ListaEnlazada<HashMap> verticesE = new ListaEnlazada<>();
        HashMap<Integer, ObjetoGrafo> mapa = new HashMap<>();
        for (int i = 1; i <= getGrafo().getNumVertices(); i++) {
            ObjetoGrafo obj = new ObjetoGrafo();
            obj.setId_clase(getGrafo().obtenerEtiqueta(i).getId());
            obj.setClase(getGrafo().obtenerEtiqueta(i).getClass().toString());
            obj.setListaAdycencias(getGrafo().adyacentes(i));
            mapa.put(i, obj);
        }
        verticesE.insertar(mapa);

        controlador.utiles.Utilidades.guardarArchivo(gson.toJson(verticesE), URL + File.separatorChar + "adyacencias.json");

    }

    class ObjetoGrafo {

        String clase;
        Integer id_clase;
        ListaEnlazada<Adyacencia> listaAdycencias = new ListaEnlazada<>();

        public String getClase() {
            return clase;
        }

        public void setClase(String clase) {
            this.clase = clase;
        }

        public Integer getId_clase() {
            return id_clase;
        }

        public void setId_clase(Integer id_clase) {
            this.id_clase = id_clase;
        }

        public ListaEnlazada<Adyacencia> getListaAdycencias() {
            return listaAdycencias;
        }

        public void setListaAdycencias(ListaEnlazada<Adyacencia> listaAdycencias) {
            this.listaAdycencias = listaAdycencias;
        }

    }

    public GrafoNoDirigidoEtiquetado<Propiedad> getGrafo() {
        if (grafo == null) {
            crearGrafo();
        }
        return grafo;
    }

    public void setGrafo(GrafoNoDirigidoEtiquetado<Propiedad> grafo) {
        this.grafo = grafo;
    }

    public ListaEnlazada<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ListaEnlazada<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

}
