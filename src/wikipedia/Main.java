/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikipedia;

import graphics.CtrlPresentacion;

/**
 *
 * @author joan
 */
public class Main {
    public static void main (String[] args) {
    javax.swing.SwingUtilities.invokeLater (
      new Runnable() {
        
        @Override
        public void run() {
          //new VistaLEEME().hacerVisible();
          CtrlPresentacion ctrlPresentacion = new CtrlPresentacion();
          ctrlPresentacion.inicializarPresentacion();
    }});
  }
}
