<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
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

        <!--Flatpickr-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <title>Thêm voucher</title>
    </head>
    <body>
        <% 
            String error = request.getParameter("error");
        %>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item"><a href="voucherlist">Danh voucher</a></li>
                        <li class="breadcrumb-item">Tạo mới voucher</li>
                    </ul>
                </div>

                <form class="row" action="vouchercrud" method="POST">
                    <div class="col-md-12">
                        <div class="tile">
                            <h3 class="tile-title">Tạo mới voucher</h3>
                            <div class="tile-body">
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Mã voucher</label>
                                        <input class="form-control" maxlength="10" type="text" name="voucherCode" required>
                                        <% 
                                            if (error != null && error.equals("2")) {
                                        %>
                                        <p class="text-warning">Mã voucher đã tồn tại</p>
                                        <% } %>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="control-label">Mô tả voucher</label>
                                        <textarea class="form-control" name="description" required></textarea>
                                    </div>

                                    <div class="form-group col-md-3">
                                        <label class="control-label">Đối tượng áp dụng</label>
                                        <select class="form-control" name="objectVoucherID">
                                            <c:forEach items="${ListOV}" var="c">
                                                <option value="${c.objectVoucherID}">${c.objectVoucher}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group col-md-3">
                                        <label class="control-label">Thể loại</label>
                                        <select id="quantitySelect" name="discountType" class="form-control">
                                            <option value="true">Phần trăm</option>
                                            <option value="false">Tiền mặt</option>
                                        </select> 
                                    </div>

                                    <div class="form-group col-md-3">
                                        <label class="control-label">Giá trị của voucher</label>
                                        <input class="form-control" type="number" name="discountValue" min="1" max="100" required>
                                    </div>


                                    <div class="form-group col-md-3">
                                        <label class="control-label">Số lượng</label>
                                        <input class="form-control" type="text" name="maxUsage" required>
                                    </div>
                                    <script>
                                        const discountValueInput = document.querySelector('input[name="discountValue"]');
                                        const discountTypeSelect = document.getElementById('quantitySelect');

                                        discountTypeSelect.addEventListener('change', function () {
                                            if (discountTypeSelect.value === 'true') { // Nếu là Phần trăm
                                                discountValueInput.setAttribute('min', '1');
                                                discountValueInput.setAttribute('max', '100');
                                            } else { // Nếu là Tiền mặt
                                                discountValueInput.removeAttribute('min');
                                                discountValueInput.removeAttribute('max');
                                            }
                                        });
                                    </script>


                                    <div class="form-group col-md-6">
                                        <label> Ngày áp dụng </label>
                                        <input class="form-control flatpickr" data-input type="text" name="startDate" value="${startDate}" required>

                                    </div>
                                    <div class="form-group col-md-6">
                                        <label> Ngày kết thúc </label>
                                        <input class="form-control flatpickr" data-input type="text" name="endDate" value="${endDate}" required>
                                    </div>
                                    <script>
                                        const flatpickrInputs = document.querySelectorAll(".flatpickr");


                                        flatpickrInputs.forEach((input) => {
                                            flatpickr(input, {
                                                enableTime: false,
                                                dateFormat: "d-m-Y"
                                            });
                                        });
                                    </script>

                                </div>
                            </div>
                            <button class="btn btn-save" type="submit">Lưu lại</button>
                            <!<!-- send input action to servlet to know what action need -->
                            <input type="hidden" name="user" value="${sessionScope.acc.accID}">
                            <input type="hidden" name="action" value="InsertVoucher"/>
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
