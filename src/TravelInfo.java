import javax.swing.*;

/**
 * Created by id12jzn on 2016-01-18.
 * <p>
 * Controller for programmet vilken knyter ihop vyn med modellen samt
 * fungerar som en main-klass
 */
public class TravelInfo {

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            new BuildOfferTable(gui).execute();
        });
    }
}