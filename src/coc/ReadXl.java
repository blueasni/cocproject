package coc;
import java.io.*;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ReadXl 
{
    public String xlData[][],xL[][];
    public ReadXl(JFrame f) throws FileNotFoundException, IOException
    {
        JFileChooser multiOpen=new JFileChooser();            
            int returnVal = multiOpen.showDialog(f,"Open Ms-Excell file");
            if(returnVal==JFileChooser.APPROVE_OPTION)
            {
                File xlsxFile = multiOpen.getSelectedFile();
                FileInputStream xl = new FileInputStream(xlsxFile);
                XSSFWorkbook myWorkBook = new XSSFWorkbook (xl);
                XSSFSheet mySheet = myWorkBook.getSheetAt(0);
                Iterator<Row> rowIterator = mySheet.iterator();
                int colCount=15,i=0,j=0;
                xlData=new String[mySheet.getPhysicalNumberOfRows()][colCount];
                xL=new String[mySheet.getPhysicalNumberOfRows()][colCount];
                for(Row row : mySheet) 
                {
                    for(int cn=0; cn<row.getLastCellNum(); cn++) 
                    {// If the cell is missing from the file, generate a blank one (Works by specifying a MissingCellPolicy)
                        Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
                        xlData[i][cn]=cell.toString();
                        //System.out.println("value of xlData ["+i+"]["+cn+"] "+xlData[i][cn+1]);                        
                    }
                    i++;
                 }            
                for(int a=0;a<xlData.length;a++)
                {for(int b=0;b<xlData[a].length;b++)
                    {
                        xL[a][0]=xlData[a][5];
                    }
                }
                for(int a=0;a<xlData.length;a++)
                {for(int b=1;b<xlData[a].length;b++)
                    {
                        xL[a][b]=xlData[a][b-1];
                    }
                }
                xlData=xL.clone();
            }
            else return;
    }
    public String[][] getData()
    {
        return xlData;
    }
    public static void main(String arg[]) throws IOException
    {
        ReadXl rx=new ReadXl(new JFrame());
        String as[][]=rx.getData();
        for(int i=0;i<as.length;i++)
        {
            for(int j=0;j<as[i].length;j++)
            {
                //System.out.print(as[i][j]+"\t");
            }
            //System.out.println();
        }
    }
}
