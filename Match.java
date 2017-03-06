package identificationjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.portable.ValueBase;

public class Match {
	
	String id = ""; 
	String consensus = "";
	
	TreeMap<String,String> sequences = new TreeMap<>();
	
	

	public Match(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConsensus() {
		return consensus;
	}

	public void setConsensus(String consensus) {
		this.consensus = consensus;
	}
	
	public void addSequence(String organism, String sequence)
	{
		sequences.put(organism, sequence);
	}
	
	public boolean inOtherOrg(){
		System.out.println("inother");
		
		for (String organism: sequences.keySet())
		{
			String sequence = sequences.get(organism);
			
			final File folder = new File("inputFastasOneLine");
			File[] filesList = folder.listFiles();
			Arrays.sort(filesList);
			for (final File fileEntry : filesList) {
			if (organism.equals(fileEntry.getName())){

				
				
				BufferedReader br;
				try {
					br = new BufferedReader(new FileReader(fileEntry));
				

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
	                	
		                if (value.contains(sequence))
		                {
		                	return true;
		                }

	                }
	                
	                
	                
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			}
			
		}
		
		return false;
	}
	
	public void print() {
		System.out.println(id);
		for (String organism: sequences.keySet())
		{
			System.out.println("match "+organism+": "+sequences.get(organism));
		}
		
	}
	

}
