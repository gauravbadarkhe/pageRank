README for Page Rank Program
Author: Zack Liston
Date: 12-07-2012

Files included in Runnable:
  FinalProject.jar		- A runnable jar file

All the raw .java files are included in src for you to inspect if you would like, as listed below:
   Main.java	Page.java	LinkExtrator.java	PageSetGenerator.java

Instructions
1: Once downloaded navigate to the Runnable directory
2: Run the program using the command "java -jar FinalProject.jar"
3: Enter 1 to generate a set of HTML documents
4: Enter the desired name of the directory to place the files in.
5: Enter how many pages you would like in the set (I would not advise doing more than 100 or the machine will have to work a while).
6: The pages will be created, linked together in some way, and placed in the directory you have named IF it does not already exist

Now to actually run the algoritm.
1: Enter 2 to run the program for a provided test set
2: Enter the test set name
3: Enter a number of iterations you would like to perform 
	(Note, the more iterations you perform, the more exact the results will be, as a guide, 5 iterations
	 will usually create very accurate results)
4: Read results

EXPLANATION:
	This program will only work for html files that contain links to other html files following the
 <a href='location'>File Name</a> scheme and only if all the pages are in the same directory.

	The page generator works by creating pages and writing a number of links to those pages. The number of links 
is a random number between 0-(number of pages divided by 4). I choose this method to ensure there were a fair number 
of links for small sets; however, it makes it less feasible to create large sets (>250)

	The program works by parsing all of the pages and reading the links. It puts information into a custom Page class.
This information includes the number of the pages outgoing links, the actual outgoing links, the incoming links,
and the rank. Throughout the algorithm the rank is recalculated several times (based on how many iterations you have it do)

	As a disclaimer I did not invent this algorithm. It is a version of Google's PageRank algorithm that I merely 
implemented for a class project.
