import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by id12jzn on 2016-01-25.
 * <p>
 * Lyssnare till update-knappen vilket läser in datat från XMLen och
 * updaterar tabellen som visas i GUI't
 */
public class UpdateListener implements ActionListener {

    private GUI gui;

    public UpdateListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gui.updater.interrupt();
    }
}
