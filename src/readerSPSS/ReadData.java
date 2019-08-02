package readerSPSS;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.pmstation.spss.reader.SPSSReader;
import com.pmstation.spss.variable.SPSSVariable;

public class ReadData {
	static String file;
	String [] columns;
	SPSSReader reader;
	public ReadData(String file) {
		this.file = file;
		this.reader = SavRead(); 
	}
	public static ReadData from(String file) {
		// TODO Auto-generated method stub
		return new ReadData(file);
	}
	public SPSSReader getReader () {
		
		return reader;
	}
	public String[] getColumns() {
		// TODO Auto-generated method stub
		return columns;
	}
	private SPSSReader SavRead () {
		try {
			reader = new SPSSReader(file, "utf-8");
			Iterator it = reader.getVariables().iterator();
			columns = new String[reader.getNumberOfVariables()];
			for(int i = 0; i < columns.length; i++) {
			    SPSSVariable var = (SPSSVariable) reader.getVariables().get(i);
			    columns[i] = var.getLegacyName();			    
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reader;

		
	}
	

}
