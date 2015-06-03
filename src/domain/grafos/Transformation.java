package domain.grafos;

import java.util.ArrayList;
import shared.Graph;

/**
 * Transformation es la clase empleada para traducir el grafo de entrada al
 * grafo compartido que se utiliza en los algoritmos de detección de comunidades
 *
 * @author Joan Rodas
 * @author Javier López
 * @version 1.0
 * @since 01/06/2015
 */
public class Transformation
{
    /**
     * Metodo encargado de aplicar las selecciones sobre un grafo de entrada
     * Elimina todos aquellos nodos que no están en la lista de seleccionados y
     * borra todos los arcos que no están apuntados por estos nodos
     * 
     * @param grafo Grafo de entrada
     * @param selections Seleccion de Categorias y Paginas
     * @return Grafo de entrada filtrado con lo seleccionado
     * @see Categoria
     * @see Pagina
     * @see Node
     * @see GrafoEntrada
     */
    public static GrafoEntrada clearGraph(GrafoEntrada grafo, Selections selections)
    {
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
        return result;
    }
    
    /**
     * Método encargado de generar el algoritmo que debe ser servido a los algoritmos.
     * 
     * @param grafo Grafo de entrada
     * @param filters Filtros a aplicar sobre el grafo de entrada
     * @return Grafo para los algoritmos con los filtros aplicados
     */
    public static Graph<Integer, Double> entryToAlgorithm(GrafoEntrada grafo, Filters filters)
    {
        Graph<Integer, Double> graph = new Graph((int) (grafo.getCategorySize() / 1.75), 0.75f);
        ArrayList<Integer> categories = grafo.getCategories();
        for(Integer category : categories)
        {
            if(!graph.getVertexs().contains(category))
            {
                graph.addVertex(category);
            }
        }
        int adyacentes, common, a = filters.getPname(), b = filters.getPcat(), c = filters.getPpag(), d = filters.getPfat(), e = filters.getPson();
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
                if(scatA != null && scatB != null)
                {
                    weight += DiceCoefficient.diceCoefficientOptimized(scatA, scatB) * a;
                }
                //Categorias en común
                adyacentes = grafo.getCategoryNumberAdyacent(category);
                common = grafo.getCategoriesCommon(category, categoryNeighbor);
                if((common + adyacentes) != 0)
                {
                    weight += ((common * 2) / (common + adyacentes)) * b;
                }
                //Paginas en común
                adyacentes = grafo.getPageNumberAdyacent(categoryNeighbor);
                common = grafo.getPagesCommon(category, categoryNeighbor);
                if((common + adyacentes) != 0)
                {
                    weight += ((common * 2) / (common + adyacentes)) * c;
                }
                //Padres en común
                adyacentes = grafo.getCsupCAdyacent(category);
                common = grafo.getCsupCCommon(category, categoryNeighbor);
                if((common + adyacentes) != 0)
                {
                    weight += ((common * 2) / (common + adyacentes)) * d;
                }
                //Hijos en común
                adyacentes = grafo.getCsubCAdyacent(category);
                common = grafo.getCsubCCommon(category, categoryNeighbor);
                if((common + adyacentes) != 0)
                {
                    weight += ((common * 2) / (common + adyacentes)) * e;
                }
                weight *= 2;
                if(weight != 0.0)
                {
                    graph.addEdge(category, categoryNeighbor, weight);
                }
            }
        }
        return graph;
    }
}