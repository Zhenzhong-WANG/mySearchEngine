<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Search</title>
    <link rel="stylesheet" type="text/css" href="./css/index.css" />
</head>
<body>
    <div class="input-container">
        <span class="input-title">Search</span>
        <form action="search.action" method="get">
            <input class="input" type="text" name="query"/>
            <input type="submit"  class="input-button" value=""/>
        </form>
    </div>
</body>
</html>