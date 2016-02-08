import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by id12jzn on 2016-01-25.
 * <p>
 * En klass som kan köras som en tråd vilket updaterar tabellen i GUI't efter
 * ett angivet intervall
 */
public class TableUpdater implements Runnable {

    private GUI gui;

    public TableUpdater(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(gui.getTimer());
        } catch (InterruptedException e) {
        }

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
        gui.rebuildTable(table);
        this.run();
    }
}
