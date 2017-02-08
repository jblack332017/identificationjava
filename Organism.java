package identification;

import java.util.ArrayList;

public class Organism {
	String name;
	public ArrayList<Hit> hits = new ArrayList<>();
	
	
	
	public Organism(String name) {
		super();
		this.name = name;
	}

	public void addHit(Hit hit){
		hits.add(hit);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Hit> getHits() {
		return hits;
	}

	public void setHits(ArrayList<Hit> hits) {
		this.hits = hits;
	}
	
	
	

	

}
