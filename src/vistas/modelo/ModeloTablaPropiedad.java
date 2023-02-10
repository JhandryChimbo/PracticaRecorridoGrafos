/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas.modelo;

import controlador.PropiedadControlador;
import javax.swing.table.AbstractTableModel;
import modelo.Propiedad;

/**
 *
 * @author LENOVO
 */
public class ModeloTablaPropiedad extends AbstractTableModel {

    private PropiedadControlador propiedades;

    public PropiedadControlador getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(PropiedadControlador propiedades) {
        this.propiedades = propiedades;
    }

    @Override
    public int getRowCount() {
        return propiedades.getPropiedades().getSize();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Nombre";
            case 2:
                return "Descripcion";
            case 3:
                return "Distancia";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Propiedad p = null;
        try {
            p = propiedades.getPropiedades().obtener(rowIndex);
        } catch (Exception e) {
        }

        switch (columnIndex) {

            case 0:
                return (p != null) ? p.getId() : "No registrado";
            case 1:
                return (p != null) ? p.getNombre() : "No registrado";
            case 2:
                return (p != null) ? p.getDescripcion() : "No registrado";
            case 3:
                return (p != null) ? p.getDistancia() : "No registrado";

            default:
                return null;
        }

    }

}
