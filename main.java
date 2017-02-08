package identification;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String outputFile = args[0];
		
		Parser parser = new Parser(outputFile);
		parser.populateHits();
		
		Select select = new Select();
		
		select.selectSeq();
		
		return;

	}

}
