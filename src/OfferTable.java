import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by id12jzn on 2016-01-18.
 *
 * Bygger upp tabellen för GUI't som består av samtliga offers programmet läst in
 * Här anges vilka kolumner som finns i tabellen och data läggs ttill enligt listan med alla offers som tas in i
 * konstruktorn. Metoder finns för att sätta samt hämta data och kolumner
 */
public class OfferTable extends AbstractTableModel {

    private int dataRows = 4;

    String[] columnNames = {"Destination",
            "Date",
            "Price",
    "Image"};

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

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return String.class;
        }
        return null;
    }

    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setData(List<ModelOffers> list) {

        for(int i = 0; i < list.size(); i++) {

            setValueAt(list.get(i).getDestinationName(), i, 0);
            setValueAt(list.get(i).getOutDate(), i, 1);
            setValueAt(list.get(i).getCurrentPrice(), i, 2);
            setValueAt(list.get(i).getHotelImage(), i, 3);
        }
    }
}
