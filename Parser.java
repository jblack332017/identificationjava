package identification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	String outputFile;
	ArrayList<Organism> organisms = new ArrayList<>();

	public Parser(String outputFile) {
		super();
		this.outputFile = outputFile;
	}
	
	public void populateHits(){
		final File folder = new File(outputFile);
		for (final File fileEntry : folder.listFiles()) {
	            File output = new File(fileEntry.getPath());
	            
	            try (BufferedReader br = new BufferedReader(new FileReader(output))) {
					Organism organism = new Organism(fileEntry.getName());
	                String line;
	                Hit hit = new Hit();
	                while ((line = br.readLine()) != null) {
	                   // process the line.
//	    	            System.out.println(line);
	                	String value="";
	                	Pattern p = Pattern.compile("\\>(.*?)\\<");
	                	Matcher m = p.matcher(line);
	                	while(m.find())
	                	{
	                		value = m.group(1); //is your string. do what you want
	                	}
	    	            
	                	
	                	
	    	            if (line.contains("</Hit>")&&hit.getGaps()>0){      	    	        
	    	            	organism.addHit(hit);
	    	            }
	    	            else if (line.contains("<Hit>")) {
	    	            	hit = new Hit();
							
						}
	    	            else if (line.contains("<Hit_id>")) {
							hit.setId(value);
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

	                }
	            
	            organisms.add(organism);
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
