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
 * Created by Zacke on 2016-01-18.
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



    //Should only be called on EDT
    public GUI() {

        //buildFrame();

    }

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

    private JPanel buildLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createTitledBorder("Offers"));
        leftPanel.setLayout(new BorderLayout());
        //leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        leftPanel.add(scrollPane);

        return leftPanel;
    }

    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();

        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel updated = new JLabel("Last updated: ");

        time.setText(sdf.format(new Date()));

        lowerPanel.add(updated);
        lowerPanel.add(time);

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

        table.removeColumn(table.getColumnModel().getColumn(3));

        table.setAutoCreateRowSorter(true);

        buildFrame();

        searchField.getDocument().addDocumentListener(new SearchListener(table, searchField));
        table.getSelectionModel().addListSelectionListener(new TableSelectionListener(this));

        this.table = table;

        time.setText(sdf.format(new Date()));
    }

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

    public void updateInfo(int row) {

        System.out.println(table.getModel().getValueAt(table.convertRowIndexToModel(row), 3));
        imageURL = table.getModel().getValueAt(table.convertRowIndexToModel(row), 3).toString();
        frame.remove(rightPanel);
        rightPanel = buildRightPanel();
        frame.add(rightPanel, BorderLayout.EAST);
        frame.pack();
    }

}
