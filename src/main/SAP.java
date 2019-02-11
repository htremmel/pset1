import java.lang.IllegalArgumentException;
import java.lang.Comparable;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;

public class SAP {

    IFinder finder;
    static Digraph _graph;
    static Digraph reversed;
    // Takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Shall provide a valid graph object in constructor.");
        finder = new WordTree();
        _graph = G; // Not the best way to handle this.
        reversed = G.reverse();
        if (_graph == null) throw new IllegalArgumentException("Shall provide a valid graph object in constructor.");
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
        return finder.length(v,w);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return finder.ancestor(v,w);
    }

    // do unit testing of this class
    public static void main(String[] args) {
    }

    static class searcher {

        int _v;
        boolean[] marked;
        Integer[] edgeTo;
        int root;

        public searcher (int v, int rt) { // could be a reversed diagraph or whatever orientation.
            root = rt;
            marked = new boolean[reversed.V()];
            edgeTo = new Integer[reversed.V()]; // Could these be unecessarily massive.  Perhaps room for a linked list?  I need this for null detection.
            _v = v;
        };

        // Traverses the graph and finally comes to the root.  This is part of the brute force attempt.
        void generate() {
            Queue<Integer> queue = new Queue<Integer>();
            queue.enqueue(root);
            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (int x: reversed.adj(v)) {
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

    static interface IFinder {
        public int ancestor(int v, int w);
        public int length(int v, int w);
        public int ancestor(Iterable<Integer> v, Iterable<Integer> w);
        public int length(Iterable<Integer> v, Iterable<Integer> w);
    }

    // Generates a common ancestor from to fields.
    static class LCA implements Comparable<LCA> {
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
    static class WordTree implements IFinder {
        MinPQ<LCA> shortest; 
        ST<Integer, searcher> mem;
        int iamroot;

        public WordTree() {
            iamroot = getRoot();
            shortest = new MinPQ<LCA>();
            mem = new ST<Integer,searcher>();
        }

        // Provides the lowest common ancestor from the collection of ST.
        public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
            for (int i : v) {
                for (int j : w) {
                    search (i,j);// Almost there.  How to handle of a pair doesn't have a CA?
                }
            }
           return shortest.min().ancestor;
        }

        // Provides the lowest common ancestor from the collection of ST.
        public int length(Iterable<Integer> v, Iterable<Integer> w) {
            for (int i : v) {
                for (int j : w) {
                    search (i,j);
                }
            }
           return shortest.min().length;
        }

        public int ancestor(int v, int w) {
            search(v,w);
            return shortest.min().ancestor;
        }

        public int length(int v, int w) {
            search(v,w);
            return shortest.min().length;
        }

        // Finds the ancestor with the shortest path. Generators an ancestor class.
        void search(int v, int w) {
            add(v);
            add(w);
            putLCA(mem.get(v),mem.get(w));
        }

        void add(int v) {
            if (!mem.contains(v))
                mem.put(v,new searcher(v,iamroot)); // BFS from root to target.  We're pretty sure this exists if its acyclic.
        }

        int getRoot() {
            for (int i = 0; i < _graph.V(); i++)
                if(_graph.outdegree(i) == 0)
                    return i;

            throw new IllegalArgumentException("Not an acyclic graph.");
        }

        void putLCA(searcher sv, searcher sw) {
            // Refactor to LCA type?
            for (int i = 0; i < reversed.V(); i++) { // Walk from root to v and w and store a LCA that's reachable by both.
                if(sv.marked[i] && sw.marked[i]) {
                    LCA lca = new LCA();
                    lca.length = sv.lengthTo(i) + sw.lengthTo(i);
                    lca.v = sv._v;
                    lca.w = sw._v;
                    lca.ancestor = i;
                    shortest.insert(lca);
                }
            }
        }
    }
}
