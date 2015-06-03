package domain.comunidades;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Permite calcular la pureza empleando el algorimo del mismo nombre
 *
 * @author Alfred Parellada Rodriguez
 * @version 1.0
 * @since 01/06/2015
 */
public class Purity
{
    /**
     * Constructor por defecto
     */
    public Purity()
    {
    }
    
    /**
     * Obtiene los tamaños de cada comunidad y se queda con el que más se repite
     * 
     * @param list lista de comunidades
     * @return cuantas veces se repite el tamaño 1
     */
    private int maxCount(ArrayList<Comunidad> list)
    {
        HashMap<Integer, Integer> count = new HashMap();
        int size = list.size();
        int maximo = 1;
        for(int i = 0; i < size; ++i)
        {
            int numcat = list.get(i).getNumCategorias();
            if(count.containsKey(numcat))
            {
                int replace = count.get(numcat) + 1;
                count.put(numcat, replace);
                if(replace > maximo)
                {
                    maximo = replace;
                }
            }
            else
            {
                count.put(numcat, 1);
            }
        }
        return maximo;
    }
    
    /**
     * Cuenta cuantos elementos de tamaño 2 hay en la lista de comunidades
     * 
     * @param list lista de comunidades
     * @return numero de veces que se repite
     */
    private int maxCountTwo(ArrayList<Comunidad> list)
    {
        HashMap<Integer, Integer> count = new HashMap();
        int size = list.size();
        int maximo = 1;
        for(int i = 0; i < size; ++i)
        {
            int numcat = list.get(i).getNumCategorias();
            if(numcat != 1)
            {
                if(count.containsKey(numcat))
                {
                    int replace = count.get(numcat) + 1;
                    count.put(numcat, replace);
                    if(replace > maximo)
                    {
                        maximo = replace;
                    }
                }
                else
                {
                    count.put(numcat, 1);
                }
            }
        }
        return maximo;
    }
    
    /**
     * Realiza el calculo de la pureza
     * 
     * @param Cto conjunto de comunidades
     * @return valor de pureza
     */
    public double calcPurity(CtoComunidad Cto)
    {
        int datasize = Cto.getNumComunidades();
        double purity = (double) this.maxCount(Cto.getCtoComunidades());
        purity = purity / (double) datasize;
        long factor = (long) Math.pow(10, 2);
        purity = purity * factor;
        long tmp = Math.round(purity);
        return (double) tmp / factor;
    }
    
    /**
     * Realiza el calculo de la pureza entre dos conjuntos
     * 
     * @param Cto1 conjunto de comunidades
     * @param Cto2 conjunto de comunidades
     * @return valor de pureza
     */
    public double calcPurity(CtoComunidad Cto1, CtoComunidad Cto2)
    {
        double purity;
        int datasize = Cto1.getNumComunidades() + Cto2.getNumComunidades();
        purity = (double) this.maxCount(Cto1.getCtoComunidades()) + this.maxCount(Cto2.getCtoComunidades());
        purity = purity / (double) datasize;
        long factor = (long) Math.pow(10, 2);
        purity = purity * factor;
        long tmp = Math.round(purity);
        return (double) tmp / factor;
    }
    
    /**
     * Realiza el calculo de la pureza empleando los de tamaño 2
     * 
     * @param Cto conjunto de comunidades
     * @return valor de pureza
     */
    public double calcPurityTwo(CtoComunidad Cto)
    {
        int datasize = 0;
        double purity;
        for(int i = 0; i < Cto.getNumComunidades(); ++i)
        {
            if(Cto.getCtoComunidades().get(i).getNumCategorias() > 1)
            {
                ++datasize;
            }
        }
        purity = (double) this.maxCountTwo(Cto.getCtoComunidades());
        purity = purity / (double) datasize;
        long factor = (long) Math.pow(10, 2);
        purity = purity * factor;
        long tmp = Math.round(purity);
        return (double) tmp / factor;
    }
    
    /**
     * Realiza el calculo de la pureza entre dos conjuntos empleando los de 
     * tamaño 2
     * 
     * @param Cto1 conjunto de comunidades
     * @param Cto2 conjunto de comunidades
     * @return valor de pureza
     */
    public double calcPurityTwo(CtoComunidad Cto1, CtoComunidad Cto2)
    {
        double purity;
        int datasize = 0;
        for(int i = 0; i < Cto1.getNumComunidades(); ++i)
        {
            if(Cto1.getCtoComunidades().get(i).getNumCategorias() > 1)
            {
                ++datasize;
            }
        }
        for(int i = 0; i < Cto2.getNumComunidades(); ++i)
        {
            if(Cto2.getCtoComunidades().get(i).getNumCategorias() > 1)
            {
                ++datasize;
            }
        }
        purity = this.maxCountTwo(Cto1.getCtoComunidades()) + this.maxCountTwo(Cto2.getCtoComunidades());
        purity = purity / (double) datasize;
        long factor = (long) Math.pow(10, 2);
        purity = purity * factor;
        long tmp = Math.round(purity);
        return (double) tmp / factor;
    }
}