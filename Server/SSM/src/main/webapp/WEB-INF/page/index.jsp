<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery/jquery-1.4.min.js"></script>
</head>
<body>
<h2>Spring+Spring MVC+Mybatis</h2>
<div style="display: inline-block">
<h4>Add user</h4>
<p>Username</p><input type="text" id="add_username"/>
<p>Password</p><input type="text" id="add_password"/>
<span style="background-color: aqua;padding: 10px" onclick="submit()">提交</span>

<h4>Update user</h4>
<p>Username</p><input type="text"  id="update_username"/>
<p>Password</p><input type="text" id="update_password"/>
<span style="background-color: aqua;padding: 10px" onclick="update()">提交</span>
<h4>Remove user</h4>
<p>Username</p><input type="text" id="delete_username"/>
<span style="background-color: aqua;padding: 10px" onclick="deleteU()">提交</span>

</div>
<div style="display: inline-block; position: absolute;left: 260px;top: 50px">
<h4>All user</h4>
<div id="list">
</div>
    </div>
<script>
    function getAllUser(){
        var url = "/userlist.action";
        $.ajax({
            url:url,
            type:"GET",
            // contentType : 'application/json;charset=utf-8',
            data : null,
            success:function(data){
                if(data!=null){
                    console.log(data)
                    document.getElementById("list").innerHTML="";
                    for(var i=0;i<data.length;i++){
                        var p=document.createElement("p");
                        p.style.backgroundColor="aqua";

                        p.innerHTML="UserName:  "+data[i].username+"  Password:  "+data[i].password;
                        document.getElementById("list").appendChild(p);
                    }
                }
            }
        });
    }
    function  submit(){
        var url = "/adduser.action";
        var username=document.getElementById("add_username").value;
        var password=document.getElementById("add_password").value;
        console.log(username+","+password)
        var json=JSON.stringify({username:username,password:password});
        $.ajax({
            url:url,
            type:"POST",
            contentType : 'application/json;charset=utf-8',
            data :json ,
            success:function(data){
                document.getElementById("add_username").value=document.getElementById("add_password").value="";
                getAllUser();
            }
        });
    }
    function  update(){
        var url = "/update.action";
        var username=document.getElementById("update_username").value;
        var password=document.getElementById("update_password").value;
        console.log(username+","+password)
        var json=JSON.stringify({username:username,password:password});
        $.ajax({
            url:url,
            type:"POST",
            contentType : 'application/json;charset=utf-8',
            data :json ,
            success:function(data){
                document.getElementById("update_username").value=document.getElementById("update_password").value="";
                getAllUser();
            }
        });
    }

    function deleteU(){
        var url = "/delete.action";
        var username=document.getElementById("delete_username").value;
        var password=null;
        console.log(username+","+password)
        var json=JSON.stringify({username:username,password:password});
        $.ajax({
            url:url,
            type:"POST",
            contentType : 'application/json;charset=utf-8',
            data :json ,
            success:function(data){
                document.getElementById("delete_username").value="";
                getAllUser();
            }
        });
    }
    window.onload=function(){
       getAllUser();
    }
</script>
</body>
</html>
