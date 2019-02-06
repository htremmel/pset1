import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;


/* Name: Shortest Ancesteral Path.
 * Crea: 02/04/2018
 */
public class SAP {

    IFinder finder;
    Digraph _graph;
    // Takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Shall provide a valid graph object in constructor.");
        finder = new WordTree(G);
        _graph = G; // Not the best way to handle this.
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return finder.length(v,w);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return finder.ancestor(v, w);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        for (int x : v) {
            for (int y : w) {
                finder.add(x);
                finder.add(y);
            }
        }
        return finder.length();
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        for (int x : v) {
            for (int y : w) {
                finder.add(x);
                finder.add(y);
            }
        }
        return finder.ancestor();
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }

    class field {
        int so;
        public boolean[] marked;
        public int[] edgeTo;
        public field(int v){
            so = v;
            generate();
        };

        // Traverses the graph and finally comes to the root.  This is part of the brute force attempt.
        void generate() {
            Queue<Integer> queue = new Queue<Integer>();
            queue.enqueue(so);
            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int x: _graph.adj(v))
                    if (!marked[x]) {
                        edgeTo[x] = v;
                        marked[x] = true;
                        queue.enqueue(x);
                    }
            }
        }
    }

    interface IFinder {
        public void add(int v); // Adds the combination to a stack, will find the CA and SL from stack.
        public int ancestor();  // Calls the CA from the current stack.
        public int length();
        public int ancestor(int v, int w);
        public int length(int v, int w);
    }

    // Generates a common ancestor from to fields.
    class LCA implements Comparable<LCA> {
        int length;
        int v;
        int w;
        int ancestor;

        @Override
        public int compareTo(LCA other) {
            if (this.length < other.length) return -1;
            if (this.length > other.length) return 1;
            return 0;
        }
    }

    // I want this pulse and find using DFS and BFS like slime mold.
    class WordTree implements IFinder {
        ST<Integer,field> fields; // Stores the fields so I'm not doing the same search again.
        Digraph tree;
        MinPQ<LCA> lca; 
        public WordTree() {
            fields = new ST<Integer,field>();
            lca = new MinPQ<LCA>();
            tree = new Digraph(_graph.V());
        }

        // Finds the ancestor with the shortest path. Generators an ancestor class.
        public int ancestor(int v, int w) {
        }

        public void add(int v) {
            field f = new field(v);
            fields.put(v, f);  // If already exists skip.
            for (int i = 0; i < f.edgeTo.length; i++ ) {
                tree.addEdge(i, f.edgeTo[i]);
            }
        }

        // Checks the tree object to see if it exists on the tree.
        boolean isfound(int v) {
            return tree.reverse().indegree(v) != 0;

        }

        public int length(int v, int w) {
            return getLCA(new field(v), new field(w)).length;
        }

        public LCA getLCA(field f1, field f2) {
            MinPQ<LCA> lca = new MinPQ<LCA>();
            for (int i = 0; i < _graph.V(); i++) {
            }
        }

        // Provides the lowest common ancestor from the collection of ST.
        public int ancestor() {
           return lca.min().ancestor;
        }

        void generate(field v, field w) {
            searcher bfs = new mybfs();
            // Iterates through the step for each.
            // If this is the first entry in fields, use the brute force method.  If 1...* use reverse diagraph method.
            while (!v.has(w)) {
               v.has(w);
            };
        }
        //
        // Provides the lowest common ancestor from the collection of ST.
        public int length() {
           return lca.min().length;
        }
    }
}
