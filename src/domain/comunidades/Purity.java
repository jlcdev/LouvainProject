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


}