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
        try {
            javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                        
                    @Override
                    public void run() {
                        CtrlPresentacion ctrlPresentacion = new CtrlPresentacion();
                        ctrlPresentacion.inicializarPresentacion();
                    }
                });
        } catch (Exception e){System.out.println("Main: Error desconocido");}
  }
}
