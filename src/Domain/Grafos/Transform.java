package Domain.Grafos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 *
 * @author Javier López Calderón
 */
public class Transform
{
    public static Grafo multipleStringToGrafo(ArrayList<String> listString)
    {
        Grafo g = new Grafo();
        int tam = listString.size();
        String[] splitString;
        Node a, b;
        for(int i=0; i<tam ;++i)
        {
            splitString = listString.get(i).split("\\s+");
            if(splitString[1].equals("cat")) a = new Categoria(splitString[0]);
            else a = new Pagina(splitString[0]);
            if(splitString[4].equals("cat")) b = new Categoria(splitString[3]);
            else b = new Pagina(splitString[3]);
            
            g.setNewNode(a);
            g.setNewNode(b);
            
            switch(splitString[2])
            {
                case "CsupC":
                    g.inverse(a, b, Arch.typeArch.CsupC);
                    break;
                case "CsubC":
                    g.inverse(a, b, Arch.typeArch.CsubC);
                    break;
                case "CP":
                    g.inverse(a, b, Arch.typeArch.CP);
                    break;
                case "PC":
                    g.inverse(a, b, Arch.typeArch.PC);
                    break;
            }
        }
        return g;
    }
}
