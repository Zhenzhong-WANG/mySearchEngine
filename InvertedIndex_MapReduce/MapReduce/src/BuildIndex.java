import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BuildIndex {
   // private static Hashtable<Text,Integer> hashtable = new Hashtable<>();
    public static class IndexMapper extends Mapper<Object, Text, Text, Text>{
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String DocId=itr.nextToken();
                StringTokenizer sentence = new StringTokenizer(itr.nextToken(),"/");
                while(sentence.hasMoreTokens()){
                    String word=sentence.nextToken();
                    context.write(new Text(word),new Text(DocId));
                }
            }
        }
    }

    public static class IndexReducer extends Reducer<Text,Text,Text,Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Hashtable<Text,Integer> hashtable = new Hashtable<>();
            String documenmts = "";
            int docF=0;
            for (Text val : values) {
                if (hashtable.containsKey(val)){
                    int wordF=hashtable.get(val);
                    wordF++;
                    hashtable.put(new Text(val.toString()),wordF);
                }else{
                    hashtable.put(new Text(val.toString()),1);
                    docF++;
                }
            }

            Set<Text> keyset=hashtable.keySet();
            for (Text docKey:keyset){
                documenmts=docKey.toString()+","+hashtable.get(docKey)+"/"+documenmts;
            }
            System.out.println(key+" is ok");
            context.write(new Text(key.toString()+"\t"+docF), new Text(documenmts));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
    //    conf.set("mapred.jar", "BuildIndex.jar");
        Job job = Job.getInstance(conf, "Inverted Index");
        job.setJarByClass(BuildIndex.class);
        job.setMapperClass(IndexMapper.class);
       // job.setCombinerClass(IndexReducer.class);
        job.setReducerClass(IndexReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}

