<%--
  Created by IntelliJ IDEA.
  User: apatters
  Date: 6/11/2017
  Time: 11:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  </head>
  <body>
    <textarea rows="50" id="text"></textarea>

    <br>
    <textarea id="poster"></textarea>
    <textarea id="content"></textarea>
    <button id="addPost" onclick="addPost()">Add Post</button>

    <br>
    <textarea id="index"></textarea>
    <button id="deletePost" onclick="deletePost()">Delete Post</button>
    <script>

      signIn(getPosts);

      function signIn(callback){
        $.post('/sessions', {username: "Bob", password: "Bob's password"}, callback);
      }


      function getPosts(){
        $.get('/posts', {username: "Bob", poster: "Bob", type:"own"}, updatePosts);
      }

      function updatePosts(posts){
//        var posts = JSON.parse(e.target.response);
        var str = "";
        var idx = 0;
        for(let post of posts){
          str += "idx=" + idx++ + "\n";
          str += post.poster;
          str += "\n";
          str += post.content;
          str += "\n\n";
        }
        $("#text").val(str);
      }

      function addPost() {
        var poster = document.getElementById("poster").value;
        var content = document.getElementById("content").value;

        $.post('/posts', {username: poster, poster, content}, function () {
          getPosts();
        })
      }

      function deletePost(){
        var index = $("#index").val();
        $.ajax({
          url: '/posts' + '?' + $.param({index, username: "Bob"}),
          method: 'DELETE',
          success: getPosts
        });
      }
    </script>
  </body>
</html>
