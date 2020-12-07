<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String errorMessage = (String)request.getAttribute("error-message");
%>

<c:if test="${not empty signupError}">
    <script>
        window.addEventListener("load",function() {
            alert("Sign up feature not available");
        });
    </script>
</c:if>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <title>Message Board</title>

  <link rel="stylesheet" href="bootstrap.css">
  <link rel="stylesheet" href="style.css">

</head>


<body>
  <script type="text/javascript">
    <%= errorMessage != null ? "alert(\"" + errorMessage + "\");" : "" %>
  </script>

<div class="app">

    <!-- NAV BAR -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand">SOEN387 A2</a>
        <div class="collapse navbar-collapse my-2 mr-2 my-lg-0" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link">${user.name}</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link">User ID: ${user.userId}</a>
                </li>
                <!-- @ACTION LOGOUT BUTTON -->
                <li class="nav-item">
                    <!-- <a type="button" class="nav-link active">Log out</a>-->
                    <form action="DownloadServlet" method="POST" class="form-login">
                        <input type="submit" name="logout" value="Sign out" class="btn btn-primary">
                    </form>
                </li>
                <li class="nav-item active dropdown">
                    <a class="nav-link dropdown-toggle"  role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        #
                    </a>
                    <!-- @ACTION DROPDOWN FOR # OF POST IN DASHBOARD -->
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                        <a class="dropdown-item" href="DownloadServlet?numPosts=5">5</a>
                        <a class="dropdown-item" href="DownloadServlet?numPosts=10">10</a>
                        <a class="dropdown-item" href="DownloadServlet?numPosts=20">20</a>
                    </div>
                </li>
            </ul>
            <!-- @ACTION SEARCH FIELD 1 HASHTAG -->
            <div class="my-2 mr-2 my-lg-0">
                <form id="searchform" action="DownloadServlet" method="POST" class="form-login"></form>
            </div>
            <div class="my-2 mr-2 my-lg-0">
                <input form="searchform"  size="5" name = "search-post-user-id" type="text" class="form-control form-control-sm" placeholder="User ID"/>
            </div>
            <div class="my-2 mr-2 my-lg-0">
                <input form="searchform" size="5" name = "search-post-date-from" type="text" class="form-control form-control-sm" placeholder="Date from"/>
            </div>
            <div class="my-2 mr-2 my-lg-0">
                <input form="searchform" size="5" name = "search-post-date-to" type="text" class="form-control form-control-sm" placeholder="Date to"/>
            </div>
            <div class="my-2 mr-2 my-lg-0">
                <input form="searchform" size="5"  name = "search-post-hash-tag" type="text" class="form-control form-control-sm" placeholder="Hash Tag"/>
            </div>
            <div class="my-2 mr-2 my-lg-0">
                <input form="searchform" size="5"  name = "search-post-num-string" type="text" class="form-control form-control-sm" placeholder="#RESULT"/>
            </div>
            <div class="my-2 mr-2 my-lg-0">
                <input form="searchform" size="5"  type="submit" name="search-post" value="Search" class="btn btn-primary">
            </div>

        </div>
    </nav>

    <!-- END OF NAV BAR -->

    <!-- POST CONTAINER -->
    <div class="post_list mx-auto">
        <!-- RENDER MULTIIPLE POST -->
        <div class="post">
            <div class="card">
                <div class="card-body">
                    <c:forEach var="post" items="${posts}">
                        <div class="card-title d-flex">
                            <h3 class="p-2"><strong>${post.title}</strong>
                                <span class="badge badge-secondary">Post ID: ${post.postId}</span>
                                <c:if test="${post.updated==true}">
                                    <span class="badge badge-secondary">Edited</span>
                                </c:if>
                            </h3>

                            <div class="ml-auto p-2">
<%--                                <span class="nav-item active dropdown">--%>
                                    <a class="nav-link dropdown-toggle" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"/>
                                    <!-- @ACTION DROPDOWN FOR # OF POST IN DASHBOARD -->
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                        <c:if test="${post.userId==user.userId}">
                                            <a id= "${post.postId}" class="dropdown-item postid" href="#${post.postId}" data-toggle="modal" data-target="#editPostModal">Edit</a>
                                            <!-- <a class="dropdown-item" href="#${post.postId}">Delete</a> -->
                                            <input id= "delete${post.postId}" form="edit_delete_post" type="submit" name="delete-post" value="Delete" class="dropdown-item">
                                        </c:if>

                                        <c:if test="${post.userId!=user.userId}">
                                            <a class="dropdown-item" href="#">More</a>
                                        </c:if>
                                        <input form = "download_attachment" id="download${post.postId}" class="dropdown-item" type="submit" name="download-file" value="Download Attachment">
                                    </div>
