package coc;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class TableToolTip {

    private final Map<String, URL> fileMap;
    String sqlQuery;
    File temp;
    JTable table;
    public TableToolTip(String arg) {
        sqlQuery=arg;
        fileMap = new HashMap<>();
        JTable table = createTable();
    }
    public TableToolTip() {
        sqlQuery="select * from trainee";
        fileMap = new HashMap<>();
        table = createTable();
    }

    public JTable createTable() {
        JTable table = new JTable(createModel()) 
        {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) 
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) 
                {
                    JComponent jc = (JComponent) c;
                    URL url = fileMap.get((String) getValueAt(row, 0));
                    String html = "<html><body>"+ "<img src='"+ url+ "' width=150 height=150> ";
                    //jc.setToolTipText(html + "<br/>"+ getValueAt(row, column).toString()+ ":  row, col (" + row + ", " + column + ")"+ "</body></html>");
                    jc.setToolTipText(html + "<br/>"+ getValueAt(row, 1).toString()+" "+ getValueAt(row, 2).toString() + "</body></html>");
                }
                return c;
            }
        };
        return table;
    }
    private DefaultTableModel createModel() {
        DefaultTableModel model = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            //Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/trial", "root", "blue");
            COC coc=new COC("");
            coc.connectToDB(coc.url, coc.username, coc.password);
            PreparedStatement ps = coc.con.prepareStatement(sqlQuery);
            //PreparedStatement ps = conn.prepareStatement("select * from trainee");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();
            String c1 = rsMeta.getColumnName(1);String c10 = rsMeta.getColumnName(10);
            String c2 = rsMeta.getColumnName(2);String c11 = rsMeta.getColumnName(11);
            String c3 = rsMeta.getColumnName(3);String c12 = rsMeta.getColumnName(12);
            String c4 = rsMeta.getColumnName(4);String c13 = rsMeta.getColumnName(13);
            String c5 = rsMeta.getColumnName(5);String c14 = rsMeta.getColumnName(14);
            String c6 = rsMeta.getColumnName(6);String c15 = rsMeta.getColumnName(15);
            String c7 = rsMeta.getColumnName(7);String c16 = rsMeta.getColumnName(16);
            String c8 = rsMeta.getColumnName(8);
            String c9 = rsMeta.getColumnName(9);
            

            model = new DefaultTableModel(new String[]{"ID","Name","Father","G.Father",c5,c6,"Occ. ID","Field Of Study","Level","Classification","Industry","Center","Training Provider","Competed","Issued","Phone"}, 0);

            while (rs.next()) {
                String tid = rs.getString("traineeID");//+"asnake ayele";
                String fName = rs.getString("fName");
                String mName = rs.getString("mName");
                String lName = rs.getString("lName");
                String D_O_G = rs.getString("D_O_G");
                String sex = rs.getString("sex");
                String occupationID = rs.getString("occupationID");
                String fieldOfStudy = rs.getString("fieldOfStudy");
                String level = rs.getString("level");
                String classification = rs.getString("classification");
                String industryName = rs.getString("industryName");
                String assessmentCenter = rs.getString("assessmentCenter");
                String trainingProvider = rs.getString("trainingProvider");
                String dateOfCompetation = rs.getString("dateOfCompetation");
                String dateOfIssue = rs.getString("dateOfIssue");
                String contactNo = rs.getString("contactNo");
                //String managedBy = rs.getString("managedBy");
                model.addRow(new Object[]{tid,fName,mName,lName,D_O_G,sex,occupationID,fieldOfStudy,level,classification,
                    industryName,assessmentCenter,trainingProvider,dateOfCompetation,dateOfIssue,contactNo});
                temp = File.createTempFile(tid, ".png");
                Blob blob = rs.getBlob("photo");
                byte[] bytes;                
                if(blob==null)
                {
                    File f=new File("src/coc/unavailable.jpg");
                    bytes=Files.readAllBytes(f.toPath());                    
                }
                else 
                {
                    int blobLength = (int) blob.length();
                    bytes = blob.getBytes(1, blobLength);                    
                }
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                ImageIO.write(img, "png", temp);
                URL fileURL = temp.toURI().toURL();
                fileMap.put(tid, fileURL);
                fileMap.put(fName, fileURL);
                fileMap.put(mName, fileURL);
                fileMap.put(lName, fileURL);
                fileMap.put(D_O_G, fileURL);
                fileMap.put(sex, fileURL);
                fileMap.put(occupationID, fileURL);fileMap.put(fieldOfStudy, fileURL);
                fileMap.put(level, fileURL);fileMap.put(classification, fileURL);
                fileMap.put(industryName, fileURL);fileMap.put(assessmentCenter, fileURL);
                fileMap.put(trainingProvider, fileURL );fileMap.put(dateOfCompetation, fileURL);
                fileMap.put(dateOfIssue, fileURL);fileMap.put(contactNo, fileURL);
                temp.deleteOnExit();
            }

        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "SQLException", JOptionPane.ERROR_MESSAGE);
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "IOException", JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            
        }
        return model;
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TableToolTip();
            }
        });
    }*/
}