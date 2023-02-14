/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas.modelo;

import controlador.Listas.ListaEnlazada;
import controlador.grafo.GrafoNoDirigidoEtiquetado;
import javax.swing.table.AbstractTableModel;
import modelo.Propiedad;

/**
 *
 * @author LENOVO
 */
public class ModeloTablaProfundidad extends AbstractTableModel{
    
    private GrafoNoDirigidoEtiquetado<Propiedad> grafo;
    private ListaEnlazada lista;

    @Override
    public int getRowCount() {
            return lista.getSize();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int column) {
        return "Recorrido";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Integer auxLista = (Integer) lista.obtener(rowIndex);
            switch (columnIndex) {
                case 0:
                    return grafo.obtenerEtiqueta(auxLista);
            }
        } catch (Exception e) {
        }
        switch (columnIndex) {
            default:
                return null;
        }
    }

    public GrafoNoDirigidoEtiquetado<Propiedad> getGrafo() {
        return grafo;
    }

    public void setGrafo(GrafoNoDirigidoEtiquetado<Propiedad> grafo) {
        this.grafo = grafo;
    }

    public ListaEnlazada getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada lista) {
        this.lista = lista;
    }
}
