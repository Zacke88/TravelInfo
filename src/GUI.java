import javax.imageio.ImageIO;
import javax.management.Query;
import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by id12jzn on 2016-01-18.
 * <p>
 * GUI-klass som bygger upp hela vyn for programmet. Den bygger en frame
 * vilket sedan bestar av en BorderlLayout.
 * Denna frame bestar av olika paneler som placeras ut enligt layouten
 */
public class GUI {

    private JFrame frame = new JFrame();
    private JButton updateButton;
    private JPanel upperPanel;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel lowerPanel;
    public JLabel errorMessage = new JLabel();
    private JLabel time = new JLabel();
    private JMenuBar menu = new JMenuBar();
    private JTextField searchField;
    private JComboBox comboBox;
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private String imageURL = "";
    private String description = "";
    private String cityName = "";
    private String themeColor = "neutral";
    private String searchFieldText = "Search";
    private int comboIndex = 0;
    private int timer = 1 * 60 * 1000;
    private TableRowSorter<TableModel> rowSorter;
    public Thread updater;
    private FileReader reader;
    private FileWriter writer;
    private Properties prop = new Properties();

    /**
     * Konstructor som laser in property filen nar GUI't skapas
     * Gar nagot fel fangar den upp exceptions och skriver ut felmeddelanden
     * at anvandaren
     */
    public GUI() {
        try {
            reader = new FileReader("prop");
            prop = new Properties();
            prop.load(reader);
            themeColor = prop.getProperty("Theme");
            comboIndex = Integer.parseInt(prop.getProperty("Combo"));
        } catch (FileNotFoundException e) {
            errorMessage.setText("<html><font color='red'>Properties file not" +
                    " found</font></html>");
        } catch (IOException e) {
            errorMessage.setText("<html><font color='red'>Could not read " +
                    "properties file</font></html>");
        } catch (NumberFormatException e) {
            errorMessage.setText("<html><font color='red'>Could not load all " +
                    "property-values</font></html>");
        }
    }

    /**
     * Bygger den ovre panalen till framen vilket bestar av olika val och
     * filtreringar anvandaren kan gora
     *
     * @return en JPanel av panale som byggts
     */
    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setBorder(BorderFactory.createTitledBorder("Offers"));
        upperPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Skapar Combo-boxen samt val for denna
        JLabel labelCombo = new JLabel("Set update interval: ");
        String[] options = {"30min", "60min", "90min"};
        comboBox = new JComboBox(options);
        comboBox.addActionListener(new ComboBoxListener(this));
        comboBox.setSelectedIndex(comboIndex);
        searchField = new JTextField(searchFieldText);
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
     * Bygger den hogra panelen till framen vilket bestar av info for den rad
     * i tabellen som ar markerad
     *
     * @return en JPanel av panale som byggts
     */
    private JPanel buildRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Info"));
        JLabel label = new JLabel();
        JLabel image = new JLabel();
        JLabel infoText = new JLabel();

        if (imageURL.equals("")) {
            label.setText("Select item in list to show more info");
        } else {
            try {
                image.setIcon(new ImageIcon(ImageIO.read(new URL(imageURL))));
            } catch (IOException e) {
                label.setText("Could not load hotel image");
            }
        }

        if (imageURL.contains("Default")) {
            label.setText("No hotel image available");
        }

        if (!cityName.equals("") || !description.equals("")) {
            infoText.setText("City: " + cityName + "   Description: " +
                    description);
            cityName = "";
            description = "";
        } else {
            infoText.setText("");
        }

        rightPanel.add(label, BorderLayout.NORTH);
        rightPanel.add(image, BorderLayout.CENTER);
        rightPanel.add(infoText, BorderLayout.SOUTH);

