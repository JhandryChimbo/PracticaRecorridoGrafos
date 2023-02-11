/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.utiles;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;

/**
 *
 * @author DEEPIN
 */
public class Utilidades {

    public static String capitalizar(String nombre) {
        char[] aux = nombre.toCharArray();
        aux[0] = Character.toUpperCase(aux[0]);
        return new String(aux);
    }

    public static Field obtenerAtributo(Class clase, String nombre) {
        Field atributo = null;
        for (Field aux : clase.getDeclaredFields()) {
//            System.out.println(aux.getName());
            if (nombre.equalsIgnoreCase(aux.getName())) {
                atributo = aux;
                break;
            }
        }

        return atributo;
    }

    public static Object transformarDato(Field atributo, String dato) {
        Object transformar = null;
        if (atributo.getType().getSuperclass().getSimpleName().equalsIgnoreCase("Number")) {
            if (atributo.getType().getSimpleName().equals("Integer")) {
                transformar = Integer.parseInt(dato);
            }
        } else if (atributo.getType().isEnum()) {
            Enum enumeracion = Enum.valueOf((Class) atributo.getType(), dato.toString());
            transformar = enumeracion;
        } else if (atributo.getType().getSimpleName().equalsIgnoreCase("Boolean")) {
            transformar = Boolean.parseBoolean(dato);
        } else {
            transformar = dato;
        }
        return transformar;
    }

    public static void guardarArchivo(String datos, String ubicacion) throws Exception {
        FileWriter file = new FileWriter(ubicacion);
        file.append(datos);
        file.flush();
        file.close();
    }

    public static void cargarArchivo(String datos, String ubicacion) throws Exception {
        FileReader file = new FileReader(ubicacion);
        
    }

    

    public static Boolean isNumber(Class clase) {
        return clase.getSuperclass().getSimpleName().equalsIgnoreCase("Number");
    }

    public static Boolean isString(Class clase) {
        return clase.getSimpleName().equalsIgnoreCase("String");
    }

    public static Boolean isCharacter(Class clase) {
        return clase.getSimpleName().equalsIgnoreCase("Character");
    }

    public static Boolean isBoolean(Class clase) {
        return clase.getSimpleName().equalsIgnoreCase("Boolean");
    }

    public static Boolean IsPrimitive(Class clase) {
        return clase.isPrimitive();
    }

    public static Boolean isObject(Class clase) {
        return (!IsPrimitive(clase) && !isBoolean(clase) && !isCharacter(clase) && !isNumber(clase) && !isString(clase));
    }

    public static Double calcularDistancia(Double y, Double y1, Double x, Double x1) {
        Double yy = y - y1;
        Double xx = x - x1;
        return redondear(Math.sqrt((yy * yy) - (xx * xx)));
    }

    public static Double redondear(Double dato) {
        return Math.round(dato * 100.0) / 100.0;
    }

}
