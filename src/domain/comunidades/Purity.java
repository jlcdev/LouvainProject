/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.comunidades;


import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Alfred Parellada Rodriguez
 */
public class Purity {


    public Purity()
    {
        
    }

    private int maxCount(ArrayList<Comunidad> list)
    {
        HashMap<Integer,Integer> count = new HashMap();
        int size = list.size();
        int maximo = 1;
        for(int i = 0;i < size; ++i)
        {
            int numcat = list.get(i).getNumCategorias();
            if(count.containsKey(numcat))
            {
                count.replace(numcat, count.get(numcat)+1);
                if(count.get(numcat) > maximo)
                    maximo = count.get(numcat);
            }
            else
                count.put(numcat, 1);
        }
        return maximo;
    }
    
    private int maxCountTwo(ArrayList<Comunidad> list)
    {
        HashMap<Integer,Integer> count = new HashMap();
        int size = list.size();
        int maximo = 1;
        for(int i = 0;i < size; ++i)
        {
            int numcat = list.get(i).getNumCategorias();
            if(numcat != 1){
                if(count.containsKey(numcat))
                {
                    count.replace(numcat, count.get(numcat)+1);
                    if(count.get(numcat) > maximo)
                        maximo = count.get(numcat);
                }
                else
                    count.put(numcat, 1);
                
            }
        }
        return maximo;
    }
    
    public double calcPurity(CtoComunidad Cto)
    {
        double purity = 0;
        int datasize = Cto.getNumComunidades();
        purity = this.maxCount(Cto.getCtoComunidades())/datasize;
        return purity;
    }
    
    public double calcPurity(CtoComunidad Cto1, CtoComunidad Cto2)
    {
        double purity = 0;
        int datasize = Cto1.getNumComunidades() + Cto2.getNumComunidades();
        purity = this.maxCount(Cto1.getCtoComunidades())+this.maxCount(Cto2.getCtoComunidades());
        return purity/datasize;
    }
    public double calcPurityTwo(CtoComunidad Cto)
    {
        double purity = 0;
        int datasize = 0;
        for(int i = 0;i < Cto.getNumComunidades(); ++i)
        {
            if(Cto.getCtoComunidades().get(i).getNumCategorias() != 1)
                ++datasize;
        }
        purity = this.maxCount(Cto.getCtoComunidades())/datasize;
        return purity;
    }
    
    public double calcPurityTwo(CtoComunidad Cto1, CtoComunidad Cto2)
    {
        double purity = 0;
        int datasize = 0;
        for(int i = 0;i < Cto1.getNumComunidades(); ++i)
        {
            if(Cto1.getCtoComunidades().get(i).getNumCategorias() != 1)
                ++datasize;
        }
        for(int i = 0;i < Cto2.getNumComunidades(); ++i)
        {
            if(Cto2.getCtoComunidades().get(i).getNumCategorias() != 1)
                ++datasize;
        }
        purity = this.maxCountTwo(Cto1.getCtoComunidades())+this.maxCountTwo(Cto2.getCtoComunidades());
        return purity/datasize;
    }


}