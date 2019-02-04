import junit.framework.*;
import org.junit.Before;
import org.junit.Test;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;

public class SAPTest {
    @Before
    public Digraph getDigraph(String uri) {
        return new Digraph(new In(uri));
    }

    @Test
    public void ItBuilds() {
        Assert.assertNotNull("SAP Builds", new SAP());
    }

    @After
    public void Clean() {
        // Release resources.
    }

}