<%--                                </span>--%>
                            </div>

                            <!-- <span class="badge badge-secondary">User ID: ${post.userId}</span> -->
                        </div>


                        <c:if test="${post.updated==true}">
                            <h5 class="card-subtitle text-muted mb-2">Updated at ${post.updateDate}</h5>
                        </c:if>


                        <h5 class="card-subtitle text-muted mb-2">Post from ${post.postDate}</h5>

                        <h5 class="card-subtitle text-muted mb-2">By User ${post.userId}</h5>

                    <span class="card-text">${post.text}</span>
                        <hr/>
                    </c:forEach>

                    <div class="owner action">
                        <form id="download_attachment" class="form-inline my-2 my-lg-0" class="get-post-form" method="POST">
                        </form>
                        <form id="edit_delete_post" action="DownloadServlet" method="POST" enctype="multipart/form-data" class="form-login">
                        </form>
                        <input id="edit-post" value = "" form="edit_delete_post" required="required" name = "update-delete-post-id" type="hidden"/>
                        <input form="download_attachment" type="hidden" value= "" maxlength="4" size="4" id="download-file-post-id" name="download-file-post-id">
                    </div>
                </div>
            </div>
        </div>
    </div>

  <!-- FLOAT OBJ -->
  <div class="floatObj">
    <div>
      <span class="d-inline-block" tabIndex="0" data-toggle="tooltip" title="Disabled tooltip">
        <button type="button" class="btn btn-primary" data-toggle="modal"  data-target="#newPostModal">New Post</button>
      </span>
    </div>
  </div>
  <!-- END OF FLOAT OBJ -->

    <form id="newpost" action="DownloadServlet" method="POST" enctype="multipart/form-data" class="form-login">
    </form>


    <!-- NEW POST MODAL -->
    <div class="modal fade" id="newPostModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">NEW POST</h5>
                    <button type="button" class="close" data-dismiss="modal" onClick={handleClose}>
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body ">

<<<<<<< HEAD:target/soen387_a3-1.0-SNAPSHOT/message-board.jsp
=======
                    <div class="TITLE mb-2">
                        <input name="create-post-group" form="newpost" class="form-control" placeHolder="Group"/>
                    </div>
>>>>>>> main:target/soen387_a2-1.0-SNAPSHOT/message-board.jsp

                    <div class="FILE mb-2">
                        <input name="create-post-file" form="newpost" type="file" class="form-control-file" id="exampleFormControlFile1"/>
                    </div>

                    <div class="GROUP mb-2">
                        <input name="create-post-group" form="newpost" class="form-control" placeHolder="Group"/>
                    </div>

                    <div class="TITLE mb-2">
                        <input name="create-post-title" form="newpost" class="form-control" placeHolder="Title"/>
                    </div>

                    <div class="CONTENT">
                        <textarea name="create-post-text" form="newpost" class="form-control" id="exampleFormControlTextarea1" rows="3" placeHolder="Text"></textarea>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" onClick={handleClose} > Cancel </button>
                    <input form="newpost" type="submit" name="create-post" value="Post" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>

    <!-- EDIT POST MODAL -->
    <div class="modal fade" id="editPostModal" tabIndex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Editing POST</h5>
                    <button type="button" class="close" data-dismiss="modal" onClick={handleClose}>
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body ">

                    <div class="FILE mb-2">
                        <input name="update-post-file" form="edit_delete_post" type="file" class="form-control-file"/>
                    </div>

                    <div class="TITLE mb-2">
                        <input id="edit_post_title" name="update-post-title" form="edit_delete_post" class="form-control" placeHolder="Title"/>
                    </div>

                    <div class="CONTENT">
                        <textarea id="edit_post_text" name="update-post-text" form="edit_delete_post" class="form-control" rows="3" placeHolder="Text"></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" onClick={handleClose} > Cancel </button>
                    <input form="edit_delete_post" type="submit" name="update-post" value="Post" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>


</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script>
      $(document).ready(function(){
          $("a").click(function(event){
              //alert("You've clicked: " + event.target.nodeName + ", id: " + event.target.id);
              if(event.target.id){
                  //
                  <c:set var = "edit_id" scope = "session" value = "${event.target.id}"/>
                  <c:forEach var="post" items="${posts}">
                  if("${post.postId}"==event.target.id) {
                      document.getElementById("edit_post_title").value="${post.title}";
                      document.getElementById("edit_post_text").value="${post.text}";
                  }
                  </c:forEach>
                  $('#edit-post').val(event.target.id);
              }
          });

          $("input").click(function(event){
              if(event.target.id){
                  //alert("You've clicked: " + event.target.nodeName + ", id: " + event.target.id);
                  $('#edit-post').val(event.target.id.replace("delete",""));
              }
          });

          $("input").click(function(event){
              if(event.target.id){
                  //alert("You've clicked: " + event.target.nodeName + ", id: " + event.target.id);
                  $('#download-file-post-id').val(event.target.id.replace("download",""));
              }
          });
      });
  </script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>


</html>