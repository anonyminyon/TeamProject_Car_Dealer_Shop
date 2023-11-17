<%-- 
    Document   : AddService
    Created on : Sep 24, 2023, 12:55:58 AM
    Author     : Hieu
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
                        <li class="breadcrumb-item">Thêm dịch vụ</li>
                    </ul>
                </div>

                <form class="row" action="servicecrud" method="POST">
                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Tạo mới dịch vụ</h3>
                            <div class="tile-body">

                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <label class="control-label">Tên dịch vụ</label>
                                        <input class="form-control" type="text" name="serviceType">
                                        <c:if test="${errorServiceType != null}">
                                            <span class="text-danger">${errorServiceType}</span>
                                        </c:if>
                                    </div>

                                    <div class="form-group col-md-3">
                                        <label class="control-label">Giá dịch vụ</label>
                                        <input class="form-control" type="number" name="servicePrice">
                                        <c:if test="${errorServicePrice != null}">
                                            <span class="text-danger">${errorServicePrice}</span>
                                        </c:if>
                                    </div>

                                    <div class="form-group col-md-12">
                                        <label class="control-label">Mô tả dịch vụ</label>
                                        <textarea class="form-control" name="serviceContent" id="mota">
                                            
                                        </textarea>

                                    </div>

                                </div>
                            </div>
                            <a class="btn btn-save" href="#" id="saveButton">Lưu lại</a>
                            <!<!-- send input action to servlet to know what action need -->
                            <input type="hidden" name="action" value="AddANewService"/>
                            <a class="btn btn-cancel" href="servicemanagement">Hủy bỏ</a>
                            <c:if test="${msg != null}">
                                <span class="text-success">${msg}</span>
                            </c:if>
                        </div>
                    </div>
                </form>


                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        const saveButton = document.querySelector("#saveButton");

                        saveButton.addEventListener("click", function (event) {
                            event.preventDefault(); // Prevent the default anchor click behavior

                            // Check if any of the input fields are empty
                            const serviceType = document.querySelector("input[name='serviceType']").value;
                            const servicePrice = document.querySelector("input[name='servicePrice']").value;
                            const serviceContent = document.querySelector("textarea[name='serviceContent']").value;

                            if (serviceType.trim() === "" || servicePrice.trim() === "" || serviceContent.trim() === "") {
                                // Show the validation modal
                                $('#validationModal').modal('show');
                            } else {
                                // All fields are filled, you can submit the form
                                document.forms[0].submit();
                            }
                        });
                    });
                </script>


                <div class="modal" id="validationModal" tabindex="-1" role="dialog" >
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Validation Error</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>Please fill in all required fields.</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                </body>
                </html>
