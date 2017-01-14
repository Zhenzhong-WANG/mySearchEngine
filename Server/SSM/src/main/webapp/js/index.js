(function () {
    var button=document.getElementById("button");
    button.onclick=function () {
        var xhr=new XMLHttpRequest();
        var query=document.getElementById("input")
        if (!query.value)return;
        var queryStr=query.value//encodeURIComponent(query.value)
        xhr.open('GET',"./search.action?query="+queryStr,true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange=function(){
            if (xhr.readyState == 4 && xhr.status == 200 || xhr.status == 304) {
                console.log(xhr.responseText);
            }
        };
        console.log(query.value);
        xhr.send();
    }
})();