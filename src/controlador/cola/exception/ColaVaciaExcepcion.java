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
public class ColaVaciaExcepcion extends Exception{
    public ColaVaciaExcepcion(String msg){
        super(msg);
    }
    
    public ColaVaciaExcepcion(){
        super("La cola está vacía");
    }
}
