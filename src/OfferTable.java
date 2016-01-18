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

        Object[][] data = new Object[list.size()][dataRows];

        for(int i = 0; i <= list.size(); i++) {
            data[i][0] = list.get(i).getCampaignName();
            data[i][1] = list.get(i).getDepartureName();
            data[i][2] = list.get(i).getOutDate();
            data[i][3] = list.get(i).getDestinationName();
        }

    }
}
