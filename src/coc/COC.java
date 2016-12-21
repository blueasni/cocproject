package coc;

import com.sun.java.swing.plaf.motif.MotifComboBoxUI;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import components.*;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS;
import static javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.text.Position;
import net.sf. jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
public class COC extends JFrame implements MouseListener, JComboBox.KeySelectionManager, FocusListener, ActionListener {

    private ImageIcon icon;
    JComboBox temp;
    private Image image;
    private int ACCSIZE = 155, width, height;
    File selection;
    private ImageIcon imageIcon = null;
    private FileInputStream fis;
    private PreparedStatement ps;
    private byte[] toDBImage;
    
     String printCenter, password = "blue", username = "root", customQuery, tempQuery, printDate, name, url = "jdbc:mysql://localhost:3306/trial";
    private JComboBox trainingCenterPrint = new JComboBox();
    TableToolTip tt;
    private boolean isImageChanged = false;
    private JTextArea sqlText;
    private Vector vcenter, vclassification, vid, vlevel, vprovider, vtitle;
    java.util.ArrayList<java.util.List<String>> searchTrainee = new java.util.ArrayList<java.util.List<String>>();
    KeyStroke keyUp, keyDown;
    InputMap im;
    ActionMap am;
    String singleText = null;
    boolean singleBoolean = false, flag = false;
    private JPopupMenu addPop = new JPopupMenu();
    private JMenuItem addSingle = new JMenuItem("Manual Record"), addMultiple = new JMenuItem("Auto Record");
    private boolean onlyHavePhoto=true;

    public COC(String s) {
    }

