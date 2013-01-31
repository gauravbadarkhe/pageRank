README for Page Rank Program
Author: Zack Liston
Due Date: 12-07-2012

Files included:
	FinalProject.jar	- A runnable jar file
	Test1			- A folder with a test set of 10 pages
	Test2			- A folder with a test set of 100 pages

	All the raw .java files for you to inspect if you would like.

Instructions
1: Place all three files in the same directory
2: Unzip both Test1 and Test2
3: Run the program using the command "java -jar FinalProject.jar"
4: Enter 2 to run the program for a provided test set
5: Enter the test set name
6: Enter a number of iterations you would like to perform 
	(Note, the more iterations you perform, the more exact the results will be, as a guide, 5 iterations
	 will usually create very accurate results)
7: Read results

	If you would like to create a new test set
1: Click 1 once starting the program
2: Enter the desired name of the directory to place the set of documents in
3: Enter how many pages you would like to be placed in the directory
	(Please not that creating more than a couple hundred pages will take the machine a while)
4: The pages will be created and placed in the directory IF the directory does not already exist
5: Follow the previous instructions to rank the newly created pages

EXPLANATION:
	This program will only work for html files that contain links to other html files following the
 <a href='location'>File Name</a> scheme and only if all the pages are in the same directory.

	The page generator works by creating pages and writing a number of links to those pages. The number of links 
is a random number between 0-(number of pages divided by 4). I choose this method to ensure there were a fair number 
of links for small sets; however, it makes it less feasible to create large sets (>250)

	The program works by parsing all of the pages and reading the links. It puts information into a custom Page class.
This information includes the number of the pages outgoing links, the actual outgoing links, the incoming links,
and the rank. Throughout the algorithm the rank is recalculated several times (based on how many iterations you have it do)

	The actual algorithm that calculates the rank follows the algorithm I described in my paper.