/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Listas;

import controlador.Listas.exception.AtributoException;
import controlador.Listas.exception.ListaNullException;
import controlador.Listas.exception.PosicionNoEncontradaExcepcion;
import controlador.utiles.Utilidades;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
//import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DEEPIN
 */
//@XmlRootElement
public class ListaEnlazada<E> {

    private NodoLista<E> cabecera;
    private NodoLista<E> cola;
    private Integer size;
    private ListaEnlazada<E> lista;
    public static Integer DESCENDENTE = 1;
    public static Integer ASCENDENTE = 2;

    public ListaEnlazada() {
        cabecera = null;
        size = 0;
    }

    public NodoLista<E> getCabecera() {
        return cabecera;
    }

    public void setCabecera(NodoLista<E> cabecera) {
        this.cabecera = cabecera;
    }

    public Integer getSize() {

        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public ListaEnlazada<E> getLista() {
        return lista;
    }

    public void setLista(ListaEnlazada<E> lista) {
        this.lista = lista;
    }

    public Boolean estaVacia() {
        return cabecera == null;
    }

    public void insertarC(E dato) {
        NodoLista<E> nodo = new NodoLista<>(dato, null);
        if (estaVacia()) {
            this.cabecera = nodo;
        } else {
            this.cola.setSiguiente(nodo);
        }
        this.cola = nodo;

        size++;
    }

    public void insertar(E dato) {

        NodoLista<E> nodo = new NodoLista<>(dato, null);
        if (estaVacia()) {
            this.cabecera = nodo;

        } else {
            NodoLista<E> aux = cabecera;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nodo);
        }
        size++;
    }

    public void insertarCabecera(E dato) {

        if (estaVacia()) {
            insertar(dato);
        } else {
            NodoLista<E> nodo = new NodoLista<>(dato, null);
            nodo.setSiguiente(cabecera);
            cabecera = nodo;
            size++;
        }
    }

    public void insertarPosicion(E dato, Integer pos) throws PosicionNoEncontradaExcepcion {
        if (estaVacia()) {
            insertar(dato);
        } else if (pos >= 0 && pos < size) {
            if (pos == (size - 1)) {
                insertar(dato);
            } else if (pos == 0) {
                insertarCabecera(dato);
            } else {
                NodoLista<E> nodo = new NodoLista(dato, null);
                NodoLista<E> aux = cabecera;
                for (int i = 0; i < (pos - 1); i++) {
                    aux = aux.getSiguiente();
                }
                NodoLista<E> siguiente = aux.getSiguiente();
                aux.setSiguiente(nodo);
                nodo.setSiguiente(siguiente);
                size++;
            }

        } else {
            throw new PosicionNoEncontradaExcepcion();
        }
    }

    public void imprimir() {
        System.out.println("-------------LISTA ENLAZADA---------------");
        NodoLista<E> aux = cabecera;
        while (aux != null) {
            System.out.print(aux.getDato().toString() + "\t\t");
            aux = aux.getSiguiente();
        }
        System.out.println("\n------------------------------------------");

    }

