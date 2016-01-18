import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Zacke on 2016-01-18.
 */
public class OfferTable extends AbstractTableModel {

    private int dataRows = 4;

    String[] columnNames = {"Campaign",
            "Departure",
            "OutDate",
            "DestinationName"};

    Object[][] data;

    public OfferTable(List<ModelOffers> list) {

        data = new Object[list.size()][dataRows];

        setData(list);

    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setData(List<ModelOffers> list) {

        for(int i = 0; i < list.size(); i++) {

            setValueAt(list.get(i).getCampaignName(), i, 0);
            setValueAt(list.get(i).getDepartureName(), i, 1);
            setValueAt(list.get(i).getOutDate(), i, 2);
            setValueAt(list.get(i).getDestinationName(), i, 3);

        }

    }
}
