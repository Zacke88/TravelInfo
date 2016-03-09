import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by id12jzn on 2016-03-09.
 *
 * Denna klass anvands for att skapa eller updatera all data i tabellen.
 * Detta gors via en SwingWorker trad for att detta skall goras i bakgrunden
 * och inte paverka resten av programmet. Nar den har kort klart sa kallar
 * den pa builTable() i guit vilket sedan visar den nya tabellen for anvandaren
 */
public class BuildOfferTable extends SwingWorker<Void, Object> {

    private GUI gui;

    public BuildOfferTable(GUI gui) {
        this.gui = gui;
    }

    @Override
    protected Void doInBackground() {
        ReadXML xml = new ReadXML();
        try {
            xml.readFile();
        } catch (ParserConfigurationException e) {
            gui.errorMessage.setText("<html><font-color 'red'>Parser Error</font></html>");
        } catch (SAXException e) {
            gui.errorMessage.setText("<html><font-color 'red'>Sax Error</font></html>");
        } catch (IOException e) {
            gui.errorMessage.setText("<html><font-color 'red'>IO " +
                    "Error</font></html>");
        }
        JTable table = new JTable(new OfferTable(xml.getList()));
        gui.buildTable(table);

        return null;
    }
}
