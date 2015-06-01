/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Joan Rodas
 */
public class VistaPrincipal extends javax.swing.JFrame 
{
    
    // Controlador de presentacion
    private final CtrlPresentacion iCtrlPresentacion;
    private ArrayList<Integer> catPosToId;
    private ArrayList<Integer> pagPosToId;
    boolean modEnlaces;
    boolean[] modConjunto = new boolean[2];    
    int p;
    int minCat;
     
    
    public VistaPrincipal (CtrlPresentacion pCtrlPresentacion) 
    {
        //System.out.println("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
        this.iCtrlPresentacion = pCtrlPresentacion;        
        this.initComponents();
        this.setLocationRelativeTo(null);
        this.catPosToId = new ArrayList();
        this.pagPosToId = new ArrayList();
        this.modConjunto[0] = false;
        this.modConjunto[1] = false;
        //this.modConjuntoNum[0] = false;
        //this.modConjuntoNum[1] = false;
        this.modEnlaces = false;
        this.p = 50;
        this.minCat = 2;
        this.pagPosToId = new ArrayList();
        this.catPosToId = new ArrayList();
    }

    public void hacerVisible() 
    {
        //System.out.println("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
        this.pack();
        this.setVisible(true);
    }

    public void activar() 
    {
        this.setEnabled(true);
    }

    public void desactivar() 
    {
        this.setEnabled(false);
    }
    
    public void goToTab(int tab)
    {
        tabsPrincipal.setSelectedIndex(tab);        
    }
    
    public void activarTab(int tab)
    {
        tabsPrincipal.setEnabledAt(tab, true);
    }
    
    public void desactivarTab(int tab)
    {
        tabsPrincipal.setEnabledAt(tab, false);
    }
    
    private void randomSel(boolean pag)
    {
        Random r = new Random();
        if(pag)
        {
            int size = this.pagPosToId.size();
            int max = r.nextInt(size + 1);
            int[] indices = new int[max];
            for(int i = 0; i < max; i++) indices[i] = r.nextInt(size);
            this.listSelPaginas.setSelectedIndices(indices);
        }
        else
        {
            int size = this.catPosToId.size();
            int max = r.nextInt(size + 1);
            int[] indices = new int[max];
            for(int i = 0; i < max; i++) indices[i] = r.nextInt(size);
            this.listSelCategorias.setSelectedIndices(indices);
        }
    }
    
    public void actualizarPag()
    {        
        ArrayList<String> lista = this.iCtrlPresentacion.mostrarGrafoPag();          
        DefaultListModel model = (DefaultListModel) this.listSelPaginas.getModel();
        model.clear();
        DefaultListModel model2 = (DefaultListModel) this.listPag.getModel();
        model2.clear();
        //this.pagPosToId = new ArrayList();
        for(String elem : lista) 
        {
            model.addElement(elem);
            model2.addElement(elem);
            this.pagPosToId.add(this.iCtrlPresentacion.getPagNum(elem));
        }        
    }
    
    public void actualizarCat()
    {           
        ArrayList<String> lista = this.iCtrlPresentacion.mostrarGrafoCat();          
        DefaultListModel model = (DefaultListModel) this.listSelCategorias.getModel();
        model.clear();
        DefaultListModel model2 = (DefaultListModel) this.listCat.getModel();
        model2.clear();
        //this.catPosToId = new ArrayList();
        for(String elem : lista) {
            model.addElement(elem);
            model2.addElement(elem);
            this.catPosToId.add(this.iCtrlPresentacion.getCatNum(elem));
        }        
    }
    
