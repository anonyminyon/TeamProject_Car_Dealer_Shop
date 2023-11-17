<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta charset="UTF-8">    
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="./assets/Staff/css/AdminPage.css">

        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>

        <link rel="stylesheet" href="./assets/Staff/css/main.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="ckeditor/ckeditor.js"></script>
        <script>
            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);
                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $('.Choicefile').css('background', '#14142B');
                $('.Choicefile').css('cursor', 'default');
                $(".removeimg").show();
                $(".Choicefile").unbind('click');

            }
            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();

                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").bind('click', function () {
                        $("#uploadfile").click();
                    });
                    $('.Choicefile').css('background', '#14142B');
                    $('.Choicefile').css('cursor', 'pointer');
                    $(".filename").text("");
                });
            })

            var loadFile = function (event, id) {
                var output = document.getElementById('subImg-' + id);
                output.src = URL.createObjectURL(event.target.files[0]);
                output.onload = function () {
                    URL.revokeObjectURL(output.src) // free memory
                }
            };

            function confirmCancel(event) {
                var confirmation = confirm("Bạn có chắc chắn muốn huỷ thêm blog mới?");
                if (!confirmation) {
                    event.preventDefault(); // Ngăn chặn điều hướng đến đường dẫn URL
                }
            }
            function changeSubImg() {
                document.getElementById("myForm").submit();
            }

        </script>

        <title>Blog List</title>
    </head>
    <body>
        <style>
            .Choicefile {
                display: block;
                background: #14142B;
                border: 1px solid #fff;
                color: #fff;
                width: 150px;
                text-align: center;
                text-decoration: none;
                cursor: pointer;
                padding: 5px 0px;
                border-radius: 5px;
                font-weight: 500;
                align-items: center;
                justify-content: center;
            }


            #uploadfile{
                display: none;
            }

            #thumbbox {
                position: relative;
                width: 100%;
                margin-bottom: 20px;
            }
        </style>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item"><a href="bloglist">Danh sách tin</a></li>
                        <li class="breadcrumb-item"><a href="blogcrud?action=InsertBlog">Tạo mới blog</a></li>
                        <li class="breadcrumb-item">Tạo mới thể loại</li>
                    </ul>
                </div>

                <form class="row" action="blogcrud" method="POST"  enctype="multipart/form-data">
                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Tạo mới thể loại blog</h3>
                            <div class="tile-body">
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Blog Category</label>
                                        <select class="form-control" name="blogCategoryID">
                                            <c:forEach items="${ListBC}" var="c">
                                                <option value="${c.blogCategoryID}">${c.blogCategory}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="control-label">Tên của thể loại</label>
                                        <input class="form-control" type="text" name="title" required>
                                    </div>         
                                </div>
                            </div>
                            <button class="btn btn-save" type="submit">Lưu lại</button>
                            <!<!-- send input action to servlet to know what action need -->
                            <input type="hidden" name="action" value="InsertBlogSubcategory"/>
                            <a class="btn btn-cancel" href="bloglist" onclick="confirmCancel(event);">Hủy bỏ</a>
                            <c:if test="${msg != null}">
                                <span class="text-success">${msg}</span>
                            </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Essential javascripts for application to work-->
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>

        <script src="js/plugins/pace.min.js"></script>
    </body>
</html>
