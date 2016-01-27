import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by id12jzn on 2016-01-25.
 *
 * En klass som kan köras som en tråd vilket updaterar tabellen i GUI't efter ett angivet intervall
 */
public class TableUpdater implements Runnable {


    @Override
    public void run() {

        ReadXML xml = new ReadXML();
        try {
            xml.readFile();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        JTable table = new JTable(new OfferTable(xml.getList()));

        GUI gui = new GUI();
        gui.buildTable(table);
    }
}
