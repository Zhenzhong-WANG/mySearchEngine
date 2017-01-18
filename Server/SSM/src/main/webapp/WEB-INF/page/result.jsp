<%@ page import="java.util.ArrayList" %>
<%@ page import="com.wonggigi.entity.Document" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .item-list,.header{
            width: 50%;
            margin: 30px 0 0 13%;
        }
        .header{
            width: 100%;
            margin-left: 0%;
        }
        .item-content{
            color: #666;
            margin-bottom: 4px;
            margin-top: 4px;
        }
        .item-url{
            color: #006d21;
            display: block;
        }
        .item-title{
            margin-bottom: 4px;
        }
        .item-title a{
            font-size: 18px;
            text-decoration: none;
            color: #001ba0;
        }
        .item-tip span{
            margin: 0 30px 0px 0px;
        }

        form,form input{
            float: left;
        }
        .input-button{
            background-color: #0c8484;
            display: inline-block;
            border: none;
            background-image: url("../../img/button.png");
            background-position: 8px 8px;
            width: 50px;
            height: 50px;
            background-repeat: no-repeat;
        }
        .input{
            height: 50px;
            width: 400px;
            padding: 1px 8px 1px 8px;
            outline: none;
            border: 1px solid #a9a9a9;
            font-size: 20px;
            margin-left: 18px;
        }
        .input-title{
            color: #666666;
            font-size: 44px;
            float: left;
            font-family: "Segoe UI",Segoe,Tahoma,Arial,Verdana,sans-serif;
        }
       .header{
           overflow: hidden;
       }
        .item{
            margin-bottom: 22px;
        }
    </style>
</head>
<body>
    <%
        PrintWriter outp = response.getWriter();
        ArrayList<Document> documentArrayList=(ArrayList<Document>)request.getAttribute("list");
        outp.println("<div class=\"header\">\n" +
                "        <span class=\"input-title\">Search</span>\n" +
                "        <form action=\"search.action\" method=\"get\">\n" +
                "            <input id='input' class=\"input\" type=\"text\" name=\"query\"/>\n" +
                "            <input type=\"submit\"  class=\"input-button\" value=\"\"/>\n" +
                "        </form>\n" +
                "    </div>");
        outp.println("<div class=\"item-list\">");
        outp.println("<p>查询结果："+documentArrayList.size()+"条</p>");
        outp.println("<p>查询时间："+request.getAttribute("time")+"s</p>");
    %>
    <%
        for (Document document:documentArrayList){
            outp.println(" <div class=\"item\">");
            outp.println("<p class=\"item-title\">\n" +
                        "                <a href="+document.getUrl()+">"+document.getTitle()+"</a>\n" +
                        "            </p>");
            outp.println("<p class=\"item-content\">\n" +
                         document.getContent()+
                        "            </p>");
            outp.println("<span class=\"item-url\">\n" +
                        document.getUrl()+
                        "            </span>");
            outp.println("<div class=\"item-tip\">\n" +
                        "                <span>PangRank值:"+document.getPr()+"</span>\n" +
                        "                <span>BM25值:"+document.getBm25()+"</span>\n" +
                        "                <span>综合值:"+document.getBm25Pr()+"</span>\n" +
                        "            </div>");
            outp.println("</div>");
            }
    %>
    <%
    outp.println("</div>");
    %>
    <!--
          <div class="item">
              <p class="item-title">
                  <a href="">为什么Java的并发备受推崇？</a>
              </p>
              <p class="item-content">
                  java的并发并不强调最快，而是强调定义的精确，避免二义性。 我们知道java或c的并发sdk都根据同一个模型，也就是线程与锁模型，这种模型更接近硬件的工作方式的本质（但实际还是有一些区别）。 来说说java并发实现的优点
              </p>
              <span class="item-url">
                  https://www.zhihu.com/question/52616541/answer/141119404
              </span>
              <div class="item-tip">
                  <span>PangRank值:0.343867</span>
                  <span>BM25值:8.42342</span>
                  <span>综合值:0.9821</span>
              </div>
          </div>-->
    <script type="text/javascript" src="./js/index.js"></script>
</body>
</html>