package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Class for applying the Girvan-Newman algorithm to a graph
 * @author Cluster 7 sub 1
 * @version 1.0
 */
public class GirvanNewman extends Algorithm
{
    public GirvanNewman(){}
    
    /**
     * Appicar l'algorisme de Girvan-Newman
     * @param g Graf al que apliquem l'algorisme
     */
    @Override
    public void calc(Graph<Integer,Double> g1){
    	
    	HashMap<Integer,HashMap<Integer,Double>> betw = new HashMap<Integer,HashMap<Integer,Double>>();
    	graphs = new HashMap<Integer, Graph<Integer,Double>>();
    	Graph<Integer,Double> g = g1.clone();
    	Integer totalArestes = calcArestes(g);
    	graphs.put(0, g.clone());
    	
    	// 1. Calcular/Recalcular la "betweeness" de cada aresta del graf
    	while (calcBetw(g, betw)) { 
	    	// 2. Treiem l'aresta amb el pes més alt
    		Edge<Integer,Double> higherEdge = null;
    		Iterator<Integer> i1V = betw.keySet().iterator();
    		
    		// Per cada aresta
    		while (i1V.hasNext()) {
    			Integer nextInt = i1V.next();
    			Iterator<Integer> i2V = betw.get(nextInt).keySet().iterator();
        		while (i2V.hasNext()) {
        			Integer next2Int = i2V.next();
        			
        			// Mirar si l'aresta existeix i te el pes mes alt
        			if (g.getEdge(nextInt, next2Int) != null && (higherEdge == null || betw.get(nextInt).get(next2Int) > higherEdge.getValue()))
        				higherEdge = g.getEdge(nextInt, next2Int);
        		}
    		}
    		
    		g.removeEdge(higherEdge.getOrigin(), higherEdge.getDesti());

    		Integer i = calcArestes(g);
    		Double perc = ((double)(i/(totalArestes*1.0)))*100;
    		graphs.put(100-perc.intValue(), g.clone());
    		//System.out.println("Arestes: " + i + " de "+totalArestes+": " + perc.intValue() + "%");
    	}
    }
    
    private Integer calcArestes(Graph<Integer,Double> g) {
    	Integer i = 0;
    	
		for (Integer v: g.getVertexs()) {
			i += g.getEdges(v).size();
		}
		
		return i;
    }
    
    private boolean calcBetw(Graph<Integer,Double> g, HashMap<Integer,HashMap<Integer,Double>> dists) {
    	boolean connected = false;
    	
    	// Per cada vertex
    	ArrayList<Integer> vertexs = g.getVertexs();
    	for (Integer v: vertexs) {
    		HashMap<Integer,Double> hm = new HashMap<Integer,Double>();
    		for (Integer u: vertexs) {
        		// Inicialitzar el seu HashMap amb distancies infinites si no esta conectat amb un vertex
    			hm.put(u, Double.POSITIVE_INFINITY);
    			
    			// Si es el mateix vertex, la distancia es 0
    			if (u == v) hm.put(u, 0.0);
    			else {
    				// Si estan conectats, posar distancia = pes
    				Edge<Integer,Double> edge = g.getEdge(v, u);
    				if (edge != null) {
    					connected = true;
    					hm.put(u, 1/edge.getValue());
    				}
    			}
    		}
    		
    		// Afegir la llista d'adjacencies al hashmap
			dists.put(v, hm);
    	}
    	
    	// Si no hi ha cap aresta, retornem fals
    	if (!connected) return false;	
    	
    	// Guardem només els camins més curts
		for (int k=0; k<vertexs.size(); k++) {
			for (int i=0; i<vertexs.size(); i++) {
				for (int j=0; j<vertexs.size(); j++) {
					double sum = dists.get(vertexs.get(i)).get(vertexs.get(k)) + 
								 dists.get(vertexs.get(k)).get(vertexs.get(j));
					if (dists.get(vertexs.get(i)).get(vertexs.get(k)) > sum)
						dists.get(vertexs.get(i)).put(vertexs.get(j), sum);
				}
			}
    	}
		
		return true;
    }
    

    /**
     * Select solution for the user
     * @param p Integer between 0 and 100
     * @return Correct graph solution for user.
     */
    @Override
    public ArrayList<ArrayList<Integer>> obtain(){
    	ArrayList<ArrayList<Integer>> coms = new ArrayList<ArrayList<Integer>>();
    	Graph<Integer,Double> graf = closestGraph();
    	
    	ArrayList<Integer> visitats = new ArrayList<Integer>();

    	if (graf == null) return coms;
    	
    	for (Integer v: graf.getVertexs()) {
    		ArrayList<Integer> current = new ArrayList<Integer>();
    		if (visitats.contains(v)) continue;
    		current.add(v);
    		
    		Stack<Integer> sgV = new Stack<Integer>();
    		sgV.push(v);
    		
    		while(!sgV.isEmpty()) {
    			Integer vertex = sgV.pop();
    			
    			for (Edge<Integer,Double> n: graf.getEdges(vertex)) {
            		if (visitats.contains(n.getDesti())) continue;
            		visitats.add(n.getDesti());
            		
            		if (!current.contains(n.getOrigin()))
            			current.add(n.getOrigin());
            		
            		if (!current.contains(n.getDesti()))
            			current.add(n.getDesti());
            		
            		sgV.push(n.getDesti());
            	}
    		}

    		coms.add(current);
    	}
    	
    	for (int i=0; i<coms.size(); i++) {
    		if (coms.get(i).isEmpty())
    			coms.remove(coms.get(i));
    	}
    	
    	return coms;
    }
    
    private Graph<Integer,Double> closestGraph() {
    	Graph<Integer,Double> graf = new Graph<Integer,Double>();
    	
    	if (graphs.containsKey(p)) return graphs.get(p);
    	
    	Iterator<Integer> iK = graphs.keySet().iterator();
    	Integer closest = 0;
    	
    	while (iK.hasNext()){
    		Integer next = iK.next();
    		
    		if (abs(next-p) < (abs(closest-p)))
    			closest = next;
    	}
    	graf = graphs.get(closest);
    	
    	return graf;
    }
    
    private Integer abs(Integer i) {
    	if (i < 0) return i*(-1);
    	return i;
    }
}