/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.comunidades;


import java.util.ArrayList;

/**
 *
 * @author Alfred Parellada Rodriguez
 */
public class Similarity {

    private CtoComunidad Cto1;
    private CtoComunidad Cto2;

    public Similarity(CtoComunidad conjunto1, CtoComunidad conjunto2)
    {
        this.Cto1 = conjunto1;
        this.Cto2 = conjunto2;
    }
    
    private boolean compare(Comunidad com1, Comunidad com2)
    {
        int size1 = com1.getNumCategorias();
        int size2 = com2.getNumCategorias();
        if(size1 != size2)return false;
        for(int i = 0;i < size1; ++i)
        {
            for(int j = 0;i < size2; ++j)
                if(!com1.getNameCategories().get(i).equals(com2.getNameCategories().get(j)))return false;
            
        }
        return true;
    }
    public double similitud()
    {
        double similitud = 0;
        int size1 = Cto1.getCtoComunidades().size();
        int size2 = Cto2.getCtoComunidades().size();
        int datasize = 0;            
        int j = 0;  
        boolean stop = false;
        
        for(int i = 0; i < size1; ++i)
        {
            j = 0;
            stop = false;
            while(j < size2 && !stop)
            {
                if(compare(Cto1.getCtoComunidades().get(i),Cto2.getCtoComunidades().get(i)))
                {
                    similitud += 2;
                    stop = true;
                }
                datasize+= Cto2.getCtoComunidades().get(j).getNumCategorias();
                ++j;
            }
            datasize+= Cto1.getCtoComunidades().get(i).getNumCategorias();
        }
        return similitud/datasize;
    }
}