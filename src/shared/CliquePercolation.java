package shared;
import java.util.*;
/**
 *
 * @author marc.mauri.ruiz
 */
public class CliquePercolation extends Algorithm {
    
    private int W;
    private int nodes;
    private ArrayList<Integer> vertexs = new ArrayList<>();
    private ArrayList< ArrayList<Integer> > graf = new ArrayList<>();
    private ArrayList< ArrayList<Integer> > cliques = new ArrayList<>();
    private ArrayList< ArrayList<Integer> > mat1 = new ArrayList<>();
    private int K;
    private ArrayList< ArrayList<Boolean> > mat2 = new ArrayList<>();
    private ArrayList< ArrayList<Integer> > conjunts = new ArrayList<>();
    
    /**
     * Post: Se crea un objeto Algorithm.CliquePercolation()
     */
    public void CliquePercolation () {
        // Constructora
    }
    
    private void determinar_w (Graph<Integer,Double> g) {
        //System.out.println(super.p);
        vertexs = g.getVertexs();
        nodes = vertexs.size();
        double pes_min = 999999;
        double pes_max = 0;
        double suma_pes = 0;
        int arestes_total = 0;
        ArrayList<Boolean> vertex_vist = new ArrayList<>(Collections.nCopies(nodes, Boolean.FALSE));
        for (int i = 0; i < nodes; ++i) {
            vertex_vist.set(i, Boolean.TRUE);
            ArrayList<Integer> veins = new ArrayList<>();
            veins = g.getNeighbors(vertexs.get(i));
            for (Integer vei : veins) {
                if (!vertex_vist.get(vertexs.indexOf(vei))) {
                    double pes = g.getEdge(vertexs.get(i), vei).getValue();
                    suma_pes = suma_pes + pes;
                    arestes_total++;
                    if (pes < pes_min) pes_min = pes;
                    if (pes > pes_max) pes_max = pes;
                }
            }
        }
        double mitja_pes = suma_pes/arestes_total;
        //System.out.println("Pes min: " + (int)pes_min);
        //System.out.println("Pes max: " + (int)pes_max);
        //System.out.println("Suma pes: " + (int)suma_pes);
        //System.out.println("Arestes total: " + arestes_total);
        //System.out.println("Media de pesos: " + mitja_pes);
        //System.out.println("P: " + super.p);
        int Q1 = ((int)pes_min+(int)mitja_pes)/2;   // Primer cuartil
        //System.out.println("W min: " + Q1);
        int Q3 = ((int)pes_max+(int)mitja_pes)/2;   // Tercer cuartil
        //System.out.println("W Max: " + Q3);
        double factor = super.p/100.;
        //System.out.println("\nfactor: " +factor);
        //W = (int) (mitja_pes*factor + pes_min*(1-factor));
        W = (int) (Q3*factor + Q1*(1-factor));
        //System.out.println("W: " + W);
    }
    
    private void transform_graph (Graph<Integer,Double> g) {
        determinar_w(g);
        //Copiar grafo a arraylist
        // Matrix initialization like Mi (nodes);
        for (int i = 0; i < nodes; ++i) {
            ArrayList<Integer> init = new ArrayList<>();
            graf.add(init);
        }
        
        ArrayList<Boolean> vertex_vist = new ArrayList<>(Collections.nCopies(nodes, Boolean.FALSE));
        for (int i = 0; i < nodes; ++i) {
            vertex_vist.set(i, Boolean.TRUE);
            ArrayList<Integer> veins = new ArrayList<>();
            veins = g.getNeighbors(vertexs.get(i));
            for (Integer vei : veins) {
                if (!vertex_vist.get(vertexs.indexOf(vei)) && g.getEdge(vertexs.get(i), vei).getValue() >= W) {
                    graf.get(i).add(vertexs.indexOf(vei));
                    graf.get(vertexs.indexOf(vei)).add(i);
                }
            }
        }
    }
    
    /*
    private void write_graph (ArrayList< ArrayList<Integer> > g) {
        for (ArrayList<Integer> aux : g) {
            for (Integer node : aux) {
                System.out.print(node + " ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    */
    
    private int calcula_grau_maxim() {
        int maxim = 0;
        for (int i = 0; i < nodes; i++) {
            if (graf.get(i).size() > maxim) {
                maxim = graf.get(i).size();
            }
        }
        return maxim;
    }
    
    private Boolean contingut (ArrayList<Integer> a, ArrayList<Integer> b) {
        int j = 0;
        for (int i = 0; i < a.size(); i++) {
            while (j < b.size() && a.get(i) > b.get(j)) {
                j++;
            }
            if (j == b.size() || b.get(j) > a.get(i)) return false;
        }
        return true;
    }
    