        return rightPanel;
    }

    /**
     * Bygger den vanstra panelen till framen vilket bestar av tabellen som
     * visar en lista over alla Offers
     * Denna tabell gar att sortera
     *
     * @return en JPanel av panale som byggts
     */
    private JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder("Offers"));
        leftPanel.setLayout(new BorderLayout());

        leftPanel.add(scrollPane);

        return leftPanel;
    }

    /**
     * Bygger den nedre panelen till framen vilket
     *
     * @return en JPanel av panale som byggts
     */
    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel updated = new JLabel("Last updated: ");
        time.setText(sdf.format(new Date()));
        lowerPanel.add(updated);
        lowerPanel.add(time);
        lowerPanel.add(errorMessage);

        return lowerPanel;
    }

    /**
     * Bygger upp sjalva framen och lagger till samtliga paneler som tillhor den
     */
    public void buildFrame() {
        // Bygger panelerna och lagger till de i framen
        upperPanel = buildUpperPanel();
        rightPanel = buildRightPanel();
        leftPanel = buildLeftPanel();
        lowerPanel = buildLowerPanel();
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        // Bygger menyn
        buildMenu();
        frame.setJMenuBar(menu);

        // Satter frame installningar
        frame.setTitle("Travel Info");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        // Laddar installningar fran properties-filen
        loadProperties();

        // Startar traden som updaterar tabellen
        startThread();

    }

    /**
     * Bygger upp menyraden som finns upptill pa framen dar anvandaren kan
     * gora de val som laggs till.
     * Innehar ocksa lyssnare for samtliga menyval
     */
    public void buildMenu() {

        JMenu file = new JMenu("File");
        menu.add(file);
        JMenuItem exit = new JMenuItem("Exit");
        file.add(exit);
        JMenu theme = new JMenu("Theme");
        menu.add(theme);
        JMenuItem neutral = new JMenuItem("Neutral");
        theme.add(neutral);
        JMenuItem funky = new JMenuItem("Funky");
        theme.add(funky);
        JMenuItem light = new JMenuItem("Light");
        theme.add(light);
        JMenuItem dark = new JMenuItem("Dark");
        theme.add(dark);
        JMenu settings = new JMenu("Settings");
        menu.add(settings);
        JMenuItem save = new JMenuItem("Save");
        settings.add(save);
        JMenuItem reset = new JMenuItem("Reset");
        settings.add(reset);
        JMenu help = new JMenu("Help");
        menu.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);

        /**
         * Lyssnare som avslutar programmet
         */
        class ExitAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }

        /**
         * Lyssnare for att spara installningar anvandaren gjort i en
         * properties-fil
         */
        class SaveAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                prop.setProperty("Theme", themeColor);
                prop.setProperty("Combo", Integer.toString(comboIndex));

                try {
                    writer = new FileWriter("prop");
                    prop.store(writer, "Authur: id12jzn");
                    writer.close();
                } catch (IOException e1) {
                    errorMessage.setText("<html><font color='red'>Could not " +
                            "write to properties file</font></html>");
                }

                JOptionPane.showMessageDialog(frame, "Current settings has " +
                        "been saved");
            }
        }

        /**
         * Lyssnare for att aterstalla standardinstallningar och andra till
         * dessa varden i properties-filen
         */
        class ResetAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                themeColor = "neutral";
                comboIndex = 0;
                prop.setProperty("Theme", themeColor);
                prop.setProperty("Combo", Integer.toString(comboIndex));

                try {
                    writer = new FileWriter("prop");
                    prop.store(writer, "Authur: id12jzn");
                    writer.close();
                } catch (IOException e1) {
                    errorMessage.setText("<html><font color='red'>Could not " +
                            "write to properties file</font></html>");
                }

                loadProperties();
                JOptionPane.showMessageDialog(frame, "Settings has been reset");
            }
        }

        /**
         * Lyssnare som oppnar en popup-ruta dar anvandaren kan lasa mer om
         * vem som skapar programmet.
         */
        class AboutAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Made by Joakim " +
                        "Zakrisson ( id12jzn)");
            }
        }

        /**
         * Lyssnare for att andra fargtema
         */
        class NeutralAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTheme("neutral");
            }
        }

        /**
         * Lyssnare for att andra fargtema
         */
        class FunkyAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTheme("cyan");
            }
        }

        /**
         * Lyssnare for att andra fargtema
         */
        class LightAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTheme("white");
            }
        }

        /**
         * Lyssnare for att andra fargtema
         */
        class DarkAction implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTheme("gray");
            }
        }

        exit.addActionListener(new ExitAction());
        about.addActionListener(new AboutAction());
        neutral.addActionListener(new NeutralAction());
        funky.addActionListener(new FunkyAction());
        light.addActionListener(new LightAction());
        dark.addActionListener(new DarkAction());
        save.addActionListener(new SaveAction());
        reset.addActionListener(new ResetAction());
    }

    /**
     * Har en tabell som inparameter vilket skapas utanfor GUI-klassen for
     * att sedan lagga till en scroll for denna och valja vilka koolumner som
     * skall visas for att tillslut visa den i ratt panel
     *
     * @param table
     */
    public void buildTable(JTable table) {
        scrollPane = new JScrollPane(table);

        table.removeColumn(table.getColumnModel().getColumn(5));
        table.removeColumn(table.getColumnModel().getColumn(4));
        table.removeColumn(table.getColumnModel().getColumn(3));
        table.setAutoCreateRowSorter(true);

        buildFrame();

        searchField.getDocument().addDocumentListener(new SearchListener
                (table, searchField));
        table.getSelectionModel().addListSelectionListener(new
                TableSelectionListener(this));
        this.table = table;
        time.setText(sdf.format(new Date()));
    }


    /**
     * Bygger om tabellen for att updatera datat i denna.
     * Blir kallad pa traden som updaterar gui't med bestamda tidsintervall
     * eller da man anvandaren manuellt updaterar
     * via knappen i gui't.
     * Metoden ar synchronzed for att tradarna ej skall kunna krocka har
     *
     * @param table
     */
    synchronized
    public void rebuildTable(JTable table) {
        scrollPane = new JScrollPane(table);
        table.removeColumn(table.getColumnModel().getColumn(5));
        table.removeColumn(table.getColumnModel().getColumn(4));
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

        searchField.getDocument().addDocumentListener(new SearchListener
                (table, searchField));
        table.getSelectionModel().addListSelectionListener(new
                TableSelectionListener(this));
        time.setText(sdf.format(new Date()));
    }

    /**
     * Updaterar infot som visas i den hogra panelen, bor kallas pa da
     * anvandaren trycker pa en ny rad i tabellen
     * Har ett radnummer som inparameter sa den vet vilket erbjudande den
     * skall visa information for
     *
     * @param row
     */
    public void updateInfo(int row) {
        imageURL = table.getModel().getValueAt(table.convertRowIndexToModel
                (row), 3).toString();
        cityName = table.getModel().getValueAt(table.convertRowIndexToModel
                (row), 4).toString();
        description = table.getModel().getValueAt(table
                .convertRowIndexToModel(row), 5).toString();
        frame.remove(rightPanel);
        rightPanel = buildRightPanel();
        frame.add(rightPanel, BorderLayout.EAST);
        frame.pack();
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer, int comboIndex) {
        this.timer = timer;
        this.comboIndex = comboIndex;
    }

    /**
     * Startar traden som bestar av klassen TableUpdater vilket ser till att
     * updatera tabellen med angivet tidsintervall
     */
    public void startThread() {
        updater = new Thread(new TableUpdater(this));
        updater.start();
    }

    /**
     * andrar det nuvarande farg-temat for framen
     *
     * @param color
     */
    public void changeTheme(String color) {
        if (color.equals("neutral")) {
            themeColor = "neutral";
            upperPanel.setBackground(UIManager.getColor("Panel.background"));
            lowerPanel.setBackground(UIManager.getColor("Panel.background"));
        }
        if (color.equals("cyan")) {
            themeColor = "cyan";
            upperPanel.setBackground(Color.cyan);
            lowerPanel.setBackground(Color.cyan);
        }
        if (color.equals("white")) {
            themeColor = "white";
            upperPanel.setBackground(Color.white);
            lowerPanel.setBackground(Color.white);
        }
        if (color.equals("gray")) {
            themeColor = "gray";
            upperPanel.setBackground(Color.lightGray);
            lowerPanel.setBackground(Color.lightGray);
        }
    }

    /**
     * Laddar installningar fran properties-filen
     */
    public void loadProperties() {
        changeTheme(themeColor);
        comboBox.setSelectedIndex(comboIndex);
    }
}
