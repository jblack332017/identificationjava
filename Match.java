package identificationjava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.STRING;

import org.omg.CORBA.portable.ValueBase;
import org.w3c.dom.css.Counter;

public class Match {
	
	
	

	
	String id = ""; 
	String consensus = "";
	
	TreeMap<String,String> sequences = new TreeMap<>();
	ArrayList<String> rightPrimers = new ArrayList<>();
	ArrayList<String> leftPrimers = new ArrayList<>();
	
	

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
		directory = new File("matchesOut");
		
		if (! directory.exists()){
	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		directory = new File("consensus");
		
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
			//String[] cmd = {"/fslhome/jblack33/bin/mafft", "--globalpair --maxiterate 1000 matches/" + id," > matches/Out"+id};

	//String cmd = "/fslhome/jblack33/bin/mafft --globalpair --maxiterate 1000 matches/" + id;// + " > matches/Out"+id;
			String cmd = "./runMafft.sh "+id;
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
            File output = new File("consensus/"+id);
            System.out.println("consensus/"+id);
			BufferedReader br = new BufferedReader(new FileReader(output));

                String line;
                while ((line = br.readLine()) != null) {
                   //process the line.
                	//System.out.println(line);
                	
                	
                	line = line.replaceAll("\n", "");
                	System.out.println("value: "+line);
                	if (!line.contains(">")){
                		consensus += line;
                	}
                }
                br.close();
                System.out.println("consensus: "+consensus);
                
			
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
		
		
		
	runPrimer();
	}
	
	public void runPrimer(){
		File directory = new File("primer3Files");

		if (! directory.exists()){
	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
		
		try {
			PrintWriter writer = new PrintWriter("primer3Files/"+id, "UTF-8");
			StringBuilder stringBuilder = new StringBuilder();
			
			stringBuilder.append("SEQUENCE_ID="+id);
		    stringBuilder.append(System.getProperty("line.separator"));
			stringBuilder.append("SEQUENCE_TEMPLATE="+consensus);
		    stringBuilder.append(System.getProperty("line.separator"));
			stringBuilder.append("SEQUENCE_TARGET=37,21");
		    stringBuilder.append(System.getProperty("line.separator"));
			stringBuilder.append("PRIMER_TASK=pick_detection_primers");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_PICK_LEFT_PRIMER=1");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_PICK_INTERNAL_OLIGO=0");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_PICK_RIGHT_PRIMER=1");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_OPT_SIZE=18");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_MIN_SIZE=15");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_MAX_SIZE=21");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_MAX_NS_ACCEPTED=1");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_PRODUCT_SIZE_RANGE=75-100");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("P3_FILE_FLAG=1");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("SEQUENCE_INTERNAL_EXCLUDED_REGION=37,21");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_EXPLAIN_FLAG=1");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("PRIMER_THERMODYNAMIC_PARAMETERS_PATH=/fslhome/jblack33/software/primer3-2.3.7/src/primer3_config/");
		    stringBuilder.append(System.getProperty("line.separator"));
		    stringBuilder.append("=");
		    writer.print(stringBuilder.toString());
		    writer.close();
		    
		    String cmd = "./runPrimer3.sh "+id;
		    Process p = Runtime.getRuntime().exec(cmd);
		    
		    BufferedReader stdInput = new BufferedReader(new 
				     InputStreamReader(p.getInputStream()));

				BufferedReader stdError = new BufferedReader(new 
				     InputStreamReader(p.getErrorStream()));

				// read the output from the command
				System.out.println("Here is the standard output of the command:\n");
				String s = null;
				int counter = 0;
				while ((s = stdInput.readLine()) != null) {
				    System.out.println(s);
				    if (s.contains("PRIMER_LEFT_"+counter+"_SEQUENCE="))
				    {
				    	leftPrimers.add(s.split("=")[1]);
				    }
				    if (s.contains("PRIMER_RIGHT_"+counter+"_SEQUENCE="))
				    {
				    	rightPrimers.add(s.split("=")[1]);
				    	counter++;
				    }
				}

				// read any errors from the attempted command
				System.out.println("Here is the standard error of the command (if any):\n");
				while ((s = stdError.readLine()) != null) {
				    System.out.println(s);
				}
		    
		    
		    p.waitFor();
		    
		    for (int i=0; i<counter;i++)
		    {
		    	if (checkPrime(rightPrimers.get(i))&&checkPrime(leftPrimers.get(i)))
		    	{
		    		
		    		final File folder = new File("inputFastasOneLine");
		    		File[] filesList = folder.listFiles();
		    		Arrays.sort(filesList);
		    		for (final File fileEntry : filesList) {
		    		BufferedReader br = new BufferedReader(new FileReader(fileEntry));
		    		
		    		String line = br.readLine();
		    		br.close();
		    		System.out.println(line);
		    		System.out.println(leftPrimers.get(i));
		    		System.out.println(rightPrimers.get(i));
		    		String value = line.substring(line.indexOf(leftPrimers.get(i)), line.indexOf(rightPrimers.get(i)));
		    		System.out.println(leftPrimers.get(i)+" "+value+" "+rightPrimers.get(i));
		    		}
		    	}
		    	

		    }
		    
		    
		    
		    
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
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
	
	
	private boolean checkPrime(String primer)
	{
	
		final File folder = new File("inputFastasOneLine");
		File[] filesList = folder.listFiles();
		Arrays.sort(filesList);
		for (final File fileEntry : filesList) {
		
		String searchFor = primer;
		int searchLength=searchFor.length();
		try {
			BufferedReader bout = new BufferedReader (new FileReader (fileEntry));
			String ffline = null;
			int lcnt = 0;
			int searchCount = 0;
			while ((ffline = bout.readLine()) != null) {
				lcnt++;
				for(int searchIndex=0;searchIndex<ffline.length();) {
					int index=ffline.indexOf(searchFor,searchIndex);
					if(index!=-1) {
						//System.out.println("Line number " + lcnt);
						searchCount++;
						if (searchCount>1)
						{
							return false;
						}
						searchIndex+=index+searchLength;
					} else {
						break;
					}
				}
				
			}
			//System.out.println(fileEntry.getName()+" = "+searchCount);
			bout.close();
		} catch(Exception e) {
			System.out.println(e);
		}
		}
		
		return true;
	}
	
	private boolean checkPrimers(String primer) {
		// TODO Auto-generated method stub

			
			final File folder = new File("inputFastasOneLine");
			File[] filesList = folder.listFiles();
			Arrays.sort(filesList);
			for (final File fileEntry : filesList) {
			
			Scanner scanner;
			try {
				scanner = new Scanner(fileEntry);
			
			String logdata = scanner.useDelimiter("\\Z").next();
			final String needle = primer;
			int index = 0;
//			int counter =0;
			while (index < logdata.length() && (index = logdata.indexOf(needle, index)) >= 0) {
//				counter++;
				System.out.println(fileEntry.getName()+": "+primer);
				
//				if (counter>1)
//				{
//					return false;
//				}
			}
			scanner.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			return true;

	}
	

}
