package test;
import junit.framework.*;
import org.junit.Before;
import org.junit.Test;

public class WordNetTest {
    String hypnym;
    String sysnet;

    @Before
    public void setup() {
        hypnym = "/mnt/data/CloudStation/2-Projects/Algorithms II/psets/pset1/testfiles/wordnet/synsets.txt";
        sysnet = "/mnt/data/CloudStation/2-Projects/Algorithms II/psets/pset1/testfiles/wordnet/hypernyms.txt";
    }

    @Test
    public void testBuild() {
        WordNet wn = new WordNet(sysnet, hypnym);
    }
}
