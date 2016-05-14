import java.io.BufferedReader;
import java.io.File;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * Gets input data and Handles the seed information as well
 * @author malini
 *
 */
public class FileHandler{
	
	static int lineNum =0;	
	
	/**
	 * Gets the input Data from the Json File 
	 * @param inptFilePath
	 * @return 2-D String array of Tweets
	 */
	public String[][] getData(String inptFilePath)
	{
		
		JSONObject jsObj = null;
		String[][] tweets = new String[lineNum+1][3];
		String inputLine;
		FileReader inFile = null;
		int idx =1;
		try{
			inFile = new FileReader(inptFilePath);
			
			BufferedReader br = new BufferedReader(inFile);
			while((inputLine=br.readLine())!=null)
			{
				try {
					jsObj = (JSONObject)new JSONParser().parse(inputLine);
				} catch (ParseException e) {					
					e.printStackTrace();
				}
				String tweetContent = (String) jsObj.get("text");
				String id = jsObj.get("id").toString();
				tweets[idx][1]= tweetContent;
				tweets[idx][2]= id;
				idx++;
			}
			
			inFile.close();
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("Error in reading input JSON file");
		}	
		
		return tweets;		
	}
	
	/**
	 * Gets the input file size 
	 * @param inFilePath
	 */
	
	void getInputFileSize(String inFilePath)
	{
		FileReader fileIn = null;
		try{
			fileIn = new FileReader(inFilePath);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fileIn);
		try{
			while(br.readLine()!=null)
			{
				lineNum++;
			}
			System.out.println("Input rows Count "+lineNum);
			fileIn.close();
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("Not able to read the File");
			e.printStackTrace();
		}		
	}
	
	/**
	 * Gets the initial seed information to the seed array 
	 * @param tweets
	 * @param fileSeed
	 * @return Seed array
	 */
	public int[] getSeedInfo(String[][] tweets,String fileSeed)
	{
		String seedInfo;
		int[] seed= new int[26];
		int idx =1;
		
		FileReader inSeedFile = null;
		String inptLine = null;
		try{
			
			inSeedFile = new FileReader(fileSeed);
			BufferedReader br = new BufferedReader(inSeedFile);
			while((inptLine=br.readLine())!=null)
			{
				seedInfo =inptLine.replaceAll(",$", "");
				seedInfo = seedInfo.replaceAll("\n","");
				for(int row =1;row<tweets.length;row++)
				{
					if(tweets[row][2].equals(seedInfo))
					{
						seed[idx++]=row;
					}
				}
			}
			
			inSeedFile.close();
			br.close();			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return seed;		
	}
	
	public static void clusterDisplay(String[][] tweets, int[] clusters, int[] seeds, String outputFileName,double sse) 
	{
	  int iter =1;
	  File outFilePath = new File(outputFileName);
	  FileWriter fw = null;
	  try{
		  fw = new FileWriter(outFilePath);
	  }catch(IOException e)
	  {
		  e.printStackTrace();
	  }
		
	  PrintWriter pw= new PrintWriter(fw);
	  
	  for(int i=1;i<seeds.length;i++)
	  {
		 // System.out.println("\n Processing Cluster "+i);
		  pw.print("Cluster "+i+" : ");
		  for(int j=1;j<clusters.length;j++)
		  {
			  if(clusters[j]==i)
			  {
				  if(iter==1)
				  {
					  pw.print(tweets[j][2]);
					  iter++;
					  
				  } 
				  else
					  pw.print(", "+tweets[j][2]);
			  }
		  }
		  iter =1;
		  pw.println();
	  }
	  pw.println("SSE for Twitter K Means is :"+sse);
	  System.out.println(" ");
	  System.out.println("End of Processing and Output is written to the output file");
	  pw.close();
	}
}
