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
            System.out.println("TIEMPO DE CREACION: "+(t2-t1));
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
        Graph<Integer, Double> graph = new Graph((int) (grafo.getCategorySize()/1.75),0.75f);
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
                if(weight != 0.0)
                    graph.addEdge(category, categoryNeighbor, weight);
            }
        }
        return graph;
    }
}
