import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

class ReadExcelFile{
	//1-this class reads an excel(csv) file into an arraylist of arrayStrings.
	
//String fileLocation="Scores.csv";
public List<String[]> readCSV(String fileLocation) { 

		
	List<String[]> allData = new ArrayList<>();

	try { 
		// Create csvReader object and skip first line
	        FileReader filereader = new FileReader(fileLocation);
	        CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
	    // Read all data in the csv and set each line in string[i]= 11;22;33
	        allData = csvReader.readAll();
	    // close the csv file.
	        csvReader.close();
	    } 
	    catch (Exception e) { 
	        e.printStackTrace(); 
	    }
	    return allData;
	}
	
}