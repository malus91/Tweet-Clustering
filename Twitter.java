import java.util.ArrayList;
import java.util.Collections;

/**
 * Twitter class for handling tweets and driver class for the program 
 * @author Malini
 *
 */
public class Twitter {
	
	FileHandler fileHandle = new FileHandler();
	
	Twitter(int k,String seedFileName,String inputFileName,String OutputFileName)
	{		
		int[] seed;
		int[] seedArr;
		fileHandle.getInputFileSize(inputFileName);
		String[][] tweets = fileHandle.getData(inputFileName);
		seed = fileHandle.getSeedInfo(tweets, seedFileName);		
		if(k>25)
		{
			k=25;
		}
		else if(k<2)
		{
			k=2;
			
		}
		
		seedArr = new int[k];
		if(k==25)
			seedArr = seed;
		else
            seedArr = getRandomSeeds(k,seed);
		
		TweetClusterHandler tCH =new TweetClusterHandler(seedArr,tweets,k,OutputFileName);
		
	}
	
	/**
	 * Get random values.
	 * @param seed
	 * @return
	 */
	private ArrayList<Integer> getRandomVal(int[] seed) {
		ArrayList<Integer> randNum = new ArrayList<>();
		for(int i=0;i<seed.length-1;i++)
			randNum.add(i+1);
		Collections.shuffle(randNum);
		System.out.println("Seeds used are at indexes as follows");
		System.out.println(randNum);
		return randNum;
	}
	/**
	 * Generate random seeds
	 * @param k
	 * @param seed
	 * @return
	 */
	private int[] getRandomSeeds(int k, int[] seed) {
		int[] seedArr = new int[k+1];
		ArrayList<Integer> randNumbers = new ArrayList<Integer>();
		randNumbers = getRandomVal(seed);
		for(int i=0;i<=k;i++)
		{
			seedArr[i]= seed[randNumbers.get(i)];
		}
		return seedArr;
	}

	/**
	 * Main Function 
	 * @param args
	 */
	public static void main(String args[])
	{
		int numClusters =0;
		String inFilePath = null, outFilePath=null,seedFilePath=null;
		if(args.length>0)
		{
		numClusters = Integer.parseInt(args[0]);
		seedFilePath = args[1];
		inFilePath = args[2];
		outFilePath = args[3];
		}
		else
		 System.out.println("Give 4 command line arguments in proper order");
		//numClusters = 25;
	    //seedFilePath = "src/InitialSeeds.txt";
		//inFilePath = "src/Tweets.json";
	    //outFilePath = "src/Output";
		
		Twitter tweet = new Twitter(numClusters, seedFilePath, inFilePath, outFilePath);
	}

}
