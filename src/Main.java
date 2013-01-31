import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
	private ArrayList<Page> pages;
	private final static double DAMPING = 0.85;
	
	public Main() {
		pages = new ArrayList<Page>();
	}
	
	private void writePagesBinary(String fileName) {
		try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream (fileName));
            outputStream.writeObject(pages);
            outputStream.close();
        }
        catch ( IOException e ){
            System.out.println("Error writing to file " + fileName);
            System.out.println(e);
            System.exit(0);
        }
	}
	
	private void loadPagesBinary(String fileName) {
        pages = null;
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream (fileName));
            pages = (ArrayList<Page>)inputStream.readObject();
            inputStream.close();
        }
        catch (Exception e){
            System.out.println("Problem reading the file " + fileName);
            System.exit(0);
        }
	}
	
	private boolean loadPagesDirectory(String directoryName) {
		File entry = new File(directoryName);
		LinkExtractor extractor = new LinkExtractor();
		try {
		    if (entry.isDirectory() && entry.getCanonicalPath().equals(entry.getAbsolutePath())) {
		    	File[] files = entry.listFiles();
		    	for (int i = 0; i < files.length; i++) {
		    		String fileContents = "";
		    		ArrayList<String> outLinks;
		    		try {
			    			BufferedReader in = new BufferedReader(new FileReader(files[i].getAbsolutePath()));
			    			String line;
			    			while ((line = in.readLine()) != null) {
			    			    fileContents += line;
			    			    fileContents += " ";
			    			}
		    		    } catch (FileNotFoundException fe) {
		    			System.out.println("File not found in load Pages from Directory\n" + fe );
		    		    }
		    		    catch (IOException ioe) {
		    			System.out.println("IOException in load Pages from Directory\n" + ioe);
		    		    }
		    		outLinks = extractor.grabLinks(fileContents);
		    		    		
		    		pages.add(new Page(files[i].getName(), files[i].getAbsolutePath(), 0, outLinks.size(), new ArrayList<Page>(), outLinks));
		    	}
		    	for (int i = 0; i < pages.size(); i++) {
		    		for (int a = 0; a < pages.get(i).getOutLinks().size(); a++) {
		    			int index = searchByAbsolutePath(pages.get(i).getOutLinks().get(a));
		    			if (index != -1) {
		    				pages.get(index).getInLinks().add(pages.get(i));
		    			}
		    		}
		    	}
		    	return true;
		    }
		    else {
		    	System.out.println("The path specified did not lead to a directory, please try again.");
		    	return false;
		    }
		    
		} catch (IOException ioe) {
			System.out.println("There was an error loading the pages in directory " + directoryName);
			System.out.println(ioe);
			return false;
		}
	}

	// This is where the actual Google Page Rank Algorithm is implemented. The one input variable
	// is the variance the user wants. This determines how accurate the algorithm should calculate
	// the PageRank, and in turn how many cycles it should run. The algorithm determines the variance 
	// by comparing the difference of rank the previous iteration computed vs. the rank the next iteration
	// computed. Once this value is less than or equal to the user give variance the algorithm is complete.
	// In this way the user determines the accuracy of the algorithm
	private void calculateRank(int iterations) {
		// Make sure there are actually pages to rank
		if (pages == null || pages.isEmpty()) {
			System.out.println("There are no pages to rank!");
			return;
		}
		
		// Next set all the page ranks to 1/n
		for (int a = 0; a < pages.size(); a++) {
			pages.get(a).setRank(1/(double)pages.size());
		}
		
		// Keep iterating the algorithm until the currentVariance is less than or equal
		// to the desired variance
		for (int b = 0; b < iterations; b++) {
			// Do this for each page in the set
			for (int i = 0; i < pages.size(); i++) {
				if (pages.get(i).getNumLinks() > 0) {
					// this value holds the newly calculated Rank
					double initialRank = 0;
					// Calculate the rank using the incoming link's weight and the number of links coming from that page
					for (int j = 0; j < pages.get(i).getInLinks().size(); j++) {
							initialRank += (pages.get(i).getInLinks().get(j).getRank()) / (double)(pages.get(i).getInLinks().get(j).getNumLinks());
					}
					// Factor in the Damping value
					initialRank = ((1-DAMPING)/(double)pages.size() + DAMPING*(initialRank));
					// Finally, set the rank of the page to the calculated value
					pages.get(i).setRank(initialRank);
				}
				else pages.get(i).setRank(0);
			}
		} 
	}
	
	private void printResult() {
		System.out.println("File Name                   Rank");
		System.out.println("-------------------------------------------");
		for (int i = 0; i < pages.size(); i++) {
			System.out.println(pages.get(i).getName() + "          Rank: " + pages.get(i).getRank());
		}
	}
	
	public int searchByAbsolutePath(String path) {
		for (int i = 0; i < pages.size(); i++) {
			if (pages.get(i).getPath().equalsIgnoreCase(path)) {
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		PageSetGenerator generator = new PageSetGenerator();
		Scanner kb = new Scanner(System.in);
		int input = 0;
		String stringInput = "";
		
		System.out.println("Welcome to Zack Liston's implementation of the Google Page Rank program!");
		
		while (input != 3) {
			System.out.println("Please enter the corrosponding number for your choice:");
			System.out.println("\t1: Generate a new Set of linked documents");
			System.out.println("\t2: Rank an existing Set of linked documents");
			System.out.println("\t3: Exit\n");
			
			input = kb.nextInt();
			kb.nextLine();
			if (input == 1) {
				System.out.print("Please enter the name of the directory you'd like to create: ");
				stringInput = kb.nextLine();
				System.out.print("\nPlease enter how many pages you would like to create in this directory: ");
				int numPages = kb.nextInt();
				kb.nextLine();
				generator.generatePages(stringInput, numPages);	
				System.out.println();
			}
			
			else if (input == 2) {
				Main main = new Main();
				
				System.out.print("Please enter the name of the directory containing the pages you want to rank: ");
				stringInput = kb.nextLine();
				if (main.loadPagesDirectory(stringInput)) {
					System.out.print("Please enter how many iterations of the algorithm you would like to preform: ");
					int iterations = kb.nextInt();
					kb.nextLine();
					main.calculateRank(iterations);
					main.printResult();
				}				
			}
			
			else if (input == 3) {
				System.out.println("Thanks for using the Page Rank Program! Goodbye.");
				System.exit(0);
			}
			else {
				System.out.println("Invalid input, please try again.");
			}
		}
	}
}
