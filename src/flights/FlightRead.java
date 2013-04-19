package flights;

import java.io.*;
public class FlightRead {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		        // The name of the file to open.
		        String fileName = "c:/flights/jan.csv";

		        // This will reference one line at a time
		        String line = null;

		        try {
		            // FileReader reads text files in the default encoding.
		            FileReader fileReader = 
		                new FileReader(fileName);

		            // Always wrap FileReader in BufferedReader.
		            BufferedReader bufferedReader = 
		                new BufferedReader(fileReader);

		            line = bufferedReader.readLine();
		            // Print out the headings
		            System.out.println(line);

		            line = bufferedReader.readLine();
		            // Print out the first line of data
		            System.out.println(line);

/*		            while((line = bufferedReader.readLine()) != null) {
		               // Determine the columns of interest here
		            	
		            	
		            
		            }
*/
		            // Always close files.
		            bufferedReader.close();			
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

