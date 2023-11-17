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
        <script src="https://cdn.ckeditor.com/ckeditor5/39.0.2/classic/ckeditor.js"></script>
        <c:set var="acc" value="${sessionScope.acc}" />

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

            function readSubURL(input) {
                if (input.files && input.files[0]) { // For Firefox and Chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimageSubIMG").attr('src', e.target.result).show();
                    };
                    reader.readAsDataURL(input.files[0]);
                } else { // For IE
                    $("#thumbimageSubIMG").attr('src', input.value).show();
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
        <% 
                            String error = request.getParameter("error");
        %>
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
            /*----------SubIMG------------------------------------------------------------*/
            #uploadfileSubIMG{
                display: none;
            }
            #thumbboxSubIMG {
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
                        <li class="breadcrumb-item"><a href="carlist">Danh sách xe</a></li>
                        <li class="breadcrumb-item"><a href="#">Chỉnh sửa xe</a></li>
                    </ul>
                </div>

                <form class="row" action="carcrud" method="POST"  enctype="multipart/form-data">

                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Chỉnh sửa xe</h3>
                            <div class="tile-body">
                                <div class="row element-button">
                                    <div class="col-sm-2">
                                        <a class="btn btn-add btn-sm" data-toggle="modal" data-target="#exampleModalCenter"><i class="fas fa-folder-plus"></i> Thêm hãng xe</a>
                                    </div>
                                    <div class="col-sm-2">
                                        <a class="btn btn-add btn-sm" data-toggle="modal" data-target="#exampleModalCenter"><i class="fas fa-folder-plus"></i> Thêm loại xe</a>
                                    </div>
                                </div>
                                <div class="row">
                                    <fieldset class="form-group col-md-12">
                                        <legend>Thông tin cơ bản</legend>
                                        <div class="row">
                                            <div class="form-group col-md-12">
                                                <% 
                                                    if (error != null && error.equals("1")) {
                                                %>
                                                <p class="text-warning">Tên xe đã tồn tại </p>
                                                <% } %>
                                            </div>

                                            <input type="hidden" name="carID" value="${car.carID}">


                                            <div class="form-group col-md-4">
                                                <label class="control-label">Tên xe</label>
                                                <input class="form-control" type="text" name="carName" 
                                                       value="${car.getCarName()}" require ="require">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Tình trạng</label>
                                                <select class="form-control" name="statusID">
                                                    <c:forEach items="${status}" var="s">
                                                        <option value="${s.getStatusID()}" ${(s.getStatusID()==car.status.getStatusID())?"selected":""}>${s.getStatusName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Kiểu dáng</label>
                                                <select class="form-control" name="designID">
                                                    <c:forEach items="${design}" var="d">
                                                        <option value="${d.getDesignID()}" ${(d.getDesignID()==car.design.getDesignID())?"selected":""}>${d.getDesign()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Hãng xe</label>
                                                <select class="form-control" name="brandID">
                                                    <c:forEach items="${brand}" var="b">
                                                        <option value="${b.getBrandID()}" ${(b.getBrandID()==car.brandID.getBrandID())?"selected":""}>${b.getBrandName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Xuất sứ </label>
                                                <input class="form-control" type="text" name="madeIn" 
                                                       value="${carGI.getMadeIn()}" require ="require">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Nguyên Liệu </label>
                                                <input class="form-control" type="text" name="fuel"
                                                       value="${carGI.getFuel()}" require="require">
                                            </div>


                                            <div class="form-group col-md-4">
                                                <label class="control-label">Giá bán</label>
                                                <input class="form-control" type="text" name="price" require ="require"
                                                       value="${car.getPrice()}"
                                                       pattern="([0-9]*(\.)?)[0-9]*" 
                                                       title="Xin hãy nhập 1 số thực">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Số ghế ngồi </label>
                                                <input class="form-control" type="number" name="seatNumber"
                                                       value="${carGI.getNumberOfSeat()}" require ="require">
                                            </div>

                                            <c:if test="${acc != null && acc.getRoleID().getRoleID() == 1}">
                                                <div class="form-group col-md-4">
                                                    <label class="control-label">Người tạo </label>
                                                    <input class="form-control" readonly
                                                           value="${car.getCreatedBy().getAccName()}">
                                                </div>

                                                <div class="form-group col-md-4">
                                                    <label class="control-label">Ngày tạo </label>
                                                    <input class="form-control" readonly
                                                           value="${car.getCreatedOn()}">
                                                </div>

                                                <div class="form-group col-md-4">
                                                    <label class="control-label">Người sửa </label>
                                                    <input class="form-control" readonly
                                                           value="${car.getModifiedBy().getAccName()}">
                                                </div>

                                                <div class="form-group col-md-4">
                                                    <label class="control-label">Ngày sửa </label>
                                                    <input class="form-control" readonly
                                                           value="${car.getModifiedOn()}">
                                                </div>
                                            </c:if>

                                        </div>
                                    </fieldset>

                                    <fieldset class="form-group col-md-12">
                                        <legend>Thông tin động cơ và khung xe</legend>
                                        <div class="row">

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Dung tích </label>
                                                <input class="form-control" type="text" name="capacity" require ="require"
                                                       value="${engine.getFuelTankCapacity()}"
                                                       pattern="([0-9]*(\.?)[0-9]*" 
                                                       title="Xin hãy nhập 1 số thực">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Loại động cơ </label>
                                                <input class="form-control" type="text" name="engineType" 
                                                       value="${engine.getEngineType()}" require ="require">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Số xi lanh </label>

                                                <input class="form-control" type="text" name="cylinderNumber"
                                                       value="${engine.getNumberOfCylinder()}" require ="require">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Dung tích xi lanh </label>

                                                <input class="form-control" type="text" name="cylinderMaxCapacity"
                                                       value="${engine.getCylinderCapacity()}" require ="require"
                                                       pattern="([0-9]*(\.)?)[0-9]*" 
                                                       title="Xin hãy nhập 1 số thực">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Van biến thiên </label>

                                                <input class="form-control" type="text" name="variableVale"
                                                       value="${engine.getVariableValveSystem()}" required>
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Hệ thống nguyên liệu </label>

                                                <input class="form-control" type="text" name="fuelSystem"
                                                       value="${engine.getFuelSystem()}" require ="require">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Dung tích tối đa </label>

                                                <input class="form-control" type="text" name="fuelCapacity"
                                                       value="${engine.getMaximumCapacity()}" require ="require"
                                                       pattern="([0-9]+)\/([0-9]+)" 
                                                       title="Hãy nhập theo format số/số">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label">Tốc độ mô-đen xoắn lớn nhất</label>
                                                <input class="form-control" type="text" name="maxTorque"
                                                       value="${engine.getMaximumTorque()}" require ="require"
                                                       pattern="([0-9]+)\/([0-9]+)(\-[0-9]+)?" 
                                                       title="Xin hãy nhập theo format số/số hoặc số/số-số">
                                            </div>

                                            <div class="form-group col-md-4">
                                                <label class="control-label" >Hộp số</label>
                                                <input class="form-control" type="text" name="gear"
                                                       value="${engine.getGear()}" require ="require">
                                            </div>

                                        </div>
                                    </fieldset>

                                    <fieldset class="form-group col-md-12">
                                        <legend>Giới thiệu xe</legend>
                                        <div>
                                            <textarea class="form-control"  name="description" id="default" >${carGI.getDescription()}</textarea>
                                        </div>
                                    </fieldset>

                                    <fieldset class="form-group col-md-12">
                                        <legend>Ảnh xe</legend>
                                        <div class="row">

                                            <div class="form-group">

                                                <div class="col-md-12">
                                                    <label  class="control-label">Ảnh chính cho sản phẩm</label>
                                                    <% 
                                                        if (error != null && error.equals("2")) {
                                                    %>
                                                    <p class="text-warning">Không tìm thấy file</p>
                                                    <% } else if (error != null && error.equals("4")) { %>
                                                    <p class="text-warning">File không hợp lệ</p>
                                                    <% } %>
                                                </div>
                                                <div class="col-md-4" style="margin-top: 20px;">
                                                    <div id="myfileupload">
                                                        <input type="file" id="uploadfile" name="ImageUpload" onchange="readURL(this);" />
                                                    </div>
                                                    <div id="thumbbox">
                                                        <img height="200" width="200" alt="Lỗi" id="thumbimage" style="display: none" />
                                                        <a class="removeimg" href="javascript:"></a>
                                                    </div>
                                                    <div id="boxchoice">
                                                        <a href="javascript:" class="Choicefile" onclick="document.getElementById('uploadfile').click();"><i class="fas fa-cloud-upload-alt"></i> Chọn ảnh thay đổi</a>
                                                        <p style="clear:both"></p>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-md-12">
                                                    <label class="control-label">Ảnh phụ cho sản phẩm</label>
                                                    <% 
                                                        if (error != null && error.equals("3")) {
                                                    %>
                                                    <p class="text-warning">Không tìm thấy file</p>
                                                    <% } else if (error != null && error.equals("5")) { %>
                                                    <p class="text-warning">File không hợp lệ</p>
                                                    <% } %>
                                                </div>
                                                <div class="col-md-4" style="margin-top: 20px;">
                                                    <div id="myfileuploadSubIMG">
                                                        <input type="file" id="uploadfileSubIMG" name="SubImageUpload" onchange="readSubURL(this);" />
                                                    </div>
                                                    <div id="thumbboxSubIMG">
                                                        <img height="200" width="200" alt="Lỗi" id="thumbimageSubIMG" style="display: none" />
                                                        <a class="removeimg" href="javascript:"></a>
                                                    </div>
                                                    <div id="boxchoiceSubIMG">
                                                        <a href="javascript:" class="Choicefile" onclick="document.getElementById('uploadfileSubIMG').click();"><i class="fas fa-cloud-upload-alt"></i> Chọn ảnh thay đổi</a>
                                                        <p style="clear:both"></p>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </fieldset>

                                    <script>
                                        ClassicEditor
                                                .create(document.querySelector('#editor'), {
                                                })
                                                .then(editor => {
                                                    window.editor = editor;
                                                })
                                                .catch(err => {
                                                    console.error(err.stack);
                                                });


                                    </script>
                                </div>
                            </div>
                            <button class="btn btn-save" type="submit">Lưu lại</button>
                            <!<!-- send input action to servlet to know what action need -->
                            <input type="hidden" name="action" value="EditCar"/>
                            <a class="btn btn-cancel" href="carlist" onclick="confirmCancel(event);">Hủy bỏ</a>
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
