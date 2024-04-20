package agoloAutomationTask;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;

public class AgoloXMLOperations {

	public static void main(String args[]) throws IOException, SAXException, ParserConfigurationException, TransformerException, ParseException { 

		String filePath = "WebSites.xml";

		WebSite webSite = DeserializeXML(filePath);

		webSite.getURL();
		webSite.getTitle();
		webSite.getDescription();
		webSite.getCreatedDate();

		AddNewWebSite(filePath,"https://www.google.com", "Google" ,"Google Search Engine", "10/01/2019 6:41:56 AM");

		JSONObject json = convertXMLToJson(filePath);

		printWebSitesInfo(json);




	}

	private static void printWebSitesInfo(JSONObject json) throws ParseException {

		JSONArray webSites = json.getJSONObject("Websites").getJSONArray("Website");



		for (int i =0 ; i<webSites.length() ; i++) {

			String CreatedDate= webSites.getJSONObject(i).getString("CreatedDate");

			String [] DateElements= CreatedDate.split(" ");

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");   

			Date date1 = sdf.parse("09/01/2019");

			if (sdf.parse(DateElements[0]).compareTo(date1) > 0) {

				System.out.println("Website " + (i+1));
				System.out.println("URL: " +webSites.getJSONObject(i).getString("URL") );
				System.out.println("Title: " +webSites.getJSONObject(i).getString("Tile") );
				System.out.println("Description: " +webSites.getJSONObject(i).getString("Description") );
				System.out.println("Created Date: " +webSites.getJSONObject(i).getString("CreatedDate") );



			}


		}

	}

	private static JSONObject convertXMLToJson(String filePath) throws IOException {
		String xmlString;
		xmlString = ReadXMLFile.readXMLFile(filePath);
		JSONObject json = XML.toJSONObject(xmlString);
		return json;

	}

	private static void AddNewWebSite(String filePath, String URL, String Title , String Description, String CreatedDate) throws ParserConfigurationException, SAXException, IOException, TransformerFactoryConfigurationError, TransformerException {

		try {  


			Element nodeElement;
			Element textElement1;
			Element textElement2;
			Element textElement3;
			Element textElement4;

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(new File(filePath));

			Element documentElement = document.getDocumentElement();

			nodeElement= document.createElement("Website");

			textElement1 = document.createElement("URL");
			textElement1.setTextContent(URL);
			textElement2 = document.createElement("Tile");
			textElement2.setTextContent(Title);
			textElement3 = document.createElement("Description");
			textElement3.setTextContent(Description);
			textElement4 = document.createElement("CreatedDate");
			textElement4.setTextContent(CreatedDate);

			nodeElement.appendChild (textElement1);
			nodeElement.appendChild (textElement2);
			nodeElement.appendChild (textElement3);
			nodeElement.appendChild (textElement4);
			documentElement.appendChild(nodeElement);

			document.replaceChild(documentElement, documentElement);

			Transformer tFormer =
					TransformerFactory.newInstance().newTransformer();
			tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
			Source source = new DOMSource(document);
			Result result = new StreamResult(new File(filePath));
			tFormer.transform(source, result);


		}


		catch (JsonMappingException e) {  

			e.printStackTrace();  
		} catch (JsonProcessingException e) {  

			e.printStackTrace();  
		}

	}

	private static WebSite DeserializeXML(String filePath) throws IOException {

		String xmlString;

		XmlMapper xmlMap = new XmlMapper();

		xmlMap.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		xmlString = ReadXMLFile.readXMLFile(filePath);

		WebSite webSite = xmlMap.readValue(xmlString, WebSite.class);

		return webSite;

	}


}


class WebSite  {   




	@JsonProperty("Tile")  
	private String Tile;  

	@JsonProperty("URL")  
	private String URL;

	@JsonProperty("Description")  
	private String Description;

	@JsonProperty("CreatedDate")  
	private String CreatedDate;

	WebSite(){

	}


	WebSite(String URL,String Tile,String Description,String CreatedDate){
		this.URL= URL;
		this.Tile = Tile;  	
		this.Description = Description;
		this.CreatedDate = CreatedDate;

	}

	public String getURL() {

		return URL;

	}


	public String getCreatedDate() {

		return CreatedDate;

	}

	public String getDescription() {

		return Description;

	}

	public String getTitle() {

		return Tile;

	}



}  
