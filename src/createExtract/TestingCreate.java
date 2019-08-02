package createExtract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bedatadriven.spss.SpssDataFileReader;
import com.bedatadriven.spss.SpssVariable;
import com.pmstation.spss.reader.SPSSReader;
import com.tableausoftware.TableauException;
import com.tableausoftware.common.Collation;
import com.tableausoftware.common.Type;
import com.tableausoftware.extract.Extract;
import com.tableausoftware.extract.ExtractAPI;
import com.tableausoftware.extract.Row;
import com.tableausoftware.extract.Table;
import com.tableausoftware.extract.TableDefinition;

import readerSPSS.ReadData;

public class TestingCreate {
	/**
     * -------------------------------------------------------------------------
     *  Create or Open Extract
     * -------------------------------------------------------------------------
     *  (NOTE: This function assumes that the Tableau SDK Extract API is initialized)
	 * @param data 
     */
    private static Extract createOrOpenExtract(
        String filename, ReadData data 
        
    )
    {
        Extract extract = null;
        Table table = null;
        try {
            //  Create Extract Object
            //  (NOTE: TabExtractCreate() opens an existing extract with the given
            //   filename if one exists or creates a new extract with the given filename
            //   if one does not)
            extract = new Extract( filename );

            //  Define Table Schema (If we are creating a new extract)
            //  (NOTE: in Tableau Data Engine, all tables must be named "Extract")
            if ( !extract.hasTable( "Extract" ) ) {
                TableDefinition schema = new TableDefinition();
                schema.setDefaultCollation( Collation.PT_BR );
                
                for (int i = 0; i<data.getColumns().length; i++) {
            		schema.addColumn(data.getColumns()[i], Type.UNICODE_STRING);
            	}
               
                
                table = extract.addTable( "Extract", schema );
                if ( table == null ) {
                    System.err.println( "A fatal error occured while creating the table" );
                    System.err.println( "Exiting now." );
                    System.exit( -1 );
                }
            }
        }
        catch ( TableauException e ) {
            System.err.println( "A fatal error occurred while creating the extract:" );
            System.err.println( e.getMessage() );
            System.err.println( "Printing stack trace now:" );
            e.printStackTrace( System.err );
            System.err.println( "Exiting now." );
            System.exit( -1 );
        }
        catch ( Throwable t ) {
            System.err.println( "An unknown error occured while creating the extract" );
            System.err.println( "Printing stack trace now:" );
            t.printStackTrace( System.err );
            System.err.println( "Exiting now." );
            System.exit( -1 );
        }

        return extract;
    }

    /**
     * -------------------------------------------------------------------------
     *  Populate Extract
     * -------------------------------------------------------------------------
     *  (NOTE: This function assumes that the Tableau SDK Extract API is initialized)
     */
    private static void populateExtract(
            Extract extract,
            ReadData data
        )
        {
            try {
                //  Get Schema
                Table table = extract.openTable( "Extract" );
                TableDefinition tableDef = table.getTableDefinition();
                SPSSReader reader = data.getReader();
                Iterator it = reader.getVariables().iterator();
                while(reader.read()) {
                	Row row = new Row( tableDef );
                	 for (int i = 0; i < reader.getVariables().size(); i++) {
           	          Object res = reader.getValue(i);
           	          String string = reader.getValue(i).toString() + "";
           	          System.out.println(i + " - " + reader.getVariables().get(i) + " - " + string);
           	          if (res instanceof String) {
           	            row.setString(i, string);
           	          } else if (res instanceof Date) {
           	            row.setString(i, (string) + "");
           	          } else
           	            row.setString(i, string);
           	        }
                	table.insert(row);
                }
                
               
            }
            catch ( TableauException e ) {
                System.err.println( "A fatal error occurred while populating the extract:" );
                System.err.println( e.getMessage() );
                System.err.println( "Printing stack trace now:" );
                e.printStackTrace( System.err );
                System.err.println( "Exiting now." );
                System.exit( -1 );
                }
            catch ( Throwable t ) {
                System.err.println( "An unknown error occured while populating the extract" );
                System.err.println( "Printing stack trace now:" );
                t.printStackTrace( System.err );
                System.err.println( "Exiting now." );
                System.exit( -1 );
            }
        }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadData data = ReadData.from("C:\\Users\\gabriel.pehls\\Dropbox\\UNP\\UNP_Satisfaction_2019.sav"); 
		try {
            //  Initialize the Tableau Extract API
            ExtractAPI.initialize();

            //  Create or Expand the Extract
            Extract extract = createOrOpenExtract( "C:\\Users\\gabriel.pehls\\Desktop\\teste java\\Extract.tde", data);
            populateExtract( extract, data );

            //  Flush the Extract to Disk
            extract.close();

            // Close the Tableau Extract API
            ExtractAPI.cleanup();
        }
        catch ( TableauException e ) {
            System.err.println( "A fatal error occurred while opening or closing the Extract API:" );
            System.err.println( e.getMessage() );
            System.err.println( "Printing stack trace now:" );
            e.printStackTrace( System.err );
            System.err.println( "Exiting now." );
            System.exit( -1 );
        }
        catch ( Throwable t ) {
            System.err.println( "An unknown error occured while opening or closing the Extract API:" );
            System.err.println( "Printing stack trace now:" );
            t.printStackTrace( System.err );
            System.err.println( "Exiting now." );
            System.exit( -1 );
        }
	}

}
