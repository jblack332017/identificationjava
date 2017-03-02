package identificationjava;

import java.util.ArrayList;

public class Organism {
	String name;
	public ArrayList<Hit> hits = new ArrayList<>();
	public ArrayList<Hit> matches = new ArrayList<>();
	public ArrayList<ArrayList<Hit>> hitsHolder = new ArrayList<>();
	
	
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
	
	public void newHits(){
		hitsHolder.add(hits);
		hits = new ArrayList<>();
	}
	
	public boolean containsID(String id) {
		for (Hit hit: hits)
		{
			if (hit.getId().equals(id))
			{
				return true;
			}
		}
		
		return false;
		
	}

	public ArrayList<ArrayList<Hit>> getHitsHolder() {
		return hitsHolder;
	}

	public void setHitsHolder(ArrayList<ArrayList<Hit>> hitsHolder) {
		this.hitsHolder = hitsHolder;
	}
	
	
	

	

}
