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
public class AtributoException extends Exception{
    
    public AtributoException(String msg){
        super(msg);
    }
    
    public AtributoException(){
        super("No se puede encontrar el atributo");
    }
}
