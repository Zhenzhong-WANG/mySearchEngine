import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Hashtable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRank {
    private static Hashtable<String,String> hashtable=new Hashtable<>();
    private static int iteration=2;
    public static class PageRankMapper extends Mapper<Object, Text, Text, Text>{
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] lines=value.toString().split("\n");
            for(String line:lines){
                Double probability=Double.parseDouble(line.split("\\s|\\t")[0]);
                String docId=line.split("\\s|\\t")[1];
                hashtable.put(docId,line.split("\\s|\\t")[2]);
                String[] outLinks=line.split("\\s|\\t")[2].split("/");
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
            String vector=key.toString()+" "+hashtable.get(key.toString());
            context.write(new Text(Double.toString(probabilty)),new Text(vector));
        }
    }

    private static void intoMySql(){
        String filePath="//home//Projects//SearchEngine//PageRank_MapReduce//output"+(iteration-1)+"//part-r-00000";
        try {
            File file=new File(filePath);
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
            String url="jdbc:mysql://localhost:3306/SearchEngine?useUnicode=true&characterEncoding=UTF-8";    //JDBC的URL
            Connection conn;
            conn = DriverManager.getConnection(url,"root","908868432");
            System.out.println("成功连接到数据库！");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int row=0;
                while((lineTxt = bufferedReader.readLine()) != null){
                    System.out.println(row++);
                    String[] line=lineTxt.split("\\t|\\s");
                    Double probatility=Double.parseDouble(line[0]);
                    int docId=Integer.parseInt(line[1]);

                    String sql = "INSERT INTO PR VALUES(?,?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1,docId);
                    pst.setDouble(2,probatility);
                    pst.executeUpdate();
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("出错");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        double factor=0.85;
        String input="/home/Projects/SearchEngine/TransferMatrix";
        String output="/home/Projects/SearchEngine/PageRank_MapReduce/output";
        int i=0;
        for(i=0;i<iteration;i++){
            Configuration conf = new Configuration();
            conf.set("factor",String.valueOf(factor));
            conf.set("num","100");
            Job job = Job.getInstance(conf, "PageRank");
            job.setJarByClass(PageRank.class);
            job.setMapperClass(PageRankMapper.class);
          //  job.setCombinerClass(PageRankReducer.class);
            job.setReducerClass(PageRankReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job, new Path(input));
            FileOutputFormat.setOutputPath(job, new Path(output+i+""));
            input=output+i+"";
            job.waitForCompletion(true);
        }
        intoMySql();
        /*
        System.out.println(i);
        for(int j=i-3;j>=0;j--){
            Configuration conf=new Configuration();
            Path path=new Path(output+"j");
            FileSystem fs=path.getFileSystem(conf);
            fs.delete(path,true);
        }
        */
    }
}

