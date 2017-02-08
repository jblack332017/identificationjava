package identification;

public class Hit{
	int gaps=0;
	int score =0;
	int queryFrom =0;
	int queryTo =0;
	int hitFrom =0;
	int hitTo =0;
	String querySequence ="";
	String hitSequence ="";
	String id ="";
	

	public int getGaps() {
		return gaps;
	}

	public void setGaps(int gaps) {
		this.gaps = gaps;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getQueryFrom() {
		return queryFrom;
	}

	public void setQueryFrom(int queryFrom) {
		this.queryFrom = queryFrom;
	}

	public int getQueryTo() {
		return queryTo;
	}

	public void setQueryTo(int queryTo) {
		this.queryTo = queryTo;
	}

	public int getHitFrom() {
		return hitFrom;
	}

	public void setHitFrom(int hitFrom) {
		this.hitFrom = hitFrom;
	}

	public int getHitTo() {
		return hitTo;
	}

	public void setHitTo(int hitTo) {
		this.hitTo = hitTo;
	}

	public String getQuerySequence() {
		return querySequence;
	}

	public void setQuerySequence(String querySequence) {
		this.querySequence = querySequence;
	}

	public String getHitSequence() {
		return hitSequence;
	}

	public void setHitSequence(String hitSequence) {
		this.hitSequence = hitSequence;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	
	
}