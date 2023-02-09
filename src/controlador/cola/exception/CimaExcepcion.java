/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.cola.exception;

/**
 *
 * @author ivangonzalez
 */
public class CimaExcepcion extends Exception{
    public CimaExcepcion(String msg){
        super(msg);
    }
    
    public CimaExcepcion(){
        super("La cola está llena");
    }
}
