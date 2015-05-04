package wikipedia;

import domain.CtrDominio;
import domain.grafos.Grafo;


public class Wikipedia
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
        CtrDominio cd = new CtrDominio();
        
        String basePath = "/Users/potnox/Desktop/PROP/project/propwikipedia/external/";
        String origin = "cats.txt";
        String destiny = "final.txt";
        cd.readEntryGraphFile(basePath+origin);
        /*
        Grafo g = cd.getGrafo();
        System.out.println("Commons: "+g.getNumCatAdyacent(6));
        for(int i=0;i < 25;++i)
        {
            System.out.println("i:"+i+"  cat->"+g.getNumCatAdyacent(i)+ "  pag->"+g.getNumPagAdyacent(i));
        }
        System.out.println("Common: "+g.getNumCommonCatAdyacent(6, 12));
        */
        //cd.saveEntryGraph(basePath+destiny);
    }
    
}
