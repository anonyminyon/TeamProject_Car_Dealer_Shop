<%-- 
    Document   : UpdateServiceAdvanced
    Created on : Oct 5, 2023, 8:46:55 AM
    Author     : hieu
--%>

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
        <script type="text/javascript" src="/ckeditor/ckeditor.js"></script>
        <script src="http://code.jquery.com/jquery.min.js" type="text/javascript"></script>
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
        </script>

        <title>Car List</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item"><a href="servicemanagement">Danh sách dịch vụ</a></li>
                        <li class="breadcrumb-item">Sửa dịch vụ</li>
                    </ul>
                </div>

                <form class="row" action="servicecrud" method="POST">
                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Sửa dịch vụ</h3>
                            <div class="tile-body">

                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Mã dịch vụ</label>
                                        <input class="form-control" type="text" name="serviceID" placeholder="${service.getServiceID()}" value="${service.getServiceID()}" readonly/>
                                    </div>
                                        
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Tên dịch vụ</label>
                                        <input class="form-control" type="text" name="serviceType" value="${service.getServiceType()}"/>
                                        <c:if test="${errorServiceType != null}">
                                            <span class="text-danger">${errorServiceType}</span>
                                        </c:if>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Trạng thái dịch vụ</label>
                                        <input class="form-control" type="text" name="serviceStatus" placeholder="${service.isServiceStatus()}" readonly/>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Giá dịch vụ</label>
                                        <fmt:formatNumber value="${money}" type="number" pattern="#,###" var="formattedValue" />
                                        <input class="form-control" type="text" name="servicePrice" value="${formattedValue} VNĐ"  />
                                        <c:if test="${errorServicePrice != null}">
                                            <span class="text-danger">${errorServicePrice}</span>
                                        </c:if>
                                    </div>

                                    <div class="form-group col-md-12">
                                        <label class="control-label">Mô tả dịch vụ</label>
                                        <textarea class="form-control" name="serviceContent" id="mota">
                                            ${service.getServiceContent()}
                                        </textarea>
                                            
                                    </div>

                                </div>
                            </div>
                            <button class="btn btn-save" type="submit">Lưu lại</button>
                            <!<!-- send input action to servlet to know what action need and data of current paging and filter-->
                            <input type="hidden" name="action" value="updateServiceAdvanced"/>
                            <input type="hidden" name="index" value="${index}"/>
                            <input type="hidden" name="search" value="${search}"/>
                            <input type="hidden" name="pageSize" value="${pageSize}"/>
                            <input type="hidden" name="serviceStatusFilter" value="${serviceStatusFilter}"/>
                            <!------------------------------------------------------------------->
                            <a class="btn btn-cancel" href="javascript:history.back();" class="cancel-link">Cancel</a>
                            <c:if test="${msg != null}">
                                <span class="text-success">${msg}</span>
                            </c:if>
                        </div>
                    </div>
                </form>
                <!--
                                 Essential javascripts for application to work
                                <script src="js/jquery-3.2.1.min.js"></script>
                                <script src="js/popper.min.js"></script>
                                <script src="js/bootstrap.min.js"></script>
                                <script src="js/main.js"></script>
                                <script src="js/plugins/pace.min.js"></script>
                                <script>
                                                            const inpFile = document.getElementById("inpFile");
                                                            const loadFile = document.getElementById("loadFile");
                                                            const previewContainer = document.getElementById("imagePreview");
                                                            const previewContainer = document.getElementById("imagePreview");
                                                            const previewImage = previewContainer.querySelector(".image-preview__image");
                                                            const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
                                                            inpFile.addEventListener("change", function () {
                                                                const file = this.files[0];
                                                                if (file) {
                                                                    const reader = new FileReader();
                                                                    previewDefaultText.style.display = "none";
                                                                    previewImage.style.display = "block";
                                                                    reader.addEventListener("load", function () {
                                                                        previewImage.setAttribute("src", this.result);
                                                                    });
                                                                    reader.readAsDataURL(file);
                                                                }
                                                            });
                                </script>-->

                </body>
                </html>
