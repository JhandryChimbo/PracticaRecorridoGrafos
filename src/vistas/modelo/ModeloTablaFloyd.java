/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas.modelo;

import controlador.grafo.GrafoNoDirigidoEtiquetado;
import javax.swing.table.AbstractTableModel;
import modelo.Propiedad;

/**
 *
 * @author LENOVO
 */
public class ModeloTablaFloyd extends AbstractTableModel {

    private GrafoNoDirigidoEtiquetado<Propiedad> grafo;
    private Double recorrido[][];
    private String[] columnas;

    private String[] crearColumnas() {
        columnas = new String[grafo.numVertices() + 1];
        columnas[0] = "--v--";
        for (int i = 1; i < columnas.length; i++) {
            columnas[i] = grafo.obtenerEtiqueta(i).toString();
        }
        return columnas;
    }

    public GrafoNoDirigidoEtiquetado<Propiedad> getGrafo() {
        return grafo;

    }

    public void setGrafo(GrafoNoDirigidoEtiquetado<Propiedad> grafo) {
        this.grafo = grafo;
        crearColumnas();
    }

    @Override
    public int getRowCount() {
        return grafo.numVertices();
    }

    @Override
    public int getColumnCount() {
        return grafo.numVertices() + 1;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return columnas[rowIndex + 1];
        } else {
            try {
                recorrido = grafo.algoritmoFloyd();
                return recorrido[rowIndex][columnIndex - 1];

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return "--";

    }

}