    public E obtener(Integer pos) throws ListaNullException, PosicionNoEncontradaExcepcion {

        if (!estaVacia()) {
            E dato = null;
            if (pos >= 0 && pos < size) {
                if (pos == 0) {
                    dato = cabecera.getDato();
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getSiguiente();
                    }
                    dato = aux.getDato();
                }
            } else {
                throw new PosicionNoEncontradaExcepcion();
            }

            return dato;
        } else {
            throw new ListaNullException();
        }

    }

    public void modificarDato(E dato, Integer pos) throws PosicionNoEncontradaExcepcion {
        if (estaVacia()) {
            insertar(dato);
        } else if (pos >= 0 && pos < size) {
            if (pos == 0) {
                cabecera.setDato(dato);
            } else {
                NodoLista<E> aux = cabecera;
                for (int i = 0; i < pos; i++) {
                    aux = aux.getSiguiente();
                }
                aux.setDato(dato);
            }
        } else {
            throw new PosicionNoEncontradaExcepcion();
        }
    }

    public E eliminar(Integer pos) throws ListaNullException, PosicionNoEncontradaExcepcion {
        System.out.println("pos " + pos);
        if (!estaVacia()) {
            E dato = null;
            if (pos >= 0 && pos < size) {
                if (pos == 0) {
                    dato = cabecera.getDato();
                    cabecera = cabecera.getSiguiente();
                    size--;
                } else {
                    NodoLista<E> aux = cabecera;
                    for (int i = 0; i < pos; i++) {
                        aux = aux.getSiguiente();
                    }
                    dato = aux.getDato();
                    NodoLista<E> proximo = aux.getSiguiente();
                    aux.setSiguiente(proximo.getSiguiente());
                    size--;
                }
            } else {
                throw new PosicionNoEncontradaExcepcion();
            }

            return dato;
        } else {
            throw new ListaNullException();
        }
    }

    public E[] toArray() {

        E[] matriz = null;
        if (this.size > 0) {
            matriz = (E[]) Array.newInstance(cabecera.getDato().getClass(), this.size);
            NodoLista<E> aux = cabecera;
            for (int i = 0; i < this.size; i++) {
                matriz[i] = aux.getDato();
                aux = aux.getSiguiente();
            }
        }
        return matriz;
    }

    public ListaEnlazada<E> toList(E[] matriz) {
        this.vaciar();
        for (int i = 0; i < matriz.length; i++) {
            this.insertarC(matriz[i]);
        }
        return this;
    }

    public void vaciar() {
        this.cabecera = null;
        this.size = 0;
    }

    public ListaEnlazada<E> burbuja(String atributo, Integer tipoOrdenacion) throws Exception {
        Class<E> clazz = null;
        E[] matriz = toArray();
        if (size > 0) {
            clazz = (Class<E>) cabecera.getDato().getClass();
            Boolean isObject = Utilidades.isObject(clazz);
            for (int i = matriz.length; i > 1; i--) {
                for (int j = 0; j < i - 1; j++) {
                    if (isObject) {
                        intercambioObjeto(j, matriz, clazz, tipoOrdenacion, atributo);
                    } else {
                        intercambioDato(j, matriz, clazz, tipoOrdenacion);
                    }

                }
            }
        }
        if (matriz != null) {
            toList(matriz);
        }
        return this;
    }

    /**
     * solo para datos simples (primitivos, boxing)
     *
     * @param j es el indice
     * @param matriz coleccion de datos
     * @param clazz clase
     * @param tipoOrdenacion como se desea ordenar
     */
    private void intercambioDato(int j, E[] matriz, Class clazz, Integer tipoOrdenacion) {
        E auxJ = matriz[j];
        E auxJ1 = matriz[j + 1];
        intercambio(j, matriz, auxJ, auxJ1, tipoOrdenacion);
    }

    private void intercambioObjeto(int j, E[] matriz, Class clazz, Integer tipoOrdenacion, String atributo) throws Exception {
        E auxJ = matriz[j];
        E auxJ1 = matriz[j + 1];
        Field field = Utilidades.obtenerAtributo(clazz, atributo);
        if (field == null) {
            throw new AtributoException();
        } else {
            field.setAccessible(true);
            Object a = field.get(auxJ);
            Object b = field.get(auxJ1);
            intercambio(j, matriz, a, b, tipoOrdenacion);
        }
    }

    private void intercambio(int j, E[] matriz, Object auxJ, Object auxJ1, Integer tipoOrdenacion) {
        Class clazz = auxJ.getClass();
        E a = matriz[j];
        E b = matriz[j + 1];

        if (Utilidades.isNumber(clazz)) {
            if (tipoOrdenacion == DESCENDENTE) {
                if (((Number) auxJ).doubleValue() < ((Number) auxJ1).doubleValue()) {
                    matriz[j] = b;
                    matriz[j + 1] = a;
                }
            } else {
                if (((Number) auxJ).doubleValue() > ((Number) auxJ1).doubleValue()) {
                    matriz[j] = b;
                    matriz[j + 1] = a;
                }
            }
        }
        if (Utilidades.isString(clazz)) {
            if (tipoOrdenacion == DESCENDENTE) {
                if (auxJ.toString().toLowerCase().compareTo(auxJ1.toString().toLowerCase()) < 0) {
                    matriz[j] = b;
                    matriz[j + 1] = a;
                }
            } else {
                if (auxJ.toString().toLowerCase().compareTo(auxJ1.toString().toLowerCase()) > 0) {
                    matriz[j] = b;
                    matriz[j + 1] = a;
                }
            }

        }
    }

    public ListaEnlazada<E> orderSeleccion(String atributo, Integer tipoOrdenacion) throws Exception {
        Class<E> clazz = null;
        E[] matriz = toArray();
        if (size > 0) {
            clazz = (Class<E>) cabecera.getDato().getClass();
            Boolean isObject = Utilidades.isObject(clazz);
            //algortimo seleccion
            Integer i, j, k = 0;
            Integer n = matriz.length;
            E t = null;
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = matriz[i];
                for (j = i + 1; j < n; j++) {
                    E auxJ1 = matriz[j];
                    Object[] aux = null;
                    if (isObject) {
                        aux = evaluarCambioObjeto(t, auxJ1, j, tipoOrdenacion, clazz, atributo);
                    } else {
                        aux = evaluarCambioDato(t, auxJ1, j, tipoOrdenacion);
                    }
                    if (aux[0] != null) {
                        t = (E) aux[0];
                        k = (Integer) aux[1];
                    }
                }
                matriz[k] = matriz[i];
                matriz[i] = t;
            }
        }
        if (matriz != null) {
            toList(matriz);
        }
        return this;
    }

    private Object[] evaluarCambioDato(E auxJ, E auxJ1, Integer j, Integer tipoOrdenacion) {
        return evaluarCambio(auxJ, auxJ1, auxJ1, j, tipoOrdenacion);
    }

    private Object[] evaluarCambioObjeto(E auxJ, E auxJ1, Integer j, Integer tipoOrdenacion, Class clazz, String atributo) throws Exception {
        Field field = Utilidades.obtenerAtributo(clazz, atributo);
        if (field == null) {
            throw new AtributoException();
        }
        field.setAccessible(true);
        Object a = field.get(auxJ);
        Object b = field.get(auxJ1);
        return evaluarCambio(a, b, auxJ1, j, tipoOrdenacion);
    }

    private Object[] evaluarCambio(Object auxJ, Object auxJ1, E dato, Integer j, Integer tipoOrdenacion) {

        Object[] aux = new Object[2];
        Class clazz = auxJ.getClass();
        if (Utilidades.isNumber(clazz)) {
            if (tipoOrdenacion == DESCENDENTE) {
                if (((Number) auxJ).doubleValue() < ((Number) auxJ1).doubleValue()) {
                    aux[0] = dato;
                    aux[1] = j;
                }
            } else {
                if (((Number) auxJ).doubleValue() > ((Number) auxJ1).doubleValue()) {
                    aux[0] = dato;
                    aux[1] = j;
                }
            }
        }
        if (Utilidades.isString(clazz)) {
            if (tipoOrdenacion == DESCENDENTE) {
                if (auxJ.toString().toLowerCase().compareTo(auxJ1.toString().toLowerCase()) < 0) {
                    aux[0] = dato;
                    aux[1] = j;
                }
            } else {
                if (auxJ.toString().toLowerCase().compareTo(auxJ1.toString().toLowerCase()) > 0) {
                    aux[0] = dato;
                    aux[1] = j;
                }
            }
        }
        return aux;
    }

    //busqueda
    public ListaEnlazada<E> buscar(String atributo, Object dato) throws Exception {
        Class<E> clazz = null;
        ListaEnlazada<E> result = new ListaEnlazada<>();
        if (size > 0) {

            E[] matriz = toArray();
            clazz = (Class<E>) cabecera.getDato().getClass();
            Boolean isObject = Utilidades.isObject(clazz);

            for (int i = 0; i < matriz.length; i++) {
                if (isObject) {
                    //cuando es objeto
                    Boolean band = evaluarBusquedaObjeto(matriz[i], dato, clazz, atributo);
                    if (band) {
                        result.insertar(matriz[i]);
                    }
                } else {
                    //cuando son datos primitivos
                    Boolean band = buscarPosicion(matriz[i], dato);
                    if (band) {
                        result.insertar(matriz[i]);
                    }
                }
            }
        }
        return result;
    }

    private Boolean buscarPosicion(Object datoMatriz, Object busqueda) {

        if (Utilidades.isNumber(busqueda.getClass())) {
            if (((Number) datoMatriz).doubleValue() == ((Number) busqueda).doubleValue()) {
                return true;
            }
        } else if (Utilidades.isString(busqueda.getClass())) {
            if (datoMatriz.toString().toLowerCase().startsWith(busqueda.toString().toLowerCase())
                    || datoMatriz.toString().toLowerCase().endsWith(busqueda.toString().toLowerCase())
                    || datoMatriz.toString().toLowerCase().equalsIgnoreCase(busqueda.toString().toLowerCase())
                    || datoMatriz.toString().toLowerCase().contains(busqueda.toString().toLowerCase())) {

                return true;
            }
        }
        return false;
    }

    private Boolean evaluarBusquedaObjeto(E aux, Object dato, Class clazz, String atributo) throws Exception {
        Field field = Utilidades.obtenerAtributo(clazz, atributo);
        if (field == null) {
            throw new AtributoException();
        }
        field.setAccessible(true);
        Object a = field.get(aux);
        return buscarPosicion(a, dato);
    }

    //ORDENAMIENTO SHELL
    public ListaEnlazada<E> ordenarShell(String atributo, Integer tipoOrdenacion) {

        E[] matriz = toArray();
        int salto, i, j, k;
        salto = matriz.length / 2;
        while (salto > 0) {
            for (i = salto; i < matriz.length; i++) {
                j = i - salto;
                while (j >= 0) {
                    k = j + salto;
                    if (intercambioShell(j, k, matriz, tipoOrdenacion)) {
                        j -= salto;
                    } else {
                        j = -1;
                    }
                }
            }
            salto = salto / 2;
        }

        if (matriz != null) {
            toList(matriz);
        }

        return this;
    }

    private boolean intercambioShell(int j, int k, E[] matriz, Integer tipoOrdenacion) {
        if (matriz[j] instanceof Comparable == false) {
            throw new IllegalArgumentException("El dato no se puede comparar");
        }

        int comparacion = ((Comparable) matriz[j]).toString().toLowerCase()
                .compareTo(((Comparable) matriz[k]).toString().toLowerCase());
        boolean intercambiado = false;

        if ((tipoOrdenacion == DESCENDENTE && comparacion < 0)
                || (tipoOrdenacion == ASCENDENTE && comparacion > 0)) {
            E aux = matriz[j];
            matriz[j] = matriz[j + 1];
            matriz[j + 1] = aux;

            intercambiado = true;
        }

        return intercambiado;

    }

    //ORDENAMIENTO QUICKSORT
    public ListaEnlazada<E> ordenarQuicksort(Integer tipoOrdenacion) {
        ordenarQuicksort(toArray(), tipoOrdenacion, 0, size - 1);

        return this;
    }

    public void ordenarQuicksort(E[] matriz, Integer tipoOrdenacion, int inicio, int fin) {
        E aux;
        int i, j, central;
        i = inicio;
        j = fin;
        central = (inicio + fin) / 2;
        Comparable pivote = (Comparable) matriz[central];
        do {
            if (tipoOrdenacion == ASCENDENTE) {
                while (((Comparable) matriz[i]).compareTo(pivote) < 0) {
                    i++;
                }
                while (((Comparable) matriz[j]).compareTo(pivote) > 0) {
                    j--;
                }
            } else {
                while (((Comparable) matriz[i]).compareTo(pivote) > 0) {
                    i++;
                }
                while (((Comparable) matriz[j]).compareTo(pivote) < 0) {
                    j--;
                }
            }

            if (i <= j) {
                aux = matriz[i];
                matriz[i] = matriz[j];
                matriz[j] = aux;

                i++;
                j--;
            }
        } while (i <= j);
        if (inicio < j) {
            ordenarQuicksort(matriz, tipoOrdenacion, inicio, j);
        }
        if (i < fin) {
            ordenarQuicksort(matriz, tipoOrdenacion, i, fin);
        }
        if (matriz != null) {
            toList(matriz);
        }

    }

    //METODO BUSQUEDA BINARIA
    public ListaEnlazada<E> busquedaBinaria(String atributo, Object dato) throws Exception {

        ListaEnlazada<E> result = new ListaEnlazada<>();

        Class<E> clazz = (Class<E>) cabecera.getDato().getClass();
        if (size > 0) {
            E[] matriz = toArray();
            Boolean isObject = Utilidades.isObject(matriz[0].getClass());
            Integer centro, inicio, fin;
            Object valorCentral, aux;
            inicio = 0;
            fin = matriz.length - 1;
            while (inicio <= fin) {
                centro = (inicio + fin) / 2;
                valorCentral = matriz[centro];
                if (isObject) {

                    Field field = Utilidades.obtenerAtributo(clazz, atributo);
                    if (field == null) {
                        throw new AtributoException();
                    } else {
                        field.setAccessible(true);
                        aux = field.get(valorCentral);
                    }
                    if (Utilidades.isNumber(aux.getClass())) {
                        if (((Number) dato).doubleValue() == ((Number) aux).doubleValue()) {
                            result.insertar(matriz[centro]);
                            return result;
                        } else if (((Number) dato).doubleValue() < ((Number) aux).doubleValue()) {
                            fin = centro - 1;
                        } else {
                            inicio = centro + 1;
                        }
                    }
                    if (Utilidades.isString(aux.getClass())) {

                        if (dato.toString().toLowerCase().equals(aux.toString().toLowerCase())) {
                            result.insertar(matriz[centro]);
                            return result;
                        } else if (dato.toString().toLowerCase().compareTo(aux.toString().toLowerCase()) < 0) {
                            fin = centro - 1;
                        } else {
                            inicio = centro + 1;
                        }
                    }
                } else {
                    if (Utilidades.isNumber(matriz[0].getClass())) {
                        if (((Number) dato).doubleValue() == ((Number) valorCentral).doubleValue()) {
                            result.insertar(matriz[centro]);
                            return result;
                        } else if (((Number) dato).doubleValue() < ((Number) valorCentral).doubleValue()) {
                            fin = centro - 1;
                        } else {
                            inicio = centro + 1;
                        }
                    }
                }
            }
        }
        return result;
    }

    //METODO BUSQUEDA LINEAL BINARIA
    public ListaEnlazada<E> busquedaLinealBinaria(String atributo, Object dato) throws Exception {
        ListaEnlazada<E> result = new ListaEnlazada<>();

        Class<E> clazz = (Class<E>) cabecera.getDato().getClass();
        if (size > 0) {
            E[] matriz = toArray();
            Boolean isObject = Utilidades.isObject(matriz[0].getClass());
            Integer centro, inicio, fin;
            Object valorCentral, aux;
            inicio = 0;
            fin = matriz.length - 1;
            while (inicio <= fin) {
                centro = (inicio + fin) / 2;
                valorCentral = matriz[centro];
                if (isObject) {

                    Field field = Utilidades.obtenerAtributo(clazz, atributo);
                    if (field == null) {
                        throw new AtributoException();
                    } else {
                        field.setAccessible(true);
                        aux = field.get(valorCentral);
                    }
                    if (Utilidades.isNumber(aux.getClass())) {
                        if (((Number) dato).doubleValue() == ((Number) aux).doubleValue()) {
//                            result.insertar(matriz[centro]);
                            
                        } else if (((Number) dato).doubleValue() < ((Number) aux).doubleValue()) {
                            fin = centro - 1;
                        } else {
                            inicio = centro + 1;
                        }
                        for (inicio = 0; inicio < fin + 1; inicio++) {
                            Boolean band = evaluarBusquedaObjeto(matriz[inicio], dato, clazz, atributo);
                            if (band) {
                                result.insertar(matriz[inicio]);
                            }
                        }
                    }
                    if (Utilidades.isString(aux.getClass())) {
                        if (dato.toString().toLowerCase().equals(aux.toString().toLowerCase())) {
                            result.insertar(matriz[centro]);
                            return result;
                        } else if (dato.toString().toLowerCase().compareTo(aux.toString().toLowerCase()) < 0) {
                            fin = centro - 1;
                        } else {
                            inicio = centro + 1;
                        }

                        for (inicio = 0; inicio < fin + 1; inicio++) {
                            Boolean band = evaluarBusquedaObjeto(matriz[inicio], dato, clazz, atributo);
                            if (band) {
                                result.insertar(matriz[inicio]);
                            }
                        }
                    }
                } else {
                    if (Utilidades.isNumber(matriz[0].getClass())) {
                        if (((Number) dato).doubleValue() == ((Number) valorCentral).doubleValue()) {
                            result.insertar(matriz[centro]);
                            
                        } else if (((Number) dato).doubleValue() < ((Number) valorCentral).doubleValue()) {
                            fin = centro - 1;
                        } else {
                            inicio = centro + 1;
                        }
                    }
                    for (inicio = 0; inicio < fin + 1; inicio++) {
                        Boolean band = evaluarBusquedaObjeto(matriz[inicio], dato, clazz, atributo);
                        if (band) {
                            result.insertar(matriz[inicio]);
                        }
                    }
                }
            }
        }
        return result;
    }
    
    
    
}