    public void actualizarLinks()
    {           
        ArrayList<String> lista = this.iCtrlPresentacion.mostrarGrafoEnlaces();          
        DefaultListModel model = (DefaultListModel) this.listLinks.getModel();
        model.clear();
        for(String elem : lista) model.addElement(elem);
        
        this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ lista.size());
    }
    
    public void actualizarSet(boolean importado)
    {           
        ArrayList<String> lista = iCtrlPresentacion.mostrarCto(importado);         
        DefaultListModel model = (DefaultListModel) listSet.getModel();
        model.clear();
        for(String elem : lista) model.addElement(elem+"["+this.iCtrlPresentacion.numCatCom(elem, importado)+"]");
        int value = importado ? 1 : 0;
        this.comboTipoSet.setSelectedIndex(value);
    }
    
    private void actualizarSetNum(boolean importado, int num)
    {           
        ArrayList<String> lista = this.iCtrlPresentacion.mostrarCto(importado);         
        DefaultListModel model = (DefaultListModel) this.listSetNum.getModel();
        model.clear();
        int n;
        for(String elem : lista)
        {
            n = this.iCtrlPresentacion.numCatCom(elem, importado);            
            if(n >= num) model.addElement(elem+"["+n+"]");
        }        
    } 
       
    public void clearTxtAreas()
    {
        DefaultListModel model = (DefaultListModel) this.listSelCategorias.getModel();
        model.clear();
        model = (DefaultListModel) this.listSelPaginas.getModel();
        model.clear();
        model = (DefaultListModel) this.listCat.getModel();
        model.clear();
        model = (DefaultListModel) this.listPag.getModel();
        model.clear();
        model = (DefaultListModel) this.listLinks.getModel();
        model.clear();
        model = (DefaultListModel) this.listSet.getModel();
        model.clear();
        model = (DefaultListModel) this.listCom.getModel();
        model.clear();
        model = (DefaultListModel) this.listLinksNode.getModel();
        model.clear();
        model = (DefaultListModel) this.listSetNum.getModel();
        model.clear();
        this.txtListComp.setText(null);
        this.labelInfoGraf.setText("Categorias: 0 | Páginas: 0 | Enlaces: 0");
        this.ckTodasCategorias.setSelected(false);
        this.ckTodasPaginas.setSelected(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoAlgoritmos = new javax.swing.ButtonGroup();
        grupoTipoNodo = new javax.swing.ButtonGroup();
        tabsPrincipal = new javax.swing.JTabbedPane();
        panelImportar = new javax.swing.JPanel();
        btnImportarGrafo = new javax.swing.JButton();
        btnImportarConj = new javax.swing.JButton();
        btnNuevoGrafo = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelGrafo = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCatToAddRmv = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPagToAddRmv = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtNodo1Enlace = new javax.swing.JTextField();
        btnAddCatToGraph = new javax.swing.JButton();
        btnRmvCatFromGraph = new javax.swing.JButton();
        btnAddLinkToGraph = new javax.swing.JButton();
        btnAddPagToGraph = new javax.swing.JButton();
        btnRmvPagFromGraph = new javax.swing.JButton();
        btnRmvLinkFromGraph = new javax.swing.JButton();
        txtNodo2Enlace = new javax.swing.JTextField();
        comboTipoEnlace = new javax.swing.JComboBox();
        btnChangeName = new javax.swing.JButton();
        btnListCatGraph = new javax.swing.JButton();
        btnListPagGraph = new javax.swing.JButton();
        btnListLinksGraph = new javax.swing.JButton();
        btnExportarGrafo = new javax.swing.JButton();
        txtNombreNodoAnterior = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombreNodoNuevo = new javax.swing.JTextField();
        btnImportarGrafo1 = new javax.swing.JButton();
        btnNuevoGrafo1 = new javax.swing.JButton();
        radioCategoria = new javax.swing.JRadioButton();
        radioPagina = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        sclistCat = new javax.swing.JScrollPane();
        listCat = new javax.swing.JList();
        sclistPag = new javax.swing.JScrollPane();
        listPag = new javax.swing.JList();
        sclistLinks = new javax.swing.JScrollPane();
        listLinks = new javax.swing.JList();
        sclistLinksNode = new javax.swing.JScrollPane();
        listLinksNode = new javax.swing.JList();
        labelInfoGraf = new javax.swing.JLabel();
        btnVisualizarGrafo = new javax.swing.JButton();
        panelAlgoritmo = new javax.swing.JPanel();
        radioGirvan = new javax.swing.JRadioButton();
        radioLouvain = new javax.swing.JRadioButton();
        radioClique = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        btnEjecutar = new javax.swing.JButton();
        tabsAlgoritmo = new javax.swing.JTabbedPane();
        tabSelCat = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listSelCategorias = new javax.swing.JList();
        ckTodasCategorias = new javax.swing.JCheckBox();
        btnAplicarSelCat = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtMinCatLink = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtMaxCatLink = new javax.swing.JTextField();
        txtCatNameSel = new javax.swing.JTextField();
        btnAddSelCatName = new javax.swing.JButton();
        btnRmvSelCatName = new javax.swing.JButton();
        btnAddSelCatRang = new javax.swing.JButton();
        btnSelCatRand = new javax.swing.JButton();
        labelCatSel = new javax.swing.JLabel();
        tabSelPag = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSelPaginas = new javax.swing.JList();
        ckTodasPaginas = new javax.swing.JCheckBox();
        btnAplicarSelPag = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtPagNameSel = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtMinPagLink = new javax.swing.JTextField();
        txtMaxPagLink = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        btnAddSelPagRang = new javax.swing.JButton();
        btnAddSelPagName = new javax.swing.JButton();
        btnRmvSelPagName = new javax.swing.JButton();
        btnSelPagRand = new javax.swing.JButton();
        labelPagSel = new javax.swing.JLabel();
        tabFiltros = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        spNombre = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        spPagComun = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        spCatComun = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        spSuperComun = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        spSubComun = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        btnAplicarFiltros = new javax.swing.JButton();
        btnRandomFilters = new javax.swing.JButton();
        spinP = new javax.swing.JSpinner();
        panelComunidades = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtAddRmvCom = new javax.swing.JTextField();
        btnAddCatToCom = new javax.swing.JButton();
        btnRmvCatFromCom = new javax.swing.JButton();
        btnAddComToSet = new javax.swing.JButton();
        btnRmvComFromSet = new javax.swing.JButton();
        btnListComFromSet = new javax.swing.JButton();
        btnExportSet = new javax.swing.JButton();
        txtCatAddRmvSet = new javax.swing.JTextField();
        txtComToAddRmvCat = new javax.swing.JTextField();
        txtComToList = new javax.swing.JTextField();
        btnListCatFromCom = new javax.swing.JButton();
        comboTipoSet = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtNombreAnterior = new javax.swing.JTextField();
        txtNombreNuevo = new javax.swing.JTextField();
        btnChangeNameSet = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        btnShowCom = new javax.swing.JButton();
        panelC = new javax.swing.JPanel();
        scListSet = new javax.swing.JScrollPane();
        listSet = new javax.swing.JList();
        scListCom = new javax.swing.JScrollPane();
        listCom = new javax.swing.JList();
        scListSetNum = new javax.swing.JScrollPane();
        listSetNum = new javax.swing.JList();
        jLabel31 = new javax.swing.JLabel();
        btnModP = new javax.swing.JButton();
        spinP1 = new javax.swing.JSpinner();
        txtMinCatAtCom = new javax.swing.JTextField();
        btnListComFromSet1 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        btnShowGraph2 = new javax.swing.JButton();
        panelComparacion = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtListComp = new javax.swing.JTextArea();
        btnCompararComunidades = new javax.swing.JButton();
        txtCompCom1 = new javax.swing.JTextField();
        txtCompCom2 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnCompararConjuntos = new javax.swing.JButton();
        ckCjtoImportado1 = new javax.swing.JCheckBox();
        ckCjtoImportado2 = new javax.swing.JCheckBox();
        barraMenu = new javax.swing.JMenuBar();
        menuFichero = new javax.swing.JMenu();
        mItemNuevoGrafo = new javax.swing.JMenuItem();
        mItemImportarGrafo = new javax.swing.JMenuItem();
        mItemImportarSet = new javax.swing.JMenuItem();
        mItemExportarGrafo = new javax.swing.JMenuItem();
        mItemExportarSet = new javax.swing.JMenuItem();
        mItemSalir = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        mItemManual = new javax.swing.JMenuItem();
        mItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Wiki");
        setMinimumSize(new java.awt.Dimension(1000, 650));
        setResizable(false);

        tabsPrincipal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsPrincipalStateChanged(evt);
            }
        });

        btnImportarGrafo.setText("Importar grafo");
        btnImportarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarGrafoActionPerformed(evt);
            }
        });

        btnImportarConj.setText("Importar Conjunto");
        btnImportarConj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarConjActionPerformed(evt);
            }
        });

        btnNuevoGrafo.setText("Nuevo Grafo");
        btnNuevoGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoGrafoActionPerformed(evt);
            }
        });

        jLabel11.setText("Grafo:");

        jLabel12.setText("Conjunto:");

        javax.swing.GroupLayout panelImportarLayout = new javax.swing.GroupLayout(panelImportar);
        panelImportar.setLayout(panelImportarLayout);
        panelImportarLayout.setHorizontalGroup(
            panelImportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImportarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnImportarGrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImportarConj, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoGrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(778, Short.MAX_VALUE))
        );
        panelImportarLayout.setVerticalGroup(
            panelImportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImportarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImportarGrafo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevoGrafo)
                .addGap(36, 36, 36)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImportarConj)
                .addContainerGap(371, Short.MAX_VALUE))
        );

        tabsPrincipal.addTab("Inicio", panelImportar);

        panelGrafo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel8.setText("Categoria:");

        txtCatToAddRmv.setText("Nombre categoria");
        txtCatToAddRmv.setToolTipText("");
        txtCatToAddRmv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCatToAddRmvMouseReleased(evt);
            }
        });
        txtCatToAddRmv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCatToAddRmvActionPerformed(evt);
            }
        });
        txtCatToAddRmv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCatToAddRmvFocusLost(evt);
            }
        });

        jLabel9.setText("Página:");

        txtPagToAddRmv.setText("Nombre página");
        txtPagToAddRmv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtPagToAddRmvMouseReleased(evt);
            }
        });
        txtPagToAddRmv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPagToAddRmvActionPerformed(evt);
            }
        });
        txtPagToAddRmv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPagToAddRmvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPagToAddRmvFocusLost(evt);
            }
        });

        jLabel10.setText("Enlace:");

        txtNodo1Enlace.setText("Nombre nodo1");
        txtNodo1Enlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNodo1EnlaceMouseReleased(evt);
            }
        });
        txtNodo1Enlace.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNodo1EnlaceFocusLost(evt);
            }
        });

        btnAddCatToGraph.setText("+");
        btnAddCatToGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCatToGraphActionPerformed(evt);
            }
        });

        btnRmvCatFromGraph.setText("-");
        btnRmvCatFromGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvCatFromGraphActionPerformed(evt);
            }
        });

        btnAddLinkToGraph.setText("+");
        btnAddLinkToGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLinkToGraphActionPerformed(evt);
            }
        });

        btnAddPagToGraph.setText("+");
        btnAddPagToGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPagToGraphActionPerformed(evt);
            }
        });

        btnRmvPagFromGraph.setText("-");
        btnRmvPagFromGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvPagFromGraphActionPerformed(evt);
            }
        });

        btnRmvLinkFromGraph.setText("-");
        btnRmvLinkFromGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvLinkFromGraphActionPerformed(evt);
            }
        });

        txtNodo2Enlace.setText("Nombre nodo2");
        txtNodo2Enlace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNodo2EnlaceMouseReleased(evt);
            }
        });
        txtNodo2Enlace.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNodo2EnlaceFocusLost(evt);
            }
        });

        comboTipoEnlace.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CsubC", "CsupC", "CP", "PC" }));
        comboTipoEnlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoEnlaceActionPerformed(evt);
            }
        });

        btnChangeName.setText("OK");
        btnChangeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeNameActionPerformed(evt);
            }
        });

        btnListCatGraph.setText("Categorias");
        btnListCatGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListCatGraphActionPerformed(evt);
            }
        });

        btnListPagGraph.setText("Páginas");
        btnListPagGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListPagGraphActionPerformed(evt);
            }
        });

        btnListLinksGraph.setText("Enlaces");
        btnListLinksGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListLinksGraphActionPerformed(evt);
            }
        });

        btnExportarGrafo.setText("Exportar");
        btnExportarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarGrafoActionPerformed(evt);
            }
        });

        txtNombreNodoAnterior.setText("Anterior");
        txtNombreNodoAnterior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNombreNodoAnteriorMouseReleased(evt);
            }
        });
        txtNombreNodoAnterior.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreNodoAnteriorFocusLost(evt);
            }
        });

        jLabel13.setText("AÑADIR/QUITAR");

        jLabel14.setText("CAMBIAR NOMBRE NODO");

        txtNombreNodoNuevo.setText("Nuevo");
        txtNombreNodoNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNombreNodoNuevoMouseReleased(evt);
            }
        });
        txtNombreNodoNuevo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreNodoNuevoFocusLost(evt);
            }
        });

        btnImportarGrafo1.setText("Importar grafo");
        btnImportarGrafo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarGrafo1ActionPerformed(evt);
            }
        });

        btnNuevoGrafo1.setText("Nuevo Grafo");
        btnNuevoGrafo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoGrafo1ActionPerformed(evt);
            }
        });

        grupoTipoNodo.add(radioCategoria);
        radioCategoria.setSelected(true);
        radioCategoria.setText("Categoria");

        grupoTipoNodo.add(radioPagina);
        radioPagina.setText("Página");

        jScrollPane2.setMaximumSize(new java.awt.Dimension(80, 80));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(80, 80));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(80, 80));

        panel.setMaximumSize(new java.awt.Dimension(80, 80));
        panel.setPreferredSize(new java.awt.Dimension(80, 80));
        panel.setLayout(new java.awt.CardLayout());

        sclistCat.setMaximumSize(new java.awt.Dimension(80, 80));
        sclistCat.setMinimumSize(new java.awt.Dimension(80, 80));
        sclistCat.setPreferredSize(new java.awt.Dimension(80, 80));

        listCat.setModel(new javax.swing.DefaultListModel());
        listCat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listCatMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listCatMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listCatMouseClicked(evt);
            }
        });
        listCat.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listCatValueChanged(evt);
            }
        });
        sclistCat.setViewportView(listCat);

        panel.add(sclistCat, "card1");

        listPag.setModel(new javax.swing.DefaultListModel());
        listPag.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listPag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                listPagMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listPagMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listPagMouseClicked(evt);
            }
        });
        listPag.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listPagValueChanged(evt);
            }
        });
        sclistPag.setViewportView(listPag);

        panel.add(sclistPag, "card2");

        listLinks.setModel(new javax.swing.DefaultListModel());
        listLinks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listLinks.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listLinksValueChanged(evt);
            }
        });
        sclistLinks.setViewportView(listLinks);

        panel.add(sclistLinks, "card3");

        listLinksNode.setModel(new javax.swing.DefaultListModel());
        listLinksNode.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listLinksNode.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listLinksNodeValueChanged(evt);
            }
        });
        sclistLinksNode.setViewportView(listLinksNode);

        panel.add(sclistLinksNode, "card4");

        jScrollPane2.setViewportView(panel);

        labelInfoGraf.setText("Categorias: 0 | Páginas: 0 | Enlaces: 0");

        btnVisualizarGrafo.setText("Visualizar");
        btnVisualizarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarGrafoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGrafoLayout = new javax.swing.GroupLayout(panelGrafo);
        panelGrafo.setLayout(panelGrafoLayout);
        panelGrafoLayout.setHorizontalGroup(
            panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGrafoLayout.createSequentialGroup()
                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGrafoLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelGrafoLayout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radioCategoria)
                                .addGap(18, 18, 18)
                                .addComponent(radioPagina))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelGrafoLayout.createSequentialGroup()
                                    .addComponent(txtNombreNodoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNombreNodoNuevo)
                                    .addGap(34, 34, 34)
                                    .addComponent(btnChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelGrafoLayout.createSequentialGroup()
                                    .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(panelGrafoLayout.createSequentialGroup()
                                            .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtNodo2Enlace, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtNodo1Enlace, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(comboTipoEnlace, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(panelGrafoLayout.createSequentialGroup()
                                            .addComponent(txtCatToAddRmv, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnAddCatToGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnRmvCatFromGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelGrafoLayout.createSequentialGroup()
                                            .addComponent(txtPagToAddRmv, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnAddPagToGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnRmvPagFromGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelGrafoLayout.createSequentialGroup()
                                            .addGap(256, 256, 256)
                                            .addComponent(btnAddLinkToGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(btnRmvLinkFromGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(panelGrafoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnImportarGrafo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevoGrafo1))
                    .addGroup(panelGrafoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExportarGrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVisualizarGrafo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(45, 64, Short.MAX_VALUE)
                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelInfoGraf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelGrafoLayout.createSequentialGroup()
                        .addComponent(btnListCatGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnListPagGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnListLinksGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelGrafoLayout.setVerticalGroup(
            panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGrafoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListCatGraph)
                    .addComponent(btnListPagGraph)
                    .addComponent(btnListLinksGraph)
                    .addComponent(btnImportarGrafo1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoGrafo1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(labelInfoGraf)
                .addGap(18, 18, 18)
                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGrafoLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(21, 21, 21)
                        .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(panelGrafoLayout.createSequentialGroup()
                                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCatToAddRmv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddCatToGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRmvCatFromGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtPagToAddRmv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddPagToGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRmvPagFromGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNodo1Enlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboTipoEnlace, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNodo2Enlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddLinkToGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRmvLinkFromGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(51, 51, 51)
                        .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(radioCategoria)
                            .addComponent(radioPagina))
                        .addGap(21, 21, 21)
                        .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreNodoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreNodoNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addGroup(panelGrafoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExportarGrafo)
                            .addComponent(btnVisualizarGrafo)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        tabsPrincipal.addTab("Grafo", panelGrafo);

        grupoAlgoritmos.add(radioGirvan);
        radioGirvan.setText("Girvan-Newman");
        radioGirvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioGirvanActionPerformed(evt);
            }
        });

        grupoAlgoritmos.add(radioLouvain);
        radioLouvain.setSelected(true);
        radioLouvain.setText("Louvain");

        grupoAlgoritmos.add(radioClique);
        radioClique.setText("K-Clique");

        jLabel1.setText("Valor P:");

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        tabsAlgoritmo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabsAlgoritmo.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabsAlgoritmo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        listSelCategorias.setModel(new javax.swing.DefaultListModel());
        listSelCategorias.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSelCategoriasValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(listSelCategorias);

        ckTodasCategorias.setText("Seleccionar todas");
        ckTodasCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckTodasCategoriasActionPerformed(evt);
            }
        });

        btnAplicarSelCat.setText("Aplicar");
        btnAplicarSelCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarSelCatActionPerformed(evt);
            }
        });

        jLabel23.setText("Selección por nombre:");

        jLabel24.setText("Selección al azar:");

        jLabel28.setText("Selección por intervalo:");

        txtMinCatLink.setText("min");
        txtMinCatLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtMinCatLinkMouseReleased(evt);
            }
        });
        txtMinCatLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMinCatLinkActionPerformed(evt);
            }
        });
        txtMinCatLink.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinCatLinkFocusLost(evt);
            }
        });

        jLabel30.setText("-");

        txtMaxCatLink.setText("max");
        txtMaxCatLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtMaxCatLinkMouseReleased(evt);
            }
        });
        txtMaxCatLink.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaxCatLinkFocusLost(evt);
            }
        });

        txtCatNameSel.setText("Nombre cat");
        txtCatNameSel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCatNameSelMouseReleased(evt);
            }
        });
        txtCatNameSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCatNameSelActionPerformed(evt);
            }
        });
        txtCatNameSel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCatNameSelFocusLost(evt);
            }
        });

        btnAddSelCatName.setText("+");
        btnAddSelCatName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSelCatNameActionPerformed(evt);
            }
        });

        btnRmvSelCatName.setText("-");
        btnRmvSelCatName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvSelCatNameActionPerformed(evt);
            }
        });

        btnAddSelCatRang.setText("Seleccionar");
        btnAddSelCatRang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSelCatRangActionPerformed(evt);
            }
        });

        btnSelCatRand.setText("Seleccionar");
        btnSelCatRand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelCatRandActionPerformed(evt);
            }
        });

        labelCatSel.setText("Categorias seleccionadas: 0");

        javax.swing.GroupLayout tabSelCatLayout = new javax.swing.GroupLayout(tabSelCat);
        tabSelCat.setLayout(tabSelCatLayout);
        tabSelCatLayout.setHorizontalGroup(
            tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSelCatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabSelCatLayout.createSequentialGroup()
                        .addComponent(txtMinCatLink, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaxCatLink, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddSelCatRang))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelCatRand)
                    .addGroup(tabSelCatLayout.createSequentialGroup()
                        .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCatNameSel)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSelCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRmvSelCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSelCatLayout.createSequentialGroup()
                        .addComponent(ckTodasCategorias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(btnAplicarSelCat, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelCatSel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabSelCatLayout.setVerticalGroup(
            tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSelCatLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAplicarSelCat)
                    .addComponent(ckTodasCategorias))
                .addGap(18, 18, 18)
                .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSelCatLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelCatRand)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCatNameSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddSelCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRmvSelCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addGroup(tabSelCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMinCatLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaxCatLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(btnAddSelCatRang, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelCatSel)
                .addGap(15, 15, 15))
        );

        tabsAlgoritmo.addTab("Selección Categorias", tabSelCat);

        listSelPaginas.setModel(new javax.swing.DefaultListModel());
        listSelPaginas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSelPaginasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listSelPaginas);

        ckTodasPaginas.setText("Seleccionar todas");
        ckTodasPaginas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckTodasPaginasActionPerformed(evt);
            }
        });

        btnAplicarSelPag.setText("Aplicar");
        btnAplicarSelPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarSelPagActionPerformed(evt);
            }
        });

        jLabel21.setText("Selección al azar:");

        jLabel22.setText("Selección por nombre:");

        txtPagNameSel.setText("Nombre pag");
        txtPagNameSel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtPagNameSelMouseReleased(evt);
            }
        });
        txtPagNameSel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPagNameSelFocusLost(evt);
            }
        });

        jLabel25.setText("Selección por intervalo:");

        txtMinPagLink.setText("min");
        txtMinPagLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtMinPagLinkMouseReleased(evt);
            }
        });
        txtMinPagLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMinPagLinkActionPerformed(evt);
            }
        });
        txtMinPagLink.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMinPagLinkFocusLost(evt);
            }
        });

        txtMaxPagLink.setText("max");
        txtMaxPagLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtMaxPagLinkMouseReleased(evt);
            }
        });
        txtMaxPagLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaxPagLinkActionPerformed(evt);
            }
        });
        txtMaxPagLink.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaxPagLinkFocusLost(evt);
            }
        });

        jLabel29.setText("-");

        btnAddSelPagRang.setText("Seleccionar");
        btnAddSelPagRang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSelPagRangActionPerformed(evt);
            }
        });

        btnAddSelPagName.setText("+");
        btnAddSelPagName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSelPagNameActionPerformed(evt);
            }
        });

        btnRmvSelPagName.setText("-");
        btnRmvSelPagName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvSelPagNameActionPerformed(evt);
            }
        });

        btnSelPagRand.setText("Seleccionar");
        btnSelPagRand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelPagRandActionPerformed(evt);
            }
        });

        labelPagSel.setText("Páginas seleccionadas: 0");

        javax.swing.GroupLayout tabSelPagLayout = new javax.swing.GroupLayout(tabSelPag);
        tabSelPag.setLayout(tabSelPagLayout);
        tabSelPagLayout.setHorizontalGroup(
            tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabSelPagLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabSelPagLayout.createSequentialGroup()
                        .addComponent(txtMinPagLink, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaxPagLink, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddSelPagRang))
                    .addComponent(btnSelPagRand)
                    .addGroup(tabSelPagLayout.createSequentialGroup()
                        .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtPagNameSel)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSelPagName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRmvSelPagName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSelPagLayout.createSequentialGroup()
                        .addComponent(ckTodasPaginas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                        .addComponent(btnAplicarSelPag, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelPagSel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabSelPagLayout.setVerticalGroup(
            tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabSelPagLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAplicarSelPag)
                    .addComponent(ckTodasPaginas))
                .addGap(18, 18, 18)
                .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabSelPagLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSelPagRand)
                        .addGap(31, 31, 31)
                        .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addGroup(tabSelPagLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAddSelPagName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRmvSelPagName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPagNameSel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(33, 33, 33)
                        .addComponent(jLabel25)
                        .addGap(18, 18, 18)
                        .addGroup(tabSelPagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMinPagLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaxPagLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(btnAddSelPagRang, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPagSel)
                .addGap(15, 15, 15))
        );

        tabsAlgoritmo.addTab("Selección Páginas", tabSelPag);

        jLabel2.setText("Nombre similar:");

        spNombre.setModel(new javax.swing.SpinnerNumberModel(5, 0, 10, 1));

        jLabel3.setText("Páginas en común:");

        spPagComun.setModel(new javax.swing.SpinnerNumberModel(5, 0, 10, 1));

        jLabel4.setText("Categorias en común:");

        spCatComun.setModel(new javax.swing.SpinnerNumberModel(5, 0, 10, 1));

        jLabel5.setText("SuperCat. en común:");

        spSuperComun.setModel(new javax.swing.SpinnerNumberModel(5, 0, 10, 1));

        jLabel6.setText("SubCat. en común:");

        spSubComun.setModel(new javax.swing.SpinnerNumberModel(5, 0, 10, 1));

        jLabel7.setText("Prioridad");

        btnAplicarFiltros.setText("Aplicar");
        btnAplicarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarFiltrosActionPerformed(evt);
            }
        });

        btnRandomFilters.setText("Al azar");
        btnRandomFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomFiltersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabFiltrosLayout = new javax.swing.GroupLayout(tabFiltros);
        tabFiltros.setLayout(tabFiltrosLayout);
        tabFiltrosLayout.setHorizontalGroup(
            tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabFiltrosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(tabFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spPagComun))
                    .addGroup(tabFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spCatComun))
                    .addGroup(tabFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spSuperComun))
                    .addGroup(tabFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spSubComun))
                    .addGroup(tabFiltrosLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spNombre)))
                    .addComponent(btnAplicarFiltros))
                .addGap(49, 49, 49)
                .addComponent(btnRandomFilters)
                .addContainerGap(441, Short.MAX_VALUE))
        );
        tabFiltrosLayout.setVerticalGroup(
            tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabFiltrosLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(spNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRandomFilters))
                .addGap(18, 18, 18)
                .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spPagComun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spCatComun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spSuperComun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(tabFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spSubComun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(26, 26, 26)
                .addComponent(btnAplicarFiltros)
                .addContainerGap(246, Short.MAX_VALUE))
        );

        tabsAlgoritmo.addTab("Filtros", tabFiltros);

        spinP.setModel(new javax.swing.SpinnerNumberModel(50, 0, 100, 10));

        javax.swing.GroupLayout panelAlgoritmoLayout = new javax.swing.GroupLayout(panelAlgoritmo);
        panelAlgoritmo.setLayout(panelAlgoritmoLayout);
        panelAlgoritmoLayout.setHorizontalGroup(
            panelAlgoritmoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAlgoritmoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAlgoritmoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radioLouvain)
                    .addComponent(radioGirvan)
                    .addComponent(radioClique)
                    .addComponent(btnEjecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAlgoritmoLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(1, 1, 1)
                        .addComponent(spinP, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addComponent(tabsAlgoritmo)
                .addContainerGap())
        );
        panelAlgoritmoLayout.setVerticalGroup(
            panelAlgoritmoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAlgoritmoLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(radioLouvain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioGirvan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioClique)
                .addGap(28, 28, 28)
                .addGroup(panelAlgoritmoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(spinP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnEjecutar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelAlgoritmoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabsAlgoritmo, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        tabsPrincipal.addTab("Algoritmo", panelAlgoritmo);

        jLabel18.setText("Categoria:");

        jLabel19.setText("Comunidad:");

        txtAddRmvCom.setText("Nombre comunidad");
        txtAddRmvCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtAddRmvComMouseReleased(evt);
            }
        });
        txtAddRmvCom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAddRmvComFocusLost(evt);
            }
        });

        btnAddCatToCom.setText("+");
        btnAddCatToCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCatToComActionPerformed(evt);
            }
        });

        btnRmvCatFromCom.setText("-");
        btnRmvCatFromCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvCatFromComActionPerformed(evt);
            }
        });

        btnAddComToSet.setText("+");
        btnAddComToSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddComToSetActionPerformed(evt);
            }
        });

        btnRmvComFromSet.setText("-");
        btnRmvComFromSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvComFromSetActionPerformed(evt);
            }
        });

        btnListComFromSet.setText("Listar");
        btnListComFromSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListComFromSetActionPerformed(evt);
            }
        });

        btnExportSet.setText("Exportar");
        btnExportSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportSetActionPerformed(evt);
            }
        });

        txtCatAddRmvSet.setText("Categoria");
        txtCatAddRmvSet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCatAddRmvSetMouseReleased(evt);
            }
        });
        txtCatAddRmvSet.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCatAddRmvSetFocusLost(evt);
            }
        });

        txtComToAddRmvCat.setText("Comunidad");
        txtComToAddRmvCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtComToAddRmvCatMouseReleased(evt);
            }
        });
        txtComToAddRmvCat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComToAddRmvCatFocusLost(evt);
            }
        });

        txtComToList.setText("Nombre comunidad");
        txtComToList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtComToListMouseReleased(evt);
            }
        });
        txtComToList.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComToListFocusLost(evt);
            }
        });

        btnListCatFromCom.setText("Listar");
        btnListCatFromCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListCatFromComActionPerformed(evt);
            }
        });

        comboTipoSet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Creado", "Importado" }));

        jLabel15.setText("AÑADIR/QUITAR ");

        jLabel16.setText("CAMBIAR NOMBRE COMUNIDAD");

        txtNombreAnterior.setText("Anterior");
        txtNombreAnterior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNombreAnteriorMouseReleased(evt);
            }
        });
        txtNombreAnterior.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreAnteriorFocusLost(evt);
            }
        });

        txtNombreNuevo.setText("Nuevo");
        txtNombreNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtNombreNuevoMouseReleased(evt);
            }
        });
        txtNombreNuevo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNombreNuevoFocusLost(evt);
            }
        });

        btnChangeNameSet.setText("OK");
        btnChangeNameSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeNameSetActionPerformed(evt);
            }
        });

        jLabel26.setText("VER CONJUNTO DE COMUNIDADES");

        jLabel27.setText("VER COMUNIDAD");

        btnShowCom.setText("Visualizar");
        btnShowCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowComActionPerformed(evt);
            }
        });

        panelC.setLayout(new java.awt.CardLayout());

        listSet.setModel(new javax.swing.DefaultListModel());
        listSet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listSet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listSetMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSetMouseClicked(evt);
            }
        });
        listSet.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSetValueChanged(evt);
            }
        });
        scListSet.setViewportView(listSet);

        panelC.add(scListSet, "card1");

        listCom.setModel(new javax.swing.DefaultListModel());
        listCom.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listComMouseReleased(evt);
            }
        });
        listCom.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listComValueChanged(evt);
            }
        });
        scListCom.setViewportView(listCom);

        panelC.add(scListCom, "card2");

        listSetNum.setModel(new javax.swing.DefaultListModel());
        listSetNum.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listSetNum.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listSetNumMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listSetNumMouseClicked(evt);
            }
        });
        listSetNum.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listSetNumValueChanged(evt);
            }
        });
        scListSetNum.setViewportView(listSetNum);

        panelC.add(scListSetNum, "card3");

        jLabel31.setText("CAMBIAR FACTOR DE COHESIÓN (P)");

        btnModP.setText("Obtener");
        btnModP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModPActionPerformed(evt);
            }
        });

        spinP1.setModel(new javax.swing.SpinnerNumberModel(50, 0, 100, 10));

        txtMinCatAtCom.setText("2");

        btnListComFromSet1.setText("Listar");
        btnListComFromSet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListComFromSet1ActionPerformed(evt);
            }
        });

        jLabel32.setText("Mín Categorias:");

        btnShowGraph2.setText("Visualizar grafo");
        btnShowGraph2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowGraph2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelComunidadesLayout = new javax.swing.GroupLayout(panelComunidades);
        panelComunidades.setLayout(panelComunidadesLayout);
        panelComunidadesLayout.setHorizontalGroup(
            panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComunidadesLayout.createSequentialGroup()
                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                .addComponent(comboTipoSet, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15)
                                .addComponent(btnShowGraph2))
                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtCatAddRmvSet, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtComToAddRmvCat, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtAddRmvCom, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAddCatToCom, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAddComToSet, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0))
                                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                                        .addComponent(txtNombreAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNombreNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRmvCatFromCom, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRmvComFromSet, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnChangeNameSet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(128, 128, 128))
                                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtMinCatAtCom, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtComToList, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnListCatFromCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(spinP1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnModP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnShowCom, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addComponent(btnListComFromSet1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnListComFromSet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExportSet, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(panelC, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelComunidadesLayout.setVerticalGroup(
            panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComunidadesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComunidadesLayout.createSequentialGroup()
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboTipoSet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnShowGraph2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(btnAddCatToCom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCatAddRmvSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtComToAddRmvCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRmvCatFromCom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtAddRmvCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddComToSet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRmvComFromSet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelComunidadesLayout.createSequentialGroup()
                                .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNombreAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnChangeNameSet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(45, 45, 45)
                                .addComponent(jLabel26))
                            .addComponent(btnListComFromSet, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMinCatAtCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnListComFromSet1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addGap(40, 40, 40)
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnShowCom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnListCatFromCom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtComToList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(panelComunidadesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnModP, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31)
                            .addComponent(spinP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(btnExportSet))
                    .addComponent(panelC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        tabsPrincipal.addTab("Conjunto", panelComunidades);

        txtListComp.setEditable(false);
        txtListComp.setColumns(20);
        txtListComp.setRows(5);
        jScrollPane6.setViewportView(txtListComp);

        btnCompararComunidades.setText("Comparar");
        btnCompararComunidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompararComunidadesActionPerformed(evt);
            }
        });

        txtCompCom1.setText("Nombre comunidad 1");
        txtCompCom1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCompCom1MouseReleased(evt);
            }
        });
        txtCompCom1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompCom1FocusLost(evt);
            }
        });

        txtCompCom2.setText("Nombre comunidad 2");
        txtCompCom2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtCompCom2MouseReleased(evt);
            }
        });
        txtCompCom2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCompCom2FocusLost(evt);
            }
        });

        jLabel17.setText("Comparar 2 comunidades");

        jLabel20.setText("Comparar 2 conjuntos");

        btnCompararConjuntos.setText("Comparar");
        btnCompararConjuntos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompararConjuntosActionPerformed(evt);
            }
        });

        ckCjtoImportado1.setText("Conjunto importado");

        ckCjtoImportado2.setText("Conjunto importado");

        javax.swing.GroupLayout panelComparacionLayout = new javax.swing.GroupLayout(panelComparacion);
        panelComparacion.setLayout(panelComparacionLayout);
        panelComparacionLayout.setHorizontalGroup(
            panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelComparacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComparacionLayout.createSequentialGroup()
                        .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCompararComunidades, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelComparacionLayout.createSequentialGroup()
                                .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCompCom1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                    .addComponent(txtCompCom2))
                                .addGap(10, 10, 10)
                                .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ckCjtoImportado1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                    .addComponent(ckCjtoImportado2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(btnCompararConjuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 53, Short.MAX_VALUE))
                    .addGroup(panelComparacionLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelComparacionLayout.setVerticalGroup(
            panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelComparacionLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelComparacionLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCompCom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ckCjtoImportado1))
                        .addGap(18, 18, 18)
                        .addGroup(panelComparacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCompCom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ckCjtoImportado2))
                        .addGap(18, 18, 18)
                        .addComponent(btnCompararComunidades, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(btnCompararConjuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        tabsPrincipal.addTab("Comparación", panelComparacion);

        menuFichero.setText("Fichero");

        mItemNuevoGrafo.setText("Nuevo grafo");
        mItemNuevoGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemNuevoGrafoActionPerformed(evt);
            }
        });
        menuFichero.add(mItemNuevoGrafo);

        mItemImportarGrafo.setText("Importar grafo");
        mItemImportarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemImportarGrafoActionPerformed(evt);
            }
        });
        menuFichero.add(mItemImportarGrafo);

        mItemImportarSet.setText("Importar conjunto");
        mItemImportarSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemImportarSetActionPerformed(evt);
            }
        });
        menuFichero.add(mItemImportarSet);

        mItemExportarGrafo.setText("Exportar grafo");
        mItemExportarGrafo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemExportarGrafoActionPerformed(evt);
            }
        });
        menuFichero.add(mItemExportarGrafo);

        mItemExportarSet.setText("Exportar conjunto creado");
        mItemExportarSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemExportarSetActionPerformed(evt);
            }
        });
        menuFichero.add(mItemExportarSet);

        mItemSalir.setText("Salir");
        mItemSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mItemSalirMouseClicked(evt);
            }
        });
        mItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemSalirActionPerformed(evt);
            }
        });
        menuFichero.add(mItemSalir);

        barraMenu.add(menuFichero);

        menuAyuda.setText("Ayuda");

        mItemManual.setText("Manual");
        mItemManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemManualActionPerformed(evt);
            }
        });
        menuAyuda.add(mItemManual);

        mItemAbout.setText("Acerca de...");
        mItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemAboutActionPerformed(evt);
            }
        });
        menuAyuda.add(mItemAbout);

        barraMenu.add(menuAyuda);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(tabsPrincipal)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(tabsPrincipal))
        );

        tabsPrincipal.setEnabledAt(1, false);
        tabsPrincipal.setEnabledAt(2, false);
        tabsPrincipal.setEnabledAt(3, false);
        tabsPrincipal.setEnabledAt(4, false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mItemImportarGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemImportarGrafoActionPerformed
        iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(true, true, false);        
    }//GEN-LAST:event_mItemImportarGrafoActionPerformed

    private void mItemSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mItemSalirMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mItemSalirMouseClicked

    private void mItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mItemSalirActionPerformed

    private void mItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemAboutActionPerformed
       this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_About();
    }//GEN-LAST:event_mItemAboutActionPerformed

    private void mItemManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemManualActionPerformed
       this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Manual();
    }//GEN-LAST:event_mItemManualActionPerformed

    private void mItemExportarGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemExportarGrafoActionPerformed
        this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(false, true, false);      
    }//GEN-LAST:event_mItemExportarGrafoActionPerformed

    private void mItemImportarSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemImportarSetActionPerformed
       this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(true, false, false);
    }//GEN-LAST:event_mItemImportarSetActionPerformed

    private void mItemExportarSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemExportarSetActionPerformed
       this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(false, false, false);
    }//GEN-LAST:event_mItemExportarSetActionPerformed

    private void mItemNuevoGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemNuevoGrafoActionPerformed
        this.clearTxtAreas();
        this.catPosToId = new ArrayList();
        this.pagPosToId = new ArrayList();
        this.iCtrlPresentacion.crearGrafo();
    }//GEN-LAST:event_mItemNuevoGrafoActionPerformed

    private void tabsPrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabsPrincipalStateChanged

    }//GEN-LAST:event_tabsPrincipalStateChanged

    private void btnCompararConjuntosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompararConjuntosActionPerformed
        this.txtListComp.setText("");
        boolean a = this.iCtrlPresentacion.existsSet(false);
        boolean b = this.iCtrlPresentacion.existsSet(true);
        if(a)
        {
            int[] infoC1 = iCtrlPresentacion.infoConjunto(false);
            this.txtListComp.append("CONJUNTO CREADO:\n\n");
            this.txtListComp.append("Número de comunidades: "+infoC1[0]+"\n");
            this.txtListComp.append("Algoritmo: "+infoC1[1]+"\n");
            this.txtListComp.append("Nivel de cohesión: "+infoC1[2]+"\n");
            this.txtListComp.append("Prioridad filtro nombre similar: "+infoC1[3]+"\n");
            this.txtListComp.append("Prioridad filtro categorias en común: "+infoC1[4]+"\n");
            this.txtListComp.append("Prioridad filtro páginas en común: "+infoC1[5]+"\n");
            this.txtListComp.append("Prioridad filtro padres en común: "+infoC1[6]+"\n");
            this.txtListComp.append("Prioridad filtro hijos en común: "+infoC1[7]+"\n");
            this.txtListComp.append("Núm. de categorias seleccionadas: "+infoC1[8]+"\n");
            this.txtListComp.append("Núm. de páginas seleccionadas: "+infoC1[9]+"\n\n\n");
            //this.txtListComp.append("Purity: "+Double.toString(this.iCtrlPresentacion.getPurity(false))+"\n\n\n");
        }
        if(b)
        {
            int[] infoC2 = iCtrlPresentacion.infoConjunto(true);
            this.txtListComp.append("CONJUNTO IMPORTADO:\n\n");
            this.txtListComp.append("Número de comunidades: "+infoC2[0]+"\n");
            this.txtListComp.append("Algoritmo: "+infoC2[1]+"\n");
            this.txtListComp.append("Nivel de cohesión: "+infoC2[2]+"\n");
            this.txtListComp.append("Prioridad filtro nombre similar: "+infoC2[3]+"\n");
            this.txtListComp.append("Prioridad filtro categorias en común: "+infoC2[4]+"\n");
            this.txtListComp.append("Prioridad filtro páginas en común: "+infoC2[5]+"\n");
            this.txtListComp.append("Prioridad filtro padres en común: "+infoC2[6]+"\n");
            this.txtListComp.append("Prioridad filtro hijos en común: "+infoC2[7]+"\n");
            this.txtListComp.append("Núm. de categorias seleccionadas: "+infoC2[8]+"\n");
            this.txtListComp.append("Núm. de páginas seleccionadas: "+infoC2[9]+"\n\n\n");            
            //this.txtListComp.append("Purity: "+Double.toString(this.iCtrlPresentacion.getPurity(false))+"\n\n\n");
        }
        if(a && b)
        {
            this.txtListComp.append("Purity1: "+Double.toString(this.iCtrlPresentacion.getAllPurityBoth())+"%\n");
            this.txtListComp.append("Purity2: "+Double.toString(this.iCtrlPresentacion.getPurityBoth())+"%\n\n");
        }
    }//GEN-LAST:event_btnCompararConjuntosActionPerformed

    private void btnShowComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnShowComActionPerformed

    private void btnChangeNameSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeNameSetActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        { 
            this.iCtrlPresentacion.modCtoNombre(this.txtNombreAnterior.getText(), this.txtNombreNuevo.getText(), importado);
            this.actualizarSet(importado);
            this.modConjunto[importado ? 1 : 0] = true;
            //this.actualizarSetNum(importado, this.minCat);
            //this.modConjuntoNum[importado ? 1 : 0] = false;
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
            
    }//GEN-LAST:event_btnChangeNameSetActionPerformed

    private void btnListCatFromComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListCatFromComActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        {         
            ArrayList<String> lista = this.iCtrlPresentacion.mostrarCom(this.txtComToList.getText(), importado);          
            DefaultListModel model = (DefaultListModel) this.listCom.getModel();
            model.clear();
            for(String elem : lista) model.addElement(elem);

            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card2");
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
    }//GEN-LAST:event_btnListCatFromComActionPerformed

    private void btnExportSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportSetActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        { 
            iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(false, false, comboTipoSet.getSelectedIndex() != 0);
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        } 
    }//GEN-LAST:event_btnExportSetActionPerformed

    private void btnListComFromSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListComFromSetActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        { 
            /*if(this.modConjunto[importado ? 1 : 0])
            {
                actualizarSet(importado);
                this.modConjunto[importado ? 1 : 0] = false;
            }
            if(this.modConjuntoNum[importado ? 1 : 0])
            {
                actualizarSetNum(importado, this.minCat);
                this.modConjuntoNum[importado ? 1 : 0] = false;
            }*/
            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card1");
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
        
    }//GEN-LAST:event_btnListComFromSetActionPerformed

    private void btnRmvComFromSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvComFromSetActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        {        
            this.iCtrlPresentacion.rmvCtoCom(this.txtAddRmvCom.getText(), importado);
            this.listSet.setSelectedIndex(1);///
            //this.listSetNum.setSelectedIndex(1);///
            actualizarSet(importado);
            this.modConjunto[importado ? 1 : 0] = true;
            //actualizarSetNum(importado, this.minCat);
            //this.modConjuntoNum[importado ? 1 : 0] = false;  
            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card1");
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
    }//GEN-LAST:event_btnRmvComFromSetActionPerformed

    private void btnAddComToSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddComToSetActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        {
           this.iCtrlPresentacion.addCtoCom(this.txtAddRmvCom.getText(), importado);
                
            DefaultListModel model = (DefaultListModel) this.listSet.getModel();
            model.addElement(this.txtAddRmvCom.getText()+"[0]");

            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card1");
            this.modConjunto[importado ? 1 : 0] = true;
            //this.modConjuntoNum[importado ? 1 : 0] = true;
            
        } 
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
        
    }//GEN-LAST:event_btnAddComToSetActionPerformed

    private void btnRmvCatFromComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvCatFromComActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        if(this.iCtrlPresentacion.existsSet(importado))
        {
            this.iCtrlPresentacion.rmvCtoCat(txtCatAddRmvSet.getText(), txtComToAddRmvCat.getText(), importado);
        
            ArrayList<String> lista = iCtrlPresentacion.mostrarCom(txtComToAddRmvCat.getText(), importado);          
            DefaultListModel model = (DefaultListModel) listCom.getModel();
            model.clear();
            for(String elem : lista) model.addElement(elem);
            this.listCom.setSelectedIndex(1);////
            this.listSet.setSelectedIndex(1);///
            //this.listSetNum.setSelectedIndex(1);///
            this.actualizarSet(importado);
            this.modConjunto[importado ? 1 : 0] = true;
            //this.actualizarSetNum(importado, this.minCat);
            //this.modConjuntoNum[importado ? 1 : 0] = false;
                        

            CardLayout cl = (CardLayout)(panelC.getLayout());
            cl.show(panelC, "card2");
        }        
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
    }//GEN-LAST:event_btnRmvCatFromComActionPerformed

    private void btnAddCatToComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCatToComActionPerformed
        boolean importado = comboTipoSet.getSelectedIndex() != 0;
        if(this.iCtrlPresentacion.existsSet(importado))
        {
            this.iCtrlPresentacion.addCtoCat(txtCatAddRmvSet.getText(), txtComToAddRmvCat.getText(), importado);

            ArrayList<String> lista = iCtrlPresentacion.mostrarCom(txtComToAddRmvCat.getText(), importado);          
            DefaultListModel model = (DefaultListModel) listCom.getModel();
            //model.clear();
            //for(String elem : lista) model.addElement(elem);
            this.actualizarSet(importado);
            this.modConjunto[importado ? 1 : 0] = true;
            //this.actualizarSetNum(importado, this.minCat);
            model.addElement(this.txtCatAddRmvSet.getText());

            CardLayout cl = (CardLayout)(panelC.getLayout());
            cl.show(panelC, "card2"); 
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
        
        
    }//GEN-LAST:event_btnAddCatToComActionPerformed

    private void btnAplicarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarFiltrosActionPerformed
        int name = Integer.parseInt(spNombre.getValue().toString());
        int pagComun = Integer.parseInt(spPagComun.getValue().toString());
        int catComun = Integer.parseInt(spCatComun.getValue().toString());
        int superComun = Integer.parseInt(spSuperComun.getValue().toString());
        int subComun = Integer.parseInt(spSubComun.getValue().toString());
        iCtrlPresentacion.aplicarFiltros(name,pagComun,catComun,superComun,subComun);
    }//GEN-LAST:event_btnAplicarFiltrosActionPerformed

    private void btnSelPagRandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelPagRandActionPerformed
        randomSel(true);
        ckTodasPaginas.setSelected(false);
    }//GEN-LAST:event_btnSelPagRandActionPerformed

    private void btnRmvSelPagNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvSelPagNameActionPerformed
        ckTodasPaginas.setSelected(false);

        int num = iCtrlPresentacion.getPagNum(txtPagNameSel.getText());
        num = pagPosToId.indexOf(num); //Posició a la llista
        int[] indices = listSelPaginas.getSelectedIndices(); //Selecció
        int newIndices[] = new int[indices.length - 1]; //Selecció -1
        int j = 0;
        for(int i = 0; i < indices.length; i++)
        {
            if(indices[i] != num)
            {
                newIndices[j] = indices[i];
                j++;
            }
        }
        listSelPaginas.setSelectedIndices(newIndices);
    }//GEN-LAST:event_btnRmvSelPagNameActionPerformed

    private void btnAddSelPagNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSelPagNameActionPerformed
        ckTodasPaginas.setSelected(false);

        int num = iCtrlPresentacion.getPagNum(txtPagNameSel.getText());
        System.out.println(num);
        num = pagPosToId.indexOf(num);
        System.out.println(num);
        int[] indices = listSelPaginas.getSelectedIndices();
        int newIndices[] = new int[indices.length + 1];
        System.arraycopy(indices, 0, newIndices, 0, indices.length);
        newIndices[indices.length] = num;
        listSelPaginas.setSelectedIndices(newIndices);
    }//GEN-LAST:event_btnAddSelPagNameActionPerformed

    private void btnAddSelPagRangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSelPagRangActionPerformed
        ckTodasPaginas.setSelected(false);

        try 
        {
            int min = Integer.parseInt(txtMinCatLink.getText());            
            int max = Integer.parseInt(txtMaxCatLink.getText());
            if(min < 0 || min>max || max >= this.pagPosToId.size()) this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("Valor incorrecto");
            else this.listSelPaginas.setSelectionInterval(min, max);
        } catch (NumberFormatException numberFormatException) 
        {
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("Por favor, introduce un número");
        }
        
       
        
    }//GEN-LAST:event_btnAddSelPagRangActionPerformed

    private void txtMinPagLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMinPagLinkActionPerformed

    }//GEN-LAST:event_txtMinPagLinkActionPerformed

    private void txtPagNameSelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPagNameSelFocusLost
        if(this.txtPagNameSel.getText().isEmpty())
        {
            this.txtPagNameSel.setText("Nombre pag");
        }
    }//GEN-LAST:event_txtPagNameSelFocusLost

    private void txtPagNameSelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPagNameSelMouseReleased
        if(this.txtPagNameSel.getText().equals("Nombre pag"))
        {
            this.txtPagNameSel.setText("");
        }
    }//GEN-LAST:event_txtPagNameSelMouseReleased

    private void btnAplicarSelPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarSelPagActionPerformed
        int[] index = this.listSelPaginas.getSelectedIndices();
        ArrayList<Integer> intList = new ArrayList<>();
        for(int intValue : index) intList.add(this.pagPosToId.get(intValue));        
        this.iCtrlPresentacion.aplicarSelPag(intList);
        this.tabsAlgoritmo.setSelectedIndex(2);
    }//GEN-LAST:event_btnAplicarSelPagActionPerformed

    private void ckTodasPaginasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckTodasPaginasActionPerformed
        if (ckTodasPaginas.isSelected()) listSelPaginas.setSelectionInterval(0, listSelPaginas.getModel().getSize() - 1);
        else listSelPaginas.clearSelection();
    }//GEN-LAST:event_ckTodasPaginasActionPerformed

    private void btnSelCatRandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelCatRandActionPerformed
        randomSel(false);
        ckTodasCategorias.setSelected(false);
    }//GEN-LAST:event_btnSelCatRandActionPerformed

    private void btnAddSelCatRangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSelCatRangActionPerformed
        ckTodasCategorias.setSelected(false);
        
        try 
        {
            int min = Integer.parseInt(txtMinCatLink.getText());            
            int max = Integer.parseInt(txtMaxCatLink.getText());
            if(min < 0 || min>max || max >= this.catPosToId.size()) this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("Valor incorrecto");
            else this.listSelCategorias.setSelectionInterval(min, max);
        } catch (NumberFormatException numberFormatException) 
        {
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("Por favor, introduce un número");
        }
        
        
    }//GEN-LAST:event_btnAddSelCatRangActionPerformed

    private void btnRmvSelCatNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvSelCatNameActionPerformed
        ckTodasCategorias.setSelected(false);

        int num = iCtrlPresentacion.getCatNum(txtCatNameSel.getText());
        num = catPosToId.indexOf(num); //Posició a la llista
        int[] indices = listSelCategorias.getSelectedIndices(); //Selecció
        int[] newIndices= new int[indices.length - 1]; //Selecció -1
        int j = 0;
        for(int i = 0; i < indices.length; i++)
        {
            if(indices[i] != num)
            {
                newIndices[j] = indices[i];
                j++;
            }
        }
        listSelCategorias.setSelectedIndices(newIndices);
    }//GEN-LAST:event_btnRmvSelCatNameActionPerformed

    private void btnAddSelCatNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSelCatNameActionPerformed
        ckTodasCategorias.setSelected(false);

        int num = iCtrlPresentacion.getCatNum(txtCatNameSel.getText());
        System.out.println(num);
        num = catPosToId.indexOf(num);
        System.out.println(num);
        int[] indices = listSelCategorias.getSelectedIndices();
        int[] newIndices = new int[indices.length + 1];
        System.arraycopy(indices, 0, newIndices, 0, indices.length);
        newIndices[indices.length] = num;
        listSelCategorias.setSelectedIndices(newIndices);

    }//GEN-LAST:event_btnAddSelCatNameActionPerformed

    private void txtCatNameSelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCatNameSelFocusLost
        if(this.txtCatNameSel.getText().isEmpty())
        {
            this.txtCatNameSel.setText("Nombre cat");
        }
    }//GEN-LAST:event_txtCatNameSelFocusLost

    private void txtCatNameSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCatNameSelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCatNameSelActionPerformed

    private void txtCatNameSelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCatNameSelMouseReleased
        if(this.txtCatNameSel.getText().equals("Nombre cat"))
        {
            this.txtCatNameSel.setText("");
        }
    }//GEN-LAST:event_txtCatNameSelMouseReleased

    private void txtMaxCatLinkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaxCatLinkFocusLost
        if(this.txtMaxCatLink.getText().isEmpty())
        {
            this.txtMaxCatLink.setText("max");
        }
    }//GEN-LAST:event_txtMaxCatLinkFocusLost

    private void txtMaxCatLinkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaxCatLinkMouseReleased
        if(this.txtMaxCatLink.getText().equals("max"))
        {
            this.txtMaxCatLink.setText("");
        }
    }//GEN-LAST:event_txtMaxCatLinkMouseReleased

    private void txtMinCatLinkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinCatLinkFocusLost
        if(this.txtMinCatLink.getText().isEmpty())
        {
            this.txtMinCatLink.setText("min");
        }
    }//GEN-LAST:event_txtMinCatLinkFocusLost

    private void txtMinCatLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMinCatLinkActionPerformed

    }//GEN-LAST:event_txtMinCatLinkActionPerformed

    private void txtMinCatLinkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMinCatLinkMouseReleased
        if(this.txtMinCatLink.getText().equals("min"))
        {
            this.txtMinCatLink.setText("");
        }
    }//GEN-LAST:event_txtMinCatLinkMouseReleased

    private void btnAplicarSelCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarSelCatActionPerformed
        int[] index = this.listSelCategorias.getSelectedIndices();
        ArrayList<Integer> intList = new ArrayList<>();
        for(int intValue : index) intList.add(this.catPosToId.get(intValue));
        this.iCtrlPresentacion.aplicarSelCat(intList);
        this.tabsAlgoritmo.setSelectedIndex(1);

    }//GEN-LAST:event_btnAplicarSelCatActionPerformed

    private void ckTodasCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckTodasCategoriasActionPerformed
        if (this.ckTodasCategorias.isSelected()) this.listSelCategorias.setSelectionInterval(0, this.listSelCategorias.getModel().getSize() - 1);
        else this.listSelCategorias.clearSelection();
    }//GEN-LAST:event_ckTodasCategoriasActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        int pVal = Integer.parseInt(this.spinP.getValue().toString());
        int alg = 1;
        if(this.radioGirvan.isSelected()) alg = 2;
        else if(this.radioClique.isSelected()) alg = 3;        
        this.iCtrlPresentacion.ejecutar(alg, pVal);
        this.p = pVal;
        System.out.println("P algoritme: " + this.p);
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void radioGirvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioGirvanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioGirvanActionPerformed

    private void btnNuevoGrafo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoGrafo1ActionPerformed
        this.clearTxtAreas();
        this.catPosToId = new ArrayList();
        this.pagPosToId = new ArrayList();
        this.iCtrlPresentacion.crearGrafo();
    }//GEN-LAST:event_btnNuevoGrafo1ActionPerformed

    private void btnImportarGrafo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarGrafo1ActionPerformed
        this.clearTxtAreas();
        this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(true, true, false);
    }//GEN-LAST:event_btnImportarGrafo1ActionPerformed

    private void txtNombreNodoNuevoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreNodoNuevoFocusLost
        if(this.txtNombreNodoNuevo.getText().isEmpty())
        {
            this.txtNombreNodoNuevo.setText("Nuevo");
        }
    }//GEN-LAST:event_txtNombreNodoNuevoFocusLost

    private void txtNombreNodoNuevoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreNodoNuevoMouseReleased
        if(this.txtNombreNodoNuevo.getText().equals("Nuevo"))
        {
            this.txtNombreNodoNuevo.setText("");
        }
    }//GEN-LAST:event_txtNombreNodoNuevoMouseReleased

    private void txtNombreNodoAnteriorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreNodoAnteriorFocusLost
        if(this.txtNombreNodoAnterior.getText().isEmpty())
        {
            this.txtNombreNodoAnterior.setText("Anterior");
        }
    }//GEN-LAST:event_txtNombreNodoAnteriorFocusLost

    private void txtNombreNodoAnteriorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreNodoAnteriorMouseReleased
        if(this.txtNombreNodoAnterior.getText().equals("Anterior"))
        {
            this.txtNombreNodoAnterior.setText("");
        }
    }//GEN-LAST:event_txtNombreNodoAnteriorMouseReleased

    private void btnExportarGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarGrafoActionPerformed
        this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(false, true, false);
    }//GEN-LAST:event_btnExportarGrafoActionPerformed

    private void btnListLinksGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListLinksGraphActionPerformed
        CardLayout cl = (CardLayout)(this.panel.getLayout());
        cl.show(this.panel, "card3");
        
        if(this.modEnlaces)
        {
            this.actualizarLinks();
            this.modEnlaces = false;
        }
        
    }//GEN-LAST:event_btnListLinksGraphActionPerformed

    private void btnListPagGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListPagGraphActionPerformed
        CardLayout cl = (CardLayout)(this.panel.getLayout());
        cl.show(this.panel, "card2");
    }//GEN-LAST:event_btnListPagGraphActionPerformed

    private void btnListCatGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListCatGraphActionPerformed
        CardLayout cl = (CardLayout)(this.panel.getLayout());
        cl.show(this.panel, "card1");
    }//GEN-LAST:event_btnListCatGraphActionPerformed

    private void btnChangeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeNameActionPerformed

        if(this.radioCategoria.isSelected()) //CATEGORIA
        {
            int id = this.iCtrlPresentacion.modGrafoNombre(this.txtNombreNodoAnterior.getText(), this.txtNombreNodoNuevo.getText(), true);
            if(id != -1)
            {
                int pos = this.catPosToId.indexOf(id);
                this.catPosToId.set(pos, id);
                
                DefaultListModel model = (DefaultListModel) this.listSelCategorias.getModel();                
                model.remove(pos);
                model.add(pos,this.txtNombreNodoNuevo.getText());
                
                model = (DefaultListModel) this.listCat.getModel();                
                model.remove(pos);               
                model.add(pos,this.txtNombreNodoNuevo.getText());  
                
                this.modEnlaces = true;
                
                CardLayout cl = (CardLayout)(this.panel.getLayout());
                cl.show(this.panel, "card1");
            }
        }
        else //PÁGINA
        {
            int id = this.iCtrlPresentacion.modGrafoNombre(this.txtNombreNodoAnterior.getText(), this.txtNombreNodoNuevo.getText(), false);
            if(id != -1)
            {
                int pos = this.pagPosToId.indexOf(id);
                this.pagPosToId.set(pos, id);
                
                DefaultListModel model = (DefaultListModel) this.listSelPaginas.getModel();
                model.remove(pos);
                model.add(pos,this.txtNombreNodoNuevo.getText());
                
                model = (DefaultListModel) this.listPag.getModel();
                model.remove(pos);
                model.add(pos,this.txtNombreNodoNuevo.getText());
                
                this.modEnlaces = true;
                
                CardLayout cl = (CardLayout)(this.panel.getLayout());
                cl.show(this.panel, "card2");
            }
        }
    }//GEN-LAST:event_btnChangeNameActionPerformed

    private void comboTipoEnlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoEnlaceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoEnlaceActionPerformed

    private void txtNodo2EnlaceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNodo2EnlaceFocusLost
        if(this.txtNodo2Enlace.getText().isEmpty())
        {
            this.txtNodo2Enlace.setText("Nombre nodo2");
        }
    }//GEN-LAST:event_txtNodo2EnlaceFocusLost

    private void txtNodo2EnlaceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNodo2EnlaceMouseReleased
        if(this.txtNodo2Enlace.getText().equals("Nombre nodo2"))
        {
            this.txtNodo2Enlace.setText("");
        }
    }//GEN-LAST:event_txtNodo2EnlaceMouseReleased

    private void btnRmvLinkFromGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvLinkFromGraphActionPerformed
        this.iCtrlPresentacion.rmvGrafoEnlace(this.txtNodo1Enlace.getText(), this.txtNodo2Enlace.getText(), this.comboTipoEnlace.getSelectedItem().toString());
        this.listLinks.setSelectedIndex(1);
        this.actualizarLinks();
        this.modEnlaces = false;        
        CardLayout cl = (CardLayout)(this.panel.getLayout());
        cl.show(this.panel, "card3");
        //this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ this.listLinks.getModel().getSize());
    }//GEN-LAST:event_btnRmvLinkFromGraphActionPerformed

    private void btnRmvPagFromGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvPagFromGraphActionPerformed
        int id = iCtrlPresentacion.rmvGrafoPag(txtPagToAddRmv.getText());
        if(id != -1)
        {
            int pos = pagPosToId.indexOf(id);
            DefaultListModel model = (DefaultListModel) listSelPaginas.getModel();            
            model.remove(pos);
            model = (DefaultListModel) listPag.getModel();            
            model.remove(pos);
            listPag.setSelectedIndex(pos);
            pagPosToId.remove(pos);
            this.modEnlaces = true;
        }
        CardLayout cl = (CardLayout)(panel.getLayout());
        cl.show(panel, "card2");
        this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ this.listLinks.getModel().getSize());
    }//GEN-LAST:event_btnRmvPagFromGraphActionPerformed

    private void btnAddPagToGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPagToGraphActionPerformed
        int id = this.iCtrlPresentacion.addGrafoPag(this.txtPagToAddRmv.getText());
        if(id != -1)
        {
            DefaultListModel model = (DefaultListModel) this.listSelPaginas.getModel();            
            model.addElement(this.txtPagToAddRmv.getText());
            model = (DefaultListModel) this.listPag.getModel();            
            model.addElement(this.txtPagToAddRmv.getText());
            this.pagPosToId.add(id);
        }
        CardLayout cl = (CardLayout)(this.panel.getLayout());
        cl.show(this.panel, "card2");
        this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ this.listLinks.getModel().getSize());

    }//GEN-LAST:event_btnAddPagToGraphActionPerformed

    private void btnAddLinkToGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddLinkToGraphActionPerformed
        this.iCtrlPresentacion.addGrafoEnlace(this.txtNodo1Enlace.getText(), this.txtNodo2Enlace.getText(), this.comboTipoEnlace.getSelectedItem().toString());
        this.actualizarLinks();
        this.modEnlaces = false;
        CardLayout cl = (CardLayout)(panel.getLayout());
        cl.show(panel, "card3");
        this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ this.listLinks.getModel().getSize());
    }//GEN-LAST:event_btnAddLinkToGraphActionPerformed

    private void btnRmvCatFromGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvCatFromGraphActionPerformed
        int id = this.iCtrlPresentacion.rmvGrafoCat(this.txtCatToAddRmv.getText());
        if (id != -1)
        {
            int pos = this.catPosToId.indexOf(id);
            DefaultListModel model = (DefaultListModel) this.listSelCategorias.getModel();            
            model.remove(pos);
            model = (DefaultListModel) this.listCat.getModel();            
            model.remove(pos);
            this.listCat.setSelectedIndex(pos);
            this.catPosToId.remove(pos);
            this.modEnlaces = true;
        }
        CardLayout cl = (CardLayout)(this.panel.getLayout());
        cl.show(this.panel, "card1");
        this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ this.listLinks.getModel().getSize());

    }//GEN-LAST:event_btnRmvCatFromGraphActionPerformed

    private void btnAddCatToGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCatToGraphActionPerformed
        int id = this.iCtrlPresentacion.addGrafoCat(txtCatToAddRmv.getText());
        if (id != -1)
        {
            DefaultListModel model = (DefaultListModel) listSelCategorias.getModel();
            model.addElement(txtCatToAddRmv.getText());
            model = (DefaultListModel) listCat.getModel();
            model.addElement(txtCatToAddRmv.getText());
            catPosToId.add(id);
        }
        CardLayout cl = (CardLayout)(panel.getLayout());
        cl.show(panel, "card1");
        this.labelInfoGraf.setText("Categorias: "+this.catPosToId.size()+" | "+"Páginas: "+this.pagPosToId.size()+" | "+"Enlaces: "+ this.listLinks.getModel().getSize());
    }//GEN-LAST:event_btnAddCatToGraphActionPerformed

    private void txtNodo1EnlaceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNodo1EnlaceFocusLost
        if(this.txtNodo1Enlace.getText().isEmpty())
        {
            this.txtNodo1Enlace.setText("Nombre nodo1");
        }
    }//GEN-LAST:event_txtNodo1EnlaceFocusLost

    private void txtNodo1EnlaceMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNodo1EnlaceMouseReleased
        if(this.txtNodo1Enlace.getText().equals("Nombre nodo1"))
        {
            this.txtNodo1Enlace.setText("");
        }
    }//GEN-LAST:event_txtNodo1EnlaceMouseReleased

    private void txtPagToAddRmvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPagToAddRmvFocusLost
        if(this.txtPagToAddRmv.getText().isEmpty())
        {
            this.txtPagToAddRmv.setText("Nombre página");
        }
    }//GEN-LAST:event_txtPagToAddRmvFocusLost

    private void txtPagToAddRmvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPagToAddRmvFocusGained

    }//GEN-LAST:event_txtPagToAddRmvFocusGained

    private void txtPagToAddRmvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPagToAddRmvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPagToAddRmvActionPerformed

    private void txtPagToAddRmvMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPagToAddRmvMouseReleased
        if(this.txtPagToAddRmv.getText().equals("Nombre página"))
        {
            this.txtPagToAddRmv.setText("");
        }
    }//GEN-LAST:event_txtPagToAddRmvMouseReleased

    private void txtCatToAddRmvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCatToAddRmvFocusLost
        if(this.txtCatToAddRmv.getText().isEmpty())
        {
            this.txtCatToAddRmv.setText("Nombre categoria");
        }
    }//GEN-LAST:event_txtCatToAddRmvFocusLost

    private void txtCatToAddRmvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCatToAddRmvActionPerformed

    }//GEN-LAST:event_txtCatToAddRmvActionPerformed

    private void txtCatToAddRmvMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCatToAddRmvMouseReleased
        if(this.txtCatToAddRmv.getText().equals("Nombre categoria"))
        {
            this.txtCatToAddRmv.setText("");
        }
    }//GEN-LAST:event_txtCatToAddRmvMouseReleased

    private void btnNuevoGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoGrafoActionPerformed
        this.clearTxtAreas();
        this.catPosToId = new ArrayList();
        this.pagPosToId = new ArrayList();
        this.iCtrlPresentacion.crearGrafo();
    }//GEN-LAST:event_btnNuevoGrafoActionPerformed

    private void btnImportarConjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarConjActionPerformed
        this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(true, false, false);        
    }//GEN-LAST:event_btnImportarConjActionPerformed

    private void btnImportarGrafoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarGrafoActionPerformed
        this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_FileChooser(true, true, false);
    }//GEN-LAST:event_btnImportarGrafoActionPerformed

    private void listCatValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listCatValueChanged
        if(!this.listCat.isSelectionEmpty())
        {
            this.txtCatToAddRmv.setText(this.listCat.getSelectedValue().toString());////////////////////////////////***************************************
            this.txtNombreNodoAnterior.setText(this.listCat.getSelectedValue().toString());
            this.radioCategoria.setSelected(true);
        }
        
    }//GEN-LAST:event_listCatValueChanged

    private void listPagValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listPagValueChanged
        if(!this.listPag.isSelectionEmpty())
        {
            this.txtPagToAddRmv.setText(this.listPag.getSelectedValue().toString());
            this.txtNombreNodoAnterior.setText(this.listPag.getSelectedValue().toString());
            this.radioPagina.setSelected(true);
        }        
    }//GEN-LAST:event_listPagValueChanged

    private void listCatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listCatMouseClicked
        if (evt.getClickCount() == 2 && !this.listCat.isSelectionEmpty()) 
        {                       
            ArrayList<String> lista = this.iCtrlPresentacion.mostrarGrafoEnlaces(true, this.listCat.getSelectedValue().toString());         
            DefaultListModel model = (DefaultListModel) this.listLinksNode.getModel();
            model.clear();
            for(String elem : lista) model.addElement(elem); 
            
            CardLayout cl = (CardLayout)(this.panel.getLayout());
            cl.show(this.panel, "card4");            
        }
    }//GEN-LAST:event_listCatMouseClicked

    private void listCatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listCatMousePressed
        
        
    }//GEN-LAST:event_listCatMousePressed

    private void listPagMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPagMousePressed
        
    }//GEN-LAST:event_listPagMousePressed

    private void txtMinPagLinkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMinPagLinkFocusLost
        if(this.txtMinPagLink.getText().isEmpty())
        {
            this.txtMinPagLink.setText("min");
        }
    }//GEN-LAST:event_txtMinPagLinkFocusLost

    private void txtMinPagLinkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMinPagLinkMouseReleased
        if(this.txtMinPagLink.getText().equals("min"))
        {
            this.txtMinPagLink.setText("");
        }
    }//GEN-LAST:event_txtMinPagLinkMouseReleased

    private void txtMaxPagLinkFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaxPagLinkFocusLost
        if(this.txtMaxPagLink.getText().isEmpty())
        {
            this.txtMaxPagLink.setText("max");
        }
    }//GEN-LAST:event_txtMaxPagLinkFocusLost

    private void txtMaxPagLinkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaxPagLinkMouseReleased
        if(this.txtMaxPagLink.getText().equals("max"))
        {
            this.txtMaxPagLink.setText("");
        }
    }//GEN-LAST:event_txtMaxPagLinkMouseReleased

    private void txtMaxPagLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaxPagLinkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaxPagLinkActionPerformed

    private void txtCompCom1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompCom1FocusLost
        if(this.txtCompCom1.getText().isEmpty())
        {
            this.txtCompCom1.setText("Nombre comunidad 1");
        }
    }//GEN-LAST:event_txtCompCom1FocusLost

    private void txtCompCom1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompCom1MouseReleased
        if(this.txtCompCom1.getText().equals("Nombre comunidad 1"))
        {
            this.txtCompCom1.setText("");
        }
    }//GEN-LAST:event_txtCompCom1MouseReleased

    private void txtCompCom2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCompCom2FocusLost
        if(this.txtCompCom2.getText().isEmpty())
        {
            this.txtCompCom2.setText("Nombre comunidad 2");
        }
    }//GEN-LAST:event_txtCompCom2FocusLost

    private void txtCompCom2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCompCom2MouseReleased
        if(this.txtCompCom2.getText().equals("Nombre comunidad 2"))
        {
            this.txtCompCom2.setText("");
        }
    }//GEN-LAST:event_txtCompCom2MouseReleased

    private void txtCatAddRmvSetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCatAddRmvSetFocusLost
        if(this.txtCatAddRmvSet.getText().isEmpty())
        {
            this.txtCatAddRmvSet.setText("Categoria");
        }
    }//GEN-LAST:event_txtCatAddRmvSetFocusLost

    private void txtCatAddRmvSetMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCatAddRmvSetMouseReleased
        if(this.txtCatAddRmvSet.getText().equals("Categoria"))
        {
            this.txtCatAddRmvSet.setText("");
        }
    }//GEN-LAST:event_txtCatAddRmvSetMouseReleased

    private void txtComToAddRmvCatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComToAddRmvCatFocusLost
        if(this.txtComToAddRmvCat.getText().isEmpty())
        {
            this.txtComToAddRmvCat.setText("Comunidad");
        }
    }//GEN-LAST:event_txtComToAddRmvCatFocusLost

    private void txtComToAddRmvCatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtComToAddRmvCatMouseReleased
        if(this.txtComToAddRmvCat.getText().equals("Comunidad"))
        {
            this.txtComToAddRmvCat.setText("");
        }
    }//GEN-LAST:event_txtComToAddRmvCatMouseReleased

    private void txtAddRmvComFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddRmvComFocusLost
        if(this.txtAddRmvCom.getText().isEmpty())
        {
            this.txtAddRmvCom.setText("Nombre comunidad");
        }
    }//GEN-LAST:event_txtAddRmvComFocusLost

    private void txtAddRmvComMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAddRmvComMouseReleased
        if(this.txtAddRmvCom.getText().equals("Nombre comunidad"))
        {
            this.txtAddRmvCom.setText("");
        }
    }//GEN-LAST:event_txtAddRmvComMouseReleased

    private void txtNombreAnteriorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreAnteriorFocusLost
        if(this.txtNombreAnterior.getText().isEmpty())
        {
            this.txtNombreAnterior.setText("Anterior");
        }
    }//GEN-LAST:event_txtNombreAnteriorFocusLost

    private void txtNombreAnteriorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreAnteriorMouseReleased
        if(this.txtNombreAnterior.getText().equals("Anterior"))
        {
            this.txtNombreAnterior.setText("");
        }
    }//GEN-LAST:event_txtNombreAnteriorMouseReleased

    private void txtNombreNuevoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNombreNuevoFocusLost
        if(this.txtNombreNuevo.getText().isEmpty())
        {
            this.txtNombreNuevo.setText("Nuevo");
        }
    }//GEN-LAST:event_txtNombreNuevoFocusLost

    private void txtNombreNuevoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNombreNuevoMouseReleased
        if(this.txtNombreNuevo.getText().equals("Nuevo"))
        {
            this.txtNombreNuevo.setText("");
        }
    }//GEN-LAST:event_txtNombreNuevoMouseReleased

    private void txtComToListFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComToListFocusLost
        if(this.txtComToList.getText().isEmpty())
        {
            this.txtComToList.setText("Nombre comunidad");
        }
    }//GEN-LAST:event_txtComToListFocusLost

    private void txtComToListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtComToListMouseReleased
        if(this.txtComToList.getText().equals("Nombre comunidad"))
        {
            this.txtComToList.setText("");
        }
    }//GEN-LAST:event_txtComToListMouseReleased

    private void listSelCategoriasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSelCategoriasValueChanged
        this.labelCatSel.setText("Categorias seleccionadas: "+this.listSelCategorias.getSelectedIndices().length+" de "+this.catPosToId.size());
    }//GEN-LAST:event_listSelCategoriasValueChanged

    private void listSelPaginasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSelPaginasValueChanged
        this.labelPagSel.setText("Páginas seleccionadas: "+this.listSelPaginas.getSelectedIndices().length+" de "+this.pagPosToId.size());
    }//GEN-LAST:event_listSelPaginasValueChanged

    private void listSetValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSetValueChanged
        if(!this.listSet.isSelectionEmpty())
        {
            String s = this.listSet.getSelectedValue().toString();
            int index = s.indexOf("[");
            s = s.substring(0, index);
            this.txtComToAddRmvCat.setText(s);
            this.txtComToList.setText(s);
            this.txtNombreAnterior.setText(s);
            this.txtAddRmvCom.setText(s);            
        }
    }//GEN-LAST:event_listSetValueChanged

    private void listSetMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSetMouseReleased
        
    }//GEN-LAST:event_listSetMouseReleased

    private void listComValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listComValueChanged
        if(!this.listCom.isSelectionEmpty())
        {
            this.txtCatAddRmvSet.setText(this.listCom.getSelectedValue().toString());
        }
    }//GEN-LAST:event_listComValueChanged

    private void listComMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listComMouseReleased
                        
    }//GEN-LAST:event_listComMouseReleased

    private void listLinksValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listLinksValueChanged
        if(!this.listLinks.isSelectionEmpty())
        {            
            String s = this.listLinks.getSelectedValue().toString();
            String data[] = s.split("\\s+");           
            this.txtNodo1Enlace.setText(data[0]);
            this.txtNodo2Enlace.setText(data[3]);    
            this.comboTipoEnlace.setSelectedItem(data[2]);
        }
    }//GEN-LAST:event_listLinksValueChanged

    private void listSetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSetMouseClicked
        if (evt.getClickCount() == 2 && !this.listSet.isSelectionEmpty()) 
        {
            String s = this.listSet.getSelectedValue().toString();
            int index = s.indexOf("[");
            s = s.substring(0, index);
            
            ArrayList<String> lista = this.iCtrlPresentacion.mostrarCom(s, this.comboTipoSet.getSelectedIndex() != 0);          
            DefaultListModel model = (DefaultListModel) this.listCom.getModel();
            model.clear();
            for(String elem : lista) model.addElement(elem);

            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card2");    
        }
    }//GEN-LAST:event_listSetMouseClicked

    private void listPagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPagMouseClicked
        if (evt.getClickCount() == 2 && !this.listPag.isSelectionEmpty()) 
        {                       
            ArrayList<String> lista = this.iCtrlPresentacion.mostrarGrafoEnlaces(false, this.listPag.getSelectedValue().toString());         
            DefaultListModel model = (DefaultListModel) this.listLinksNode.getModel();
            model.clear();
            for(String elem : lista) model.addElement(elem); 
            
            CardLayout cl = (CardLayout)(this.panel.getLayout());
            cl.show(this.panel, "card4");            
        }
    }//GEN-LAST:event_listPagMouseClicked

    private void btnModPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModPActionPerformed
        int pVal = Integer.parseInt(this.spinP1.getValue().toString());
        
        if(this.iCtrlPresentacion.existsSet(false))
        {
            if(pVal != this.p)
            {
                this.iCtrlPresentacion.obtainCjto(pVal);
                this.actualizarSet(false);
                this.actualizarSetNum(false, this.minCat);
                this.modConjunto[0] = false;
                //this.modConjuntoNum[0] = false;
                this.p = pVal;
                this.minCat = -1;                                
            }            
            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card1"); 
        }
        else this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto creado");
    }//GEN-LAST:event_btnModPActionPerformed

    private void btnCompararComunidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompararComunidadesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCompararComunidadesActionPerformed

    private void listCatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listCatMouseReleased
        if(!this.listCat.isSelectionEmpty())
        {
            if (evt.getButton() == MouseEvent.BUTTON1) this.txtNodo1Enlace.setText(this.listCat.getSelectedValue().toString());
            else if (evt.getButton() == MouseEvent.BUTTON3)
            {
                JList list = (JList)evt.getSource();
                int row = list.locationToIndex(evt.getPoint());
                list.setSelectedIndex(row);
                this.txtNodo2Enlace.setText(this.listCat.getSelectedValue().toString());
            } 
        }
    }//GEN-LAST:event_listCatMouseReleased

    private void listPagMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPagMouseReleased
        if(!this.listPag.isSelectionEmpty())
        {
            if (evt.getButton() == MouseEvent.BUTTON1)
            {
                this.txtNodo1Enlace.setText(this.listPag.getSelectedValue().toString());
                this.comboTipoEnlace.setSelectedIndex(3);
            }
            else if (evt.getButton() == MouseEvent.BUTTON3)
            {
                JList list = (JList)evt.getSource();
                int row = list.locationToIndex(evt.getPoint());
                list.setSelectedIndex(row);
                this.txtNodo2Enlace.setText(this.listPag.getSelectedValue().toString());
                this.comboTipoEnlace.setSelectedIndex(2);
            }
        }
    }//GEN-LAST:event_listPagMouseReleased

    private void btnRandomFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomFiltersActionPerformed
        Random r = new Random();
        int val = r.nextInt(11);
        this.spNombre.setValue(val);
        val = r.nextInt(11);
        this.spCatComun.setValue(val);
        val = r.nextInt(11);
        this.spPagComun.setValue(val);
        val = r.nextInt(11);
        this.spSuperComun.setValue(val);
        val = r.nextInt(11);
        this.spSubComun.setValue(val);
    }//GEN-LAST:event_btnRandomFiltersActionPerformed

    private void btnListComFromSet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListComFromSet1ActionPerformed
        boolean importado = this.comboTipoSet.getSelectedIndex() != 0;
        
        if(this.iCtrlPresentacion.existsSet(importado))
        { 
            int min = Integer.parseInt(this.txtMinCatAtCom.getText());
            if(min != this.minCat || this.modConjunto[importado ? 1 : 0]) 
            {
                this.actualizarSetNum(this.comboTipoSet.getSelectedIndex() != 0, min);
                this.minCat = min;
                this.modConjunto[importado ? 1 : 0] = false;
            }
            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card3");
        }
        else 
        {
            String s = "importado";
            if(!importado) s = "creado";
            this.iCtrlPresentacion.sincronizacionVistaPrincipal_a_Error("No hay ningún conjunto "+s);
        }
        
        
         
    }//GEN-LAST:event_btnListComFromSet1ActionPerformed

    private void listLinksNodeValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listLinksNodeValueChanged
        if(!this.listLinksNode.isSelectionEmpty())
        {            
            String s = this.listLinksNode.getSelectedValue().toString();
            String data[] = s.split("\\s+");           
            this.txtNodo1Enlace.setText(data[0]);
            this.txtNodo2Enlace.setText(data[3]);    
            this.comboTipoEnlace.setSelectedItem(data[2]);
        }
    }//GEN-LAST:event_listLinksNodeValueChanged

    private void listSetNumMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSetNumMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_listSetNumMouseReleased

    private void listSetNumMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listSetNumMouseClicked
        if (evt.getClickCount() == 2 && !this.listSetNum.isSelectionEmpty()) 
        {
            String s = this.listSetNum.getSelectedValue().toString();
            int index = s.indexOf("[");
            s = s.substring(0, index);
            
            ArrayList<String> lista = this.iCtrlPresentacion.mostrarCom(s, this.comboTipoSet.getSelectedIndex() != 0);          
            DefaultListModel model = (DefaultListModel) this.listCom.getModel();
            model.clear();
            for(String elem : lista) model.addElement(elem);

            CardLayout cl = (CardLayout)(this.panelC.getLayout());
            cl.show(this.panelC, "card2");    
        }
    }//GEN-LAST:event_listSetNumMouseClicked

    private void listSetNumValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listSetNumValueChanged
        if(!this.listSetNum.isSelectionEmpty())
        {
            String s = this.listSetNum.getSelectedValue().toString();
            int index = s.indexOf("[");
            s = s.substring(0, index);
            this.txtComToAddRmvCat.setText(s);
            this.txtComToList.setText(s);
            this.txtNombreAnterior.setText(s);
            this.txtAddRmvCom.setText(s);            
        }
    }//GEN-LAST:event_listSetNumValueChanged

    private void btnVisualizarGrafoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnVisualizarGrafoActionPerformed
    {//GEN-HEADEREND:event_btnVisualizarGrafoActionPerformed
        this.iCtrlPresentacion.visualizarGrafo();
    }//GEN-LAST:event_btnVisualizarGrafoActionPerformed

    private void btnShowGraph2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnShowGraph2ActionPerformed
    {//GEN-HEADEREND:event_btnShowGraph2ActionPerformed
        this.iCtrlPresentacion.visualizarGrafoGenerado();
    }//GEN-LAST:event_btnShowGraph2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnAddCatToCom;
    private javax.swing.JButton btnAddCatToGraph;
    private javax.swing.JButton btnAddComToSet;
    private javax.swing.JButton btnAddLinkToGraph;
    private javax.swing.JButton btnAddPagToGraph;
    private javax.swing.JButton btnAddSelCatName;
    private javax.swing.JButton btnAddSelCatRang;
    private javax.swing.JButton btnAddSelPagName;
    private javax.swing.JButton btnAddSelPagRang;
    private javax.swing.JButton btnAplicarFiltros;
    private javax.swing.JButton btnAplicarSelCat;
    private javax.swing.JButton btnAplicarSelPag;
    private javax.swing.JButton btnChangeName;
    private javax.swing.JButton btnChangeNameSet;
    private javax.swing.JButton btnCompararComunidades;
    private javax.swing.JButton btnCompararConjuntos;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnExportSet;
    private javax.swing.JButton btnExportarGrafo;
    private javax.swing.JButton btnImportarConj;
    private javax.swing.JButton btnImportarGrafo;
    private javax.swing.JButton btnImportarGrafo1;
    private javax.swing.JButton btnListCatFromCom;
    private javax.swing.JButton btnListCatGraph;
    private javax.swing.JButton btnListComFromSet;
    private javax.swing.JButton btnListComFromSet1;
    private javax.swing.JButton btnListLinksGraph;
    private javax.swing.JButton btnListPagGraph;
    private javax.swing.JButton btnModP;
    private javax.swing.JButton btnNuevoGrafo;
    private javax.swing.JButton btnNuevoGrafo1;
    private javax.swing.JButton btnRandomFilters;
    private javax.swing.JButton btnRmvCatFromCom;
    private javax.swing.JButton btnRmvCatFromGraph;
    private javax.swing.JButton btnRmvComFromSet;
    private javax.swing.JButton btnRmvLinkFromGraph;
    private javax.swing.JButton btnRmvPagFromGraph;
    private javax.swing.JButton btnRmvSelCatName;
    private javax.swing.JButton btnRmvSelPagName;
    private javax.swing.JButton btnSelCatRand;
    private javax.swing.JButton btnSelPagRand;
    private javax.swing.JButton btnShowCom;
    private javax.swing.JButton btnShowGraph2;
    private javax.swing.JButton btnVisualizarGrafo;
    private javax.swing.JCheckBox ckCjtoImportado1;
    private javax.swing.JCheckBox ckCjtoImportado2;
    private javax.swing.JCheckBox ckTodasCategorias;
    private javax.swing.JCheckBox ckTodasPaginas;
    private javax.swing.JComboBox comboTipoEnlace;
    private javax.swing.JComboBox comboTipoSet;
    private javax.swing.ButtonGroup grupoAlgoritmos;
    private javax.swing.ButtonGroup grupoTipoNodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel labelCatSel;
    private javax.swing.JLabel labelInfoGraf;
    private javax.swing.JLabel labelPagSel;
    private javax.swing.JList listCat;
    private javax.swing.JList listCom;
    private javax.swing.JList listLinks;
    private javax.swing.JList listLinksNode;
    private javax.swing.JList listPag;
    private javax.swing.JList listSelCategorias;
    private javax.swing.JList listSelPaginas;
    private javax.swing.JList listSet;
    private javax.swing.JList listSetNum;
    private javax.swing.JMenuItem mItemAbout;
    private javax.swing.JMenuItem mItemExportarGrafo;
    private javax.swing.JMenuItem mItemExportarSet;
    private javax.swing.JMenuItem mItemImportarGrafo;
    private javax.swing.JMenuItem mItemImportarSet;
    private javax.swing.JMenuItem mItemManual;
    private javax.swing.JMenuItem mItemNuevoGrafo;
    private javax.swing.JMenuItem mItemSalir;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuFichero;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelAlgoritmo;
    private javax.swing.JPanel panelC;
    private javax.swing.JPanel panelComparacion;
    private javax.swing.JPanel panelComunidades;
    private javax.swing.JPanel panelGrafo;
    private javax.swing.JPanel panelImportar;
    private javax.swing.JRadioButton radioCategoria;
    private javax.swing.JRadioButton radioClique;
    private javax.swing.JRadioButton radioGirvan;
    private javax.swing.JRadioButton radioLouvain;
    private javax.swing.JRadioButton radioPagina;
    private javax.swing.JScrollPane scListCom;
    private javax.swing.JScrollPane scListSet;
    private javax.swing.JScrollPane scListSetNum;
    private javax.swing.JScrollPane sclistCat;
    private javax.swing.JScrollPane sclistLinks;
    private javax.swing.JScrollPane sclistLinksNode;
    private javax.swing.JScrollPane sclistPag;
    private javax.swing.JSpinner spCatComun;
    private javax.swing.JSpinner spNombre;
    private javax.swing.JSpinner spPagComun;
    private javax.swing.JSpinner spSubComun;
    private javax.swing.JSpinner spSuperComun;
    private javax.swing.JSpinner spinP;
    private javax.swing.JSpinner spinP1;
    private javax.swing.JPanel tabFiltros;
    private javax.swing.JPanel tabSelCat;
    private javax.swing.JPanel tabSelPag;
    private javax.swing.JTabbedPane tabsAlgoritmo;
    private javax.swing.JTabbedPane tabsPrincipal;
    private javax.swing.JTextField txtAddRmvCom;
    private javax.swing.JTextField txtCatAddRmvSet;
    private javax.swing.JTextField txtCatNameSel;
    private javax.swing.JTextField txtCatToAddRmv;
    private javax.swing.JTextField txtComToAddRmvCat;
    private javax.swing.JTextField txtComToList;
    private javax.swing.JTextField txtCompCom1;
    private javax.swing.JTextField txtCompCom2;
    private javax.swing.JTextArea txtListComp;
    private javax.swing.JTextField txtMaxCatLink;
    private javax.swing.JTextField txtMaxPagLink;
    private javax.swing.JTextField txtMinCatAtCom;
    private javax.swing.JTextField txtMinCatLink;
    private javax.swing.JTextField txtMinPagLink;
    private javax.swing.JTextField txtNodo1Enlace;
    private javax.swing.JTextField txtNodo2Enlace;
    private javax.swing.JTextField txtNombreAnterior;
    private javax.swing.JTextField txtNombreNodoAnterior;
    private javax.swing.JTextField txtNombreNodoNuevo;
    private javax.swing.JTextField txtNombreNuevo;
    private javax.swing.JTextField txtPagNameSel;
    private javax.swing.JTextField txtPagToAddRmv;
    // End of variables declaration//GEN-END:variables
}
