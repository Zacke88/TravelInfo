import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by id12jzn on 2016-01-18.
 *
 * Lyssnare för val som görs i ComboBoxen som GUI't skapar
 * Denna combobox anger i vilket intervall tabellen skall updateras
 * Efter ett val har gjorts ändrar den tiden för i vilket intervall tråden skall updatera tabellen med offers
 */
public class ComboBoxListener implements ActionListener {

    private GUI gui;
    public ComboBoxListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().toString().contains("30min")) {
            gui.setTimer(30*60*1000, 0);
        }
        if(e.getSource().toString().contains("60min")) {
            gui.setTimer(60*60*1000, 1);
        }
        if(e.getSource().toString().contains("90min")) {
            gui.setTimer(90*60*1000, 2);
        }
    }
}