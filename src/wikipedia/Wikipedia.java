package wikipedia;

import domain.CtrDominio;


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
        cd.saveEntryGraph(basePath+destiny);
    }
    
}
