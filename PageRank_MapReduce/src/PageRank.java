import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRank {
    public static class PageRankMapper extends Mapper<Object, Text, Text, Text>{
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines=value.toString().split("\n");
            for(String line:lines){
                Double probability=Double.parseDouble(line.split("/")[0]);
                String docId=line.split("/")[1];
                String[] outLinks=line.split("/")[2].split(",");
                int outLinkNum=outLinks.length;
                context.write(new Text(docId),new Text("0.0"));
                for (String link:outLinks){
                    double pro=probability/outLinkNum;
                    context.write(new Text(link),new Text(Double.toString(pro)));
                }
            }
        }
    }

    public static class PageRankReducer extends Reducer<Text,Text,Text,Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Configuration conf=context.getConfiguration();
            double factor=Double.parseDouble(conf.get("factor"));
            int num=Integer.parseInt(conf.get("num"));
            Double sum=0.0;
            for (Text pro:values){
                sum+=Double.parseDouble(pro.toString());
            }
            Double probabilty=factor*sum+(1-factor)/num;
            context.write(key,new Text(Double.toString(probabilty)));
        }
    }

    public static void main(String[] args) throws Exception {
        double factor=0.80;
        String input="/home/Projects/SearchEngine/PageRank_MapReduce/input";
        String output="/home/Projects/SearchEngine/PageRank_MapReduce/output";
        for(int i=0;i<1;i++){
            Configuration conf = new Configuration();
            conf.set("factor",String.valueOf(factor));
            conf.set("num","4");
            Job job = Job.getInstance(conf, "PageRank");
            job.setJarByClass(PageRank.class);
            job.setMapperClass(PageRankMapper.class);
            job.setCombinerClass(PageRankReducer.class);
            job.setReducerClass(PageRankReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job, new Path(input));
            FileOutputFormat.setOutputPath(job, new Path(output+i+""));
            input=output+i+"";
            job.waitForCompletion(true);
        }
    }
}

