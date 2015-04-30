package wikipedia;

import Domain.CtrDominio;


public class Wikipedia
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        CtrDominio cd = new CtrDominio();
        cd.leerFichero("/Users/potnox/Desktop/PROP/project/propwikipedia/external/cats.txt");
        cd.cargar();
    }
    
}
