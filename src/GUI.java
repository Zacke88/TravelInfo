import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by id12jzn on 2016-01-18.
 *
 * GUI-klass som bygger upp hela vyn för programmet. Den bygger en frame vilket sedan består av en BorderlLayout.
 * Denna frame består av olika paneler som placeras ut enligt layouten
 */
public class GUI {

    private JFrame frame = new JFrame();

    private JButton updateButton;

    private JPanel upperPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel lowerPanel;

    private JLabel time = new JLabel();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    private JMenuBar menu = new JMenuBar();

    private JTextField searchField;

    private JComboBox comboBox;

    private JScrollPane scrollPane = new JScrollPane();

    private String imageURL = "";

    private TableRowSorter<TableModel> rowSorter;

    private JTable table;

    /**
     * Bygger den övre panalen till framen vilket består av olika val och filtreringar användaren kan göra
     *
     * @return
     */
    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBorder(BorderFactory.createTitledBorder("Offers"));
        upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Combobox
        JLabel labelCombo = new JLabel("Set update interval: ");

        // Options in the combobox
        String[] options = { "30min", "60min", "90min"};
        comboBox = new JComboBox(options);
        comboBox.addActionListener(new ComboBoxListener());

        searchField = new JTextField("Search");
        searchField.setColumns(12);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new UpdateListener(this));

        upperPanel.add(searchField);
        upperPanel.add(labelCombo);
        upperPanel.add(comboBox);
        upperPanel.add(updateButton);

        return upperPanel;
    }

    /**
     * Bygger den högra panelen till framen vilket består av info för den rad i tabellen som är markerad
     *
     * @return
     */
    private JPanel buildRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Info"));

        JLabel label = new JLabel();
        JLabel image = new JLabel();

        if(imageURL.equals("")) {
            label.setText("Select item in list to show more info");
        }
        if(imageURL.contains("Default")) {
            label.setText("No hotel image available");
        }
        try {
            image.setIcon(new ImageIcon(ImageIO.read(new URL(imageURL))));
        } catch (IOException e) {
            label.setText("Could not load hotel image");
        }

        rightPanel.add(label, BorderLayout.NORTH);
        rightPanel.add(image, BorderLayout.CENTER);

        return rightPanel;
    }

    /**
     * Bygger den vänstra panelen till framen vilket består av tabellen som visar en lista över alla Offers
     * Denna tabell går att sortera
     *
     * @return
     */
    private JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder("Offers"));
        leftPanel.setLayout(new BorderLayout());
        //leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        leftPanel.add(scrollPane);

        return leftPanel;
    }

    /**
     * Bygger den nedre panelen till framen vilket
     *
     * @return
     */
    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();

        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel updated = new JLabel("Last updated: ");

        time.setText(sdf.format(new Date()));

        lowerPanel.add(updated);
        lowerPanel.add(time);

        return lowerPanel;
    }

    /**
     * Bygger upp själva framen och lägger till samtliga paneler som tillhör den
     */
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

    /**
     * Bygger upp menyraden som finns upptill på framen där användaren kan göra de val som läggs till
     * Innehar också lyssnare för samtliga menyval
     */
    public void buildMenu() {

        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);
        JMenu theme = new JMenu("Theme");
        menu.add(theme);
        JMenuItem light = new JMenuItem("Neutral");
        theme.add(light);
        JMenuItem dark = new JMenuItem("Funky");
        theme.add(dark);
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

        class LightAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                upperPanel.setBackground(Color.white);
                lowerPanel.setBackground(Color.white);
            }
        }

        class DarkAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                upperPanel.setBackground(Color.cyan);
                lowerPanel.setBackground(Color.cyan);

            }
        }

        exit.addActionListener(new ExitAction());
        about.addActionListener(new AboutAction());
        light.addActionListener(new LightAction());
        dark.addActionListener(new DarkAction());
    }

    /**
     * Har en tabell som inparameter vilket skapas utanför GUI-klassen för att sedan lägga till en scroll för denna
     * och välja vilka koolumner som skall visas för att tillslut visa deni rätt panel
     *
     * @param table
     */
    public void buildTable(JTable table) {

        scrollPane = new JScrollPane(table);

        table.removeColumn(table.getColumnModel().getColumn(3));

        table.setAutoCreateRowSorter(true);

        buildFrame();

        searchField.getDocument().addDocumentListener(new SearchListener(table, searchField));
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener(this));

        this.table = table;

        time.setText(sdf.format(new Date()));
    }


    /**
     * Bygger om tabellen för att updatera datat i denna
     *
     * @param table
     */
    public void rebuildTable(JTable table) {

        scrollPane = new JScrollPane(table);
        table.removeColumn(table.getColumnModel().getColumn(3));
        table.setAutoCreateRowSorter(true);

        imageURL = "";
        frame.remove(rightPanel);
        rightPanel = buildRightPanel();
        frame.remove(leftPanel);
        leftPanel = buildLeftPanel();
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.pack();

        searchField.getDocument().addDocumentListener(new SearchListener(table, searchField));
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener(this));


        time.setText(sdf.format(new Date()));
    }

    /**
     * Updaterar infot som visas i den högra panelen, bör kallas på då användaren trycker på en ny rad i tabellen
     *
     * @param row
     */
    public void updateInfo(int row) {

        System.out.println(table.getModel().getValueAt(table.convertRowIndexToModel(row), 3));
        imageURL = table.getModel().getValueAt(table.convertRowIndexToModel(row), 3).toString();
        frame.remove(rightPanel);
        rightPanel = buildRightPanel();
        frame.add(rightPanel, BorderLayout.EAST);
        frame.pack();
    }

}