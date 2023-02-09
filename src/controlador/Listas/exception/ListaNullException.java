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
public class ListaNullException extends Exception{
    public ListaNullException(String msg){
        super(msg);
    }
    
    public ListaNullException(){
        super("La lista está vaćia");
    }
}
