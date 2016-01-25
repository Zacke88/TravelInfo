import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Zacke on 2016-01-18.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
