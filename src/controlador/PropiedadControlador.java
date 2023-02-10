/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.Listas.ListaEnlazada;
import controlador.grafo.GrafoNoDirigidoEtiquetado;
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
