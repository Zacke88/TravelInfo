import javax.swing.*;

/**
 * Created by Zacke on 2016-01-18.
 */
public class ControllerMain {

    public static void main(String[] args) throws Exception {

        ReadXML xml = new ReadXML();
        xml.readFile();

        JTable table = new JTable(new OfferTable(xml.getList()));
        JScrollPane scrollPane = new JScrollPane(table);

        GUI gui = new GUI();
        gui.buildTable(table);


    }

}