    private ArrayList<Integer> copia_arraylist (ArrayList<Integer> v) {
        ArrayList<Integer> copia = new ArrayList<>();
        for (int pos : v) {
            copia.add(pos);
        }
        return copia;
    }
    
    private Boolean contingut_en_alguna_clique (ArrayList<Integer> v) {
        ArrayList<Integer> vcopia = new ArrayList<>();
        vcopia = copia_arraylist(v);
        //sort (v.begin(), v.end());  Ordenar vector en C++
        Collections.sort(vcopia);   // Ordenar vector en Java
        for (ArrayList<Integer> clique : cliques) {
            if (contingut(vcopia, clique)) {
                return true;
            }
        }
        return false;
    }
    
    private ArrayList<Integer> unio_de_vectors (ArrayList<Integer> a, ArrayList<Integer> b) {
        ArrayList<Integer> res = new ArrayList<>();
        for (Integer a1 : a) res.add(a1);
        for (Integer b1 : b) res.add(b1);
        return res;
    }
    
    private ArrayList<Integer> buscar_nous_candidats (ArrayList<Integer> candidats, int posicio_node) {
        ArrayList<Integer> res = new ArrayList<>();
        int node = candidats.get(posicio_node);
        for (int i = posicio_node + 1; i < candidats.size(); i++) {
            for (int j = 0; j < graf.get(node).size(); j++) {
                if (candidats.get(i) == graf.get(node).get(j)) {
                    res.add(candidats.get(i));
                    break;
                }
            }
        }
        return res;
    }
    
    private void backtracking (ArrayList<Integer> actual, ArrayList<Integer> candidats, int k) {
        if (contingut_en_alguna_clique(unio_de_vectors(actual, candidats)))
            return;
        
        if (actual.size() == k) {
            if (!contingut_en_alguna_clique(actual)) {
                ArrayList<Integer> copia = new ArrayList<>();
                copia = copia_arraylist(actual);
                Collections.sort(copia);
                cliques.add(copia);
            }
            return;
        }
        
        for (int i = 0; i < candidats.size(); i++) {
            actual.add(candidats.get(i));
            ArrayList<Integer> nous_candidats = new ArrayList<>();
            nous_candidats = buscar_nous_candidats(candidats, i);
            backtracking(actual, nous_candidats, k);
            //actual.pop_back(); Remove last vector element on C++
            actual.remove (actual.size()-1);
        }
    }
    
    // Pre: El vector cliques conte totes les cliques maximals de mida mes gran que k
    private void calcula_cliques_mida_k (int k) {
        for (int i = 0; i < nodes; i++) {
            ArrayList<Integer> actual = new ArrayList<>();
            actual.add(i);
            ArrayList<Integer> candidats = new ArrayList<>();
            for (int j = 0; j < graf.get(i).size(); j++) {
                if (graf.get(i).get(j) > i) candidats.add(graf.get(i).get(j));
            }
            backtracking(actual, candidats, k);
        }
    }
    
    private void calcula_cliques () {
        int mida_maxima = calcula_grau_maxim() + 1;
        for (int i = mida_maxima; i > 0; i--) {
            calcula_cliques_mida_k(i);
        }
    }
    
    private int quants_nodes_coincideixen (ArrayList<Integer> a, ArrayList<Integer> b) {
        int coincidencies = 0;
        for (int i = 0; i < a.size(); i++)
            for (int j = 0; j < b.size(); ++j)
                if (a.get(i) == b.get(j)) coincidencies++;
        
        return coincidencies;
    }
    
    private void calcula_mat1 () {
        
        // Matrix initialization like Mi (cliques.size(), Vi(cliques.size(), 0))
        for (int i = 0; i < cliques.size(); i++) {
            ArrayList<Integer> init = new ArrayList<>(Collections.nCopies(cliques.size(), 0));
            mat1.add(init);
        }
        
        for (int i = 0; i < cliques.size(); i++){
            for (int j = i; j < cliques.size(); ++j) {
                int coincidencies = quants_nodes_coincideixen(cliques.get(i), cliques.get(j));
                //mat1[i][j] = mat1[j][i] = coincidencies; En C++
                mat1.get(i).set(j, coincidencies);
                mat1.get(j).set(i, coincidencies);
            }
        }
    }
    
