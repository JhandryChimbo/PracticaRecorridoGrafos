/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.Listas.exception;

/**
 *
 * @author DEEPIN
 */
public class PosicionNoEncontradaExcepcion extends Exception{
     public PosicionNoEncontradaExcepcion(String msg){
         super(msg);
     }
     
     public PosicionNoEncontradaExcepcion(){
         super("La posicion dada está fuera de los límites de la lista");
     }
}
