package com.wonggigi;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class RunJob {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        try {
            FileSystem fs = FileSystem.get(config);
            Job job = Job.getInstance(config);
            job.setJobName("Build Index");
            job.setJarByClass(RunJob.class);
            job.setMapperClass(InvertedIndexMapper.class);
            job.setReducerClass(InvertedIndexReducer.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job, new Path("/wonggigi/input/"));
            Path outPath = new Path("/wonggigi/output/index/");
            if(fs.exists(outPath)){
                fs.delete(outPath, true);
            }
            FileOutputFormat.setOutputPath(job, outPath);
            Boolean result = job.waitForCompletion(true);
            if(result){
                System.out.println("Job is complete!");
            }else{
                System.out.println("Job is fail!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}