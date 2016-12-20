package com.wonggigi;

/**
 * Created by Hanoi on 2016/12/20.
 */
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

public class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    protected void map(LongWritable key, Text value,Context context)
            throws IOException, InterruptedException {
        String[] words = StringUtils.split(value.toString(), ' ');
        for(String word : words){
            context.write(new Text(word), new IntWritable(1));
        }
    }
}