import java.io.IOException;
import java.util.*;
	
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class GroupBy
{

	 public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>
	  {
       private final static IntWritable one = new IntWritable(1);
       private Text word = new Text();
	
	      public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter)
	    	 throws IOException
	    	{
	    	  String action="";
	    	  String uid="";
	    	  
	          String line = value.toString();
	          StringTokenizer tokenizer = new StringTokenizer(line);
	        
	        if (tokenizer.hasMoreTokens())
	        {
	         action =tokenizer.nextToken();	
	       //  System.out.println(action);
	         tokenizer.nextToken();
	         tokenizer.nextToken();
	         tokenizer.nextToken();
	         tokenizer.nextToken();
	         tokenizer.nextToken();
	         uid =tokenizer.nextToken();
	        // System.out.println(uid);	         	         
	        }  
	        
	        if(action.equals("DEL"))
	        {
	        	word.set(uid);
	            output.collect(word, one); 
	        
	        }         	        
	        
	      }//map method
	    }//map class
	
	    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>
	    {
	      public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter)
	      throws IOException
	      {
	        int sum = 0;
	        while (values.hasNext())
	        {
	          sum += values.next().get();
	        }
	        output.collect(key, new IntWritable(sum));
	      }//reduce method
   }//reduce class
	    
	public static void main(String[] args) throws IOException
	{
		 // Configuration cf = new Configuration();	
	    //	 cf.set("mapred.max.split.size","10000");
	    
	     long startTime = System.currentTimeMillis();
	    	
	      JobConf conf = new JobConf(GroupBy.class);
	      conf.setNumMapTasks(Integer.parseInt(args[2]));
	      conf.setNumReduceTasks(Integer.parseInt(args[3]));
         conf.setJobName("groupby");

	      conf.setOutputKeyClass(Text.class);
	      conf.setOutputValueClass(IntWritable.class);
	
	      conf.setMapperClass(Map.class);
	      conf.setCombinerClass(Reduce.class);
	      conf.setReducerClass(Reduce.class);
	
	      conf.setInputFormat(TextInputFormat.class);
	      conf.setOutputFormat(TextOutputFormat.class);
	
	      FileInputFormat.setInputPaths(conf, new Path(args[0]));
	      FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	      JobClient.runJob(conf);
	      
	        long endTime   = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			 System.out.println(" Total time taken in millisec "+totalTime);

	}

}
