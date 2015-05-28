package wikipedia;

import domain.CtrAlgoritmo;
import domain.CtrDominio;
import domain.comunidades.CtoComunidad;
import java.util.ArrayList;
import java.util.Scanner;
import shared.Graph;


public class Wikipedia
{
    public static void msg(String s)
    {
        System.out.println(s);
    }
    
    public static void showMenu()
    {
        System.out.println("--MENU WIKIPEDIA--");
        System.out.println("1.- Cargar fichero de entrada.");
        System.out.println("2.- Introducir datos manualmente.");
        System.out.println("3.- Guardar el grafo Wikpedia en un fichero.");
        System.out.println("4.- Establecer filtros.");
        System.out.println("5.- Seleccionar Categorias y Páginas.");
        System.out.println("6.- Escoger algoritmo de detección de comunidades.");
        System.out.println("7.- Aplicar algoritmo.");
        System.out.println("8.- Mostrar Resultados.");
        System.out.println("9.- Guardar Conjunto de comunidad generado.");
        System.out.println("0.- Cerrar programa.");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
        CtrDominio domainController = new CtrDominio();
        CtrAlgoritmo algorithmController = new CtrAlgoritmo();
        Graph<Integer, Double> graf = null; 
        Scanner scanner = new Scanner(System.in);
        int x = -1;
        int num;
       
        while(x != 0)
        {
            showMenu();
            x = scanner.nextInt();
            switch(x)
            {
                case 1:
                    msg("--Introducir datos a la aplicación desde un fichero--");
                    msg("Introduce la ruta absoluta al fichero:");
                    String ruta = scanner.next();
                    if(ruta == null || ruta.isEmpty())
                    {
                        msg("La ruta introducida no es correta.");
                        break;
                    }
                    domainController.readEntryGraphFile(ruta);
                    msg("Fichero cargado con éxito");
                    break;
                case 2:
                    String na = "", ta = "cat", arc = "CsupC", nb = "", tb = "cat";
                    msg("--Introducir datos a la aplicación manualmente--");
                    msg("Selecciona el tipo de enlace (CsupC, CsubC, CP, PC):");
                    arc = scanner.next();
                    switch(arc)
                    {
                        case "CsubC":
                            arc = "CsubC";
                        case "CsupC":
                            msg("Introduce el nombre de la primera categoría");
                            na = scanner.next();
                            msg("Introduce el nombre de la segunda categoría");
                            nb = scanner.next();
                            break;
                        case "CP":
                            arc = "CP";
                            tb = "pag";
                            msg("Introduce el nombre de la categoría");
                            na = scanner.next();
                            msg("Introduce el nombre de la pagina");
                            nb = scanner.next();
                            break;
                        case "PC":
                            arc = "PC";
                            ta = "pag";
                            msg("Introduce el nombre de la pagina");
                            na = scanner.next();
                            msg("Introduce el nombre de la categoría");
                            nb = scanner.next();
                            break;
                    }
                    domainController.addToEntryGraph(na, ta, arc, nb, tb);
                    msg("Nueva información introducida correctamente en el grafo");
                    break;
                case 3:
                    msg("--Guardar grafo de wikipedia en un fichero--");
                    msg("Introduce un nombre para el fichero.");
                    String name = scanner.next();
                    name+=".txt";
                    msg("Introduce una ruta donde guardar el fichero.");
                    ruta = scanner.next();
                    ruta+=name;
                    boolean resp = false;
                    if(!ruta.isEmpty()) resp = domainController.saveEntryGraph(ruta);
                    msg( (resp)?"Fichero guardado correctamente":"Error al guardar en fichero");
                    break;
                case 4:
                    msg("--Filtros--");
                    msg("Introduce la importancia (0-10) de tener un nombre similar:");
                    int pname = scanner.nextInt();
                    msg("Introduce la importancia (0-10) de tener mismas categorias");
                    int pcat = scanner.nextInt();
                    msg("Introduce la importancia (0-10) de tener mismas paginas");
                    int ppag = scanner.nextInt();
                    msg("Intorduce la importancia (0-10) de tener el mismo padre");
                    int pfat = scanner.nextInt();
                    msg("Introduce la importancia (0-10) de tener el mismo hijo");
                    int pson = scanner.nextInt();
                    algorithmController.setFilters(pname, pcat, ppag, pfat, pson);
                    break;
                case 5:
                    msg("--Seleccionar Categorias y Paginas--");
                    int option = -1;
                    while(option != 0)
                    {
                        msg("1.- Mostrar todas las Categorías");
                        msg("2.- Mostrar todas las Páginas");
                        msg("3.- Seleccionar Categorías");
                        msg("4.- Seleccionar Páginas");
                        msg("0.- Salir");
                        option = scanner.nextInt();
                        int cont = 0;
                        String action;
                        switch(option)
                        {
                            case 1:
                                msg("Categorías:");
                                for(String s : domainController.verCatGeneral())
                                {
                                    msg(cont + ": "+ s);
                                    ++cont;
                                }
                                break;
                            case 2:
                                msg("Páginas:");
                                for(String s : domainController.verPagGeneral())
                                {
                                    msg(cont + ": "+ s);
                                    ++cont;
                                }
                                break;
                            case 3:
                                msg("Seleccionar Categorías");
                                msg("Elige una acción: (all(elegir todo), range(rango concreto), in(específico))");
                                action = scanner.next();
                                if(action.equalsIgnoreCase("all")) algorithmController.setCatSelections(domainController.getNumCatGeneral());
                                if(action.equalsIgnoreCase("range"))
                                {
                                    msg("Introduce el rango deseado: (ejem: 3-50)");
                                    action = scanner.next();
                                    String[] range = action.split("-");
                                    int a = Integer.parseInt(range[0]);
                                    int b = Integer.parseInt(range[1]);
                                    algorithmController.setCatSelections(new ArrayList<>(domainController.getNumCatGeneral().subList(a, b)));
                                }
                                if(action.equalsIgnoreCase("in"))
                                {
                                    msg("Introduce el número de la Categoría:");
                                    algorithmController.setCategorySelectionConcrete(domainController.verNumCat(scanner.nextInt()));
                                }
                                break;
                            case 4:
                                msg("Seleccionar Paginas");
                                msg("Elige una acción: (all(elegir todo), range(rango concreto), in(específico))");
                                action = scanner.next();
                                if(action.equalsIgnoreCase("all")) algorithmController.setPageSelections(domainController.getNumPagGeneral());
                                if(action.equalsIgnoreCase("range"))
                                {
                                    msg("Introduce el rango deseado: (ejem: 3-50)");
                                    action = scanner.next();
                                    String[] range = action.split("-");
                                    int a = Integer.parseInt(range[0]);
                                    int b = Integer.parseInt(range[1]);
                                    algorithmController.setPageSelections(new ArrayList<>(domainController.getNumPagGeneral().subList(a, b)));
                                }
                                if(action.equalsIgnoreCase("in"))
                                {
                                    msg("Introduce el número de la Categoría:");
                                    algorithmController.setPageSelectionConcrete(domainController.verNumPag(scanner.nextInt()));
                                }
                                break;
                        }
                    }
                    break;
                case 6:
                    msg("--Selección de Algoritmo--");
                    msg("1.- Louvain");
                    msg("2.- Girvan-Newman");
                    msg("3.- Clique Percolation");
                    num = scanner.nextInt();
                    msg("Seleccionado: "+num);
                    algorithmController.setAlgorithm(num);
                    msg("Especifica el nivel de cohesión (0-100):");
                    num = scanner.nextInt();
                    msg("Cohesión fijada: "+num);
                    algorithmController.setP(num);
                    break;
                case 7:
                    msg("--Aplicando algoritmo--");
                    domainController.setAlgorithmGraph(algorithmController.generate(domainController.getGrafo()));
                    domainController.information();
                    CtoComunidad cto = algorithmController.ejecutar(domainController.getAlgorithmGraph(), domainController.getGrafo());
                    domainController.setGeneratedCto(cto);
                    break;
                case 8:
                    System.out.println("--Mostrar resultado--");
                    break;
                case 9:
                    System.out.println("--Guardar conjunto de comunidad--");
                    msg("Introduce una ruta donde guardar el fichero:");
                    String path = scanner.next();
                    domainController.saveCtoComunidad(path);
                    break;
            }
        }
    }
}
