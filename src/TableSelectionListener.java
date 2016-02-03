import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by id12jzn on 2016-01-25.
 * <p>
 * Lyssnare till tabellen för att visa rätt info i GUI't baserat på vilken
 * rad användaren klickar på
 * Kallas på barje gång en ny rad klickas på
 */
public class TableSelectionListener implements ListSelectionListener {

    private GUI gui;
    public TableSelectionListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() && !e.getSource().toString().contains
                ("{}")) {
            DefaultListSelectionModel selection = (DefaultListSelectionModel)
                    e.getSource();
            int row = selection.getAnchorSelectionIndex();
            gui.updateInfo(row);
        }
    }
}
