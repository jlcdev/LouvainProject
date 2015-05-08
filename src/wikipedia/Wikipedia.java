package wikipedia;

import domain.CtrAlgoritmo;
import domain.CtrDominio;
import java.util.Scanner;
import shared.Graph;


public class Wikipedia
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        CtrDominio var = new CtrDominio();
        CtrAlgoritmo var2 = new CtrAlgoritmo();
        Graph<Integer, Double> graf = null; 
        int x = -1;
        while(x != 0)
        {
            System.out.println("1. Cargar fichero");
            System.out.println("2. Introducir datos manual");
            System.out.println("3. Guardar en fichero (entrante)");
            System.out.println("4. Poner filtros");
            System.out.println("5. Valor a P");
            System.out.println("6. Elegir algortimo");
            System.out.println("7. Generar grafo");
            System.out.println("8. Ejecutar");
           
            System.out.println("0. Salir");            
            Scanner teclat = new Scanner(System.in);
            x = teclat.nextInt();
            String ruta, linia;
            int num;
            switch(x)
            {
                case 1:
                    ruta = teclat.nextLine();
                    var.readEntryGraphFile(ruta);
                    break;
                case 2:
                    linia = teclat.nextLine();
                    var.addToEntryGraph(linia);
                    break;
                case 3:
                    ruta = teclat.nextLine();
                    var.saveEntryGraph(ruta);
                    break;
                case 4:
                    //System.out.println("");
                    linia = teclat.nextLine();
                    var2.setFilters(linia);
                    break;
                case 5:
                    System.out.println("P: numero entre 0-100");
                    num = teclat.nextInt();
                    var2.setP(num);
                    break;
                case 6:
                    System.out.println("1:  ,   2:   , 3:  ");
                    num = teclat.nextInt();
                    var2.setAlgorithm(num);
                    break;
                case 7:
                    graf = var2.generate(var.getGrafo());
                    break;
                case 8:
                    var2.ejecutar(graf);
                    break;
                    
            }
        }
    }
}
