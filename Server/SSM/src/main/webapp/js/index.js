(function () {
    var search=window.location.search.split("=")[1];
    var query=document.getElementById("input");
    query.value=decodeURIComponent(search);
})()