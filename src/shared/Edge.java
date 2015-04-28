package shared;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Aresta generica
 * @param <T> El tipus del pes de l'aresta
 */
public class Edge<K,T> {
	
	private K origen, desti;
	private T value;
	
	/**
	 * Crea l'aresta
	 * @param o Vertex origen
	 * @param d Vertex desti
	 * @param v Pes de l'aresta
	 */
	public Edge(K o, K d, T v) {
		origen = o;
		desti = d;
		value = v;
	}

	/**
	 * Constructor per a clonar arestes
	 * @param toClone Edge a clonar
	 */
	@SuppressWarnings("unchecked")
	public Edge (Edge<K,T> toClone) {
		Class<?> kClass = toClone.origen.getClass();
		Class<?> vClass = toClone.value.getClass();
		try {
			// Clonar clau
			Constructor<?> kConstructor = kClass.getConstructor(kClass);
			this.origen = (K) kConstructor.newInstance(toClone.origen);
			this.desti = (K) kConstructor.newInstance(toClone.desti);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			this.origen = toClone.origen;
			this.desti = toClone.desti;
		}
		try {
			// Clonar valor
			Constructor<?> vConstructor = vClass.getConstructor(vClass);
			this.value = (T) vConstructor.newInstance(toClone.value);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			this.value = toClone.value;
		}
		
	}

	@SuppressWarnings("unchecked")
	public Edge<K,T> clone () {
		K o,d;
		T val;
		
		Class<?> kClass = this.origen.getClass();
		Class<?> vClass = this.value.getClass();
		try {
			// Clonar clau
			Constructor<?> kConstructor = kClass.getConstructor(kClass);
			o = (K) kConstructor.newInstance(this.origen);
			d = (K) kConstructor.newInstance(this.desti);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			o = this.origen;
			d = this.desti;
		}
		try {
			// Clonar valor
			Constructor<?> vConstructor = vClass.getConstructor(vClass);
			val = (T) vConstructor.newInstance(this.value);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			val = this.value;
		}

		Edge<K,T> eClone = new Edge<K,T>(o,d,val);
		return eClone;
	}
	
	
	
	/**
	 * Override de l'equal. Compara l'objecte 'o' amb el Edge<K,T> nostre.
	 */
	@Override
	@SuppressWarnings("unchecked")
    public boolean equals(Object o) {
		// Si son el mateix objecte, true
		if (o == this) return true;
			
		// Si l'objecte no es una instancia d'Edge, false
		if (!(o instanceof Edge)) return false;
		
		try {
			// Convertir-lo a l'objecte que es realment
			Edge<K,T> e = (Edge<K,T>) o;
			return (e.origen == origen && e.desti == desti && e.value == value);
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
	 * @return desti, el vertex desti de l'aresta
	 */
	public K getDesti() { return desti; }
	
	/**
	 * Obte l'origen de l'aresta
	 * @return origen, el vertex origen de l'aresta
	 */
	public K getOrigin() { return origen; }
	
	/**
	 * Modifica el pes de l'aresta
	 * @param val El nou pes de l'aresta
	 */
	public void setValue(T val) { value = val; }
}