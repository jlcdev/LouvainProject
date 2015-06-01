package optional;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.BasicRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Javier López Calderón
 * @param <V> Vertex for internal graph
 * @param <E> Type of Edge for internal graph
 */
public class Visual <V,E>
{
    private Graph<V,E> g;
    private Layout layout;
    private Renderer renderer;
    private VisualizationViewer viasualization;
    private DefaultModalGraphMouse modalMouse;
    
    public Visual(ArrayList<V> vertex, HashMap<V, HashMap<V,E>> edges, int layout, Color color)
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
    
    public void setLayout(int selected)
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
    
    public void setGraph(ArrayList<V> vertex, HashMap<V, HashMap<V,E>> edges)
    {
        this.g = new SparseGraph();
        Iterator<Entry<V, HashMap<V, E>>> it = edges.entrySet().iterator();
        for(V v : vertex)
        {
            this.g.addVertex(v);
        }
        while(it.hasNext())
        {
            Entry<V, HashMap<V, E>> entry = it.next();
            Iterator<Entry<V, E>> at = entry.getValue().entrySet().iterator();
            while(at.hasNext())
            {
                Entry<V, E> edge = at.next();
                g.addVertex(entry.getKey());
                g.addVertex(edge.getKey());
                
                    this.g.addEdge(edge.getValue(), entry.getKey(), edge.getKey());
                
            }
        }
    }
    
    public void setBackgroundColor(Color color)
    {
        this.viasualization.setBackground(color);
    }
    
    public void launchWindow()
    {
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.add(this.viasualization);
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
