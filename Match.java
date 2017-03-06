package identificationjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
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
	
	public boolean inOtherOrg() throws FileNotFoundException{
		System.out.println("inother");
		
		for (String organism: sequences.keySet())
		{
			String sequence = sequences.get(organism);
			String cleanSeq = sequence.replaceAll("-", "");
			
			final File folder = new File("inputFastasOneLine");
			File[] filesList = folder.listFiles();
			Arrays.sort(filesList);
			for (final File fileEntry : filesList) {
			
			Scanner scanner = new Scanner(fileEntry);
			String logdata = scanner.useDelimiter("\\Z").next();
			final String needle = cleanSeq;
			int index = 0;
			while (index < logdata.length() && (index = logdata.indexOf(needle, index)) >= 0) {
				scanner.close();
				return true;
			}
			scanner.close();

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
