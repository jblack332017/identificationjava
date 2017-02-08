package identification;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class Select {

	ArrayList<Organism> organisms = new ArrayList<>();

	public Select() {
		super();
		this.organisms = OrganismHolder.getInstance().getOrganisms();
	}
	
	public void selectSeq(){
		boolean select = true;
		int number = 1;
		while (select){
		
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
		
		
		for (Organism organism : organisms) {
			
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
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		number++;
		select = false;
		}
	}
	
	

}
