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
        <% 
            String error = request.getParameter("error");
        %>
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
                        <li class="breadcrumb-item">Tạo mới blog</li>
                    </ul>
                </div>

                <form class="row" action="blogcrud" method="POST"  enctype="multipart/form-data">
                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Tạo mới blog</h3>
                            <div class="tile-body">
                                <div class="row element-button">
                                    <div class="col-sm-2">
                                        <a class="btn btn-add btn-sm" href="blogcrud?action=InsertBlogSubcategory" title="Thêm"><i class="fas fa-plus"></i>
                                            Tạo mới thể loại</a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Tiêu đề</label>
                                        <input class="form-control" type="text" name="title" required>
                                        <% 
                                            if (error != null && error.equals("2")) {
                                        %>
                                        <p class="text-warning">Tiêu đề blog đã tồn tại</p>
                                        <% } %>
                                    </div>


                                    <div class="form-group col-md-6">
                                        <label class="control-label">Thể loại</label>
                                        <select class="form-control" name="parentID">
                                            <c:forEach items="${ListPI}" var="c">
                                                <option value="${c.parentID}">${c.title}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <label class="control-label">Mô tả ngắn</label>
                                        <textarea class="form-control" name="description" required></textarea>
                                    </div>

                                    <div class="form-group col-md-12">
                                        <label class="control-label">Nội dung tin</label>
                                        <textarea class="form-control"  name="content" id="default" >${textareaData}</textarea>
                                    </div>

                                    <div class="form-group">

                                        <div class="col-md-12">
                                            <label  class="control-label">Ảnh chính cho blog</label>
                                            <% 
                                            if (error != null && error.equals("1")) {
                                            %>
                                            <p class="text-warning">File không hợp lệ</p>
                                            <% } %>
                                        </div>
                                        <div class="col-md-4" style="margin-top: 20px;">
                                            <div id="myfileupload">
                                                <input type="file" id="uploadfile" name="ImageUpload"  onchange="readURL(this);" />
                                            </div>
                                            <div id="thumbbox">
                                                <img width="600px" alt="Lỗi" id="thumbimage" style="display: none" />
                                                <a class="removeimg" href="javascript:"></a>
                                            </div>
                                            <div id="boxchoice">
                                                <a href="javascript:" class="Choicefile" onclick="document.getElementById('uploadfile').click();"><i class="fas fa-cloud-upload-alt"></i> Chọn ảnh thay đổi</a>
                                                <p style="clear:both"></p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <button class="btn btn-save" type="submit">Lưu lại</button>
                            <!<!-- send input action to servlet to know what action need -->
                            <input type="hidden" name="user" value="${sessionScope.acc.accID}">
                            <input type="hidden" name="action" value="InsertBlog"/>
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

        <script src="tinymce/tinymce.min.js"></script>
        <script src="assets/Staff/js/TinyMCE.js"></script>
    </body>
</html>
