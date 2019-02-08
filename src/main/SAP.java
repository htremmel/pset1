import java.lang.IllegalArgumentException;
import java.lang.Comparable;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Bag;

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

    class searcher {

        int _v;
        boolean[] marked;
        Integer[] edgeTo;
        Digraph _g;
        int root;

        public searcher (int v, int rt, Digraph g) { // could be a reversed diagraph or whatever orientation.
            _g = g;
            root = rt;
            marked = new boolean[_g.V()];
            edgeTo = new Integer[_g.V()]; // Could these be unecessarily massive.  Perhaps room for a linked list?
            _v = v;
        };

        // Traverses the graph and finally comes to the root.  This is part of the brute force attempt.
        void generate() {
            Queue<Integer> queue = new Queue<Integer>();
            queue.enqueue(root);
            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int x: _g.adj(v)) {
                    if (!marked[x]) {
                        edgeTo[x] = v;
                        marked[x] = true;
                        queue.enqueue(x);
                    }
                    if (x == _v) break; // If the searching is found stop generating edgeTo.
                }
            }
        }

        Integer lengthTo() {
            int len = 0;
            int t = _v;
            while (t == root) {
                len++;
                t = this.edgeTo[t];
            }
            return len;
        }

        Integer lengthTo(int root) {
            int len = 0;
            int t = _v;
            if (this.edgeTo[root] == null) return null; // if the root provided was found, then not valid.  Exit.
            while (t == root) {
                len++;
                t = this.edgeTo[t];
            }
            return len;
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
        MinPQ<LCA> common; 
        int iamroot;
        public WordTree() {
            common = new MinPQ<LCA>();
            iamroot = getRoot();
        }

        // Finds the ancestor with the shortest path. Generators an ancestor class.
        public int ancestor(int v, int w) {

        }

        public int getRoot() {
            for (int i = 0; i < _graph.V(); i++)
                if(_graph.outdegree(i) == 0)
                    return i;

            throw new IllegalArgumentException("Not an acyclic graph.");
        }

        // Checks the tree object to see if it exists on the tree.
        boolean isfound(int v) {
            return tree.reverse().indegree(v) != 0;
        }

        public int length(int v, int w) {
            return getLCA(new field(v), new field(w)).length;
        }

        public LCA getLCA(int v, int w) {
            Digraph reversed = _graph.reverse();
            searcher sv = new searcher(v,iamroot,reversed); // BFS from root to target.  We're pretty sure this exists if its acyclic.
            searcher sw = new searcher(w,iamroot,reversed);
            MinPQ<LCA> shortest = new MinPQ<LCA>();

            // Refactor to LCA type?
            for (int i = 0; i < reversed.V(); i++) { // Walk from root to v and w and store a LCA that's reachable by both.
                if(sv.marked[i] && sw.marked[i]) {
                    LCA lca = new LCA();
                    lca.length = sv.lengthTo(i) + sw.lengthTo(i);
                    lca.v = v;
                    lca.w = w;
                    lca.ancestor = i;
                    shortest.insert(lca);
                }
            }
            return shortest.min();
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
