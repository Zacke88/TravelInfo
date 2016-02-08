import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Zacke on 2016-02-08.
 *
 * Test för ReadXML klassen som ser till så att inläsningen fungerar och
 * faktiskt returnerar en lista som inte är null där man också kan komma åt
 * element från ModelOffers som lsitan innehåller som inte heller returnerar
 * null
 */
public class ReadXMLTest {

    ReadXML xml;

    @Before
    public void setUp() throws Exception {
        xml = new ReadXML();
        xml.readFile();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testListNotNull() throws Exception {
        assertNotNull(xml.getList());
    }

    @Test
    public void testListNotEmpty() {
        assertFalse(xml.getList().isEmpty());
    }

    @Test
    public void testAcessItemFromList() {
        assertNotNull(xml.getList().get(0).getDestinationName());
    }

}