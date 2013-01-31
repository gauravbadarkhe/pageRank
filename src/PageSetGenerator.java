import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PageSetGenerator {
	ArrayList<String> paths;
	ArrayList<String> fileNames;
	
	public PageSetGenerator() {
		paths = new ArrayList<String>();
		fileNames = new ArrayList<String>();
	}
	
	public void generatePages(String directoryName, int numPages) {
		File directory = new File(directoryName);
		boolean created = directory.mkdir();
		if (created) System.out.println("Directory successfuly created");
		else {
			System.out.println("That directory name is already in use");
			return;
		}		
		for (int i = 0; i < numPages; i++) {
			try {
			File file = new File(directoryName + "/FileNumber" + i +".html");
			file.createNewFile();
			paths.add(file.getAbsolutePath());
			fileNames.add(file.getName());
			
			} catch (Exception e) {
				System.out.println("Error creating file " + directoryName + "/FileNumber" + i + "\n" + e);
			}
		}
		populatePages(directory, numPages);
	}
	
	private void populatePages(File directory, int numPages) {
		int maxLinks = (int)(numPages/4);
		int linkToAdd;
		boolean[] linkWritten = new boolean[directory.listFiles().length];
		linkWritten = resetToFalse(linkWritten);
		
		for (int i = 0; i < directory.listFiles().length; i++) {
			String output = "";
			Random random = new Random();
			linkWritten[i] = true;			
			int numberOfLinks = random.nextInt(maxLinks);
			int a = 0;
			
			while (a < numberOfLinks) {
				linkToAdd = random.nextInt(directory.listFiles().length);			
				if (!linkWritten[linkToAdd]) {
					output += "<a href='" + paths.get(linkToAdd) + "'>" + fileNames.get(linkToAdd) + "</a>";
					output += " \n ";
					linkWritten[linkToAdd] = true;
					a++;
				}
			}
			linkWritten = resetToFalse(linkWritten);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(directory.listFiles()[i]));
				writer.write(output);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Error creating writer for file: " + directory.listFiles()[i].getName());
				System.out.println(e);
			}
		}
		System.out.println("Successfully wrote data to all files");
	}
	
	private boolean[] resetToFalse(boolean[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = false;
		}
		return array;
	}
}
