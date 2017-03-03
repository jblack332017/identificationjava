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

public class Parser {
	
	String outputFile;
	TreeMap<String,Organism> organisms = new TreeMap<>();

	public Parser(String outputFile) {
		super();
		this.outputFile = outputFile;
		organisms = OrganismHolder.getInstance().getOrganisms();
	}
	
	public void populateHits(){
		String id ="";
		final File folder = new File(outputFile);
		
		File[] filesList = folder.listFiles();
		Arrays.sort(filesList);
		for (final File fileEntry : filesList) {
	            File output = new File(fileEntry.getPath());
        		Organism organism = new Organism(fileEntry.getName());
        		String fileName = fileEntry.getName();
    			//System.out.println(fileName);

        		if (organisms.containsKey(fileName))
        		{
        			//System.out.println(outputFile+ ": filename: "+fileName);
        			organism = organisms.get(fileName);
        			organism.newHits();
        			//System.out.println("new hits");
        		}

	            try (BufferedReader br = new BufferedReader(new FileReader(output))) {

	                String line;
	                Hit hit = new Hit();
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
	                	
	                	
	    	            if (line.contains("</Hsp>")&&hit.positive!=hit.alignLength&&!organism.containsID(hit.getId())){      	    	        
	    	            	//System.out.println(organism.name+": "+ hit.id);
	    	            	organism.addHit(hit);
	    	            }
	    	            else if (line.contains("<Hsp>")) {
	    	            	hit = new Hit();
						}
	    	            else if (line.contains("<Hit_id>")) {
							id = value;
						}
	    	            else if (line.contains("<Hsp_num>")) {
							hit.setId(id);
						}
	    	            else if (line.contains("<Hsp_gaps>")) {
							hit.setGaps(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_score>")) {
							hit.setScore(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_query-from>")) {
							hit.setQueryFrom(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_query-to>")) {
							hit.setQueryTo(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_hit-from>")) {
							hit.setHitFrom(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_hit-to>")) {
							hit.setHitTo(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_qseq>")) {
							hit.setQuerySequence(value);
						}
	    	            else if (line.contains("<Hsp_hseq>")) {
							hit.setHitSequence(value);
						}
	    	            else if (line.contains("<Hsp_positive>")) {
							hit.setPositive(Integer.parseInt(value));
						}
	    	            else if (line.contains("<Hsp_align-len>")) {
							hit.setAlignLength(Integer.parseInt(value));
						}

	                }
	            
	            organisms.put(fileName, organism);
	            //System.out.print(organism.hits.size()+"\n");
	            } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            //System.out.print(organisms.size()+"\n");
	            
	            OrganismHolder.getInstance().setOrganisms(organisms);
	            
	            
	    }
		
	}
	


} 
