import static com.lowagie.text.pdf.PdfName.T;
import static com.lowagie.text.pdf.PdfPKCS7.X509Name.T;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.sf.dynamicreports.examples.Templates;
import static net.sf.dynamicreports.examples.Templates.boldCenteredStyle;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.ListType;
import net.sf.dynamicreports.report.constant.Orientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.dynamicreports.report.exception.DRException;

public class SampleReport {
static Connection connection = null;
static TextColumnBuilder<String> mName = col.column("mName", type.stringType());
                static TextColumnBuilder<String> fName = col.column("fName", type.stringType());
                static TextColumnBuilder<String> lName = col.column("lName", type.stringType());
	public static void main(String[] args) throws DRException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trial","root", "blue");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		//JasperReportBuilder report = DynamicReports.report();
		 //create a new report
              SampleReport sr=new  SampleReport();
              sr.build();
	}
        private void build() throws DRException
        {
                //TextColumnBuilder<String> countryColumn = col.column("Full Name", "fName", type.stringType());
                
                TextColumnBuilder<String> fullName = col.column("Full Name", new ExpressionColumn());
                TextColumnBuilder<String> xx = col.column("lName","lName",DataTypes.stringType());
                JasperReportBuilder report = DynamicReports.report();
                report
                        .columns
                        (
                            col.column("Id", "traineeID", DataTypes.stringType()),
                                fullName
                        )
                        //.columnGrid(ListType.HORIZONTAL)
                        //.columns
                        //(
                                //col.column("Id", "traineeID", DataTypes.stringType()),
                                //fullName,
                                //col.column("name", "fName", DataTypes.stringType()),
                                //col.column("father", "mName", DataTypes.stringType()),
                                //col.column("lName", "lName", DataTypes.stringType()),
                                //col.column("occid", "occupationid", DataTypes.stringType()),
                                //col.column("field", "fieldofstudy", DataTypes.stringType())
                        //)
                        
                        .title(Components.text("MySampleReport").setHorizontalAlignment(HorizontalAlignment.CENTER))
                        .pageFooter(Components.pageXofY().setStyle(stl.style(boldCenteredStyle).setTopBorder(stl.penDotted())))
                        .pageFooter(Components.currentDate())
                        .pageHeader(Components.line())
                        .pageFooter(Components.roundRectangle())
                        .setTitleStyle(boldCenteredStyle)
                        .setPrintOrder(Orientation.HORIZONTAL)
                        .setPageFormat(PageType.A4)
                        .setTemplate(Templates.reportTemplate)
                        //.addBackground(Components.image("src/coc/unavailable.jpg"))
                        .setTitleBackgroundComponent(Components.image("src/coc/noImage.jpg"))
                        //.addFirstPageImageBanner("pathToImage/firstPageBanner.jpg",new Integer(50), new Integer(50),ImageBanner.ALIGN_LEFT).addImageBanner("pathToImage/allPagesBanner.jpg", new Integer(30), new Integer(30), ImageBanner.ALIGN_RIGHT)
                        .setDataSource("SELECT * FROM trainee", connection);
                        try
                        {
                            report.show(true);
                        }
                        catch(DRException e)
                        {
                            e.printStackTrace();
                        }
                //report().show(true);
        }
        private static class ExpressionColumn extends AbstractSimpleExpression<String>
        {
            @Override
            public String evaluate(ReportParameters rp) 
            {
                return rp.getValue(fName)+" "+
                        rp.getValue(mName)+" "+rp.getValue(lName);
            }            
        }
}