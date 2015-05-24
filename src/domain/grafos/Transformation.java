package domain.grafos;

import java.util.ArrayList;
import shared.Graph;

/**
 *
 * @author Joan Rodas
 */
public class Transformation
{
    public static void clearGraph(GrafoEntrada grafo, Selections selections)
    {
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
    }
    
    public static Graph<Integer, Double> entryToAlgorithm(GrafoEntrada grafo, Filters filters)
    {
        Graph<Integer, Double> graph = new Graph();
        ArrayList<Integer> categories = grafo.getCategories();
        //Primera pasada -> añadir todas las categorías al graph
        for(Integer category : categories)
        {
            if(!graph.getVertexs().contains(category))
                graph.addVertex(category);
        }
        
        for(Integer category : categories)
        {
            for(Integer categoryNeighbor : grafo.getCategoriesAdyacentCategories(category))
            {
                double weight = 0.0;
                //Nombre similar
                weight += DiceCoefficient.diceCoefficientOptimized(grafo.getNumberNameCategory(category),grafo.getNumberNameCategory(categoryNeighbor)) * filters.getPname();
                //Categorias en común
                int adyacentes = grafo.getCategoryNumberAdyacent(category);
                int common = grafo.getCategoriesCommon(category, categoryNeighbor);
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
                graph.addEdge(category, categoryNeighbor, weight);
            }
        }
        return graph;
    }
}