    public COC() {
        //UIManager.put("ToolTip.foreground", new ColorUIResource(Color.red));
        //UIManager.put("ToolTip.background", new ColorUIResource(Color.yellow));
        //UIManager.put("ToolTip.font", new FontUIResource(new Font("Verdana", Font.PLAIN, 18)));
        //UIManager.put("ToolTip.border", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Trainee's Detail"), BorderFactory.createLineBorder(Color.blue))));
        trainingCenterPrint.setFont(new java.awt.Font("Tahoma", 1, 12));
        //mName.setToolTipText("Please add your Trainee's Middle Name");
        //lName.setToolTipText("Please add your Trainee's Last Name");
        Toolkit tk = Toolkit.getDefaultToolkit();
        setExtendedState(JFrame.MAXIMIZED_BOTH);        //setSize((int)tk.getScreenSize().getWidth(),(int)tk.getScreenSize().getHeight());
        setPreferredSize(new Dimension((int) tk.getScreenSize().getWidth(), (int) tk.getScreenSize().getHeight()));
        loadDB();
        initComponents();

        //actionPanel.add(dcb);
        setForEdit(false);
        fillTable();
        fName.addFocusListener(this);           lName.addFocusListener(this);        
        mName.addFocusListener(this);           sex.addFocusListener(this);
        D_O_G.addFocusListener(this);           occuptionID.addFocusListener(this);
        fieldOfStudy.addFocusListener(this);    level.addFocusListener(this);
        classification.addFocusListener(this);  browseImage.addActionListener(this);
        jTable1.addMouseListener(this);         report.addActionListener(this);
        printAll.addActionListener(this);       insertOccupation.addActionListener(this);
        insertTrainee.addActionListener(this);  editRecord.addActionListener(this);
        saveRecord.addActionListener(this);     insertTrainingProvider.addActionListener(this);
        insertClassification.addActionListener(this);insertAssessmentCenter.addActionListener(this);
        xSearch.addActionListener(this);        printOne.addActionListener(this);
        dateOfCompetation.addActionListener(this);test.addActionListener(this);
        jTable1.setFocusable(true);
        im = jTable1.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        am = jTable1.getActionMap();
        keyDown = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        keyUp = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        im.put(keyDown, "Action.down");
        im.put(keyUp, "Action.up");
        am.put("Action.down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {keyBind(jTable1.getSelectedRow(), true);}
        });
        am.put("Action.up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {keyBind(jTable1.getSelectedRow(), false);}
        });
        JComponent cc = new JComboBox();
        occuptionID.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    connectToDB(url, username, password);
                    st = con.createStatement();
                    res = st.executeQuery("select * from occupation");
                    String tempID = occuptionID.getSelectedItem().toString();
                    while (res.next()) {
                        String tid = res.getString("occupationid");
                        String tlevel = res.getString("level");
                        String ttitle = res.getString("fieldOfStudy");
                        if (tempID.equals(tid)) {
                            level.setSelectedItem(tlevel);
                            fieldOfStudy.setSelectedItem(ttitle);
                        }
                    }

                    disconnect();
                } catch (SQLException ex) {
                    Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });        
        assessmentCenter.addFocusListener(this);        trainingProvider.addFocusListener(this);
        dateOfCompetation.addFocusListener(this);       dateOfIssue.addFocusListener(this);
        insert.addFocusListener(this);                  traineeID.addFocusListener(this);
        printOne.addFocusListener(this);                printAll.addFocusListener(this);
        report.addFocusListener(this);                  xSearch.addFocusListener(this);
        test.addFocusListener(this);                    jButton15.addFocusListener(this);
        contactNo.addFocusListener(this);               delete.addFocusListener(this);
        insert.addActionListener(this);                 delete.addActionListener(this);
        edit.addActionListener(this);                   save.addActionListener(this);
        search.addActionListener(this);                 load.addActionListener(this);
        addSingle.addActionListener(this);              addMultiple.addActionListener(this);
        update.addActionListener(this);                 industry.addFocusListener(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        searchFrame = new javax.swing.JFrame();
        jLabel3 = new javax.swing.JLabel();
        searchCombo = new javax.swing.JComboBox();
        searchPhoto = new javax.swing.JLabel();
        searchOk = new javax.swing.JButton();
        searchCancel = new javax.swing.JButton();
        searchBox = new javax.swing.JTextField();
        searchPanel = new javax.swing.JPanel();
        searchName = new javax.swing.JTextField();
        searchGFather = new javax.swing.JTextField();
        SearchFather = new javax.swing.JTextField();
        searchTID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        actionPanel = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        insert = new javax.swing.JButton();
        update = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        load = new javax.swing.JButton();
        search = new javax.swing.JButton();
        xSearch = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        printOne = new javax.swing.JButton();
        printAll = new javax.swing.JButton();
        report = new javax.swing.JButton();
        test = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        detailPanel = new javax.swing.JPanel();
        traineeIDLabel = new javax.swing.JLabel();
        traineeID = new javax.swing.JTextField();
        fNameLabel = new javax.swing.JLabel();
        fName = new javax.swing.JTextField();
        /*AutoSuggestor autoSuggestor = new AutoSuggestor(fName, this, null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) {
            @Override
            boolean wordTyped(String typedWord) {
                System.out.println("Getting Auto suggestion ");
                //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
                ArrayList<String> words = new ArrayList<>();
                words.add("hello");
                words.add("heritage");
                words.add("happiness");
                words.add("goodbye");
                words.add("cruel");
                words.add("car");
                words.add("war");
                words.add("will");
                words.add("world");
                words.add("wall");
                setDictionary(words);
                return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
            }
        };*/
        mNameLabel = new javax.swing.JLabel();
        mName = new javax.swing.JTextField();
        lNameLabel = new javax.swing.JLabel();
        lName = new javax.swing.JTextField();
        ageLabel = new javax.swing.JLabel();
        sexPanel = new javax.swing.JPanel();
        D_O_G = new javax.swing.JTextField();
        sexLabel = new javax.swing.JLabel();
        sex = new javax.swing.JComboBox();
        occupationIDLabel = new javax.swing.JLabel();
        occuptionID = null;
        fieldOfStudyLabel = new javax.swing.JLabel();
        fieldOfStudy = new javax.swing.JComboBox();
        levelLabel = new javax.swing.JLabel();
        level = new javax.swing.JComboBox();
        classificationLabel = new javax.swing.JLabel();
        classification = new javax.swing.JComboBox();
        industryLabel = new javax.swing.JLabel();
        industry = new javax.swing.JTextField();
        assessmentCenterLabel = new javax.swing.JLabel();
        assessmentCenter = new javax.swing.JComboBox();
        trainingProviderLabel = new javax.swing.JLabel();
        trainingProvider = new javax.swing.JComboBox();
        DOBLabel = new javax.swing.JLabel();
        DOB = new javax.swing.JTextField();
        dateOfCompetationLabel = new javax.swing.JLabel();
        dateOfCompetation = new javax.swing.JComboBox();
        dateOfCompetation = new DateComboBox();
        dateOfIssueLabel = new javax.swing.JLabel();
        dateOfIssue = new javax.swing.JTextField();
        contactNoLabel = new javax.swing.JLabel();
        contactNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        imageNameLabel = new javax.swing.JLabel();
        browseImage = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jToolBar2 = new javax.swing.JToolBar();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        editRecord = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        saveRecord = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        deleteRecord = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        insertMenu = new javax.swing.JMenu();
        insertTrainee = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        insertTrainingProvider = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        insertAssessmentCenter = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        insertOccupation = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        insertClassification = new javax.swing.JMenuItem();
        settingMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();

        jDialog1.getContentPane().setLayout(new java.awt.GridLayout(0, 1, 0, 1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jDialog1.getContentPane().add(jScrollPane2);

        jButton2.setText("jButton2");

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(197, 197, 197))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(38, 38, 38))
        );

        jDialog1.getContentPane().add(jPanel1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("search by :");

        searchCombo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Trainee ID", "First Name", "Middle Name", "Last Name" }));

        searchPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder("Photo"));

        searchOk.setText("OK");
        searchOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchOkActionPerformed(evt);
            }
        });

        searchCancel.setText("Cancel");

        searchBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchGFather.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        SearchFather.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchTID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchTID)
            .addComponent(searchName)
            .addComponent(SearchFather)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addComponent(searchGFather, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchTID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SearchFather)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchGFather)
                .addGap(18, 18, 18))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Trainee ID :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Name :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Father :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("G. Father :");

        javax.swing.GroupLayout searchFrameLayout = new javax.swing.GroupLayout(searchFrame.getContentPane());
        searchFrame.getContentPane().setLayout(searchFrameLayout);
        searchFrameLayout.setHorizontalGroup(
            searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchFrameLayout.createSequentialGroup()
                        .addGroup(searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(searchBox)
                            .addComponent(searchPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(searchPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchFrameLayout.createSequentialGroup()
                        .addComponent(searchOk)
                        .addGap(18, 18, 18)
                        .addComponent(searchCancel))))
        );
        searchFrameLayout.setVerticalGroup(
            searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchFrameLayout.createSequentialGroup()
                .addGroup(searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(searchCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(searchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(searchFrameLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel9))
                    .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(searchFrameLayout.createSequentialGroup()
                .addComponent(searchPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(searchFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchOk)
                    .addComponent(searchCancel)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Oromiya center of competence");

        actionPanel.setLayout(new java.awt.GridLayout(0, 1));

        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/save.gif"))); // NOI18N
        save.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        save.setLabel("Save");
        actionPanel.add(save);

        insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/new.gif"))); // NOI18N
        insert.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        insert.setLabel("New");
        actionPanel.add(insert);

        update.setText("Update");
        update.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        actionPanel.add(update);

        edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/edit.png"))); // NOI18N
        edit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        edit.setLabel("Edit");
        actionPanel.add(edit);

        load.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/refresh.gif"))); // NOI18N
        load.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        load.setLabel("Reload");
        actionPanel.add(load);

        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/search.gif"))); // NOI18N
        search.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        search.setLabel("Search");
        actionPanel.add(search);

        xSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/search.gif"))); // NOI18N
        xSearch.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        xSearch.setLabel("Adv. Search");
        actionPanel.add(xSearch);

        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/delete.gif"))); // NOI18N
        delete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        delete.setLabel("Delete");
        actionPanel.add(delete);

        printOne.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/print.gif"))); // NOI18N
        printOne.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        printOne.setLabel("Print This");
        actionPanel.add(printOne);

        printAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/components/images/print.gif"))); // NOI18N
        printAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        printAll.setLabel("Print All");
        actionPanel.add(printAll);

        report.setText("Report");
        report.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        actionPanel.add(report);

        test.setText("Custom Report");
        test.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        test.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testActionPerformed(evt);
            }
        });
        actionPanel.add(test);

        jButton15.setText("jButton7");
        jButton15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        actionPanel.add(jButton15);

        getContentPane().add(actionPanel, java.awt.BorderLayout.WEST);

        detailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trainee's Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14), new java.awt.Color(0, 51, 255))); // NOI18N
        detailPanel.setLayout(new java.awt.GridLayout(0, 2));

        traineeIDLabel.setBackground(new java.awt.Color(240, 240, 255));
        traineeIDLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        traineeIDLabel.setForeground(new java.awt.Color(0, 0, 255));
        traineeIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        traineeIDLabel.setText("Trainee ID:   ");
        detailPanel.add(traineeIDLabel);

        traineeID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(traineeID);

        fNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fNameLabel.setForeground(new java.awt.Color(0, 0, 255));
        fNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fNameLabel.setText("First Name:   ");
        detailPanel.add(fNameLabel);
        fNameLabel.getAccessibleContext().setAccessibleName("fName");

        fName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fName.setToolTipText("1234567890");
        detailPanel.add(fName);

        mNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        mNameLabel.setForeground(new java.awt.Color(0, 0, 255));
        mNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mNameLabel.setText("Middle Name:   ");
        mNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(mNameLabel);

        mName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(mName);

        lNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lNameLabel.setForeground(new java.awt.Color(0, 0, 255));
        lNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lNameLabel.setText("Last Name:   ");
        detailPanel.add(lNameLabel);

        lName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(lName);

        ageLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        ageLabel.setForeground(new java.awt.Color(0, 0, 255));
        ageLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ageLabel.setText("Date Of Graduation:   ");
        detailPanel.add(ageLabel);

        sexPanel.setLayout(new java.awt.GridLayout(1, 0));

        D_O_G.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sexPanel.add(D_O_G);

        sexLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sexLabel.setForeground(new java.awt.Color(0, 0, 255));
        sexLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sexLabel.setText("Sex:   ");
        sexPanel.add(sexLabel);

        sex.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));
        AutoCompleteDecorator.decorate(sex);
        sexPanel.add(sex);

        detailPanel.add(sexPanel);

        occupationIDLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        occupationIDLabel.setForeground(new java.awt.Color(0, 0, 255));
        occupationIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        occupationIDLabel.setText("Occupation ID:  ");
        detailPanel.add(occupationIDLabel);

        loadDB();
        occuptionID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(occuptionID);

        fieldOfStudyLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fieldOfStudyLabel.setForeground(new java.awt.Color(0, 0, 255));
        fieldOfStudyLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fieldOfStudyLabel.setText("Field Of Study:   ");
        fieldOfStudyLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(fieldOfStudyLabel);

        fieldOfStudy.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AutoCompleteDecorator.decorate(sex);
        detailPanel.add(fieldOfStudy);

        levelLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        levelLabel.setForeground(new java.awt.Color(0, 0, 255));
        levelLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        levelLabel.setText("Level:   ");
        levelLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(levelLabel);

        level.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AutoCompleteDecorator.decorate(sex);
        detailPanel.add(level);

        classificationLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        classificationLabel.setForeground(new java.awt.Color(0, 0, 255));
        classificationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        classificationLabel.setText("Classification:   ");
        classificationLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(classificationLabel);

        classification.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AutoCompleteDecorator.decorate(sex);
        detailPanel.add(classification);

        industryLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        industryLabel.setForeground(new java.awt.Color(0, 0, 255));
        industryLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        industryLabel.setText("(if selected) Industry Name:    ");
        industryLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(industryLabel);

        industry.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(industry);

        assessmentCenterLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        assessmentCenterLabel.setForeground(new java.awt.Color(0, 0, 255));
        assessmentCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        assessmentCenterLabel.setText("Assessment Center:   ");
        assessmentCenterLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(assessmentCenterLabel);

        assessmentCenter.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(assessmentCenter);

        trainingProviderLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        trainingProviderLabel.setForeground(new java.awt.Color(0, 0, 255));
        trainingProviderLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        trainingProviderLabel.setText("Training Provider:   ");
        trainingProviderLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(trainingProviderLabel);

        trainingProvider.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(trainingProvider);

        DOBLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DOBLabel.setForeground(new java.awt.Color(0, 0, 255));
        DOBLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        DOBLabel.setText("Date Of Birth:   ");
        DOBLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(DOBLabel);

        DOB.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(DOB);

        dateOfCompetationLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dateOfCompetationLabel.setForeground(new java.awt.Color(0, 0, 255));
        dateOfCompetationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateOfCompetationLabel.setText("Date Of Competation:   ");
        dateOfCompetationLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(dateOfCompetationLabel);

        dateOfCompetation.setEditable(true);
        detailPanel.add(dateOfCompetation);

        dateOfIssueLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dateOfIssueLabel.setForeground(new java.awt.Color(0, 0, 255));
        dateOfIssueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateOfIssueLabel.setText("Date Of Issue:   ");
        dateOfIssueLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        detailPanel.add(dateOfIssueLabel);

        dateOfIssue.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(dateOfIssue);

        contactNoLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        contactNoLabel.setForeground(new java.awt.Color(0, 0, 255));
        contactNoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        contactNoLabel.setText("Contact No:   ");
        detailPanel.add(contactNoLabel);

        contactNo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        detailPanel.add(contactNo);

        getContentPane().add(detailPanel, java.awt.BorderLayout.CENTER);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.orange, java.awt.Color.pink, java.awt.Color.green, java.awt.Color.red));
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 110));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setPreferredSize(new java.awt.Dimension(300, 100));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("pgkjgjhjfh"));

        browseImage.setText("Browse photo");

        jLabel1.setBackground(new java.awt.Color(0, 0, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(browseImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(browseImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imageNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.EAST);

        jToolBar1.setRollover(true);

        jButton4.setText("jButton4");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton4);

        jButton8.setText("jButton8");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton8);

        jTextField3.setText("jTextField3");
        jToolBar1.add(jTextField3);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.SOUTH);

        jToolBar2.setBackground(new java.awt.Color(0, 0, 255));
        jToolBar2.setRollover(true);

        jScrollPane3.setViewportView(jTextPane1);

        jToolBar2.add(jScrollPane3);

        jButton3.setText("jButton3");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton3);

        getContentPane().add(jToolBar2, java.awt.BorderLayout.NORTH);

        jMenuBar1.setBackground(java.awt.SystemColor.textHighlight);

        fileMenu.setText("File");
        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");

        editRecord.setText("Edit Record");
        editRecord.setActionCommand("editRecord");
        editMenu.add(editRecord);
        editMenu.add(jSeparator1);

        saveRecord.setText("Save Record");
        saveRecord.setActionCommand("saveRecord");
        editMenu.add(saveRecord);
        editMenu.add(jSeparator2);

        deleteRecord.setText("Delete Record");
        editMenu.add(deleteRecord);
        editMenu.add(jSeparator3);

        jMenuBar1.add(editMenu);

        insertMenu.setText("Insert");

        insertTrainee.setText("Trainee");
        insertMenu.add(insertTrainee);
        insertMenu.add(jSeparator5);

        insertTrainingProvider.setText("Training provider");
        insertMenu.add(insertTrainingProvider);
        insertMenu.add(jSeparator4);

        insertAssessmentCenter.setText("Assessment Center");
        insertMenu.add(insertAssessmentCenter);
        insertMenu.add(jSeparator6);

        insertOccupation.setText("Occupation");
        insertMenu.add(insertOccupation);
        insertMenu.add(jSeparator7);

        insertClassification.setText("Classification");
        insertMenu.add(insertClassification);

        jMenuBar1.add(insertMenu);

        settingMenu.setText("Setting");
        jMenuBar1.add(settingMenu);

        helpMenu.setText("Help");
        jMenuBar1.add(helpMenu);

        jMenu1.setText("jMenu1");
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void testActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_testActionPerformed

    private void searchOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchOkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchOkActionPerformed
