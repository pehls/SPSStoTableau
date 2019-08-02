package readerSPSS;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import com.pmstation.spss.reader.SPSSReader;
import com.pmstation.spss.variable.SPSSVariable;

public class Demo {

	  public static void main(String[] args) {
		  String args22 = "C:\\\\Users\\\\gabriel.pehls\\\\Dropbox\\\\UNP\\\\UNP_Satisfaction_2019.sav";
	    try {
	      TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	      Date start = new Date();
	      SPSSReader reader = new SPSSReader(args22, "utf-8");
	      Date parsed = new Date();
	      Iterator it = reader.getVariables().iterator();
	      reader.getNumberOfVariables()
	      while (it.hasNext()) {
	        SPSSVariable var = (SPSSVariable) it.next();
	        System.out.println(var.getLegacyName());
	      }
	      Date variablesPrinted = new Date();
	      while (reader.read()) {
	        for (int i = 0; i < reader.getVariables().size(); i++) {
	          Object res = reader.getValue(i);
	          if (res instanceof String) {
	            System.out.print(((String) res).trim() + " ");
	          } else if (res instanceof Date) {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss Z");
	            System.out.print(sdf.format(res) + " ");
	          } else
	            System.out.print(res + " ");
	        }
	        System.out.println();
	      }
	      Date finish = new Date();

	      System.out.println("Parsing dictionary: "
	        + makeTimeDiffString(start, parsed));
	      System.out.println("Printing out variables: "
	        + makeTimeDiffString(parsed, variablesPrinted));
	      System.out.println("Parsing and printing data: "
	        + makeTimeDiffString(variablesPrinted, finish));
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  private static String makeTimeDiffString(Date start, Date finish) {
	    return (finish.getTime() - start.getTime()) + " ms";
	  }

	  private static boolean checkOrShowUsage(String[] args) {
	    if (args.length != 1 || !new File(args[0]).exists()) {
	      System.out.println("Usage: java Demo2 ");
	      return false;
	    } else
	      return true;
	  }
}