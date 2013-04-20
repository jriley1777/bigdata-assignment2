package flights;

import java.io.*;
import java.util.Hashtable;
import java.util.Arrays;
public class FlightRead {

	/**
	* @param args
	*/
	public static void main(String[] args) {
		        
		for (int month = 1; month < 13; month++) {
				// The name of the file to open.
				String fileName = "C:/Users/Riles/FlightData/On_Time_On_Time_Performance_2012_"
						+ 1 + ".csv"; //loop our file names in the directory
				String outFileName = "AllFlights.txt";

		        // This will reference one line at a time
		        String line = null;
		        
		      //Create a hashtable for the columns
	            Hashtable <String, Integer> headerhash = new Hashtable <String, Integer>(); //create a dictionary
	            String[] columnNames = new String[] {"Year","Quarter","Month","DayofMonth","DayofWeek",
            			"UniqueCarrier","Origin","OriginCityName","OriginState","Dest","DestCiyName",
            			"DestState","CRSDeptTime","DepTime","DepDelay","DepDelayMinutes","DepDel15",
            			"DepartureDelayGroups","TaxiOut","TaxiIn","ArrDelay","ArrDelayMinutes","ArrDel15",
            			"Cancelled","ActualElapsedTime","AirTime","Distance"};
	            for(int i=0; i<columnNames.length; i++){
	            	headerhash.put(columnNames[i], 1);//the dictionary will look akin to {"Year":1,"Quarter:2, etc.}
	            }
	            System.out.println(headerhash);

		        try {
		            // FileReader reads text files in the default encoding.
		            FileReader fileReader = 
		                new FileReader(fileName);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);
		        	
		            FileWriter fileWriter = 
		            		new FileWriter(outFileName, month != 1);
		            
		        	BufferedWriter bufferedWriter = 
		        			new BufferedWriter(fileWriter);
		            
		        	// Locate the desired headings' indexes
		            String[] titleTemp = bufferedReader.readLine()
		            		.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		            int[] columnIndex = new int[headerhash.size()];
		            for (int i = 0, j = 0; i < titleTemp.length; i++) {
		            	if (headerhash.containsKey(titleTemp[i])) {
		            		columnIndex[j] = i;
		            		j++;
		            	}
		            }
		            int dataLength = columnIndex.length;

		            while ((line = bufferedReader.readLine()) != null) {
		            	String[] data = line
		            			.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		            	String[] dataclean = new String[dataLength];
		            	for (int i = 0; i < dataLength; i++) {
		            		dataclean[i] = data[columnIndex[i]];
		            	}

		            	String datacleanstring = Arrays.toString(dataclean);
		            	datacleanstring = datacleanstring.replaceAll("\\[", "")
		            		.replaceAll("\\]", "");
		            	bufferedWriter.write(datacleanstring);
		            	bufferedWriter.newLine();
		            	System.out.println(datacleanstring);

		            }
		            bufferedReader.close();
		            bufferedWriter.close();
		        }    
		        catch(FileNotFoundException ex) {
		            System.out.println(
		                "Unable to open file '" + 
		                fileName + "'");				
		        }
		        catch(IOException ex) {
		            System.out.println(
		                "Error reading file '" 
		                + fileName + "'");					
		            // Or we could just do this: 
		            // ex.printStackTrace();
		        }
		}
	}
}


