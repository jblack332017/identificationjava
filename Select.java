package identificationjava;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.jar.Attributes.Name;

import org.w3c.dom.ls.LSException;


public class Select {

	TreeMap<String,Organism> organisms;
	String originalName = ""; 

	public Select() {
		super();
		this.organisms = OrganismHolder.getInstance().getOrganisms();
		getOriginalName();
	}
	
	public void getOriginalName(){
		final File folder = new File("inputFastas0");
		boolean first = true;
		File[] filesList = folder.listFiles();
		Arrays.sort(filesList);
		for (final File fileEntry : filesList) {
			
			if (first)
			{
				originalName = fileEntry.getName();
				first = false;
			}
		
		}
	}
	
	public void selectSeq(){
		int number = 1;
		int topCounter = 0;
		for (int q=0; q< organisms.size();q++) { //This is the one that will have the database created against it
			//Organism topOrganism = organisms.get(topOrganismKey);
		
		try{
			File directory = new File(String.valueOf("inputFastas"+number));
		    if (! directory.exists()){
		        directory.mkdir();
		        // If you require it to make the entire directory path including parents,
		        // use directory.mkdirs(); here instead.
		    }
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		int bottomCounter =0;
		for (String organismKey : organisms.keySet()) {
			bottomCounter++;
				if (bottomCounter>=topCounter){
				Organism organism = organisms.get(organismKey);
				
				PrintWriter writer;
				try {
					writer = new PrintWriter("inputFastas"+number+"/"+organism.getName(), "UTF-8");
				
			    StringBuilder stringBuilder = new StringBuilder();
				//System.out.println("name"+organism.getName());

				
				for (int i=0; i< organism.hits.size();i++)
				{
					Hit hit = organism.hits.get(i);
					//System.out.println(organisms.size());

										    
					stringBuilder.append(">");
					stringBuilder.append(hit.id);
				    stringBuilder.append(System.getProperty("line.separator"));
				    stringBuilder.append(hit.getQuerySequence());
				    stringBuilder.append(System.getProperty("line.separator"));	
				    
				    if (number == organisms.size()&&bottomCounter == organisms.size())
					{
				    	
				    	boolean first = true;
				    	System.out.println(organisms.keySet());
				    	for (String key: organisms.keySet())
				    	{
				    		Organism newOrganism = organisms.get(key);
			    			//System.out.println(newOrganism.name);

				    		for (Hit newHit: newOrganism.hits)
				    		{
				    			//System.out.println(newOrganism.name+ ": "+ newHit.id);

				    			if (newHit.getId().equals(hit.getId()))
				    			{
				    				if (first)
				    				{
				    					System.out.println(newHit.id);
				    					
				    					if (newOrganism.getHitsHolder().size()>0)
				    					{
				    					for (Hit originalHit: newOrganism.getHitsHolder().get(0))
				    					{
				    						if (originalHit.getId().equals(newHit.getId()))
				    						{
				    							System.out.println("original" +originalName+": " +originalHit.getHitSequence());
						    					first = false;
				    						}
				    					}
				    					}
				    					else {
			    							System.out.println("original" +originalName+": " +newHit.getHitSequence());
			    							first = false;
										}
				    					
				    					
				    				}
			    					System.out.println(newHit.id);
				    				System.out.println(newOrganism.name+": "+ newHit.getQuerySequence());
				    		
				    			}
				    		}
				    	}
					}
				   
				}
				//System.out.println("out: "+);
				 writer.print(stringBuilder.toString());
				    writer.close();
				    
				    
				    
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}
		
		if (number < organisms.size()){
		
			String cmd = "python runBlast.py "+number;
			
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();
				Parser parser= new Parser("blastOutput"+number);
				parser.populateHits();
				
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File file = new File("./");
			String[] directories = file.list(new FilenameFilter() {
			  @Override
			  public boolean accept(File current, String name) {
			    return new File(current, name).isDirectory();
			  }
			});
			//System.out.println(Arrays.toString(directories));
			
			number++;
		}
		
		}
	}
	
	

}
