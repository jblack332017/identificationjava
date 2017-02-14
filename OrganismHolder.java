package identificationjava;

import java.util.ArrayList;
import java.util.TreeMap;

public class OrganismHolder {
	   private static OrganismHolder singleton = new OrganismHolder( );
	   private OrganismHolder() {
		// TODO Auto-generated constructor stub
	   }
	   public static OrganismHolder getInstance( ) {
		      return singleton;
		   }
		TreeMap<String,Organism> organisms = new TreeMap<>();
		public TreeMap<String,Organism> getOrganisms() {
			return organisms;
		}
		public void setOrganisms(TreeMap<String,Organism> organisms) {
			this.organisms = organisms;
		}

		
	   



}
