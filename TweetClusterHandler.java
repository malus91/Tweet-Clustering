/**
 * Handles tweets clustering
 * @author Malini
 *
 */
public class TweetClusterHandler {

	int[] seeds;
	String[][] tweets;
	float[][] centroidArr;
	int[] clusters;
	int clusterNum =0;
	JackardDistance dist;
	float[][] distArr;
	boolean finishFlg; //False needs processing //True Finished
	int iter = 1;
	
	//Constructor for the TweetCluster Handler
	public TweetClusterHandler(int[] seedArr, String[][] tweet, int k, String outputFileName) {
		
		dist = new JackardDistance();		
		seeds = seedArr;
		tweets = tweet;
		clusterNum = k;
		while(!finishFlg)
		{
			//System.out.println("***********************");
			//System.out.println("Iteration "+iter);
			getCentroids(seedArr,tweets);
			iter++;
		}
		
		double sse = calcSSE(k);
		FileHandler.clusterDisplay(this.tweets,clusters,this.seeds,outputFileName, sse);
		
	}
	
	/**
	 * Calculates the centroids for the tweets 
	 * @param seedArr
	 * @param tweetsArr
	 */
	private void getCentroids(int[] seedArr,String[][]tweetsArr) {
		centroidArr = new float[seedArr.length][tweetsArr.length+1];
		distArr = new float[tweetsArr.length][tweetsArr.length];
		for(int seedIdx = 1;seedIdx<centroidArr.length;seedIdx++)
		{
			for(int tweetIdx =1;tweetIdx<centroidArr[1].length-1;tweetIdx++)
			{
				centroidArr[seedIdx][tweetIdx] = dist.calcJackardDist(tweetsArr[seedArr[seedIdx]][1], tweetsArr[tweetIdx][1]);
			}
			
		}
		
		for(int r =1;r<tweetsArr.length;r++)
		{
			for(int c = 1;c<tweetsArr.length;c++)
			{
				distArr[r][c]= dist.calcJackardDist(tweetsArr[r][1], tweetsArr[c][1]);
			}
		}
		
		assignPoints(centroidArr,tweetsArr);
		updateCentroids(tweetsArr);
		
	}
	/**
	 * Updates centroids for the next iteration
	 * @param tweets
	 */
	private void updateCentroids(String[][] tweets) {
		float distance = Integer.MAX_VALUE;
		float sum =0;
		int oldSeed;
		int newSeed;
		
		finishFlg = true;
		for(int j=1;j<seeds.length;j++)
		{
			oldSeed = seeds[j];
			for(int i=1;i<clusters.length;i++)
			{
				sum =0;
				if(clusters[i]==j)
				{
					for(int jk=1;jk<tweets.length;jk++)
					{
						if(j==clusters[jk])
						{
							//System.out.println( "Tweets issue on i and 1"+tweets[i][1]+"Value of i j jk"+i+" "+j+" "+jk);
							//System.out.println("Tweets issue on jk and i "+tweets[jk][i]);
							sum +=  dist.calcJackardDist(tweets[i][1],tweets[jk][1]);
							
						}
					}
					
					if(sum<distance)
					{
						distance = sum;
						sum =0;
						seeds[j]=i;
						
					}
				}
			}
			newSeed = seeds[j];
			//System.out.println("New seed Value :"+tweets[newSeed][2]);
			if(newSeed!=oldSeed)
				finishFlg = false;
			/*else
				//System.out.println("End of processing for Seed "+j);
*/			
			distance = Integer.MAX_VALUE;
			sum =0;
		}
		
	}
	/**
	 * Assign Tweets to the respective clusters
	 * @param centroidArr
	 * @param tweets
	 */

	private void assignPoints(float[][] centroidArr,String[][] tweets) {
		
		int clusterVal = -1;
		float minDist = Integer.MAX_VALUE;
		clusters = new int[centroidArr[1].length-1];
		for(int c = 1;c<centroidArr[1].length-1;c++)
		{
			for(int r = 1;r<centroidArr.length;r++)
			{
				if(centroidArr[r][c]<minDist)
				{
					minDist = centroidArr[r][c];
					clusterVal= r;
				}
			}
			clusters[c] = clusterVal;
			centroidArr[clusterVal][tweets.length]+=1;
			minDist = Integer.MAX_VALUE;
		}
	}

	/**
	 * Calculate the SSE for the Tweets clustering
	 * @param k - num of clusters
	 */
	private double calcSSE(int k) {
		
		float sum =0;
		for(int i=1;i<=k;i++)
		{
			for(int j=1;j<clusters.length;j++)
			{
				if(i==clusters[j])
				{
					sum+=(float)(Math.pow(dist.calcJackardDist(tweets[seeds[i]][1], tweets[j][1]), 2));
				}
			}
		}
		
		System.out.println("SSE :"+sum);
		
		return sum;
		
	}
	

}
