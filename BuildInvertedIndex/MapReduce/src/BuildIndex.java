import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class BuildIndex {

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
        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String documenmts = "";
            HashMap<Text,IntWritable> map = new HashMap<Text,IntWritable>();
            for (Text val : values) {
                if (map.containsKey(val)){
                    continue;
                }else{
                    map.put(val,new IntWritable(1));
                    documenmts =  val.toString()+"/"+documenmts;
                }
            }
            result.set(documenmts);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
    //    conf.set("mapred.jar", "BuildIndex.jar");
        Job job = Job.getInstance(conf, "Inverted Index");
        job.setJarByClass(BuildIndex.class);
        job.setMapperClass(IndexMapper.class);
        job.setCombinerClass(IndexReducer.class);
        job.setReducerClass(IndexReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);
    }
}

