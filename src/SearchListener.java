import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * Created by id12jzn on 2016-01-18.
 * <p>
 * Lyssnare for sokfaltet i GUI't, en form av docemtlistener som updaterar
 * tabellen baserat pa vad som skrivits in
 * eftersom det skrivs. Den soker i samtliga kolumner som syns i tabellen
 */
public class SearchListener implements DocumentListener {

    private JTextField searchFilter;
    private TableRowSorter<TableModel> rowSorter;

    public SearchListener(JTable table, JTextField searchFilter) {
        this.searchFilter = searchFilter;
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        String text = searchFilter.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = searchFilter.getText();

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
