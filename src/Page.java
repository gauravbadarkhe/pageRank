import java.io.Serializable;
import java.util.ArrayList;

public class Page implements Serializable {
	private String name;
	private String path;
	private double rank;
	private int numOutLinks;
	private ArrayList<Page> inLinks;
	private ArrayList<String> outLinks;
	
	public Page (String name, String path, double rank, int numOutLinks, ArrayList<Page> inLinks, ArrayList<String> outLinks) {
		this.name = name;
		this.path = path;
		this.rank = rank;
		this.numOutLinks = numOutLinks;
		this.inLinks = inLinks;
		this.outLinks = outLinks;
	}
	
	public String toString() {
		String out = "Page - Name: " + name;
		out += "\n Absolute Path: " + path;
		out += "\n Rank: " + rank;
		out += "\n Number of links to other pages: " + numOutLinks;
		out += "\n Pages that are linked to from this page: ";
		for (int i = 0; i < outLinks.size(); i++) {
			out += "\n\t" + outLinks.get(i);
		}
		out += "\nPages that link to this page:";
		for (int i = 0; i < inLinks.size(); i++) {
			out += "\n\t" + inLinks.get(i).getName();
		}
		out +="\n";
		return out;
		
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public int getNumLinks() {
		return numOutLinks;
	}

	public void setNumLinks(int numLinks) {
		this.numOutLinks = numLinks;
	}

	public ArrayList<Page> getInLinks() {
		return inLinks;
	}

	public void setInLinks(ArrayList<Page> links) {
		this.inLinks = links;
	}
	
	public ArrayList<String> getOutLinks() {
		return outLinks;
	}
	
	public void setOutLinks(ArrayList<String> outLinks) {
		this.outLinks = outLinks;
	}
}
