import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by id12jzn on 2016-01-18.
 * <p>
 * Controller för programmet vilken knyter ihop vyn med modellen samt
 * fungerar som en main-klass
 */
public class ControllerMain {

    public static void main(String[] args) throws Exception {

        ReadXML xml = new ReadXML();
        xml.readFile();

        JTable table = new JTable(new OfferTable(xml.getList()));

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.buildTable(table);
        });
    }
}
