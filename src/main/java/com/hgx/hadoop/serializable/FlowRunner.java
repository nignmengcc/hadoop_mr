package com.hgx.hadoop.serializable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @Author: hegx
 * @Description:
 * @Date: 15:10 2017/12/19
 */
public class FlowRunner extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        Configuration config = new Configuration();

        Job job = Job.getInstance(config);

        job.setJarByClass(FlowRunner.class);

        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowRuducer.class);

        job.setMapOutputValueClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int run = ToolRunner.run(new Configuration(), new FlowRunner(), args);
        System.exit(run);
    }
}
