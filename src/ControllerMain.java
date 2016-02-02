import javax.swing.*;
import java.io.FileReader;
import java.util.Properties;

/**
 * Created by Zacke on 2016-01-18.
 *
 * Controller för programmet vilken knyter ihop vyn med modellen samt fungerar som en main-klass
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
