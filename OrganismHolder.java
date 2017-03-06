package identificationjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrganismHolder {
	   private static OrganismHolder singleton = new OrganismHolder( );
	   private OrganismHolder() {
		// TODO Auto-generated constructor stub
	   }
	   public static OrganismHolder getInstance( ) {
		      return singleton;
		   }
		TreeMap<String,Organism> organisms = new TreeMap<>();
		TreeMap<String, String> oneLineFastas = new TreeMap<>();
		
		public TreeMap<String,Organism> getOrganisms() {
			return organisms;
		}
		public void setOrganisms(TreeMap<String,Organism> organisms) {
			this.organisms = organisms;
		}
		
		public void populateOnelineFasta()
		{
			final File folder = new File("inputFastasOneLine");
			File[] filesList = folder.listFiles();
			Arrays.sort(filesList);
			for (final File fileEntry : filesList) {
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(fileEntry));
				

	                String line;
	                while ((line = br.readLine()) != null) {
	                   //process the line.
	                	//System.out.println(line);
	                	String value="";
	                	Pattern p = Pattern.compile("\\>(.*?)\\<");
	                	Matcher m = p.matcher(line);
	                	while(m.find())
	                	{
	                		value = m.group(1); //is your string. do what you want
	                	}
	                	
		                oneLineFastas.put(fileEntry.getName(), value);

	                }
	                
	                br.close();
	                
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		public TreeMap<String, String> getOneLineFastas() {
			return oneLineFastas;
		}
		public void setOneLineFastas(TreeMap<String, String> oneLineFastas) {
			this.oneLineFastas = oneLineFastas;
		}

		
	   



}
