import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by id12jzn on 2016-01-18.
 *
 * Lyssnare för val som görs i ComboBoxen som GUI't skapar
 * Denna combobox anger i vilket intervall tabellen skall updateras
 */
public class ComboBoxListener implements ActionListener {

    private GUI gui;

    public ComboBoxListener(GUI gui) {
        this.gui = gui;
        gui.updater = new Thread(new TableUpdater(gui));
        gui.updater.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().toString().contains("30min")) {
            System.out.println("30");
            gui.setTimer(3*60*1000);
            //gui.updater.start();
        }
        if(e.getSource().toString().contains("60min")) {
            System.out.println("60");
            gui.setTimer(60*60*1000);
            //gui.updater.start();
        }
        if(e.getSource().toString().contains("90min")) {
            System.out.println("90");
            gui.setTimer(90*60*1000);
            //gui.updater.start();
        }
    }
}