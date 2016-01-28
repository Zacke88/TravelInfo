import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by id12jzn on 2016-01-25.
 *
 * Lyssnare till update-knappen vilket läser in datat från XMLen och updaterar tabellen som visas i GUI't
 */
public class UpdateListener implements ActionListener {

    private GUI gui;

    public UpdateListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        gui.updater.interrupt();

        /*

        ReadXML xml = new ReadXML();
        try {
            xml.readFile();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (SAXException e1) {
            e1.printStackTrace();
        }

        JTable table = new JTable(new OfferTable(xml.getList()));

        gui.rebuildTable(table);

        */

    }
}
