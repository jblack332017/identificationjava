package identificationjava;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class Select {

	TreeMap<String,Organism> organisms;

	public Select() {
		super();
		this.organisms = OrganismHolder.getInstance().getOrganisms();
	}
	
	public void selectSeq(){
		int number = 1;
		int topCounter = 0;
		for (String topOrganismKey : organisms.keySet()) { //This is the one that will have the database created against it
			Organism topOrganism = organisms.get(topOrganismKey);
			topCounter++;
		
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
				
				for (int i=0; i< organism.hits.size();i++)
				{
					Hit hit = organism.hits.get(i);
										    
					stringBuilder.append(">");
					stringBuilder.append(hit.id);
				    stringBuilder.append(System.getProperty("line.separator"));
				    stringBuilder.append(hit.getHitSequence());
				    stringBuilder.append(System.getProperty("line.separator"));			    
	
				   
				}
				 writer.print(stringBuilder.toString());
				    writer.close();
				    if (number+1 == organisms.size())
					{
						System.out.println(number+1+" "+organisms.size());
					}
				    
				    
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}
		
		if (number+1 == organisms.size()){
		
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
			System.out.println(Arrays.toString(directories));
			
			number++;
		}
		
		}
	}
	
	

}
