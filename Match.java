package identificationjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
	
	public boolean inOtherOrg(){
		System.out.println("inother");
		
		for (String organism: sequences.keySet())
		{
			String sequence = sequences.get(organism);
			String cleanSeq = sequence.replaceAll("-", "");
			
			final File folder = new File("inputFastasOneLine");
			File[] filesList = folder.listFiles();
			Arrays.sort(filesList);
			for (final File fileEntry : filesList) {
			if (!fileEntry.getName().equals(organism)){
				Scanner scanner;
				try {
					scanner = new Scanner(fileEntry);
				
				String logdata = scanner.useDelimiter("\\Z").next();
				final String needle = cleanSeq;
				int index = 0;
				while (index < logdata.length() && (index = logdata.indexOf(needle, index)) >= 0) {
					scanner.close();
					return true;
				}
				scanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}
			
		}
		
		return false;
	}
		
	
	public void align() {
		
		File directory = new File("matches");
		
		if (! directory.exists()){
	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		try {
			PrintWriter writer = new PrintWriter("matches/"+id);
			StringBuilder stringBuilder = new StringBuilder();
			
			System.out.println(id);
			for (String organism: sequences.keySet())
			{
				System.out.println("match "+organism+": "+sequences.get(organism));
				stringBuilder.append(">");
				stringBuilder.append(organism);
			    stringBuilder.append(System.getProperty("line.separator"));
			    stringBuilder.append(sequences.get(organism));
			    stringBuilder.append(System.getProperty("line.separator"));
			}
			
			writer.print(stringBuilder.toString());
			writer.close();
			String[] cmd = {"/fslhome/jblack33/bin/mafft","--globalpair","--maxiterate 1000","matches/" + id,"> matches/Out"+id};

			//String cmd = "/fslhome/jblack33/bin/mafft --globalpair --maxiterate 1000 matches/" + id + " > matches/Out"+id;
			System.out.println(cmd);
			Process p = Runtime.getRuntime().exec(cmd);
			
			BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new 
				     InputStreamReader(p.getErrorStream()));

				// read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				String s = null;
				while ((s = stdInput.readLine()) != null) {
				    System.out.println(s);
				}

				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
				    System.out.println(s);
				}
			
			
			p.waitFor();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	

}
