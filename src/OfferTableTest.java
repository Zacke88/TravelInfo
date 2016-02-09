import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Zacke on 2016-02-08.
 *
 * Test for klassen OfferTable vilket ser till sa att ratt antal rader och
 * kolumner kan hamtas ur denna tabell-klass och att data gar att lagga till
 * som det ska, bade manuellt och fran en lista. Ser aven till sa att
 * kolumnerna ar av ratt datatyp.
 */
public class OfferTableTest {

    OfferTable offer;
    ModelOffers offers;

    @Before
    public void setUp() throws Exception {
        offers = new ModelOffers();
        ArrayList<ModelOffers> list = new ArrayList<>();
        list.add(offers);
        offer = new OfferTable(list);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRowCount() {
        assertEquals(1, offer.getRowCount());
    }

    @Test
    public void testColumnCount() {
        assertEquals(6, offer.getColumnCount());
    }

    @Test
    public void testGetColumnClass() {
        assertEquals(Integer.class, offer.getColumnClass(2));
    }

    @Test
    public void testAddDataToTableFromList() throws Exception {
        offers.setDestinationName("test");
        ArrayList<ModelOffers> list = new ArrayList<>();
        list.add(offers);
        offer = new OfferTable(list);
        assertNotNull(offer.getValueAt(0, 0));
    }

    @Test
    public void testSetValueAt() {
        offer.setValueAt("test", 0, 2);
        assertNotNull(offer.getValueAt(0, 2));
    }
}