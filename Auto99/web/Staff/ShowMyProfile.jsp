<%-- 
    Document   : ServiceManagement
    Created on : Sep 23, 2023, 9:55:50 PM
    Author     : hieuHT
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
                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css" />

                    <link rel="stylesheet" href="./assets/Staff/css/main.css">

                    <!-- Main CSS-->
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

                    <!-- Font-icon css-->
                    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">


                    <title>show employee info</title>
                </head>

                <body>
                    <div class="wrapper">

                        <!--    Navbar-->
                        <%@ include file="NavbarForStaffPage.jsp" %>

                            <div class="container">

                                <div class="app-title">
                                    <ul class="app-breadcrumb breadcrumb">
                                        <li class="breadcrumb-item">MY PROFILE</li>
                                    </ul>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 ">
                                        <div class="tile">
                                            <div class="tile-body">
                                                <!-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->
                                                <form id="FormChangeIMG" action="myemployeeprofile" method="POST" enctype="multipart/form-data">
                                                    <!--  send input action to servlet to know what action need -->
                                                    <input type="hidden" name="ACTION" value="UpdateIMGEmployee" />
                                                    <input type="hidden" name="employeeID" value="${employeeProfile.getEmployeeID()}">
                                                    <fieldset class="form-group col-md-12 ">
                                                        <div class="row d-flex justify-content-around">
                                                            <legend>Ảnh Nhân viên</legend>
                                                            <div class=" form-group ">
                                                                <div class="col-md-12">
                                                                    <p class="text-warning">${mess}</p>
                                                                    <div id="thumbbox" class="align-items-center" style="height: 20rem;width: 20rem">
                                                                        <img alt="Lỗi" id="thumbimage" src="img/EmployeeIMG/${employeeProfile.getEmployeeID()}/${employeeProfile.getImg()}" onclick="document.getElementById('uploadfile').click();" />

                                                                        <a class="removeimg" href="javascript:"></a>
                                                                        <div id="myfileupload">
                                                                            <input type="file" id="uploadfile" name="ImageUpload" onchange="readURL(this);" />
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </fieldset>


                                                    <script>
                                                        // Tạo URL cho ảnh ban đầu bằng cách sử dụng dữ liệu từ employeeProfile
                                                        var imgSrc = "img/EmployeeIMG/${employeeProfile.getEmployeeID()}/${employeeProfile.getImg()}";
                                                        // Function để load ảnh ban đầu
                                                        function loadInitialImage() {
                                                            // Đặt thuộc tính src của phần tử hình ảnh để hiển thị ảnh ban đầu
                                                            $("#thumbimage").attr('src', imgSrc).show();
                                                        }

                                                        // Function để đọc đường dẫn URL của ảnh mới khi chọn ảnh mới
                                                        function readURL(input) {
                                                            // Kiểm tra xem đã chọn tệp hình ảnh chưa
                                                            if (input.files && input.files[0]) {
                                                                var reader = new FileReader();
                                                                reader.onload = function(e) {
                                                                    // Đặt thuộc tính src của phần tử hình ảnh để hiển thị ảnh đã chọn dưới dạng URL dữ liệu
                                                                    $("#thumbimage").attr('src', e.target.result);
                                                                };
                                                                // Đọc tệp hình ảnh đã chọn dưới dạng URL dữ liệu
                                                                reader.readAsDataURL(input.files[0]);
                                                            }
                                                        }

                                                        // Gọi function để tải ảnh ban đầu khi trang được tải
                                                        $(document).ready(function() {
                                                            loadInitialImage();
                                                        });

                                                        var loadFile = function(event, id) {
                                                            var output = document.getElementById('subImg-' + id);
                                                            output.src = URL.createObjectURL(event.target.files[0]);
                                                            output.onload = function() {
                                                                URL.revokeObjectURL(output.src) // free memory
                                                            }
                                                        };

                                                        function confirmCancel(event) {
                                                            var confirmation = confirm("Bạn có chắc chắn muốn huỷ thêm ảnh profile mới?");
                                                            if (!confirmation) {
                                                                event.preventDefault(); // Ngăn chặn điều hướng đến đường dẫn URL
                                                            }
                                                        }

                                                        function changeSubImg() {
                                                            document.getElementById("FormChangeIMG").submit();
                                                        }


                                                        function submitForm() {
                                                            var currentImageSrc = document.getElementById("thumbimage").src;
                                                            if (currentImageSrc !== imgSrc) {
                                                                // Image source has changed, submit the form
                                                                document.getElementById("FormChangeIMG").submit();
                                                            } else {
                                                                // Image source has not changed, perform any other action you need
                                                                alert("You have not changed the image.");
                                                            }
                                                        }
                                                    </script>
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
                                                        
                                                        #uploadfile {
                                                            display: none;
                                                        }
                                                        
                                                        #thumbbox {
                                                            position: relative;
                                                            /* Để chứa ảnh và nút xóa */
                                                            width: 200px;
                                                            /* Độ rộng của thẻ div cha (thay đổi theo kích thước mong muốn) */
                                                            height: auto;
                                                            /* Tự động tính chiều cao dựa trên tỷ lệ khung hình của ảnh */
                                                            overflow: hidden;
                                                            /* Để ẩn phần ngoài khung nếu ảnh lớn hơn */
                                                        }
                                                        
                                                        #thumbimage {
                                                            border-radius: 50%;
                                                            width: 100%;
                                                            /* Ảnh tự động điền toàn bộ chiều rộng của div cha */
                                                            height: 100%;
                                                            /* Tự động tính chiều cao dựa trên tỷ lệ khung hình của ảnh */
                                                            display: block;
                                                            /* Loại bỏ khoảng trắng dư thừa ở dưới ảnh */
                                                        }
                                                        
                                                        .removeimg {
                                                            position: absolute;
                                                            top: 5px;
                                                            right: 5px;
                                                            /* Các thuộc tính khác cho nút xóa */
                                                        }
                                                        
                                                        label {
                                                            text-align: left;
                                                        }
                                                    </style>

                                                    <!-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------->
                                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                                        <h4 class="text-right">Profile Settings</h4>
                                                    </div>
                                                    <div class="row mt-2">
                                                        <div class="col-md-6">
                                                            <label class="labels">Name</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getFirstName()}'/>" readonly="readonly" placeholder="Name">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Surname</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getLastName()}'/>" readonly="readonly" placeholder="Surname">
                                                        </div>
                                                    </div>
                                                    <div class="row mt-3">
                                                        <div class="col-md-6">
                                                            <label class="labels">Mã nhân viên</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getEmployeeID()}'/>" readonly="readonly" placeholder="Mã nhân viên">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">NoID</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getIDNo()}'/>" readonly="readonly" placeholder="NoID">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Địa chỉ</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getAddress()}'/>" readonly="readonly" placeholder="Địa chỉ">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Ngày bắt đầu làm việc</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getStartDate()}'/>" readonly="readonly" placeholder="Ngày bắt đầu làm việc">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Email</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getEmail()}'/>" readonly="readonly" placeholder="Email">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Ngày sinh</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getDOB()}'/>" readonly="readonly" placeholder="Ngày sinh">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Số điện thoại</label>
                                                            <input type="text" class="form-control" value="<c:out value='${employeeProfile.getPhoneNumber()}'/>" readonly="readonly" placeholder="Số điện thoại">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <label class="labels">Giới tính</label>
                                                            <input type="text" class="form-control" placeholder="" value="<c:out value=" ${employeeProfile.isGender()==true ? 'Nam' : 'Nữ'} " />" readonly="">
                                                        </div>
                                                    </div>
                                                    <!--                                <div class="row mt-3">
                                                                        <div class="col-md-6"><label class="labels">Country</label><input type="text" class="form-control" placeholder="country" value=""></div>
                                                                        <div class="col-md-6"><label class="labels">State/Region</label><input type="text" class="form-control" value="" placeholder="state"></div>
                                                                    </div>
                                                                    <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="button">Save Profile</button></div>-->
                                                    <div style="padding-top: 2rem; padding: 40px;text-align: center; ">
                                                        <button class="btn btn-save" onclick="submitForm()">Lưu lại</button>
                                                        <a class="btn btn-cancel" href="adminhome" onclick="confirmCancel(event);">Hủy bỏ</a>
                                                        <c:if test="${msg != null}">
                                                            <span class="text-success">${msg}</span>
                                                        </c:if>
                                                        <a href="change" class="btn btn-dark"> Đổi mật khẩu</a>
                                                </form>
                                                </div>
                                            </div>
                                            <!--                            </div>            -->
                                        </div>

                                    </div>
                                </div>
                            </div>


                            <!-- Essential javascripts for application to work-->
                            <script src="js/jquery-3.2.1.min.js"></script>
                            <script src="js/popper.min.js"></script>
                            <script src="js/bootstrap.min.js"></script>
                            <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
                            <script src="src/jquery.table2excel.js"></script>
                            <script src="js/main.js"></script>
                            <!-- The javascript plugin to display page loading on top-->
                            <script src="js/plugins/pace.min.js"></script>
                            <!-- Page specific javascripts-->
                            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
                            <!-- Data table plugin-->
                            <script type="text/javascript" src="js/plugins/jquery.dataTables.min.js"></script>
                            <script type="text/javascript" src="js/plugins/dataTables.bootstrap.min.js"></script>

                            <script>
                                function sendSelectedServiceIDs() {
                                    // Create a hidden input element to add an attribute
                                    var hiddenInput = document.createElement("input");
                                    hiddenInput.type = "hidden";
                                    hiddenInput.name = "action"; // Change to the desired attribute name
                                    hiddenInput.value = "deleteAllByServiceID"; // Change to the desired attribute value

                                    // Append the hidden input element to the form
                                    var form = document.getElementById('SendAllServiceIDChecked');
                                    form.appendChild(hiddenInput);
                                    // Submit the form
                                    form.submit();
                                }
                            </script>

                </body>

                </html>