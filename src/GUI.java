import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Zacke on 2016-01-18.
 */
public class GUI {

    private JFrame frame = new JFrame();

    private JButton blackButton;
    private JButton redButton;
    private JButton greenButton;
    private JButton searchButton;

    private JTextField searchField;

    private JComboBox comboBox;


    //Should only be called on EDT
    public GUI() {

        buildFrame();

    }

    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBorder(BorderFactory.createTitledBorder("Offers"));


        //upperPanel.add(textField, BorderLayout.CENTER);

        // Combobox
        JLabel labelCombo = new JLabel("Combo Boxxen");

        // Options in the combobox
        String[] options = { "Option1", "Option2", "Option3", "Option4", "Option15" };
        comboBox = new JComboBox(options);
        comboBox.addActionListener(new ComboBoxListener());

        searchField = new JTextField("Search filter");

        searchButton.addActionListener(new SearchListener());

        upperPanel.add(labelCombo, FlowLayout.LEFT);
        upperPanel.add(comboBox, FlowLayout.LEFT);
        upperPanel.add(searchField, FlowLayout.RIGHT);
        upperPanel.add(searchButton, FlowLayout.RIGHT);

        return upperPanel;
    }

    private JPanel buildRightPanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(BorderFactory.createTitledBorder("Textfärgskontroll"));
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        return middlePanel;
    }

    private JPanel buildLeftPanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setBorder(BorderFactory.createTitledBorder("Textfärgskontroll"));
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        return middlePanel;
    }

    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();

        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JCheckBox disableCheckBox = new JCheckBox("Inga förändringar");

        lowerPanel.add(disableCheckBox);

        return lowerPanel;
    }

    public void buildFrame() {

        // Build panels
        JPanel upperPanel = buildUpperPanel();
        JPanel rightPanel = buildRightPanel();
        JPanel leftPanel = buildLeftPanel();
        JPanel lowerPanel = buildLowerPanel();

        //Add panels to the frame
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.EAST);
        frame.add(rightPanel, BorderLayout.WEST);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        frame.setTitle("Offers");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void buildTable(JTable table) {


    }


}
