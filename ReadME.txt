Part B

Files for this part 

Twitter.java -> Contains the main function 
JackardDistance.java -> Calculates the Jackard Distance
TweetClusterHandler.java -> handles cluster assignment and update
FileHandler.java -> Handles getting input from files

Packages needs to be installed. Add this jar file to the Java JRE and make sure it is available.
-> json-simple-1.1.1.jar

Compilation Command:javac -cp json-simple-1.1.1.jar Twitter.java FileHandler.java TweetClusterHandler.java JackardDistance.java

//Windows system command
Execution Command : java -cp json-simple-1.1.1.jar;. Twitter 20 InitialSeeds.txt Tweets.json Output



