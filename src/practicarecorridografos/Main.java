/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practicarecorridografos;

import controlador.Listas.ListaEnlazada;
import controlador.grafo.GrafoNoDirigidoEtiquetado;
import vista.FrmGrafo;
import vista.FrmPrincipal;

/**
 *
 * @author LENOVO
 */
public class Main {

    /**
     * @param args the command line arguments
     */
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
            Integer nodo = 4;
            gde.algoritmoDijkstra(nodo).imprimir();
            
            new FrmGrafo(null, true, gde, 1).setVisible(true);
        } catch (Exception e) {
        }
        
        FrmPrincipal frmPrincipal = new FrmPrincipal();
        frmPrincipal.setVisible(true);
    }

}
