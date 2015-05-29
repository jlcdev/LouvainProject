package shared;

import java.util.*;

/**
 *
 * @author marc.mauri.ruiz
 */
public class CliquePercolation extends Algorithm
{
    private int W;
    private int nodes;
    private ArrayList<Integer> vertexs = new ArrayList<>();
    private ArrayList< ArrayList<Integer>> graf = new ArrayList<>();
    private ArrayList< ArrayList<Integer>> cliques = new ArrayList<>();
    private ArrayList< ArrayList<Integer>> mat1 = new ArrayList<>();
    private int K;
    private ArrayList< ArrayList<Boolean>> mat2 = new ArrayList<>();
    private ArrayList< ArrayList<Integer>> conjunts = new ArrayList<>();

    /**
     * Post: Se crea un objeto Algorithm.CliquePercolation()
     */
    public void CliquePercolation()
    {
        // Constructora
    }

    private void determinar_w(Graph<Integer, Double> g)
    {
        this.vertexs = g.getVertexs();
        this.nodes = this.vertexs.size();
        double pes_min = 999999;
        double pes_max = 0;
        double suma_pes = 0;
        int arestes_total = 0;
        ArrayList<Boolean> vertex_vist = new ArrayList<>(Collections.nCopies(this.nodes, Boolean.FALSE));
        for(int i = 0; i < this.nodes; ++i)
        {
            vertex_vist.set(i, Boolean.TRUE);
            ArrayList<Integer> veins = g.getNeighbors(this.vertexs.get(i));
            for(Integer vei : veins)
            {
                if(!vertex_vist.get(this.vertexs.indexOf(vei)))
                {
                    double pes = g.getEdge(this.vertexs.get(i), vei).getValue();
                    suma_pes = suma_pes + pes;
                    arestes_total++;
                    if(pes < pes_min)
                    {
                        pes_min = pes;
                    }
                    if(pes > pes_max)
                    {
                        pes_max = pes;
                    }
                }
            }
        }
        double mitja_pes = suma_pes / arestes_total;
        int Q1 = ((int) pes_min + (int) mitja_pes) / 2;
        int Q3 = ((int) pes_max + (int) mitja_pes) / 2;
        double factor = super.p / 100.;
        this.W = (int) (Q3 * factor + Q1 * (1 - factor));
    }

    private void transform_graph(Graph<Integer, Double> g)
    {
        determinar_w(g);
        //Copiar grafo a arraylist
        // Matrix initialization like Mi (nodes);
        ArrayList<Integer> init;
        for(int i = 0; i < this.nodes; ++i)
        {
            init = new ArrayList<>();
            this.graf.add(init);
        }
        ArrayList<Boolean> vertex_vist = new ArrayList<>(Collections.nCopies(this.nodes, Boolean.FALSE));
        for(int i = 0; i < this.nodes; ++i)
        {
            vertex_vist.set(i, Boolean.TRUE);
            for(Integer vei : g.getNeighbors(this.vertexs.get(i)))
            {
                if(!vertex_vist.get(this.vertexs.indexOf(vei)) && g.getEdge(this.vertexs.get(i), vei).getValue() >= this.W)
                {
                    this.graf.get(i).add(this.vertexs.indexOf(vei));
                    this.graf.get(this.vertexs.indexOf(vei)).add(i);
                }
            }
        }
    }
    
    private int calcula_grau_maxim()
    {
        int maxim = 0, tmp;
        for(int i = 0; i < this.nodes; i++)
        {
            tmp = this.graf.get(i).size();
            if(tmp > maxim) maxim = tmp;
        }
        return maxim;
    }

    private Boolean contingut(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        int j = 0, tam = b.size();
        for(Integer a1 : a)
        {
            while(j < tam && a1 > b.get(j))
            {
                j++;
            }
            if(j == tam || b.get(j) > a1)
                return false;
        }
        return true;
    }

    private ArrayList<Integer> copia_arraylist(ArrayList<Integer> v)
    {
        ArrayList<Integer> copia = new ArrayList<>();
        for(int pos : v) copia.add(pos);
        return copia;
    }

    private Boolean contingut_en_alguna_clique(ArrayList<Integer> v)
    {
        ArrayList<Integer> vcopia = copia_arraylist(v);
        for(ArrayList<Integer> clique : this.cliques)
        {
            if(contingut(vcopia, clique)) return true;
        }
        return false;
    }

