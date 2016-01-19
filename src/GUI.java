import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Zacke on 2016-01-18.
 */
public class GUI {

    private JFrame frame = new JFrame();

    private JButton blackButton;
    private JButton redButton;
    private JButton greenButton;
    private JButton searchButton;

    private JPanel upperPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel lowerPanel;

    private JMenuBar menu = new JMenuBar();

    private JTextField searchField;

    private JComboBox comboBox;

    private JScrollPane scrollPane = new JScrollPane();

    private String imageURL = "";



    //Should only be called on EDT
    public GUI() {

        //buildFrame();

    }

    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBorder(BorderFactory.createTitledBorder("Offers"));

        // Combobox
        JLabel labelCombo = new JLabel("Combo Boxxen");

        // Options in the combobox
        String[] options = { "Option1", "Option2", "Option3", "Option4", "Option15" };
        comboBox = new JComboBox(options);
        comboBox.addActionListener(new ComboBoxListener());

        searchButton = new JButton("Search");

        searchField = new JTextField("Search filter");

        searchButton.addActionListener(new SearchListener());

        upperPanel.add(labelCombo);
        upperPanel.add(comboBox);
        upperPanel.add(searchField);
        upperPanel.add(searchButton);

        return upperPanel;
    }

    private JPanel buildRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Info"));
        //rightPanel.setLayout(new FlowLayout());

        JLabel label = new JLabel();
        JLabel image = new JLabel();
        label.setText("TJENARE JESPER");
        //image.setIcon(new ImageIcon("http://images.tuinordic.com/travel/images/hotel/Default.jpg"));
        try {
            image.setIcon(new ImageIcon(ImageIO.read(new URL(imageURL))));
        } catch (IOException e) {
            //e.printStackTrace();
        }

        rightPanel.add(label);
        rightPanel.add(image, FlowLayout.LEFT);

        return rightPanel;
    }

    private JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder("Offers"));
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        leftPanel.add(scrollPane);

        return leftPanel;
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
        upperPanel = buildUpperPanel();
        rightPanel = buildRightPanel();
        leftPanel = buildLeftPanel();
        lowerPanel = buildLowerPanel();

        //Add panels to the frame
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        buildMenu();
        frame.setJMenuBar(menu);

        frame.setTitle("Offers");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void buildMenu() {

        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);
        JMenu help = new JMenu("Help");
        menu.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);

        class ExitAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }

        class AboutAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Made by Joakim Zakrisson ( id12jzn)");

            }
        }

        exit.addActionListener(new ExitAction());
        about.addActionListener(new AboutAction());
    }

    public void buildTable(JTable table) {

        scrollPane = new JScrollPane(table);
        table.setAutoCreateRowSorter(true);
        buildFrame();
    }

    public void updateInfo(String imageURL) {

        this.imageURL = imageURL;
        frame.remove(rightPanel);
        rightPanel = buildRightPanel();
        frame.add(rightPanel);
        frame.pack();
        System.out.println(imageURL);

    }


}