    private void determinar_k () {
        double k_min = 999999;
        double k_max = 0;
        double suma_k = 0;
        for (int i = 0; i < mat1.size(); i++) {
            int k_size = mat1.get(i).get(i);
            suma_k = suma_k + k_size;
            if (k_size > k_max) k_max = k_size;
            if (k_size < k_min) k_min = k_size;
        }
        double mitja_k = suma_k/mat1.size();
        //System.out.print("\n");
        //System.out.println("k min: " + (int)k_min);
        //System.out.println("k max: " + (int)k_max);
        //System.out.println("Media de k: " + mitja_k);
        //System.out.println("P: " + super.p);
        double factor = super.p/100.;
        //System.out.println("factor: " +factor);
        this.K = (int) (mitja_k*factor + k_min*(1-factor));  // this.k = function related to the percentage
        if (this.K < 2) this.K = 2;
        //System.out.println("K: " + K);
    }
    
    private void calcula_mat2 () {
        determinar_k();
        
        // Matrix initialization like Mb (cliques.size(), Vb(cliques.size(), false))
        for (int i = 0; i < cliques.size(); i++) {
            ArrayList<Boolean> init = new ArrayList<>();
            for (int j = 0; j < cliques.size(); j++) init.add(Boolean.FALSE);
            mat2.add(init);
        }
        
        for (int i = 0; i < mat1.size(); i++) {
            for (int j = 0; j < mat1.size(); j++) {
                if ((i != j && mat1.get(i).get(j) >= (K-1)) || (i == j && mat1.get(i).get(j) > (K-1))) {
                    mat2.get(i).set(j, Boolean.TRUE);
                    mat2.get(j).set(i, Boolean.TRUE);
                }
            }
        }
    }
    
    private void visitar_dfs (int i, ArrayList<Integer> aux, ArrayList<Boolean> vist) {
        vist.set(i, Boolean.TRUE);
        aux.add(i);
        for (int j = 0; j < cliques.size(); j++) {
            if (j != i && !vist.get(j) && mat2.get(i).get(j))
                visitar_dfs(j, aux, vist);
        }
    }
    
    private ArrayList< ArrayList<Integer> > dfs () {
        
        // Vector initialization like Vb (cliques.size(), false);
        ArrayList<Boolean> vist = new ArrayList<>();
        for (int i = 0; i < cliques.size(); i++) vist.add(Boolean.FALSE);
        
        ArrayList< ArrayList<Integer> > res_dfs = new ArrayList<>();
        for (int i = 0; i < cliques.size(); i++) {
            if (mat2.get(i).get(i) && !vist.get(i)) {
                ArrayList<Integer> aux = new ArrayList<>();
                visitar_dfs(i, aux, vist);
                res_dfs.add(aux);
            }
        }
        return res_dfs;
    }
    
    private void calcula_conjunts () {
        ArrayList< ArrayList<Integer> > res_dfs = new ArrayList<>();
        res_dfs = dfs();
        for (int i = 0; i < res_dfs.size(); i++) {
            ArrayList<Integer> nodes_implicats = new ArrayList<>();
            // Vector initialization like Vb (cliques.size(), false);
            ArrayList<Boolean> brut = new ArrayList<>();
            for (int p = 0; p < nodes; p++) brut.add(Boolean.FALSE);
            
            for (int j = 0; j < res_dfs.get(i).size(); j++) {
                for (int z = 0; z < cliques.get(res_dfs.get(i).get(j)).size(); z++) {
                    //if (not brut[cliques[res_dfs[i][j]][z]]) {
                    if (!brut.get(cliques.get(res_dfs.get(i).get(j)).get(z))) {
                        nodes_implicats.add(vertexs.get(cliques.get(res_dfs.get(i).get(j)).get(z)));
                        brut.set(cliques.get(res_dfs.get(i).get(j)).get(z), Boolean.TRUE);
                    }
                }
            }
            conjunts.add(nodes_implicats);
        }
    }
    /*
    public void read_graph(ArrayList< ArrayList<Integer> > g) {
        //Copiar arraylist
        for (int i = 0; i < g.size(); ++i) {
            ArrayList<Integer> linia = new ArrayList<>();
            linia = copia_arraylist(g.get(i));
            graf.add(linia);
        }
    }
    */
    private void main () {
        //write_graph(graf);
        calcula_cliques();
        calcula_mat1();
        //write_graph(mat1);
        calcula_mat2();
        calcula_conjunts();
        //write_graph(conjunts);
    }
    
    @Override
    public void calc(Graph<Integer,Double> g) {
        // Reserva de nuevos espacios de memoria
        vertexs = new ArrayList<>();
        graf = new ArrayList<>();
        cliques = new ArrayList<>();
        mat1 = new ArrayList<>();
        mat2 = new ArrayList<>();
        conjunts = new ArrayList<>();
        // Graph to ArrayList
        transform_graph(g);
        // Calc
        main();
    }
    
    @Override
    public ArrayList<ArrayList<Integer>> obtain() {
        return conjunts;
    }
    
}