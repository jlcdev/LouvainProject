package domain.grafos;

import java.util.ArrayList;
import shared.Graph;

/**
 *
 * @author Joan Rodas
 */
public class Transformation
{
    public static GrafoEntrada clearGraph(GrafoEntrada grafo, Selections selections)
    {/*
        if(selections.getCategoriesSelected().size() < grafo.getCategorySize()/2)
        {*/
            System.out.println("Limpiando el grafo.");
            double t1 = System.currentTimeMillis();
            GrafoEntrada result = new GrafoEntrada();
            ArrayList<Integer> selected = selections.getCategoriesSelected();
            for(Integer category : selected)
            {
                result.addCategoria(grafo.getNumberCategory(category));
            }
            selected = selections.getPagesSelected();
            for(Integer page : selected)
            {
                result.addPagina(grafo.getNumberPage(page));
            }
            for(Integer vertex : result.getCategories())
            {
                for(Arch arc : grafo.getCsupCArch(vertex))
                {
                    if(result.getCategoryNumber(grafo.getNumberCategory(arc.getDestiny())) != -1)
                    {
                        result.addArch(arc);
                    }
                }
                for(Arch arc : grafo.getCsubCArch(vertex))
                {
                    if(result.getCategoryNumber(grafo.getNumberCategory(arc.getDestiny())) != -1)
                    {
                        result.addArch(arc);
                    }
                }
                for(Arch arc : grafo.getCPArch(vertex))
                {
                    if(result.getPageNumber(grafo.getNumberPage(arc.getDestiny())) != -1)
                    {
                        result.addArch(arc);
                    }
                }
            }
            for(Integer vertex : result.getPages())
            {
                for(Arch arc : grafo.getPCArch(vertex))
                {
                    if(result.getPageNumber(grafo.getNumberPage(arc.getDestiny())) != -1)
                    {
                        result.addArch(arc);
                    }
                }
            }
            double t2 = System.currentTimeMillis();
            System.out.println("Limpieza completada en: "+(t2-t1)+"ms");
            return result;
            /*
        }
        else
        {
            double t1 = System.currentTimeMillis();
            ArrayList<Integer> selected = selections.getCategoriesSelected();
            ArrayList<Integer> total = grafo.getCategories();
            for(Integer category : total)
            {
                if(!selected.contains(category))
                {
                    grafo.removeCategoria(grafo.getNumberCategory(category));
                }
            }
            selected = selections.getPagesSelected();
            total = grafo.getPages();
            for(Integer page : total)
            {
                if(!selected.contains(page))
                {
                    grafo.removePagina(grafo.getNumberPage(page));
                }
            }
            double t2 = System.currentTimeMillis();
            System.out.println("TIEMPO DE BORRADO: "+(t2-t1));
        }*/
    }
    
    public static Graph<Integer, Double> entryToAlgorithm(GrafoEntrada grafo, Filters filters)
    {
        System.out.println("Iniciando Transformación de grafoEntrada a GrafoCompartido");
        Graph<Integer, Double> graph = new Graph((int) (grafo.getCategorySize()/1.75),0.75f);
        ArrayList<Integer> categories = grafo.getCategories();
        //Primera pasada -> añadir todas las categorías al graph
        for(Integer category : categories)
        {
            if(!graph.getVertexs().contains(category))
                graph.addVertex(category);
        }
        int adyacentes,common;
        String scatA, scatB;
        double weight;
        for(Integer category : categories)
        {
            for(Integer categoryNeighbor : grafo.getCategoriesAdyacentCategories(category))
            {
                weight = 0.0;
                //Nombre similar
                scatA = grafo.getNumberNameCategory(category);
                scatB = grafo.getNumberNameCategory(categoryNeighbor);
                if(scatA != null || scatB != null)
                    weight += DiceCoefficient.diceCoefficientOptimized(scatA,scatB) * filters.getPname();
                //Categorias en común
                adyacentes = grafo.getCategoryNumberAdyacent(category);
                common = grafo.getCategoriesCommon(category, categoryNeighbor);
                if((common+adyacentes) != 0)
                {
                    weight += ((common*2)/(common+adyacentes)) * filters.getPcat();
                }
                //Paginas en común
                adyacentes = grafo.getPageNumberAdyacent(categoryNeighbor);
                common = grafo.getPagesCommon(category, categoryNeighbor);
                if((common+adyacentes) != 0)
                {
                    weight += ((common*2)/(common+adyacentes)) * filters.getPpag();
                }
                //Padres en común
                adyacentes = grafo.getCsupCAdyacent(category);
                common = grafo.getCsupCCommon(category, categoryNeighbor);
                if((common+adyacentes) != 0)
                {
                    weight += ((common*2)/(common+adyacentes)) * filters.getPfat();
                }
                //Hijos en común
                adyacentes = grafo.getCsubCAdyacent(category);
                common = grafo.getCsubCCommon(category, categoryNeighbor);
                if((common+adyacentes) != 0)
                {
                    weight += ((common*2)/(common+adyacentes)) * filters.getPson();
                }
                
                weight *= 2;
                if(weight != 0.0)
                    graph.addEdge(category, categoryNeighbor, weight);
            }
        }
        System.out.println("Transformación completa");
        return graph;
    }
}
