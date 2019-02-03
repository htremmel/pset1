import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.

/* Name: Shortest Ancesteral Path.
 * Desc: An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length. For example, in the digraph at left (digraph1.txt), the shortest ancestral path between 3 and 11 has length 4 (with common ancestor 1). In the digraph at right (digraph2.txt), one ancestral path between 1 and 5 has length 4 (with common ancestor 5), but the shortest ancestral path has length 2 (with common ancestor 0).
 * Crea: 02/04/2018
 */
public class SAP {

    IFinder finder;
    Digraph _graph;
    // Takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Shall provide a valid graph object in constructor.");
        finder = new WordTree(G);
        _graph = new WordTree(G); // Not the best way to handle this.

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
    public static void main(String[] args) {}

    /*
     * name: scanner
     * desc: Scans the graph for the target nodes v and w.
     */

    private class field {
        int so;
        public boolean[] marked;
        public int[] edgeTo;
        public field(int v){
            so = v;
        };
        public boolean has(field other) {
            for (int i = 0; i <= marked.length; i++) {
              if (marked[i] && other.marked[i]) return true;
            }
            return false;
        }
    }

    abstract class searcher {

        // Consists of a single permutation of seek, bfs and dfs.
        public int step() {

        }

        // will populate the field object via bfs and dfs implementations.
        public abstract void seek();
    }

    class mybfs extends searcher{
        @Override
        public void seek() {
            Queue<Integer> queue = new Queue<Integer>();
            queue.enqueue(s);
            while (! queue.isEmpty()) {
                int v = queue.dequeue();
                for (int x: _graph.adj(v))
                    if (!f.marked[x]) {
                        f.edgeTo[x] = v;
                        f.marked[x] = true;
                        queue.enqueue(x);
                    }
            }
        }
    }

    class mydfs extends searcher {
        @Override
        public void seek() {
            f.marked[v] = true;
            for (int w: _graph.adj(v))
                if (!f.marked[w])
                    dfs(v);
        }
    }

    private interface IFinder {
        public void add(int v); // Adds the combination to a stack, will find the CA and SL from stack.
        public int ancestor();  // Calls the CA from the current stack.
        public int length();
        public int ancestor(int v, int w);
        public int length(int v, int w);
    }

    class ca implements Comparable<ca> {
        int length;
        int v;
        int w;
        int ancestor;

        @Override
        public int compareTo(ca other) {
            if (this.length < other.length) return -1;
            if (this.length > other.length) return 1;
            return 0;
        }
    }

    // I want this pulse and find using DFS and BFS like slime mold.
    private class WordTree extends Digraph implements IFinder {
        boolean[] marked;
        ST<Integer,field> fields;
        MinPQ<ca> minlength; 
        WordTree working;
        public WordTree(Digraph graph) {
            super(graph);
            fields = new ST<Integer,field>();
            minlength = new MinPQ<ca>();
            working = new WordTree(new Digraph(graph.V()));
            marked = new boolean[graph.V()];
        }

        int root() {
            // returns the root of the digraph if found.
            return 1;

        }

        boolean has (int v) {
            return marked[v];
        }


        // Finds the ancestor with the shortest path. Generators an ancestor class.
        public int ancestor(int v, int w) {
           add(v);
           add(w);
           generate(fields.get(v), fields.get(w));
        }

        public void add(int v) {
            Queue<Integer> queue = new Queue<Integer>();
            queue.enqueue(v);
            while (! queue.isEmpty()) {
                int w = queue.dequeue();
                for (int x: super.adj(v))
                    if (!working.marked[x]) {
                        f.edgeTo[x] = v;
                        f.marked[x] = true;
                        queue.enqueue(x);
                    }
            }

        }

        public int length(int v, int w) {
           add(v);
           add(w);
           generate(fields.get(v), fields.get(w));
        }

        // Provides the lowest common ancestor from the collection of ST.
        public int ancestor() {

        }

        void generate(field v, field w) {
            searcher bfs = new mybfs();
            // Iterates through the step for each.
            // If this is the first entry in fields, use the brute force method.  If 1...* use reverse diagraph method.
            while (!v.has(w)) {
               v.has(w);
            };


        }

        // Provides the lowest common ancestor from the collection of ST.
        public int length() {

        }
    }
}
