package identification;

import java.util.ArrayList;

public class OrganismHolder {
	   private static OrganismHolder singleton = new OrganismHolder( );
	   private OrganismHolder() {
		// TODO Auto-generated constructor stub
	   }
	   public static OrganismHolder getInstance( ) {
		      return singleton;
		   }
		ArrayList<Organism> organisms = new ArrayList<>();
		public ArrayList<Organism> getOrganisms() {
			return organisms;
		}
		public void setOrganisms(ArrayList<Organism> organisms) {
			this.organisms = organisms;
		}

		
	   



}
