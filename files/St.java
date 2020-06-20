package dataset;

public class Statement {
	
	private String type;
	private String type2;
	private String firstLink;
	private String secondLink;
	
	
	public Statement(String type, String firstLink, String type2, String secondLink) {
		this.type = type;
		this.firstLink = firstLink;
		this.secondLink = secondLink;
		this.type2 = type2;
	}
	
	public void setSecondLink(String type2,String secondLink) {
		this.secondLink = secondLink;
		this.type2 = type2;
	}
	
	public String getType1() {
		return type;
	}
	
	public String getType2() {
		return type2;
	}
	
	public String getFirstLink() {
		return firstLink;
	}
	
	public String getSecondLink() {
		return secondLink;
	}
	
	public String toString() {
		return "< "+ type + ": " + firstLink + " ; " + type2 + ": " + secondLink + " >";
	}
}
