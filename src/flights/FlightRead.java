package flights;

import java.io.*;
import java.util.Hashtable;
import java.util.Arrays;
public class FlightRead {

	/**
	* @param args
	*/
	public static void main(String[] args) {
		/******************
		//All credit to Robert and Benedict for the set up and implementation.
		*******************/
		
		//most of work here is some sensitivity testing, rebuilding with new variable names, 
		//and commentary working through the process, along with merging processes where I 
		//felt one approach was a bit more elegant.  
		
		//Wanted to implement the use of hashtables
		
		//COLUMNS TO KEEP START
		
		//Create a hashtable for the columns
        Hashtable <String, Integer> headerhash = new Hashtable <String, Integer>(); //create a dictionary
        String[] colToKeep = new String[] 
        		
        		{"\"Year\"","\"Quarter\"","\"Month\"","\"DayofMonth\"",
        		 
        		"\"DayOfWeek\"","\"UniqueCarrier\"","\"Origin\"","\"OriginCityName\"","\"OriginState\"",
        		"\"Dest\"","\"DestCityName\"","\"DestState\"","\"CRSDepTime\"","\"DepTime\"","\"DepDelay\"",
        		"\"DepDelayMinutes\"","\"DepDel15\"","\"DepartureDelayGroups\"","\"TaxiOut\"","\"TaxiIn\"",
        		"\"ArrDelay\"","\"ArrDelayMinutes\"","\"ArrDel15\"","\"Cancelled\"","\"ActualElapsedTime\"",
        		"\"AirTime\"","\"Distance\""};

        //Since the implementation will be doing a check on the string values, 
        //we need to ensure that quotes are removed and make sure that the two string sets are exactly alike.
        //hence, below was initially tried and did not work - only returning the year
        
        		/*		
        		 {"Year","Quarter","Month","DayofMonth","DayofWeek",
    			"UniqueCarrier","Origin","OriginCityName","OriginState","Dest","DestCiyName",
    			"DestState","CRSDeptTime","DepTime","DepDelay","DepDelayMinutes","DepDel15",
    			"DepartureDelayGroups","TaxiOut","TaxiIn","ArrDelay","ArrDelayMinutes","ArrDel15",
    			"Cancelled","ActualElapsedTime","AirTime","Distance"};
        		 */
        
        
        //put our column headers that we want to keep into the dictionary
        for(int i=0; i<colToKeep.length; i++){
        	headerhash.put(colToKeep[i], 1);//the dictionary will look akin to {Year=1,Quarter=1, etc.}
        }
        System.out.println(headerhash);//print to check
        
        //COLUMNS TO KEEP END
		        
		for (int month = 1; month < 13; month++) {//file looping hack.  have come across some more elegant solutions but this is quick and easy.
				
				// The name of the file to open.
				String fileName = "C:/Users/Riles/FlightData/On_Time_On_Time_Performance_2012_"
						+ month + ".csv"; //loop our file names in the directory with month variable concatenated
				String outFile = "AllFlights.txt";

		        // This will reference one line at a time
		        String line = null;

		        try {
		        	//We're going to read and write in the same sequence here.
		        	
		            // FileReader reads text files in the default encoding.
		            FileReader fileReader = 
		                new FileReader(fileName);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);
		        	
		            FileWriter fileWriter = 
		            		new FileWriter(outFile, month != 1);
		            
		        	BufferedWriter bufferedWriter = 
		        			new BufferedWriter(fileWriter);
		        	
		        	//HEADING INDEXING START
		            
		        	// Locate the desired headings' indexes
		            String[] original = bufferedReader.readLine()
		            		.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); 
		            //questions here but realize this is doing some split on the comma while ignoring other possible
		            //delimiters contained in a particular column
		            //http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
		        	
		            //index length of columns to keep
		            int lengthKeepers = colToKeep.length;
		            //index length of original data
		            int lengthOriginal = original.length;
		            //initiate index for those to keep
		            int [] keeperIndex = new int[lengthKeepers];
		            int j=0;
		            //filling index - loop through the original index and pull values in keepers 
		            //that exist in both header lists
		            //i.e. get the headerhash element that equals original[i] for i in oglength
		            for (int i=0;i<lengthOriginal;i++){
			            if (headerhash.get(original[i])!=null){
			            	keeperIndex[j]=i;
			            	j++;
			            }
		            }
		            
		            //HEADING INDEXING END

		            while ((line = bufferedReader.readLine()) != null) {
		            	//DATA READ/WRITE START
		            	
		            	//take a line, and again split on delimiter, but ignore some other possible
		            	//delimiters that may be contained within strings or columns
		            	String[] startingDataLine = line
		            			.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		            	
		            	//this is the key to pulling only the relevant columns that we want to include
		            	String[] cleanerDataLine = new String[lengthKeepers]; //line the length of columns to keep index
		            	//loop through keeper index range, and set each line to contain only the values from the
		            	//original data line that contain the subset of the keeper group
		            	for (int i=0;i<lengthKeepers;i++){
		            		cleanerDataLine[i]=startingDataLine[keeperIndex[i]];
		            	}
		            	
		            	// From Benedict:  Sort of a 'hack', this is to concatenate all the tokens that 
		            	//we want with a ",". However there are square brackets at the beginning and end
		            	//so the next command is to remove the brackets
		            	String finalDataLine = Arrays.toString(cleanerDataLine);
		            	finalDataLine=finalDataLine.replaceAll("\\[","").replaceAll("\\]","");
		            	bufferedWriter.write(finalDataLine);//write our final cleaned line
		            	bufferedWriter.newLine();//jump to new line
		            	
		            	//DATA READ/WRITE END
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


