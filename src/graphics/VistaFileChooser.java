/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import javax.swing.JFileChooser;

/**
 *
 * @author joan
 */
public class VistaFileChooser extends javax.swing.JFrame {

    private boolean importar;
    private boolean grafo;
    private boolean importado;
    private final CtrlPresentacion iCtrlPresentacion;
    /**
     * Creates new form VistaManual
     * @param pCtrlPresentacion
     */
    public VistaFileChooser(CtrlPresentacion pCtrlPresentacion) {
        this.iCtrlPresentacion = pCtrlPresentacion;        
        this.importar = true;
        this.grafo = true;
        this.importado = false;
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void hacerVisible(boolean importar, boolean grafo, boolean importado) {
        if(importar) 
        {
            fileChooser.setApproveButtonText("Import");
            this.fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        }
        else
        {
            this.fileChooser.setApproveButtonText("Export");
            this.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        }
        this.importar = importar;
        this.grafo = grafo;
        this.importado = importado;
        this.pack();
        this.setVisible(true);        
    }

    public void hacerInvisible() {
        this.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(719, 520));
        setMinimumSize(new java.awt.Dimension(719, 520));
        setResizable(false);

        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        System.out.println(evt.getActionCommand());
        if (null != evt.getActionCommand())
        switch (evt.getActionCommand()) 
        {
            case "ApproveSelection":
                if(this.importar == true)
                {
                    if(this.grafo == true) iCtrlPresentacion.importarGrafo(fileChooser.getSelectedFile().toString());
                    else iCtrlPresentacion.importarConjunto(fileChooser.getSelectedFile().toString());
                }
                else
                {
                    if(this.grafo == true) iCtrlPresentacion.exportarGrafo(fileChooser.getSelectedFile().toString());
                    else iCtrlPresentacion.exportarConjunto(fileChooser.getSelectedFile().toString(), importado);
                }
                iCtrlPresentacion.sincronizacionVistaFileChooser_a_Principal();
                break;        
            case "CancelSelection":
                iCtrlPresentacion.sincronizacionVistaFileChooser_a_Principal();
                break;
        }         
    }//GEN-LAST:event_fileChooserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    // End of variables declaration//GEN-END:variables
    
}
