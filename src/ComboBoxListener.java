import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Zacke on 2016-01-18.
 */
public class ComboBoxListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().toString().contains("30min")) {
            System.out.println("30");
        }
        if(e.getSource().toString().contains("60min")) {
            System.out.println("60");
        }
        if(e.getSource().toString().contains("90min")) {
            System.out.println("90");
        }


    }
}
