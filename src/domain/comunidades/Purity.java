/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.comunidades;


import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Alfred Parellada Rodriguez
 */
public class Purity {

    private CtoComunidad Cto;

    public Purity(CtoComunidad conjunto)
    {
        this.Cto = conjunto;
    }

    private int maxCount(ArrayList<String> list)
    {
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        String currentVal = list.get(0);
        String lastVal = currentVal;
        int currentCount=1;
        int maxCount=1;
        int size = list.size();
        if(size == 1)
            return 1;

        for(int i = 1; i < size - 1; i++)
        {
            currentVal=list.get(i);
            if(currentVal.equals(lastVal))
            {
                currentCount++;
            }
            else
            {
                if(currentCount > maxCount)maxCount=currentCount;
                currentCount=1;
            }
            lastVal=currentVal;
        }
        currentVal = list.get(size-1);
        if(currentVal.equals(lastVal))currentCount++;
        if(currentCount > maxCount)maxCount = currentCount;
        return maxCount;
    }
    
    public double overallPurity()
    {
        double purity = 0;
        int size = Cto.getCtoComunidades().size();
        int datasize = 0;
        for(int i = 0;i < size;++i)
            datasize+= Cto.getCtoComunidades().get(i).getNumCategorias();

        for(int i = 0;i < size;++i)
            purity+= maxCount(Cto.getCtoComunidades().get(i).getNameCategories())/datasize;
        
        return purity;
    }


}