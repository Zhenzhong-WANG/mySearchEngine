import java.io.*;
import java.sql.*;
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
          //  System.out.println(key+" is ok");
            context.write(new Text(key.toString()+"\t"+docF), new Text(documenmts));
        }
    }

    private static void intoMySql(){
        String filePath="//home//Projects//SearchEngine//InvertedIndex_MapReduce//MapReduce//output//part-r-00000";
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
                    String[] line=lineTxt.split("\t");
                    String word=line[0];
                    int df=Integer.parseInt(line[1]);
                    String list=line[2];

                    String sql2 = "INSERT INTO InvertedIndex VALUES(?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(sql2);
                    pst.setString(1,word);
                    pst.setInt(2,df);
                    pst.setString(3,list);
                    pst.executeUpdate();
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
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
        intoMySql();
    }
}

