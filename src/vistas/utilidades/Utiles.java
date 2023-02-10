/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas.utilidades;

import com.google.gson.Gson;
import controlador.Listas.exception.ListaNullException;
import controlador.Listas.exception.PosicionNoEncontradaExcepcion;
import controlador.PropiedadControlador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JComboBox;
import modelo.Propiedad;

/**
 *
 * @author LENOVO
 */
public class Utiles {

    private static String URL = "data";

    public static Boolean guardarJson(PropiedadControlador dato) throws Exception {
        Parse parse = new Parse(dato);
        Gson gson = new Gson();
        String json = gson.toJson(parse);
        try ( BufferedWriter bw = new BufferedWriter(new FileWriter(URL + File.separatorChar + "datos.json"))) {
            bw.write(json);
            bw.flush();
            bw.close();
            System.out.println("Se guardo :)");
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
            return false;
        }

    }

    public static PropiedadControlador cargarJson() throws Exception {
        Parse parse = new Parse();
        String json = "";
        Gson gson = new Gson();
        try ( BufferedReader br = new BufferedReader(new FileReader(URL + File.separatorChar + "datos.json"))) {

            String linea = "";
            while ((linea = br.readLine()) != null) {
                json += linea;
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        parse = (gson.fromJson(json, Parse.class));

        return parse.getPc();
    }

    public static JComboBox cargarComboPropiedad(JComboBox cbx, PropiedadControlador pc) throws Exception {
        cbx.removeAllItems();
        for (int i = 0; i < pc.getPropiedades().getSize(); i++) {
            cbx.addItem(pc.getPropiedades().obtener(i));
        }
        return cbx;
    }
}
