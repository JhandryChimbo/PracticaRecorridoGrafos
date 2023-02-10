/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.Listas.ListaEnlazada;
import modelo.Propiedad;

/**
 *
 * @author LENOVO
 */
public class PropiedadControlador {

    private ListaEnlazada<Propiedad> propiedades = new ListaEnlazada<>();

    public ListaEnlazada<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(ListaEnlazada<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

}