    private ArrayList<Integer> unio_de_vectors(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        ArrayList<Integer> res = new ArrayList<>();
        for(Integer a1 : a) res.add(a1);
        for(Integer b1 : b) res.add(b1);
        return res;
    }

    private ArrayList<Integer> buscar_nous_candidats(ArrayList<Integer> candidats, int posicio_node)
    {
        ArrayList<Integer> res = new ArrayList<>();
        int node = candidats.get(posicio_node), tam, cansize = candidats.size();
        for(int i = posicio_node + 1; i < cansize; i++)
        {
            tam = graf.get(node).size();
            for(int j = 0; j < tam; j++)
            {
                if(candidats.get(i).equals(graf.get(node).get(j)))
                {
                    res.add(candidats.get(i));
                    break;
                }
            }
        }
        return res;
    }

    private void backtracking(ArrayList<Integer> actual, ArrayList<Integer> candidats, int k)
    {
        if(contingut_en_alguna_clique(unio_de_vectors(actual, candidats))) return;
        if(actual.size() == k)
        {
            if(!contingut_en_alguna_clique(actual))
            {
                ArrayList<Integer> copia = copia_arraylist(actual);
                Collections.sort(copia);
                this.cliques.add(copia);
            }
            return;
        }
        int cansize = candidats.size();
        ArrayList<Integer> nous_candidats;
        for(int i = 0; i < cansize; ++i)
        {
            actual.add(candidats.get(i));
            nous_candidats = buscar_nous_candidats(candidats, i);
            backtracking(actual, nous_candidats, k);
            //actual.pop_back(); Remove last vector element on C++
            actual.remove(actual.size() - 1);
        }
    }

    // Pre: El vector cliques conte totes les cliques maximals de mida mes gran que k
    private void calcula_cliques_mida_k(int k)
    {
        int tam, tmp;
        ArrayList<Integer> actual;
        ArrayList<Integer> candidats;
        for(int i = 0; i < this.nodes; ++i)
        {
            actual = new ArrayList<>();
            actual.add(i);
            candidats = new ArrayList<>();
            tam = this.graf.get(i).size();
            for(int j = 0; j < tam; ++j)
            {
                tmp = this.graf.get(i).get(j);
                if(tmp > i) candidats.add(tmp);
            }
            backtracking(actual, candidats, k);
        }
    }

    private void calcula_cliques()
    {
        int mida_maxima = calcula_grau_maxim() + 1;
        for(int i = mida_maxima; i > 0; i--)
        {
            calcula_cliques_mida_k(i);
        }
    }

    private int quants_nodes_coincideixen(ArrayList<Integer> a, ArrayList<Integer> b)
    {
        int coincidencies = 0, tam = b.size();
        for(Integer a1 : a)
        {
            for(int j = 0; j < tam; ++j)
            {
                if(Objects.equals(a1, b.get(j))) ++coincidencies;
            }
        }
        return coincidencies;
    }

    private void calcula_mat1()
    {
        // Matrix initialization like Mi (cliques.size(), Vi(cliques.size(), 0))
        int tam = this.cliques.size();
        for(ArrayList<Integer> clique : this.cliques)
        {
            ArrayList<Integer> init = new ArrayList<>(Collections.nCopies(tam, 0));
            this.mat1.add(init);
        }
        for(int i = 0; i < tam; ++i)
        {
            for(int j = i; j < tam; ++j)
            {
                int coincidencies = quants_nodes_coincideixen(this.cliques.get(i), this.cliques.get(j));
                //mat1[i][j] = mat1[j][i] = coincidencies; En C++
                this.mat1.get(i).set(j, coincidencies);
                this.mat1.get(j).set(i, coincidencies);
            }
        }
    }

    private void determinar_k()
    {
        double k_min = 999999;
        double k_max = 0;
        double suma_k = 0;
        int tam = this.mat1.size();
        for(int i = 0; i < tam; ++i)
        {
            int k_size = this.mat1.get(i).get(i);
            suma_k = suma_k + k_size;
            if(k_size > k_max) k_max = k_size;
            if(k_size < k_min) k_min = k_size;
        }
        double mitja_k = suma_k / tam;
        double factor = super.p / 100.;
        this.K = (int) (mitja_k * factor + k_min * (1 - factor));  // this.k = function related to the percentage
        if(this.K < 2)
        {
            this.K = 2;
        }
    }