public void loadDB() {
        try {
            this.connectToDB(url, username, password);
            st = con.createStatement();
            
            res = st.executeQuery("select * from trainee");
            while (res.next()) 
            {
                searchTrainee.add(java.util.Arrays.asList(res.getString("traineeID"),
                        res.getString("fName") ,res.getString("mName"),res.getString("lName")));
            }
            //System.out.println(searchTrainee.get(0).get(0));            
            res = st.executeQuery("select * from occupation");
            vid = new Vector();
            vlevel = new Vector();
            vtitle = new Vector();
            vclassification = new Vector();
            vcenter = new Vector();
            vprovider = new Vector();

            while (res.next()) {
                String id = res.getString("occupationid"),level = res.getString("level"),title = res.getString("fieldOfStudy");
                vid.addElement(id);
                if (!(vlevel.contains(level))) {
                    vlevel.addElement(level);
                }
                vtitle.addElement(title);
            }
            occuptionID = new JComboBox(vid);
            //fieldOfStudy=new JComboBox(vtitle);
            Object str[] = vtitle.toArray();
            WiderDropDownCombo wider = new WiderDropDownCombo(str);
            wider.setWide(true);
            level = new JComboBox(vlevel);
            fieldOfStudy = wider;
            //ArrayList<String> words = new ArrayList<>();
            //words.add("hello");words.add("heritage");words.add("happiness");words.add("goodbye");words.add("cruel");words.add("car");words.add("war");words.add("will");words.add("world");words.add("wall");           
            //fieldOfStudy.setKeySelectionManager( new COC(fieldOfStudy) );
            AutoCompleteDecorator.decorate(fieldOfStudy);
            AutoCompleteDecorator.decorate(occuptionID);
            AutoCompleteDecorator.decorate(level);

            res = st.executeQuery("select * from training_provider");
            while (res.next()) {
                String provider = res.getString("provider_name");
                vprovider.addElement(provider);
            }
            trainingProvider = new JComboBox(vprovider);
            
            AutoCompleteDecorator.decorate(trainingProvider);
            //trainingProvider.setKeySelectionManager( new COC(trainingProvider) );
            res = st.executeQuery("select * from assessement_center");
            while (res.next()) {
                String center = res.getString("assessmentCenter");
                vcenter.addElement(center);
            }
            assessmentCenter = new JComboBox(vcenter);
            trainingCenterPrint = new JComboBox(vcenter);
            AutoCompleteDecorator.decorate(assessmentCenter);
            //assessmentCenter.setKeySelectionManager( new COC(assessmentCenter) );
            res = st.executeQuery("select * from classification");
            while (res.next()) {
                String center = res.getString("classification");
                vclassification.addElement(center);
            }
            classification = new JComboBox(vclassification);
            AutoCompleteDecorator.decorate(classification);
            this.disconnect();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) throws ClassNotFoundException {
        try {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            /*try {
             for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
             if ("Nimbus".equals(info.getName())) {
             javax.swing.UIManager.setLookAndFeel(info.getClassName());
             break;
             }
             }
             } catch (ClassNotFoundException ex) {
             java.util.logging.Logger.getLogger(COC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
             } catch (InstantiationException ex) {
             java.util.logging.Logger.getLogger(COC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
             } catch (IllegalAccessException ex) {
             java.util.logging.Logger.getLogger(COC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
             } catch (javax.swing.UnsupportedLookAndFeelException ex) {
             java.util.logging.Logger.getLogger(COC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
             }*/
            //</editor-fold>

            /* Create and display the form */
            //loadDB();
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (InstantiationException ex) {
            Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                COC log = new COC("");
                log.login(log);
                new COC().setVisible(true);
            }
        });
    }
    Statement st = null;
    Connection con = null;
    ResultSet res = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DOB;
    private javax.swing.JLabel DOBLabel;
    private javax.swing.JTextField D_O_G;
    private javax.swing.JTextField SearchFather;
    private javax.swing.JPanel actionPanel;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JComboBox assessmentCenter;
    private javax.swing.JLabel assessmentCenterLabel;
    private javax.swing.JButton browseImage;
    private javax.swing.JComboBox classification;
    private javax.swing.JLabel classificationLabel;
    private javax.swing.JTextField contactNo;
    private javax.swing.JLabel contactNoLabel;
    private javax.swing.JComboBox dateOfCompetation;
    private javax.swing.JLabel dateOfCompetationLabel;
    private javax.swing.JTextField dateOfIssue;
    private javax.swing.JLabel dateOfIssueLabel;
    private javax.swing.JButton delete;
    private javax.swing.JMenuItem deleteRecord;
    public javax.swing.JPanel detailPanel;
    private javax.swing.JButton edit;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editRecord;
    public javax.swing.JTextField fName;
    private javax.swing.JLabel fNameLabel;
    private javax.swing.JComboBox fieldOfStudy;
    private javax.swing.JLabel fieldOfStudyLabel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel imageNameLabel;
    private javax.swing.JTextField industry;
    private javax.swing.JLabel industryLabel;
    private javax.swing.JButton insert;
    private javax.swing.JMenuItem insertAssessmentCenter;
    private javax.swing.JMenuItem insertClassification;
    private javax.swing.JMenu insertMenu;
    private javax.swing.JMenuItem insertOccupation;
    private javax.swing.JMenuItem insertTrainee;
    private javax.swing.JMenuItem insertTrainingProvider;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton8;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    public javax.swing.JTextField lName;
    private javax.swing.JLabel lNameLabel;
    private javax.swing.JComboBox level;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JButton load;
    private javax.swing.JTextField mName;
    private javax.swing.JLabel mNameLabel;
    private javax.swing.JLabel occupationIDLabel;
    private javax.swing.JComboBox occuptionID;
    private javax.swing.JButton printAll;
    private javax.swing.JButton printOne;
    private javax.swing.JButton report;
    private javax.swing.JButton save;
    private javax.swing.JMenuItem saveRecord;
    private javax.swing.JButton search;
    public javax.swing.JTextField searchBox;
    private javax.swing.JButton searchCancel;
    private javax.swing.JComboBox searchCombo;
    private javax.swing.JFrame searchFrame;
    private javax.swing.JTextField searchGFather;
    private javax.swing.JTextField searchName;
    private javax.swing.JButton searchOk;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JLabel searchPhoto;
    private javax.swing.JTextField searchTID;
    private javax.swing.JMenu settingMenu;
    private javax.swing.JComboBox sex;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JPanel sexPanel;
    private javax.swing.JButton test;
    private javax.swing.JTextField traineeID;
    private javax.swing.JLabel traineeIDLabel;
    private javax.swing.JComboBox trainingProvider;
    private javax.swing.JLabel trainingProviderLabel;
    private javax.swing.JButton update;
    private javax.swing.JButton xSearch;
    // End of variables declaration//GEN-END:variables
    @Override
    public void mouseClicked(MouseEvent me) {
        JComponent c = (JComponent) me.getSource();
        if (c == jTable1) {
            System.out.println("table clicked");
            if (me.getClickCount() == 1) {
                getHighlight();
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent me) {
    }
    @Override
    public void mouseReleased(MouseEvent me) {
    }
    @Override
    public void mouseEntered(MouseEvent me) {
    }
    @Override
    public void mouseExited(MouseEvent me) {
    }
    @Override
    public int selectionForKey(char aKey, ComboBoxModel aModel) {
        if (useComboBoxModel) {
            listBox.setModel(aModel);
        }
        time = System.currentTimeMillis();
        boolean startingFromSelection = true;
        int startIndex = ((JComboBox) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()).getSelectedIndex();
        if (time - lastTime < timeFactor) {
            typedString += aKey;
            if ((prefix.length() == 1) && (aKey == prefix.charAt(0))) {
                startIndex++;
            } else {
                prefix = typedString;
            }
        } else {
            startIndex++;
            typedString = "" + aKey;
            prefix = typedString;
        }
        lastTime = time;
        if (startIndex < 0 || startIndex >= aModel.getSize()) {
            startingFromSelection = false;
            startIndex = 0;
        }
        int index = listBox.getNextMatch(prefix, startIndex, Position.Bias.Forward);
        if (index < 0 && startingFromSelection) {
            index = listBox.getNextMatch(prefix, 0, Position.Bias.Forward);
        }
        return index;
    }
    public COC(JComboBox comboBox) {
        int uiTimeFactor = UIManager.getInt("ComboBox.timeFactor");
        timeFactor = (uiTimeFactor == 0) ? 1000 : uiTimeFactor;
        Object child = comboBox.getAccessibleContext().getAccessibleChild(0);
        if (child instanceof BasicComboPopup) {
            BasicComboPopup popup = (BasicComboPopup) child;
            listBox = popup.getList();
            useComboBoxModel = false;
        } else {
            listBox = new JList();
            useComboBoxModel = true;
        }
    }
    private boolean useComboBoxModel;
    private JList listBox;
    private long time;
    private int timeFactor;
    private long lastTime;
    private String typedString = "";
    private String prefix = "";
    public void keyBind(int cursor, boolean move) {
        if (move == false) {
            System.out.println("up");
            if (cursor == 0) {
                cursor = jTable1.getRowCount();
            }
            jTable1.changeSelection(--cursor, 0, false, false);
            getHighlight();
        } else {
            System.out.println("down");
            if (cursor == jTable1.getRowCount() - 1) {
                cursor = -1;
            }
            jTable1.changeSelection(++cursor, 0, false, false);
            getHighlight();
        }
        getHighlight();
    }
    @Override
    public void focusGained(FocusEvent e) {
        JComponent c = (JComponent) e.getSource();
        c.setBackground(Color.yellow);
        //fNameLabel.setForeground(Color.yellow);
        if (c == traineeID) {
            traineeIDLabel.setForeground(Color.MAGENTA);
        } else if (c == fName) {
            fNameLabel.setForeground(Color.MAGENTA);
        } else if (c == mName) {
            mNameLabel.setForeground(Color.MAGENTA);
        } else if (c == lName) {
            lNameLabel.setForeground(Color.MAGENTA);
        } else if (c == D_O_G) {
            ageLabel.setForeground(Color.MAGENTA);
        } else if (c == sex) {
            sexLabel.setForeground(Color.MAGENTA);
        } else if (c == occuptionID) {
            occupationIDLabel.setForeground(Color.MAGENTA);
        } else if (c == fieldOfStudy) {
            fieldOfStudyLabel.setForeground(Color.MAGENTA);
        } else if (c == level) {
            levelLabel.setForeground(Color.MAGENTA);
        } else if (c == classification) {
            classificationLabel.setForeground(Color.MAGENTA);
        } else if (c == industry) {
            industryLabel.setForeground(Color.MAGENTA);
        } else if (c == assessmentCenter) {
            assessmentCenterLabel.setForeground(Color.MAGENTA);
        } else if (c == trainingProvider) {
            trainingProviderLabel.setForeground(Color.MAGENTA);
        } else if (c == dateOfCompetation) {
            dateOfCompetationLabel.setForeground(Color.MAGENTA);
        } else if (c == dateOfIssue) {
            dateOfIssueLabel.setForeground(Color.MAGENTA);
        } else if (c == contactNo) {
            contactNoLabel.setForeground(Color.MAGENTA);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void focusLost(FocusEvent e) {
        JComponent c = (JComponent) e.getSource();
        c.setBackground(Color.white);
        if (c == fName) {
            fNameLabel.setForeground(Color.blue);
        } else if (c == traineeID) {
            traineeIDLabel.setForeground(Color.blue);
        } else if (c == mName) {
            mNameLabel.setForeground(Color.blue);
        } else if (c == lName) {
            lNameLabel.setForeground(Color.blue);
        } else if (c == D_O_G) {
            ageLabel.setForeground(Color.blue);
        } else if (c == sex) {
            sexLabel.setForeground(Color.blue);
        } else if (c == occuptionID) {
            occupationIDLabel.setForeground(Color.blue);
        } else if (c == fieldOfStudy) {
            fieldOfStudyLabel.setForeground(Color.blue);
        } else if (c == level) {
            levelLabel.setForeground(Color.blue);
        } else if (c == classification) {
            classificationLabel.setForeground(Color.blue);
        } else if (c == industry) {
            industryLabel.setForeground(Color.blue);
        } else if (c == assessmentCenter) {
            assessmentCenterLabel.setForeground(Color.blue);
        } else if (c == trainingProvider) {
            trainingProviderLabel.setForeground(Color.blue);
        } else if (c == dateOfCompetation) {
            dateOfCompetationLabel.setForeground(Color.blue);
        } else if (c == dateOfIssue) {
            dateOfIssueLabel.setForeground(Color.blue);
        } else if (c == contactNo) {
            contactNoLabel.setForeground(Color.blue);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        JComponent c = (JComponent) e.getSource();
        if (c == browseImage) {browseImge();} 
        else if (c == test) {test();}
        else if (c == dateOfCompetation) {}
        else if (c == insertOccupation) {insertOccupation();} 
        else if (c == insertClassification) {insertClassification();} 
        else if (c == insertTrainingProvider) {insertTrainingProvider();} 
        else if (c == insertAssessmentCenter) {insertAssessmentCenter();} 
        else if (c == saveRecord || c == save) {save();} 
        else if (c == update) {update();} 
        else if (c == addMultiple) {
            singleBoolean = false;
            flag = true;
            save();
        } else if (c == addSingle) {
            singleBoolean = true;
            flag = true;
            setForEdit(true);
            fName.setText("");
            mName.setText("");
            lName.setText("");
            D_O_G.setText("");
            industry.setText("");
            dateOfCompetation.addItem("");
            dateOfCompetation.setSelectedItem("");
            dateOfIssue.setText("");
            contactNo.setText("");
        } else if (c == insert) {
            addPop.removeAll();addPop.add(addSingle);addPop.addSeparator();addPop.add(addMultiple);addSingle.setBackground(Color.yellow);addMultiple.setBackground(Color.yellow);
            addPop.show(c, c.getX() + c.getWidth(), c.getY() - c.getHeight());
        } else if (c == edit || c == editRecord) {
            singleBoolean = true;
            edit();
        } 
        else if (c == delete || c == deleteRecord) {delete(traineeID.getText());} 
        else if (c == search) {search(); }
        else if (c == load) {load();} 
        else if (c == xSearch) {xSearch();} 
        else if (c == printAll) { printAll();}
        else if (c == report) { report();}
        else if (c == printOne) {
            try {
                //setForEdit(true);
                printOne(traineeID.getText());
                //setForEdit(false);
            } catch (JRException ex) {
                Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void update() {
        if (isImageChanged) {
            try {
                this.connectToDB(url, username, password);
                Statement sttm = con.createStatement();
                String editData;
                editData = "update trainee set fName=?, mName=?,lName=?,D_O_G=?,sex=?,occupationID=?,fieldOfStudy=?,level=?,classification=?,industryName=?,assessmentCenter=?,trainingProvider=?,dateOfCompetation=?,dateOfIssue=?,contactNo=?,photo=?,managedBy=? where traineeID= ?";
                con.setAutoCommit(false);
                //fis = new FileInputStream(selection);
                ps = con.prepareStatement(editData);
                //ps.setString(1, traineeID.getText());
                ps.setString(1, fName.getText());
                ps.setString(2, mName.getText());
                ps.setString(3, lName.getText());
                ps.setInt(4, Integer.parseInt(D_O_G.getText()));
                ps.setString(5, sex.getSelectedItem().toString());
                ps.setString(6, occuptionID.getSelectedItem().toString());
                ps.setString(7, fieldOfStudy.getSelectedItem().toString());
                ps.setString(8, level.getSelectedItem().toString());
                ps.setString(9, classification.getSelectedItem().toString());
                ps.setString(10, industry.getText());
                ps.setString(11, assessmentCenter.getSelectedItem().toString());
                ps.setString(12, trainingProvider.getSelectedItem().toString());
                ps.setString(13, dateOfCompetation.getSelectedItem().toString());
                ps.setString(14, dateOfIssue.getText());
                ps.setString(15, contactNo.getText());
                ps.setBytes(16, toDBImage);
                ps.setString(17, "root");
                ps.setString(18, traineeID.getText());
                int x = ps.executeUpdate();
                con.commit();
                JOptionPane.showMessageDialog(null, "Confirmed :- update successful for selected Id -> " + traineeID.getText(), "confirmation", JOptionPane.INFORMATION_MESSAGE);
                this.disconnect();
            } catch (SQLException | NumberFormatException e) {
                //JOptionPane.showMessageDialog(null, e.getMessage(), "\'"+traineeID.getText()+"\'", 0);
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", 0);
            }
        } else {
            try {
                this.connectToDB(url, username, password);
                Statement sttm = con.createStatement();
                String editData;
                editData = "update trainee set fName=?, mName=?,lName=?,D_O_G=?,sex=?,occupationID=?,fieldOfStudy=?,level=?,classification=?,industryName=?,assessmentCenter=?,trainingProvider=?,dateOfCompetation=?,dateOfIssue=?,contactNo=?,managedBy=? where traineeID= ?";
                con.setAutoCommit(false);
                //fis = new FileInputStream(selection);
                ps = con.prepareStatement(editData);
                //ps.setString(1, traineeID.getText());
                ps.setString(1, fName.getText());
                ps.setString(2, mName.getText());
                ps.setString(3, lName.getText());
                ps.setInt(4, Integer.parseInt(D_O_G.getText()));
                ps.setString(5, sex.getSelectedItem().toString());
                ps.setString(6, occuptionID.getSelectedItem().toString());
                ps.setString(7, fieldOfStudy.getSelectedItem().toString());
                ps.setString(8, level.getSelectedItem().toString());
                ps.setString(9, classification.getSelectedItem().toString());
                ps.setString(10, industry.getText());
                ps.setString(11, assessmentCenter.getSelectedItem().toString());
                ps.setString(12, trainingProvider.getSelectedItem().toString());
                ps.setString(13, dateOfCompetation.getSelectedItem().toString());
                ps.setString(14, dateOfIssue.getText());
                ps.setString(15, contactNo.getText());
                ps.setString(16, "asnake");
                ps.setString(17, traineeID.getText());
                int x = ps.executeUpdate();
                con.commit();
                this.disconnect();
                JOptionPane.showMessageDialog(null, "Confirmed :- update successful for selected Id -> " + traineeID.getText(), "confirmation", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        fillTable();
        setForEdit(false);
    }
    private void insert(String singleOrMulti) throws SQLException, IOException {
        System.out.println("insert in");
        if (singleOrMulti.equals("single")) {
            try {
                connectToDB(url, username, password);
                Statement st11 = con.createStatement();
                String assignment = occuptionID.getSelectedItem().toString(), alt = occuptionID.getSelectedItem().toString();
                System.out.println(assignment + " " + res.toString());
                res = st11.executeQuery("select * from occupation where occupationID='" + assignment + "'");
                int stuid = 0, toStuid = 0;
                while (res.next()) {
                    stuid = res.getInt("counter");
                }
                //res = st11.executeQuery("select counter from occupation where occupationID='" + assignment+"'");
                toStuid = stuid + 1;
                assignment += "-OR-" + toStuid;
                String sq = "select photo from trainee where traineeID='" + traineeID.getText() + "'";
                String insertData, updateCounter;
                insertData = "insert into trainee(traineeID, fName, mName,lName,D_O_G,sex,occupationID,fieldOfStudy,"
                        + "level,classification,industryName,assessmentCenter,trainingProvider,"
                        + "dateOfCompetation,dateOfIssue,contactNo,photo,managedBy) "
                        + "values (?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?)";
                updateCounter = "update occupation set counter=? where occupationID=?";

                con.setAutoCommit(false);
                PreparedStatement occ = con.prepareStatement(updateCounter);
                traineeID.setText(assignment);
                fis = new FileInputStream(selection);
                ps = con.prepareStatement(insertData);
                //ps.setString(1, traineeID.getText());
                ps.setString(1, assignment);
                ps.setString(2, fName.getText());
                ps.setString(3, mName.getText());
                ps.setString(4, lName.getText());
                ps.setInt(5, Integer.parseInt(D_O_G.getText()));
                ps.setString(6, sex.getSelectedItem().toString());
                ps.setString(7, occuptionID.getSelectedItem().toString());
                ps.setString(8, fieldOfStudy.getSelectedItem().toString());
                ps.setString(9, level.getSelectedItem().toString());
                ps.setString(10, classification.getSelectedItem().toString());
                ps.setString(11, industry.getText());
                ps.setString(12, assessmentCenter.getSelectedItem().toString());
                ps.setString(13, trainingProvider.getSelectedItem().toString());
                ps.setString(14, dateOfCompetation.getSelectedItem().toString());
                ps.setString(15, dateOfIssue.getText());
                ps.setString(16, contactNo.getText());
                //ps.setBinaryStream(17, fis, (int) selection.length());
                ps.setBytes(17, toDBImage);
                ps.setString(18, username);
                int comfirm = ps.executeUpdate();

                if (comfirm > 0) {
                    occ.setInt(1, stuid + 1);
                    System.out.println(stuid + 1);
                    occ.setString(2, alt);
                    occ.executeUpdate();
                    JOptionPane.showMessageDialog(null, traineeID.getText() + " Added to datbase.", "Insertion Success", JOptionPane.INFORMATION_MESSAGE);
                }
                con.commit();
                fillTable();
                disconnect();
            } catch (SQLException | FileNotFoundException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error inserting", JOptionPane.ERROR_MESSAGE);
            }
        } else if (singleOrMulti.equals("multi")) {
            
            ReadXl xl = new ReadXl(this);
            String[][] xlData = xl.getData();
            String insertData, updateCounter;
            int intValue = 0, confirm = 0;
            insertData = "insert into trainee(traineeID, fName, mName,lName,D_O_G,sex,occupationID,fieldOfStudy,"
                    + "level,classification,industryName,assessmentCenter,trainingProvider,"
                    + "dateOfCompetation,contactNo,managedBy) "
                    + "values (?,?, ?, ?,?, ?, ?,?, ?, ?,?, ?,?, ?, ?, ?)";
            updateCounter = "update occupation set counter=? where occupationID=?";
            this.connectToDB(url, username, password);
            PreparedStatement occ = con.prepareStatement(updateCounter);
            ps = con.prepareStatement(insertData);
            try {
                
                con.setAutoCommit(false);
                for (int i = 1; i < xlData.length; i++)
                {
                            Statement st11 = con.createStatement();
                            String assignment = xlData[i][0];
                            res = st11.executeQuery("select * from occupation where occupationID='" + assignment + "'");
                            int stuid = 0, toStuid = 0;
                            while (res.next()) {stuid = res.getInt("counter");}
                            toStuid = stuid + 1;
                            assignment += "-OR-" + toStuid;confirm++;
                            occ.setInt(1, stuid + 1);
                            occ.setString(2, xlData[i][0]);
                            ps.setString(1, assignment);
                    ps.setString(2, xlData[i][1].trim());
                    ps.setString(3, xlData[i][2].trim());
                    ps.setString(4, xlData[i][3].trim());
                    ps.setInt(5, Integer.parseInt(xlData[i][4].trim()));
                    ps.setString(6, xlData[i][5].trim());
                    ps.setString(7, xlData[i][6].trim());
                    ps.setString(8, xlData[i][7].trim());
                    ps.setString(9, xlData[i][8].trim());
                    ps.setString(10, xlData[i][9].trim());
                    ps.setString(11, xlData[i][10].trim());
                    ps.setString(12, xlData[i][11].trim());
                    ps.setString(13, xlData[i][12].trim());
                    ps.setString(14, xlData[i][13].trim());
                    ps.setString(15, xlData[i][14].trim());
                    ps.setString(16, username);
                    int returnee1=ps.executeUpdate();
                    System.out.println(returnee1);
                    int returnee2=occ.executeUpdate();
                    System.out.println(returnee2);
                    //ps.addBatch();
                    //occ.addBatch();
                }
                //int []cc= ps.executeBatch();
                //int []dd= occ.executeBatch();
                //for(int i =0;i<cc.length;i++)System.out.println(cc[i]);
                
                con.commit();
                if (confirm == xlData.length-1) {
                    JOptionPane.showMessageDialog(null, confirm + " Trainee Added Successfully.", "Inserting Success", JOptionPane.INFORMATION_MESSAGE);
                } else if (confirm < xlData.length-1) {
                    JOptionPane.showMessageDialog(null, confirm + "/" + xlData.length + " Successfully, You should check mannualy for the error.", "Inserting to be checked", JOptionPane.QUESTION_MESSAGE);
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, exc.toString(), "Error inserting", JOptionPane.ERROR_MESSAGE);
                System.out.println(exc.getStackTrace());
            }
            this.disconnect();
        }
    }
    private void browseImge() {
        JFileChooser fc = null;
        if (fc == null) {
            fc = new JFileChooser();
            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileView(new ImageFileView());
            fc.setAccessory(new ImagePreview(fc));
            fc.setBackground(Color.red);
        }
        int returnVal = fc.showDialog(COC.this, "Attach");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            isImageChanged = true;
            selection = new File("");
            selection = fc.getSelectedFile();
            fc.setSelectedFile(selection);
            imageNameLabel.setText(selection.getAbsolutePath());
            try {
                BufferedImage bImage = ImageIO.read(selection);
                //JOptionPane.showConfirmDialog(null, bImage.getWidth() + " " + bImage.getHeight());
                BufferedImage resizedImage = resize(bImage, jLabel1.getWidth(), jLabel1.getHeight());
                imageIcon = new ImageIcon(resizedImage);
                
                ////////////////////////////////////////////////////
                Image image = imageIcon.getImage();
                RenderedImage rendered = null;
                if (image instanceof RenderedImage) {
                    rendered = (RenderedImage) image;
                } else {
                    BufferedImage buffered = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                    buffered.flush();
                    Graphics2D g = buffered.createGraphics();
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                    rendered = buffered;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //BufferedImage resizedImage=resize(rendered,jLabel1.getWidth(),jLabel1.getHeight());
                ImageIO.write(rendered, "png", baos);
                toDBImage = baos.toByteArray();
                ImageIO.write(rendered, "png", selection);
                jLabel1.setIcon(imageIcon);
                //JOptionPane.showConfirmDialog(null, resizedImage.getWidth() + " " + resizedImage.getHeight());
            } catch (IOException ex) {
                Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void edit() {;
        setForEdit(true);
    }
    private void save() {
        if (singleBoolean == true && flag == true) {
            try {
                insert("single");
            } catch (SQLException | IOException ex) {
                Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (singleBoolean == false && flag == true) {
            try {
                insert("multi");
            } catch (SQLException | IOException ex) {
                Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to save", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        flag = false;
    }
    private void search() {
    
        /*JTextField txt=new JTextField();
        Insets dummyInsets;
        javax.swing.border.Border border=UIManager.getBorder("TextField.border");
        dummyInsets = border.getBorderInsets(txt);
        JTextFieldHint te=new JTextFieldHint(txt,"search","Search...");*/ 
        
        //setForEdit(true);
        //fName =AutoCompleteDocument.createAutoCompleteTextField((trName));
        //traineeID =AutoCompleteDocument.createAutoCompleteTextField(trID);
        //JList list = new JList(myStrings); //data has type Object[]
        //AutoCompleteDecorator.decorate(list, this.fName, ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
        AutoSuggestor autoSuggestor = new AutoSuggestor(fName, new COC(""), null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f) 
         {
         @Override
         boolean wordTyped(String typedWord) 
         {
         //create list for dictionary this in your case might be done via calling a method which queries db and returns results as arraylist
         ArrayList<String> words = new ArrayList<>();
         words.add("hello");
         words.add("heritage");
         words.add("happiness");
         words.add("goodbye");
         words.add("cruel");
         words.add("car");
         words.add("war");
         words.add("will");
         words.add("world");
         words.add("wall");
         setDictionary(words);
         return super.wordTyped(typedWord);//now call super to check for any matches against newest dictionary
         }
         };
        //AutoSuggestor1 autoSuggestor = new AutoSuggestor1(fName, new COC(""), null, Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.75f);
    }
    private void load() {
    }
    public void fillTable() {
        tt = new TableToolTip();
        jTable1 = tt.table;
        jTable1.setRowHeight(jTable1.getEditingRow(), 30);
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(Color.magenta);
        jTable1.setForeground(Color.blue);
        jTable1.setSelectionBackground(Color.yellow);
        jTable1.setSelectionForeground(Color.blue);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);

        jTable1.setAutoCreateColumnsFromModel(useComboBoxModel);
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(true);
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setAutoscrolls(true);
        jTable1.addMouseListener(this);

        jTable1.repaint();
        jScrollPane1.repaint();
    }
    public void fillTable(String arg) {
        tt = new TableToolTip(arg);
        jTable1 = tt.createTable();
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(Color.magenta);
        jTable1.setForeground(Color.blue);
        jTable1.setSelectionBackground(Color.yellow);
        jTable1.setSelectionForeground(Color.blue);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setAutoResizeMode(AUTO_RESIZE_NEXT_COLUMN);
        //jTable1.set
        jTable1.setShowHorizontalLines(true);
        jTable1.setShowVerticalLines(true);
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setAutoscrolls(true);
        jTable1.addMouseListener(this);
        im.put(keyDown, "Action.down");
        im.put(keyUp, "Action.up");
        jTable1.repaint();
        jScrollPane1.repaint();
    }
    private void printAll() {
        
        openDialog(this);
        if (printDate != "" && printCenter != "") {
            System.out.println("Print all clicked");
            try {
                connectToDB(url, username, password);
                JasperDesign jd = JRXmlLoader.load("src/coc/tempo.jrxml");
                String sql="";
                if(onlyHavePhoto)
                {
                    sql = "select *from trainee where assessmentCenter=\'" + printCenter + "\' " + "AND dateOFCompetation=\'" + printDate + "\' and photo is not null";
                }
                else
                {
                    sql = "select *from trainee where assessmentCenter=\'" + printCenter + "\' " + "AND dateOFCompetation=\'" + printDate + "\'";
                }
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                jp.setName("Asnake");
                JasperViewer jv = new JasperViewer(jp, false);
                if ((!jp.getPages().isEmpty())) {
                    jv.setVisible(true);
                    st = con.createStatement();
                    SimpleDateFormat issued = new SimpleDateFormat("dd/MM/yyyy");
                    Date dd = new Date();
                    String cDate = issued.format(dd);
                    st.execute("update trainee set dateOfIssue='" + cDate + "' where assessmentCenter=\'" + printCenter + "\' " + " AND dateOFCompetation=\'" + printDate + "\'");
                }
                disconnect();
            } catch (JRException | SQLException jr) {
                JOptionPane.showConfirmDialog(null, jr.toString());
            }
        }
    }
    private void reportDialog(JFrame parent)
    {
        final JDialog dialog = new JDialog(parent, "Summary report", true);
        final JButton go = new JButton("Print");
        final JButton cancel = new JButton("Cancel");
        DateComboBox dcb = new DateComboBox();
        final JComboBox date = new DateComboBox();
        date.setEditable(true);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(date.getSelectedItem().toString().isEmpty())) {
                    printCenter = trainingCenterPrint.getSelectedItem().toString();
                    printDate = date.getSelectedItem().toString();
                    dialog.dispose();
                    //dialog.addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){System.exit(0);}});
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printCenter = "";
                printDate = "";
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        JLabel comboLabel = new JLabel("Select Training Ccenter : ");
        JLabel textLabel = new JLabel("Select Competation Date : ");
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        comboLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //final JComboBox test = null;

        AutoCompleteDecorator.decorate(trainingCenterPrint);
        panel.setLayout(new java.awt.GridLayout(0, 2));
        panel.add(comboLabel);
        panel.add(trainingCenterPrint);
        panel.add(textLabel);
        panel.add(date);
        JButton[] buttons = {go, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        //dialog.setUndecorated(true);
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

    }
    private void openDialog(Frame f) {
        final JDialog dialog = new JDialog(f, "Printing Temporary", true);
        final JButton go = new JButton("Print");
        final JButton cancel = new JButton("Cancel");
        DateComboBox dcb = new DateComboBox();
        final JComboBox date = new DateComboBox();
        final JCheckBox onlyPhoto=new JCheckBox("Print Only Have photo");
        onlyPhoto.setSelected(true);
        date.setEditable(true);
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(date.getSelectedItem().toString().isEmpty())) {
                    printCenter = trainingCenterPrint.getSelectedItem().toString();
                    printDate = date.getSelectedItem().toString();
                    dialog.dispose();
                    //dialog.addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){System.exit(0);}});
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printCenter = "";
                printDate = "";
                dialog.dispose();
            }
        });
        onlyPhoto.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(onlyPhoto.isSelected())
                {
                    onlyHavePhoto=true;
                }
                else onlyHavePhoto=false;
            }
        });
        JPanel panel = new JPanel();
        JLabel comboLabel = new JLabel("Select Training Ccenter : ");
        JLabel textLabel = new JLabel("Select Competation Date : ");
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        comboLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        final JComboBox test = null;

        AutoCompleteDecorator.decorate(trainingCenterPrint);
        panel.setLayout(new java.awt.GridLayout(0, 2));
        panel.add(comboLabel);
        panel.add(trainingCenterPrint);
        panel.add(textLabel);
        panel.add(date);
        panel.add(onlyPhoto);
        JButton[] buttons = {go, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        //dialog.setUndecorated(true);
        dialog.setSize(500, 200);
        dialog.setLocationRelativeTo(f);
        dialog.setVisible(true);

    }
    private void login(Frame f) {
        final JDialog dialog = new JDialog(f, "", true);
        final JButton login = new JButton("Login"),cancel = new JButton("Cancel");
        final JTextField dbUser = new JTextField(),url=new JTextField();
        JPasswordField pass = new JPasswordField();
        dialog.setUndecorated(true);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        JLabel userNameLabel = new JLabel("Username ");
        JLabel passLabel = new JLabel("Password ");
        JLabel urlLabel =new JLabel("Database URL");
        userNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        passLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        urlLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        AutoCompleteDecorator.decorate(trainingCenterPrint);
        panel.setLayout(new java.awt.GridLayout(0, 2));
        panel.add(urlLabel);
        panel.add(url);
        //panel.add(new JLabel());
        panel.add(userNameLabel);
        panel.add(dbUser);
        //panel.add(new JLabel());
        panel.add(passLabel);
        panel.add(pass);
        //panel.add(new JLabel());
        //panel.add(a);
        JButton[] buttons = {login, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        //dialog.setUndecorated(true);
        dialog.setSize(600, 150);
        dialog.setLocationRelativeTo(f);
        dialog.setVisible(true);

    }
    private void printOne(String traineeID) throws JRException {
        if (!traineeID.isEmpty()) 
        {
            System.out.println("Print one clicked");
            
            JasperDesign jd = JRXmlLoader.load("src/coc/tempo.jrxml");//
            String sql = "select *from trainee where traineeID=\'" + traineeID + "\' ";
            JRDesignQuery newQuery = new JRDesignQuery();
            Map params = new HashMap();
            
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            this.connectToDB(url, username, password);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);        
            jp.setName("Asnake");
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            try {
                Statement print = con.createStatement();
                SimpleDateFormat issued = new SimpleDateFormat("dd/MM/yyyy");
                Date dd = new Date();
                String cDate = issued.format(dd);
                
                int zz=print.executeUpdate("update trainee set dateOfIssue='" + cDate + "' where traineeID='" + traineeID + "'");
                System.out.println("issued= "+zz);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", 0);
            }
            this.disconnect();
        }
    }
    private void delete(String id) {
        try {
            connectToDB(url, username, password);
            Statement st1 = con.createStatement();
            int x = st1.executeUpdate("delete from trainee where traineeID='" + id + "'");
            JOptionPane.showMessageDialog(null, x);
            fillTable();
            this.disconnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void setForEdit(boolean state) {
        for (int i = 0; i < detailPanel.getComponents().length; i++) {
            JComponent c = (JComponent) detailPanel.getComponent(i);
            if (!c.getClass().getName().endsWith("JLabel")) {
                detailPanel.getComponent(i).setEnabled(state);
            }
        }
    }
    private void xSearch() {
        final JDialog dialog = new JDialog(this, "Advanced Search", true);
        sqlText = new JTextArea(tempQuery);
        final JButton go = new JButton("Go Head");
        final JButton cancel = new JButton("Cancel");
        final JButton apply = new JButton("Apply");
        sqlText.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}
            @Override public void keyPressed(KeyEvent e) { } @Override public void keyReleased(KeyEvent e) { }
        });

        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTable(sqlText.getText());
                tempQuery = sqlText.getText();
            }
        });
        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fillTable(sqlText.getText());
                tempQuery = sqlText.getText();
                dialog.dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();

        JScrollPane holder = new JScrollPane();
        holder.setViewportView(sqlText);
        panel.setLayout(new java.awt.GridLayout(1, 0));
        panel.add(holder);
        JButton[] buttons = {cancel, go, apply};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        dialog.setSize(500, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void test() {

    }
    private void insertOccupation() {
        final JDialog dialog = new JDialog(this, "Insert Occupation", true);
        final JButton insertOCC = new JButton("Insert");
        final JButton cancel = new JButton("Cancel");
        final JTextField occuID = new JTextField();
        final JComboBox occuLevel = new JComboBox(vlevel), occuField = new JComboBox(vtitle);
        occuField.setEditable(true);
        occuLevel.setEditable(true);
        insertOCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(occuID.getText().isEmpty()) && !(occuField.getSelectedItem().toString().isEmpty()) && !(occuLevel.getSelectedItem().toString().isEmpty())) {
                    try {
                        connectToDB(url, username, password);
                        String x = occuID.getText(), y = occuLevel.getSelectedItem().toString(), z = occuField.getSelectedItem().toString();
                        con.setAutoCommit(false);
                        ps = con.prepareStatement("insert into occupation(OccupationID,level,fieldOfStudy) values(?,?,?)");
                        ps.setString(1, x);
                        ps.setString(2, y);
                        ps.setString(3, z);
                        ps.executeUpdate();
                        con.commit();
                        occuptionID.addItem(x);
                        fieldOfStudy.addItem(z);
                        disconnect();
                        dialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString(), "Error inserting", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        //dialog.addWindowListener(new WindowAdapter(){@Override public void windowClosing(WindowEvent e){System.exit(0);}});
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        JLabel id = new JLabel("Occupation ID : ");
        JLabel lev = new JLabel("Level : ");
        JLabel field = new JLabel("Field Of Study : ");
        id.setHorizontalAlignment(SwingConstants.RIGHT);
        lev.setHorizontalAlignment(SwingConstants.RIGHT);
        field.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.setLayout(new java.awt.GridLayout(0, 2));
        panel.add(id);
        panel.add(occuID);
        panel.add(lev);
        panel.add(occuLevel);
        panel.add(field);
        panel.add(occuField);
        //panel.add(a);
        JButton[] buttons = {insertOCC, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        dialog.setSize(500, 180);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void insertAssessmentCenter() {
        final JDialog dialog = new JDialog(this, "Insert Assessement Center", true);
        final JButton insertOCC = new JButton("Insert"), cancel = new JButton("Cancel");
        final JTextField center = new JTextField();
        insertOCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(center.getText().isEmpty())) {
                    try {
                        connectToDB(url, username, password);
                        String x = center.getText();
                        con.setAutoCommit(false);
                        ps = con.prepareStatement("insert into assessement_center(assessmentCenter) values(?)");
                        ps.setString(1, x);
                        ps.executeUpdate();
                        con.commit();
                        assessmentCenter.addItem(x);
                        disconnect();
                        dialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString(), "Error inserting", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        JLabel id = new JLabel("Assessment Center : ");
        id.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.setLayout(new java.awt.GridLayout(1, 2));
        panel.add(id);
        panel.add(center);
        JButton[] buttons = {insertOCC, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        dialog.setSize(500, 140);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void insertClassification() {
        final JDialog dialog = new JDialog(this, "Insert Classification", true);
        final JButton insertOCC = new JButton("Insert"), cancel = new JButton("Cancel");
        final JTextField classf = new JTextField();
        insertOCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(classf.getText().isEmpty())) {
                    try {
                        connectToDB(url, username, password);
                        String x = classf.getText();
                        con.setAutoCommit(false);
                        ps = con.prepareStatement("insert into classification(classification) values(?)");
                        ps.setString(1, x);
                        ps.executeUpdate();
                        con.commit();
                        classification.addItem(x);
                        disconnect();
                        dialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString(), "Error inserting", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        JLabel id = new JLabel("Classification : ");
        id.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.setLayout(new java.awt.GridLayout(1, 2));
        panel.add(id);
        panel.add(classf);
        JButton[] buttons = {insertOCC, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        dialog.setSize(500, 140);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    private void insertTrainingProvider() {
        final JDialog dialog = new JDialog(this, "Insert Classification", true);
        final JButton insertOCC = new JButton("Insert"), cancel = new JButton("Cancel");
        final JTextField provider = new JTextField();
        insertOCC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(provider.getText().isEmpty())) {
                    try {
                        //COC te=new COC("");
                        connectToDB(url, username, password);
                        String x = provider.getText();
                        con.setAutoCommit(false);
                        ps = con.prepareStatement("insert into training_provider(provider_name) values(?)");
                        ps.setString(1, x);
                        ps.executeUpdate();
                        con.commit();
                        trainingProvider.addItem(x);
                        disconnect();
                        dialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString(), "Error inserting", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (provider.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Empty String", "Error inserting", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        JLabel id = new JLabel("Training Provider : ");
        id.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.setLayout(new java.awt.GridLayout(1, 2));
        panel.add(id);
        panel.add(provider);
        panel.add(new DateComboBoxTest());
        JButton[] buttons = {insertOCC, cancel};
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, buttons);
        dialog.getContentPane().add(optionPane);
        dialog.setSize(500, 140);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public void connectToDB(String url, String username, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, username, password);
            Statement sttm = con.createStatement();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex.toString(), "url Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void disconnect() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(COC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void getHighlight() {

        System.out.println("row clicked");
        int row = jTable1.getSelectedRow();
        int col = 0;
        int tempClick = row;
        traineeID.setText(jTable1.getValueAt(row, col).toString());
        fName.setText(jTable1.getValueAt(row, col += 1).toString());
        mName.setText(jTable1.getValueAt(row, col += 1).toString());
        lName.setText(jTable1.getValueAt(row, col += 1).toString());
        D_O_G.setText(jTable1.getValueAt(row, col += 1).toString());
        sex.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        occuptionID.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        fieldOfStudy.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        level.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        classification.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        industry.setText(jTable1.getValueAt(row, col += 1).toString());
        assessmentCenter.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        trainingProvider.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        dateOfCompetation.setSelectedItem((Object) jTable1.getValueAt(row, col += 1));
        dateOfIssue.setText(jTable1.getValueAt(row, col += 1).toString());
        contactNo.setText(jTable1.getValueAt(row, col += 1).toString());
        byte[] imageByte;
        this.connectToDB(url, username, password);
        try {

            st = con.createStatement();
            String sq = "select photo from trainee where traineeID='" + traineeID.getText() + "'";
            res = st.executeQuery(sq);
            res = st.getResultSet();
            while (res.next()) {
                Blob blob = res.getBlob("photo");
                /////////////////////////////
                byte[] bytes;                
                if(blob==null)
                {
                    File f=new File("src/coc/unavailable.jpg");
                    //bytes=Files.readAllBytes(f.toPath());
                    //ImageIcon xx= new ImageIcon(ImageIO.read(f));
                    jLabel1.setIcon(new ImageIcon(ImageIO.read(f)));
                }
                else 
                {
                    InputStream ist = blob.getBinaryStream(1, blob.length());
                    BufferedImage imagee = ImageIO.read(ist);
                    Image img = imagee;
                    ImageIcon ico = new ImageIcon(img);
                    jLabel1.setIcon(ico);                   
                }
                /////////////////////////////
                //imageByte = blob.getBytes(1, (int) blob.length());
                
            }
            this.disconnect();
        } catch (IOException | SQLException ex) {
            JOptionPane.showConfirmDialog(null, ex, "Error gethighlight", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void report() 
    {
        openDialog(this);
        if (printDate != "" && printCenter != "") {
            try {
                connectToDB(url, username, password);
                JasperDesign jd = JRXmlLoader.load("src/coc/report.jrxml");
                String sql="";
                sql = "select *from trainee where assessmentCenter=\'" + printCenter + "\' " + "AND dateOFCompetation=\'" + printDate + "\'";
                JRDesignQuery newQuery = new JRDesignQuery();
                newQuery.setText(sql);
                jd.setQuery(newQuery);
                JasperReport jr = JasperCompileManager.compileReport(jd);
                JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
                jp.setName("Asnake");
                JasperViewer jv = new JasperViewer(jp, false);
                if ((!jp.getPages().isEmpty())) {
                    jv.setVisible(true);
                    //st = con.createStatement();
                    //SimpleDateFormat issued = new SimpleDateFormat("dd/MM/yyyy");
                    //Date dd = new Date();
                    //String cDate = issued.format(dd);
                    //st.execute("update trainee set dateOfIssue='" + cDate + "' where assessmentCenter=\'" + printCenter + "\' " + " AND dateOFCompetation=\'" + printDate + "\'");
                }
                disconnect();
            } catch (JRException jr) {
                JOptionPane.showConfirmDialog(null, jr.toString());
            }
        }
    }
}

class DateComboBoxTest extends JComboBox {

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DateComboBoxTest(/*SimpleDateFormat dateFormat*/) {
        this.dateFormat = dateFormat;
        this.setSelectedItem(dateFormat.format(Calendar.getInstance().getTime()));
    }

    @Override
    public void setSelectedItem(Object item) {
        removeAllItems(); // hides the popup if visible
        addItem(item);
        super.setSelectedItem(item);
    }

    @Override
    public void updateUI() {
        ComboBoxUI cui = (ComboBoxUI) UIManager.getUI(this);
        if (cui instanceof MetalComboBoxUI) {
            cui = new MetalDateComboBoxUI();
        } else if (cui instanceof MotifComboBoxUI) {
            cui = new MotifDateComboBoxUI();
        } else if (cui instanceof WindowsComboBoxUI) {
            cui = new WindowsDateComboBoxUI();
        }
        setUI(cui);
    }

    class MetalDateComboBoxUI extends MetalComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class WindowsDateComboBoxUI extends WindowsComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class MotifDateComboBoxUI extends MotifComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class DatePopup extends COC implements ComboPopup, MouseMotionListener, MouseListener, KeyListener, PopupMenuListener {

        protected JComboBox comboBox;
        protected Calendar calendar;
        protected JPopupMenu popup;
        protected JLabel monthLabel;
        protected JPanel days = null;
        protected SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy");

        protected Color selectedBackground;
        protected Color selectedForeground;
        protected Color background;
        protected Color foreground;

        public DatePopup(JComboBox comboBox) {
            this.comboBox = comboBox;
            calendar = Calendar.getInstance();
            background = UIManager.getColor("ComboBox.background");
            foreground = UIManager.getColor("ComboBox.foreground");
            selectedBackground = UIManager.getColor("ComboBox.selectionBackground");
            selectedForeground = UIManager.getColor("ComboBox.selectionForeground");
            initializePopup();
        }

        public void show() {
            try {
                // if setSelectedItem() was called with a valid date, adjust the calendar
                calendar.setTime(dateFormat.parse(comboBox.getSelectedItem().toString()));
            } catch (Exception e) {
            }
            updatePopup();
            popup.show(comboBox, 0, comboBox.getHeight());
        }

        public void hide() {
            popup.setVisible(false);
        }

        protected JList list = new JList();

        public JList getList() {
            return list;
        }

        public MouseListener getMouseListener() {
            return this;
        }

        public MouseMotionListener getMouseMotionListener() {
            return this;
        }

        public KeyListener getKeyListener() {
            return this;
        }

        public boolean isVisible() {
            return popup.isVisible();
        }

        public void uninstallingUI() {
            popup.removePopupMenuListener(this);
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        // something else registered for MousePressed

        public void mouseClicked(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e)) {
                return;
            }
            if (!comboBox.isEnabled()) {
                return;
            }
            if (comboBox.isEditable()) {
                comboBox.getEditor().getEditorComponent().requestFocus();
            } else {
                comboBox.requestFocus();
            }
            togglePopup();
        }
        protected boolean mouseInside = false;

        public void mouseEntered(MouseEvent e) {
            mouseInside = true;
        }

        public void mouseExited(MouseEvent e) {
            mouseInside = false;
        }

        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                togglePopup();
            }
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
        }
        protected boolean hideNext = false;

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            hideNext = mouseInside;
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        protected void togglePopup() {
            if (isVisible() || hideNext) {
                hide();
            } else {
                show();
            }
            hideNext = false;
        }

        protected JLabel createUpdateButton(final int field, final int amount) {
            final JLabel label = new JLabel();
            final Border selectedBorder = new EtchedBorder();
            final Border unselectedBorder = new EmptyBorder(selectedBorder.getBorderInsets(new JLabel()));
            label.setBorder(unselectedBorder);
            label.setForeground(foreground);
            label.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    calendar.add(field, amount);
                    updatePopup();
                }

                public void mouseEntered(MouseEvent e) {
                    label.setBorder(selectedBorder);
                }

                public void mouseExited(MouseEvent e) {
                    label.setBorder(unselectedBorder);
                }
            });
            return label;
        }

        protected void initializePopup() {
            JPanel header = new JPanel(); // used Box, but it wasn't Opaque
            header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
            header.setBackground(background);
            header.setOpaque(true);

            JLabel label;
            label = createUpdateButton(Calendar.YEAR, -1);
            label.setText("<<");
            label.setToolTipText("Previous Year");

            header.add(Box.createHorizontalStrut(12));
            header.add(label);
            header.add(Box.createHorizontalStrut(12));

            label = createUpdateButton(Calendar.MONTH, -1);
            label.setText("<");
            label.setToolTipText("Previous Month");
            header.add(label);

            monthLabel = new JLabel("", JLabel.CENTER);
            monthLabel.setForeground(foreground);
            header.add(Box.createHorizontalGlue());
            header.add(monthLabel);
            header.add(Box.createHorizontalGlue());

            label = createUpdateButton(Calendar.MONTH, 1);
            label.setText(">");
            label.setToolTipText("Next Month");
            header.add(label);

            label = createUpdateButton(Calendar.YEAR, 1);
            label.setText(">>");
            label.setToolTipText("Next Year");

            header.add(Box.createHorizontalStrut(12));
            header.add(label);
            header.add(Box.createHorizontalStrut(12));

            popup = new JPopupMenu();
            popup.setBorder(BorderFactory.createLineBorder(Color.black));
            popup.setLayout(new BorderLayout());
            popup.setBackground(background);
            popup.addPopupMenuListener(this);
            popup.add(BorderLayout.NORTH, header);
        }

        // update the Popup when either the month or the year of the calendar has been changed
        protected void updatePopup() {
            monthLabel.setText(monthFormat.format(calendar.getTime()));
            if (days != null) {
                popup.remove(days);
            }
            days = new JPanel(new GridLayout(0, 7));
            days.setBackground(background);
            days.setOpaque(true);

            Calendar setupCalendar = (Calendar) calendar.clone();
            setupCalendar.set(Calendar.DAY_OF_WEEK, setupCalendar.getFirstDayOfWeek());
            for (int i = 0; i < 7; i++) {
                int dayInt = setupCalendar.get(Calendar.DAY_OF_WEEK);
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setForeground(foreground);
                if (dayInt == Calendar.SUNDAY) {
                    label.setText("Sun");
                } else if (dayInt == Calendar.MONDAY) {
                    label.setText("Mon");
                } else if (dayInt == Calendar.TUESDAY) {
                    label.setText("Tue");
                } else if (dayInt == Calendar.WEDNESDAY) {
                    label.setText("Wed");
                } else if (dayInt == Calendar.THURSDAY) {
                    label.setText("Thu");
                } else if (dayInt == Calendar.FRIDAY) {
                    label.setText("Fri");
                } else if (dayInt == Calendar.SATURDAY) {
                    label.setText("Sat");
                }
                days.add(label);
                setupCalendar.roll(Calendar.DAY_OF_WEEK, true);
            }

            setupCalendar = (Calendar) calendar.clone();
            setupCalendar.set(Calendar.DAY_OF_MONTH, 1);
            int first = setupCalendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < (first - 1); i++) {
                days.add(new JLabel(""));
            }
            for (int i = 1; i <= setupCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                final int day = i;
                final JLabel label = new JLabel(String.valueOf(day));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setForeground(foreground);
                label.addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent e) {
                    }

                    public void mouseClicked(MouseEvent e) {
                    }

                    public void mouseReleased(MouseEvent e) {
                        label.setOpaque(false);
                        label.setBackground(background);
                        label.setForeground(foreground);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        comboBox.setSelectedItem(dateFormat.format(calendar.getTime()));
			    // hide();
                        // hide is called with setSelectedItem() ... removeAll()
                        comboBox.requestFocus();
                    }

                    public void mouseEntered(MouseEvent e) {
                        label.setOpaque(true);
                        label.setBackground(selectedBackground);
                        label.setForeground(selectedForeground);
                    }

                    public void mouseExited(MouseEvent e) {
                        label.setOpaque(false);
                        label.setBackground(background);
                        label.setForeground(foreground);
                    }
                });

                days.add(label);
            }

            popup.add(BorderLayout.CENTER, days);
            popup.pack();
        }
    }
}

class AutoSuggestor {

    private final JTextField textField;
    private final Window container;
    private JPanel suggestionsPanel;
    private JWindow autoSuggestionPopUpWindow;
    private String typedWord;
    private final ArrayList<String> dictionary = new ArrayList<>();
    private int currentIndexOfSpace, tW, tH;
    private DocumentListener documentListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void removeUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }

        @Override
        public void changedUpdate(DocumentEvent de) {
            checkForAndShowSuggestions();
        }
    };
    private final Color suggestionsTextColor;
    private final Color suggestionFocusedColor;

    public AutoSuggestor(JTextField textField, Window mainWindow, ArrayList<String> words, Color popUpBackground, Color textColor, Color suggestionFocusedColor, float opacity) {
        this.textField = textField;
        this.suggestionsTextColor = textColor;
        this.container = mainWindow;
        this.suggestionFocusedColor = suggestionFocusedColor;
        this.textField.getDocument().addDocumentListener(documentListener);
        setDictionary(words);
        typedWord = "";
        currentIndexOfSpace = 0;
        tW = 0;
        tH = 0;
        autoSuggestionPopUpWindow = new JWindow(mainWindow);
        autoSuggestionPopUpWindow.setOpacity(opacity);
        suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new GridLayout(0, 1));
        suggestionsPanel.setBackground(popUpBackground);
        addKeyBindingToRequestFocusInPopUpWindow();
    }

    private void addKeyBindingToRequestFocusInPopUpWindow() {
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "Up released");
        textField.getActionMap().put("Down released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {//focuses the first label on popwindow
                for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        ///////
        textField.getActionMap().put("Up released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {//focuses the first label on popwindow
                for (int i = suggestionsPanel.getComponentCount() - 1; i >= 0; i--) {
                    if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                        ((SuggestionLabel) suggestionsPanel.getComponent(i)).setFocused(true);
                        autoSuggestionPopUpWindow.toFront();
                        autoSuggestionPopUpWindow.requestFocusInWindow();
                        suggestionsPanel.requestFocusInWindow();
                        suggestionsPanel.getComponent(i).requestFocusInWindow();
                        break;
                    }
                }
            }
        });
        suggestionsPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
        suggestionsPanel.getActionMap().put("Down released", new AbstractAction() {
            int lastFocusableIndex = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {//allows scrolling of labels in pop window (I know very hacky for now :))

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = 0; i < max; i++) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = 0;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex <= i) {
                            if (i < max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
        suggestionsPanel.getActionMap().put("Up released", new AbstractAction() {
            int lastFocusableIndex = 0;

            @Override
            public void actionPerformed(ActionEvent ae) {//allows scrolling of labels in pop window (I know very hacky for now :))

                ArrayList<SuggestionLabel> sls = getAddedSuggestionLabels();
                int max = sls.size();

                if (max > 1) {//more than 1 suggestion
                    for (int i = max; i >= 0; i--) {
                        SuggestionLabel sl = sls.get(i);
                        if (sl.isFocused()) {
                            if (lastFocusableIndex == max - 1) {
                                lastFocusableIndex = max - i;
                                sl.setFocused(false);
                                autoSuggestionPopUpWindow.setVisible(false);
                                setFocusToTextField();
                                checkForAndShowSuggestions();//fire method as if document listener change occured and fired it

                            } else {
                                sl.setFocused(false);
                                lastFocusableIndex = i;
                            }
                        } else if (lastFocusableIndex >= i) {
                            if (i > max) {
                                sl.setFocused(true);
                                autoSuggestionPopUpWindow.toFront();
                                autoSuggestionPopUpWindow.requestFocusInWindow();
                                suggestionsPanel.requestFocusInWindow();
                                suggestionsPanel.getComponent(i).requestFocusInWindow();
                                lastFocusableIndex = i;
                                break;
                            }
                        }
                    }
                } else {//only a single suggestion was given
                    autoSuggestionPopUpWindow.setVisible(false);
                    setFocusToTextField();
                    checkForAndShowSuggestions();//fire method as if document listener change occured and fired it
                }
            }
        });
    }

    private void setFocusToTextField() {
        container.toFront();
        container.requestFocusInWindow();
        textField.requestFocusInWindow();
    }

    public ArrayList<SuggestionLabel> getAddedSuggestionLabels() {
        ArrayList<SuggestionLabel> sls = new ArrayList<>();
        for (int i = 0; i < suggestionsPanel.getComponentCount(); i++) {
            if (suggestionsPanel.getComponent(i) instanceof SuggestionLabel) {
                SuggestionLabel sl = (SuggestionLabel) suggestionsPanel.getComponent(i);
                sls.add(sl);
            }
        }
        return sls;
    }

    private void checkForAndShowSuggestions() {
        typedWord = getCurrentlyTypedWord();

        suggestionsPanel.removeAll();//remove previos words/jlabels that were added

        //used to calcualte size of JWindow as new Jlabels are added
        tW = 0;
        tH = 0;

        boolean added = wordTyped(typedWord);

        if (!added) {
            if (autoSuggestionPopUpWindow.isVisible()) {
                autoSuggestionPopUpWindow.setVisible(false);
            }
        } else {
            showPopUpWindow();
            setFocusToTextField();
        }
    }

    protected void addWordToSuggestions(String word) {
        SuggestionLabel suggestionLabel = new SuggestionLabel(word, suggestionFocusedColor, suggestionsTextColor, this);

        calculatePopUpWindowSize(suggestionLabel);

        suggestionsPanel.add(suggestionLabel);
    }

    public String getCurrentlyTypedWord() {//get newest word after last white spaceif any or the first word if no white spaces
        String text = textField.getText();
        String wordBeingTyped = "";
        if (text.contains(" ")) {
            int tmp = text.lastIndexOf(" ");
            if (tmp >= currentIndexOfSpace) {
                currentIndexOfSpace = tmp;
                wordBeingTyped = text.substring(text.lastIndexOf(" "));
            }
        } else {
            wordBeingTyped = text;
        }
        return wordBeingTyped.trim();
    }

    private void calculatePopUpWindowSize(JLabel label) {
        //so we can size the JWindow correctly
        if (tW < label.getPreferredSize().width) {
            tW = label.getPreferredSize().width;
        }
        tH += label.getPreferredSize().height;
    }

    private void showPopUpWindow() {
        autoSuggestionPopUpWindow.getContentPane().add(suggestionsPanel);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.setSize(tW, tH);
        autoSuggestionPopUpWindow.setVisible(true);

        int windowX = 0;
        int windowY = 0;
        COC coo = new COC("");
        //System.out.println(coo.detailPanel.getWidth());
        windowX = container.getX() + textField.getX() + 75;
        if (suggestionsPanel.getHeight() > autoSuggestionPopUpWindow.getMinimumSize().height) {
            System.out.println("panel >");
            //windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getMinimumSize().height;
            //windowY=KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getHeight()+200;
        } else {
            //Rectangle p=coo.fName.getBounds();
            //windowY=(int) (p.getY()+textField.getY())+autoSuggestionPopUpWindow.getHeight()+100;
            //System.out.println(p.getX() +" "+p.getY());
            windowY = container.getY() + textField.getY() + textField.getHeight() + autoSuggestionPopUpWindow.getHeight();
            System.out.println("windowY " + windowY + " \ncontainer.getY() " + container.getY()
                    + "\n textField.getY() " + textField.getY() + " \ntextField.getHeight() " + textField.getHeight() + " \nautoSuggestionPopUpWindow.getHeight() " + autoSuggestionPopUpWindow.getHeight());
        }
        //windowY=KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getHeight()+200;
        int yy=windowY=(int) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getLocation().getY();
        windowY=KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner().getHeight()+yy+155;
        autoSuggestionPopUpWindow.setLocation(windowX, windowY);
        autoSuggestionPopUpWindow.setMinimumSize(new Dimension(textField.getWidth(), 30));
        autoSuggestionPopUpWindow.revalidate();
        autoSuggestionPopUpWindow.repaint();

    }

    public void setDictionary(ArrayList<String> words) {
        dictionary.clear();
        if (words == null) {
            return;//so we can call constructor with null value for dictionary without exception thrown
        }
        for (String word : words) {
            dictionary.add(word);
        }
    }

    public JWindow getAutoSuggestionPopUpWindow() {
        return autoSuggestionPopUpWindow;
    }

    public Window getContainer() {
        return container;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void addToDictionary(String word) {
        dictionary.add(word);
    }

    boolean wordTyped(String typedWord) {

        if (typedWord.isEmpty()) {
            return false;
        }
        //System.out.println("Typed word: " + typedWord);

        boolean suggestionAdded = false;

        for (String word : dictionary) {//get words in the dictionary which we added
            boolean fullymatches = true;
            for (int i = 0; i < typedWord.length(); i++) {//each string in the word
                if (!typedWord.toLowerCase().startsWith(String.valueOf(word.toLowerCase().charAt(i)), i)) {//check for match
                    fullymatches = false;
                    break;
                }
            }
            if (fullymatches) {
                addWordToSuggestions(word);
                suggestionAdded = true;
            }
        }
        return suggestionAdded;
    }
}

class SuggestionLabel extends JLabel {

    private boolean focused = false;
    private final JWindow autoSuggestionsPopUpWindow;
    private final JTextField textField;
    private final AutoSuggestor autoSuggestor;
    private Color suggestionsTextColor, suggestionBorderColor;

    public SuggestionLabel(String string, final Color borderColor, Color suggestionsTextColor, AutoSuggestor autoSuggestor) {
        super(string);

        this.suggestionsTextColor = suggestionsTextColor;
        this.autoSuggestor = autoSuggestor;
        this.textField = autoSuggestor.getTextField();
        this.suggestionBorderColor = borderColor;
        this.autoSuggestionsPopUpWindow = autoSuggestor.getAutoSuggestionPopUpWindow();

        initComponent();
    }

    private void initComponent() {
        setFocusable(true);
        setForeground(suggestionsTextColor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                replaceWithSuggestedText();

                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "Enter released");
        getActionMap().put("Enter released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                replaceWithSuggestedText();
                autoSuggestionsPopUpWindow.setVisible(false);
            }
        });
    }

    public void setFocused(boolean focused) {
        if (focused) {
            setBorder(new LineBorder(suggestionBorderColor));
        } else {
            setBorder(null);
        }
        repaint();
        this.focused = focused;
    }

    public boolean isFocused() {
        return focused;
    }

    private void replaceWithSuggestedText() {
        String suggestedWord = getText();
        String text = textField.getText();
        String typedWord = autoSuggestor.getCurrentlyTypedWord();
        String t = text.substring(0, text.lastIndexOf(typedWord));
        String tmp = t + text.substring(text.lastIndexOf(typedWord)).replace(typedWord, suggestedWord);
        textField.setText(tmp);
    }
}
