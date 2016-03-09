import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by id12jzn on 2016-01-25.
 * <p>
 * En klass som kan koras som en trad vilket updaterar tabellen i GUI't efter
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

        new BuildOfferTable(gui).execute();

        this.run();
    }
}
