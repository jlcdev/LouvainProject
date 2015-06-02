/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikipedia;

import graphics.CtrlPresentacion;

/**
 *
 * @author Joan Rodas
 */
public class Main {

    /**
     * Inicializa el programa
     * @param args
     */
    public static void main (String[] args) {
    javax.swing.SwingUtilities.invokeLater (
      new Runnable() {
        
        @Override
        public void run() {
          CtrlPresentacion ctrlPresentacion = new CtrlPresentacion();
          ctrlPresentacion.inicializarPresentacion();
    }});
  }
}
