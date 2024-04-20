package agoloAutomationTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadXMLFile {
	
	public static String readXMLFile(String filePath) throws IOException {
		String line;
		String xmlString;
		File file = new File(filePath);


		InputStream data = new FileInputStream(file);

		StringBuilder builder = new StringBuilder();


		BufferedReader reader = new BufferedReader(new InputStreamReader(data));  
		while ((line = reader.readLine()) != null) {  
			builder.append(line);  
		}  
		reader.close();  
		xmlString = builder.toString();
		
		return xmlString;
		
		
		
	}

}
