package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Graf generic
 * @param <T> El tipus del pes de les arestes
 * @author Cluster 7 sub 1
 */
public class Graph<K,T> {
	
	private ArrayList<K> vertexs;
	private HashMap<K,HashMap<K,T>> edges;
	
	/**
	 * Inicialitza les llistes de vertexs i d'arestes
	 */
	public Graph() {
		vertexs = new ArrayList<K>();
		edges = new HashMap<K,HashMap<K,T>>();
	}
	
	/**
	 * Inicialitza les llistes de vertexs i d'arestes
	 */
	public Graph(int initialCapacity, float loadFactor) {
		vertexs = new ArrayList<K>();
		edges = new HashMap<K,HashMap<K,T>>(initialCapacity, loadFactor);
	}

	/**
	 * Constructor per a clonar grafs
	 * @param toClone Graph a clonar
	 */
	@SuppressWarnings("unchecked")
	public Graph (Graph<K,T> toClone) {
		// Clonar vertexs
		vertexs = (ArrayList<K>) toClone.vertexs.clone();
		
		// Clonar arestes
		// Inicialitzar HashMap
		edges = new HashMap<K,HashMap<K,T>>();
		// Agafar la llista de vertexs amb arestes
		Set<K> keys = toClone.edges.keySet();
		
		// Iterador dels vertexs
		Iterator<K> iKeys = keys.iterator();
		while (iKeys.hasNext()) {
			// Clona la llista d'arestes
			K next = iKeys.next();
			edges.put(next, new HashMap<K,T> (toClone.edges.get(next)));
		}
	}
	
	/**
	 * Clona el graph i el retorna
	 */
	@SuppressWarnings("unchecked")
	public Graph<K,T> clone () {
		Graph<K,T> oClone = new Graph<K,T>();
		// Clonar vertexs
		oClone.vertexs = (ArrayList<K>) vertexs.clone();
		
		// Clonar arestes
		oClone.edges = new HashMap<K,HashMap<K,T>>();
		// Agafar la llista de vertexs amb arestes
		Set<K> keys = this.edges.keySet();
		
		// Iterador dels vertexs
		Iterator<K> iKeys = keys.iterator();
		while (iKeys.hasNext()) {
			// Clona la llista d'arestes
			K next = iKeys.next();
			oClone.edges.put(next, (HashMap<K,T>) this.edges.get(next).clone());
		}
		
		return oClone;
	}
	
	/**
	 * Override de l'equal. Compara l'objecte 'o' amb el Graph<K,T> nostre.
	 */
	@Override
	@SuppressWarnings("unchecked")
    public boolean equals(Object o) {
		// Si son el mateix objecte, true
		if (o == this) return true;
		
		// Si l'objecte no es una instancia de Graph, false
		if (!(o instanceof Graph)) return false;
		
		try {
			// Convertir-lo a l'objecte que es realment
			Graph<K,T> g = (Graph<K,T>) o;
			return (g.vertexs.equals(vertexs) && g.edges.equals(edges));
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Afegim un vertex al graf
	 * @param v El vertex
	 */
	public void addVertex(K v) {
		if (vertexs.contains(v)) return;
		
		// Afegim el vertex a la llista de vertexs
		vertexs.add(v);
		
		// Inicialitzem al hashMap la llista d'arestes d'aquest vertex
		HashMap<K,T> vEdges = new HashMap<K,T>();
		edges.put(v, vEdges);
	}
	
	/**
	 * Afegeix una aresta al graf
	 * @param a Un vertex de l'aresta
	 * @param b L'altre vertex de l'aresta
	 * @param v El pes de l'aresta
	 */
	public void addEdge(K a, K b, T v) {
		// Obtenim la llista d'arestes que surten del vertex 'a'
		HashMap<K,T> vAEdges = getEdges(a);
		
		if (vAEdges.containsKey(b)) return;
		
		// Afegim l'aresta previament creada
		vAEdges.put(b, v);
		
		// Obtenim la llista d'arestes que surten del vertex 'b'
		HashMap<K,T> vBEdges = getEdges(b);
		// Afegim l'aresta previament creada
		vBEdges.put(a, v);
	}
	
	public void addEdgeUnidir(K a, K b, T v) {
		// Obtenim la llista d'arestes que surten del vertex 'a'
		HashMap<K,T> vAEdges = getEdges(a);
		// Afegim l'aresta previament creada
		vAEdges.put(b, v);
	}
	
	/**
	 * Obte la llista d'arestes del vertex 'v'
	 * @param v El vertex
	 * @return HashMap amb les arestes
	 */
	public HashMap<K,T> getEdges(K v) {
		if (edges.containsKey(v))
			return edges.get(v);
		
		// No existeix el vertex a la llista de vertexs, retornar HashMap buit
		return new HashMap<K,T>();
	}
	
	/**
	 * Obte la llista de vertexs
	 * @return ArrayList dels vertexs
	 */
	public ArrayList<K> getVertexs() {
		return vertexs;
	}
	
	/**
	 * Obte la llista de veins
	 * @param v Vertex origen
	 * @return Llista de vertexs veins al vertex origen.
	 */
	public ArrayList<K> getNeighbors(K v) {
		ArrayList<K> neighs = new ArrayList<K>();
	
		// Afegir el desti de cada aresta que surt de 'v'
		Set<K> keys = getEdges(v).keySet(); 
		for (K key: keys)
			neighs.add(key);
		
		return neighs;
	}
	
	/**
	 * Elimina una aresta del graf
	 * @param o Vertex origen de l'aresta
	 * @param d Vertex desti de l'aresta
	 */
	public void removeEdge(K o, K d) {
		HashMap<K,T> edges = getEdges(o);
		if (!edges.isEmpty() && edges.containsKey(d))
			edges.remove(d);
		
		edges = getEdges(d);
		if (!edges.isEmpty() && edges.containsKey(o))
			edges.remove(o);
	}
	
	/**
	 * Elimina un vertex del graf
	 * @param v El vertex
	 */
	public void removeVertex(K v) {
		
		if (vertexs.contains(v)) {
			vertexs.remove(v);
			edges.remove(v);
			Iterator<K> esI = edges.keySet().iterator();
			
			while (esI.hasNext()) {
				K next = esI.next();
				HashMap<K,T> actuals = edges.get(next);
				
				if (actuals.containsKey(v))
					actuals.remove(v);
			}
				
		}
	}
	
	/**
	 * Obte l'aresta d'un vertex origen a un vertex desti
	 * @param o El vertex origen
	 * @param d El vertex desti
	 * @return El valor de l'aresta
	 */
	public T getEdge(K o, K d) {
		if (edges.containsKey(o)) {
			HashMap<K,T> es = getEdges(o);
			if (es.containsKey(d))
				return es.get(d);
		}
		
		return null;
	}
}