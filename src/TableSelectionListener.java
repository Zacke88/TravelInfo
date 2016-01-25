import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by Zacke on 2016-01-25.
 */
public class TableSelectionListener implements ListSelectionListener {

    GUI gui;

    public TableSelectionListener(GUI gui) {
        this.gui = gui;
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (e.getValueIsAdjusting()) {
            DefaultListSelectionModel selection = (DefaultListSelectionModel) e.getSource();
            int row = selection.getAnchorSelectionIndex();
            gui.updateInfo(row);
        }
    }
}
