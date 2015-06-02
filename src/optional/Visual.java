package optional;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.BasicRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * La clase Visual se encarga de representar en una JFrame o en un JPanel
 * un grafo.
 * Para hacerlo, cuenta con 5 layouts disponibles 0-4
 * ·FRLayout
 * ·CircleLayout
 * ·ISOMLayout
 * ·SpringLayout
 * ·KKLayout
 * Solo hay que, en función de la necesidad, llamar a launchWindow si deseamos
 * que el grafo se muestre en una ventana nueva o, si se desde, llamar a
 * getGraphPanel para obtener un JPanel listo para añadir en otro componente.
 * @author Javier López Calderón
 * @param <V> Vertex origin for internal graph
 * @param <V> Vertex destiny for internal graph
 */
public final class Visual<V>
{
    private Graph<V, V> g;
    private Layout layout;
    private final Renderer renderer;
    private VisualizationViewer viasualization;
    private final DefaultModalGraphMouse modalMouse;
    
    /**
     * Constructor de la clase visual
     * @param vertex lista de vertices para el grafo a representar
     * @param edges todas las aristas que deben estar presentes en el grafo
     * @param layout que tipo de layout escoger para pintar el grafo
     * @param color color de fondo para la ventana
     */
    public Visual(ArrayList<V> vertex, ArrayList<ArrayList<V>> edges, int layout, Color color)
    {
        this.renderer = new BasicRenderer();
        this.setGraph(vertex, edges);
        this.setLayout(layout);
        this.viasualization = new VisualizationViewer(this.layout);
        this.setBackgroundColor(color);
        this.viasualization.setRenderer(this.renderer);
        this.modalMouse = new DefaultModalGraphMouse();
        this.viasualization.setGraphMouse(this.modalMouse);
    }
    
    /**
     * Permite
     * @param selected 
     */
    private void setLayout(int selected)
    {
        switch(selected)
        {
            case 0:
                this.layout = new FRLayout(this.g);
                this.viasualization = new VisualizationViewer(this.layout);
                break;
            case 1:
                this.layout = new CircleLayout(this.g);
                this.viasualization = new VisualizationViewer(this.layout);
                break;
            case 2:
                this.layout = new ISOMLayout(this.g);
                this.viasualization = new VisualizationViewer(this.layout);
                break;
            case 3:
                this.layout = new SpringLayout(this.g);
                this.viasualization = new VisualizationViewer(this.layout);
                break;
            case 4:
                this.layout = new KKLayout(this.g);
                this.viasualization = new VisualizationViewer(this.layout);
                break;
        }
    }

    private void setGraph(ArrayList<V> vertex, ArrayList<ArrayList<V>> edges)
    {
        this.g = new DirectedSparseMultigraph<>();
        int size = this.g.getVertexCount();
        for(V v : vertex)
        {
            this.g.addVertex(v);
        }
        for(V v : this.g.getVertices())
        {
            if(edges.contains(v))
            {
                for(V v2 : edges.get(edges.indexOf(v)))
                {
                    this.g.addEdge(v, v, v2, EdgeType.DIRECTED);
                }
            }
        }
    }

    private void setBackgroundColor(Color color)
    {
        this.viasualization.setBackground(color);
    }

    public void launchWindow()
    {
        JFrame frame = new JFrame();
        frame.add(this.viasualization);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getGraphPanel()
    {
        JPanel panel = new JPanel();
        panel.add(this.viasualization);
        panel.setVisible(true);
        return panel;
    }
}