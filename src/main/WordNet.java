import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import edu.princeton.cs.algs4.Bag;
import java.lang.UnsupportedOperationException;

/* Name: WordNet, an organization of hyper and hypo nyms.
 * Appr: Perahps best represented as SymbolGraph with a directed graph spin.
 */
public class WordNet {

    Digraph dag;
    ST<Integer, String> synT;

    // each is a file name to the each data type.
    public WordNet(String synsets, String hypernyms) {
        // Take a synset a set containing an id, a pair of synoyms, and a dictionary definition (gloss).
        // A hypernym, a successor relationship linking a specific synset (hyponym) to a general synset (hypernym).
        // The wordnet will map the synsets to a root as the most generic (widely applicable) hypernym.
        // Task, build a DAG of this relationship and operate on it.
        StdOut.print("I'm here.");
        if ( hypernyms == null ) {  throw new java.lang.IllegalArgumentException(); } 
        if ( synsets == null ) { throw new java.lang.IllegalArgumentException(); }

        parseSynset(new In(synsets));
        parseHypernyms(new In(hypernyms));
    }

    public Iterable<String> nouns() {
       throw new UnsupportedOperationException();
    }

    public boolean isNoun(String word) {
       throw new UnsupportedOperationException();

    }

    public int distance(String nounA, String nounB) {
       throw new UnsupportedOperationException(); }

    public String sap(String nounA, String nounB) {
       throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        WordNet wn = new WordNet("/mnt/data/CloudStation/2-Projects/Algorithms II/psets/pset1/testfiles/wordnet/synsets.txt","/mnt/data/CloudStation/2-Projects/Algorithms II/psets/pset1/testfiles/wordnet/hypernyms.txt");

        StdOut.printf("Parsed!");
    }

    private void parseHypernyms (In hyper) {
       String[] ints;
       Integer id;
       int cnt = 0;
       Bag<hypernym> hyps = new Bag<hypernym>();
       hypernym root, nxt;
       String noun;

       // Read the file to bag, get count.
        do {
            ints = hyper.readLine().split("\\,");
            id = Integer.parseInt(ints[0]);
            root = new hypernym(id, synT.get(id));

            for (int i = 1; i < ints.length; i++) {
                cnt++;
                id = Integer.parseInt(ints[i]);
                noun = synT.get(Integer.parseInt(ints[i]));
                root.put(new hypernym(Integer.parseInt(ints[i]), noun));
                StdOut.println(cnt + "\r");
            }
            hyps.add(root);
        } while (hyper.hasNextLine());

        // Create my DAG.
        dag = new Digraph(cnt);
        for (hypernym hyp : hyps) {
            root = hyp;
            do {
                nxt = (hypernym) hyp.toNext();
                dag.addEdge(root.id, nxt.id);
            } while (hyp.toNext() != null);
        }
       StdOut.println("out of hypernyms.");
    }

    private void parseSynset (In syns) {
        String line;
        Integer id;
        synT = new ST<Integer,String>();

        do {
            line = syns.readLine();
            id = Integer.parseInt(line.split("\\,")[0]);
            synT.put(id,line);
        } while (syns.hasNextLine());
    }

    private class hypernym {
        hypernym prev = null;
        hypernym next = null;

        public String noun;
        public int id;

        /**
         * Shaboom constructor for hypernym.
         */
        public hypernym(int id, String noun) {
            this.id = id;
            this.noun = noun;
        }

        public void put(hypernym nxt) {
            hypernym pv = this.toLast();
            pv.next = nxt;
            nxt.prev = pv;
        }

        public hypernym toNext() {
            return next;
        }

        public hypernym toPrev() {
            return prev;
        }

        public hypernym toLast() {
            hypernym tmp;
            tmp = this;
            while ( tmp.toNext() != null ){
                tmp = tmp.toNext();
            };
            return tmp;
        }

        public boolean isRoot() {
            if (this.prev == null) {
                return true;
            }
            return false;
        }

    }
}
