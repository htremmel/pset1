import junit.framework.*;
import org.junit.Before;
import org.junit.Test;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

public class SAPTest {
    public Digraph getDigraph() {
        return new Digraph(new In("/home/hans/documents/Algorithms2/psets/pset1/src/test/data/digraph1.txt"));
    }

    @Test
    public void LoadsDiagraph() {
        Assert.assertNotNull("Diagraph loads.", new Digraph(new In("/home/hans/documents/Algorithms2/psets/pset1/src/test/data/digraph1.txt")));
    }

    @Test
    public void ItBuilds() {
        Assert.assertNotNull("SAP Builds", new SAP(getDigraph()));
    }

    public void Clean() {
        // Release resources.
    }
}
