import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Zacke on 2016-02-08.
 *
 * Test for ReadXML klassen som ser till sa att inlasningen fungerar och
 * faktiskt returnerar en lista som inte ar null dar man ocksa kan komma at
 * element fran ModelOffers som lsitan innehaller som inte heller returnerar
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