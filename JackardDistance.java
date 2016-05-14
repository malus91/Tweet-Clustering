import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/**
 * Calculates the Jackard Distance between the tweet and centroid
 * @author Malini
 *
 */
public class JackardDistance {
	
	
   public float calcJackardDist(String tweet1,String tweet2)
   {
	   
	   float distance = 0;
	   String[] tweet1Wds = tweet1.split(" ",-1);//Split as many number of times possible and array can be of anysize
	   String[] tweet2Wds = tweet2.split(" ",-1);
	   
	   Set<String> tweet2Set = new HashSet<String>(Arrays.asList(tweet2Wds));
	   Set<String> tweetIntersection = new HashSet<String>(Arrays.asList(tweet1Wds));
	   //Gets similar words in both tweets
	   tweetIntersection.retainAll(tweet2Set);
	   Set<String> tweetJoin = new HashSet<String>(Arrays.asList(tweet1Wds));
	   //Get the join of both tweets
	   tweetJoin.addAll(tweet2Set);
	   
	   distance = (1-(float)tweetIntersection.size()/tweetJoin.size());
	   
	return distance;
	   
   }
}
