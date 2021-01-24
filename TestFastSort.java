//package MiniStressTester;

import java.io.PrintStream;
import java.util.*;




public class TestFastSort extends StressTest {
    //in-house hashtable
    MyHashTable<String, String> tweetTable;
    //hash table java
    private Hashtable<String, String> solnTweetTable;
    
    
 

    //varuable for testing purpose
    private ArrayList<String> authorList;
    private Long refExecutionTime;
    private int multiplier;
    private TreeMap<String, String> orderedTreemap = new TreeMap<String, String>();



    TestFastSort(Integer timeOut, int mult, PrintStream output, PrintStream err){
        super(timeOut, output, err);
        this.multiplier = mult;
    }

    public void createHashTable(){

        int bucketCount = (int)(data.size()/0.75) + 1;
        tweetTable = new MyHashTable<String,String>(bucketCount);
        solnTweetTable = new Hashtable<String,String>(bucketCount);
        ArrayList<String> tempAuthorList = new ArrayList<String>();
        for (Tweet tweet: data) {
            tweetTable.put(tweet.getAuthor(), tweet.getMessage());
            orderedTreemap.put(tweet.getAuthor(), tweet.getMessage());
            solnTweetTable.put(tweet.getAuthor(), tweet.getMessage());
            tempAuthorList.add(tweet.getAuthor());
            
        }
        Set<String> authorSet = new HashSet<String>(tempAuthorList);
        this.authorList = new ArrayList<String>(authorSet);
        
        ArrayList<String> ll = this.tweetTable.values();
        Collection<String> ll2 = this.orderedTreemap.values();
        Collection<String> ll3 = this.solnTweetTable.values();
        
        System.out.println("constructor-values:" + ll.size() + " values:" + ll2.size() + "values:" + ll3.size());
        
        
        

    }
    /*
     *  Provide implementation of this (tester) method  for each test.
     */




    Boolean testAccuracy(ArrayList<String> keyList)
    {

        try
        {
            Boolean accurate=true;
            int mismatch = 0;
            System.out.println("keyList size=" + keyList.size() + " tweettable size=" + this.tweetTable.size() + " Soln.tweettable size=" + this.solnTweetTable.size() );
            
            ArrayList<String> ll = this.tweetTable.values();
            Collection<String> ll2 = this.orderedTreemap.values();
            Collection<String> ll3 = this.solnTweetTable.values();
            
            System.out.println("values:" + ll.size() + " values:" + ll2.size() + "values:" + ll3.size());
            
            for (int i=0; i< keyList.size()-1 ; i++)
            	
            
            	
            {
                if(this.tweetTable.get(keyList.get(i)).compareTo(this.tweetTable.get(keyList.get(i+1)))<0)
                {
                    mismatch++;
                    accurate=false;
                }
            }
            this.out.println("Number of misordered keys : " + mismatch);
            return accurate;
        }catch(Exception e)
        {
        	e.printStackTrace();
            return false;
        }
    }


    private void getReferenceExecutionTime()
    {
        this.refExecutionTime = (long)500;
        this.out.print("Approximate execution time (for reference) " + this.refExecutionTime+"ms");
    }

    Boolean tester(){
        try {
            getReferenceExecutionTime();

            Long startTime = System.currentTimeMillis();

            ArrayList<String> keyList = tweetTable.fastSort(tweetTable);

            Long timeTaken = System.currentTimeMillis() - startTime;

            if (!Thread.interrupted()) {
                if (verbose) {
                    if (tweetTable.size() == this.authorList.size()) {
                        this.out.println(" | Execution time of solution code : " + timeTaken + "ms");
                        if (testAccuracy(keyList))
                        {
                            if (timeTaken < this.multiplier * this.refExecutionTime)
                            {
                                this.out.println("[PASS] Code executed correctly under acceptable time.");
                                return true;
                            }
                            else
                                this.out.println("[FAIL] Code is not optimized enough.");
                        } else
                            this.out.println("[FAIL] The sorting is not correct.");
                    } else {
                        this.out.println("[FAIL] The size of the tables created did not match!");
                        return false;
                    }
                }
            }
            return false;
        }catch(Exception te){
            this.out.println("Error " + te.toString() + "raised during the stress test.");
            return false;
        }
        finally{
            this.out.flush();
        }
    }

}
