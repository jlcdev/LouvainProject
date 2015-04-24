package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Aresta generica
 * @param <T> El tipus del pes de l'aresta
 */
public class Edge<K,T> {
	
	private K key;
	private T value;
	
	/**
	 * Crea l'aresta
	 * @param k Vertex desti
	 * @param v Pes de l'aresta
	 */
	public Edge(K k, T v) {
		key = k;
		value = v;
	}

	/**
	 * Constructor per a clonar arestes
	 * @param toClone Edge a clonar
	 */
	public Edge (Edge<K,T> toClone) {
		// Clonar clau
		key = toClone.key;
		
		// Clonar valor
		value = toClone.value;
	}
	
	/**
	 * Override de l'equal. Compara l'objecte 'o' amb el Edge<K,T> nostre.
	 */
	@Override
    public boolean equals(Object o) {
		// Si son el mateix objecte, true
		if (o == this) return true;
			
		// Si l'objecte no es una instancia de Graph, false
		if (!(o instanceof Edge)) return false;
		try {
			// Convertir-lo a l'objecte que es realment
			Edge<K,T> e = (Edge<K,T>) o;
			return (e.key == key && e.value == value);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Obte el pes de l'aresta
	 * @return value, el pes de l'aresta
	 */
	public T getValue() { return value; }
	
	/**
	 * Obte el desti de l'aresta
	 * @return key, el vertex desti de l'aresta
	 */
	public K getKey() { return key; }
	
	/**
	 * Modifica el pes de l'aresta
	 * @param val El nou pes de l'aresta
	 */
	public void setValue(T val) { value = val; }
        
        @Override
        public String toString()
        {
            return "key: "+this.key+"  value: "+this.value;
        }
}