    private void calcula_mat2()
    {
        determinar_k();
        // Matrix initialization like Mb (cliques.size(), Vb(cliques.size(), false))
        for(ArrayList<Integer> clique1 : this.cliques)
        {
            ArrayList<Boolean> init = new ArrayList<>();
            for(ArrayList<Integer> clique : this.cliques)
            {
                init.add(Boolean.FALSE);
            }
            this.mat2.add(init);
        }
        int tam = this.mat1.size();
        for(int i = 0; i < tam; ++i)
        {
            for(int j = 0; j < tam; ++j)
            {
                if((i != j && this.mat1.get(i).get(j) >= (K - 1)) || (i == j && this.mat1.get(i).get(j) > (K - 1)))
                {
                    this.mat2.get(i).set(j, Boolean.TRUE);
                    this.mat2.get(j).set(i, Boolean.TRUE);
                }
            }
        }
    }

    private void visitar_dfs(int i, ArrayList<Integer> aux, ArrayList<Boolean> vist)
    {
        vist.set(i, Boolean.TRUE);
        aux.add(i);
        int tam = this.cliques.size();
        for(int j = 0; j < tam; ++j)
        {
            if(j != i && !vist.get(j) && this.mat2.get(i).get(j))
            {
                visitar_dfs(j, aux, vist);
            }
        }
    }

    private ArrayList< ArrayList<Integer>> dfs()
    {
        // Vector initialization like Vb (cliques.size(), false);
        ArrayList<Boolean> vist = new ArrayList<>();
        for(ArrayList<Integer> clique : this.cliques)
        {
            vist.add(Boolean.FALSE);
        }
        ArrayList< ArrayList<Integer>> res_dfs = new ArrayList<>();
        int tmp = this.cliques.size();
        ArrayList<Integer> aux;
        for(int i = 0; i < tmp; ++i)
        {
            if(this.mat2.get(i).get(i) && !vist.get(i))
            {
                aux = new ArrayList<>();
                visitar_dfs(i, aux, vist);
                res_dfs.add(aux);
            }
        }
        return res_dfs;
    }

    private void calcula_conjunts()
    {
        ArrayList< ArrayList<Integer>> res_dfs = dfs();
        int tmp = res_dfs.size();
        for(int i = 0; i < tmp; ++i)
        {
            ArrayList<Integer> nodes_implicats = new ArrayList<>();
            // Vector initialization like Vb (cliques.size(), false);
            ArrayList<Boolean> brut = new ArrayList<>();
            for(int p = 0; p < this.nodes; ++p)
            {
                brut.add(Boolean.FALSE);
            }
            int tam = res_dfs.get(i).size();
            for(int j = 0; j < tam; ++j)
            {
                int size = this.cliques.get(res_dfs.get(i).get(j)).size();
                for(int z = 0; z < size; ++z)
                {
                    //if (not brut[cliques[res_dfs[i][j]][z]]) {
                    if(!brut.get(this.cliques.get(res_dfs.get(i).get(j)).get(z)))
                    {
                        nodes_implicats.add(this.vertexs.get(this.cliques.get(res_dfs.get(i).get(j)).get(z)));
                        brut.set(this.cliques.get(res_dfs.get(i).get(j)).get(z), Boolean.TRUE);
                    }
                }
            }
            this.conjunts.add(nodes_implicats);
        }
    }

    private void main()
    {
        //write_graph(graf);
        calcula_cliques();
        calcula_mat1();
        //write_graph(mat1);
        calcula_mat2();
        calcula_conjunts();
        //write_graph(conjunts);
    }

    @Override
    public void calc(Graph<Integer, Double> g)
    {
        // Reserva de nuevos espacios de memoria
        this.vertexs = new ArrayList<>();
        this.graf = new ArrayList<>();
        this.cliques = new ArrayList<>();
        this.mat1 = new ArrayList<>();
        this.mat2 = new ArrayList<>();
        this.conjunts = new ArrayList<>();
        // Graph to ArrayList
        transform_graph(g);
        // Calc
        main();
    }

    @Override
    public ArrayList<ArrayList<Integer>> obtain()
    {
        return this.conjunts;
    }
}
