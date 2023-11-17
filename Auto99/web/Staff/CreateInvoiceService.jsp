<%-- 
    Document   : CreateInvoiceService
    Created on : Oct 15, 2023, 2:52:19 AM
    Author     : hieu
--%>

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
        <link rel="stylesheet" href="assets/Staff/css/Table.css">



        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>

        <link rel="stylesheet" href="./assets/Staff/css/main.css">

        <!-- Main CSS-->
        <!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">-->

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <title>Service appointment List</title>
    </head>

    <body>
        <div class="wrapper">
            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>
            <!-- Body-content-->
            <div class="container">

                <div class="app-title">
                    <ul class="app-breadcrumb breadcrumb">
                        <li class="breadcrumb-item">Quản lý dịch vụ</li>
                    </ul>
                </div>

                <div class="row">
                    <div class="col-md-12">
                        <div class="tile">
                            <div class="tile-body">
                                <style>
                                    .border-box {
                                        border: 1px solid #ccc; /* Set your desired border style, color, and width */
                                        padding: 10px; /* Add padding to create some space between the content and the border */
                                        border-radius: 5px; /* Optional: Add rounded corners */
                                    }
                                </style>

                                <!-- Display the modal if modalValue has a value -->
                                <div class="fade show" style="display: block;">
                                    <div >
                                        <div class="body">
                                            <!-- show thông tin của dịch vụ mà khách hàng đang làm đang làm dịch vụ -->
                                            <div class="row" >
                                                <div class="form-group col-md-12 ">
                                                    <h1>HÓA ĐƠN DỊCH VỤ</h1>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label class="control-label">Trạng thái dịch vụ:</label>
                                                    <label>${clientService.getStatus()}</label>
                                                </div>

                                                <div class="form-group col-md-12 border-box">
                                                    <h4>THÔNG TIN KHÁCH HÀNG</h4>
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                <label class="control-label">Tên khách hàng:</label>
                                                            </td>
                                                            <td>
                                                                <label>${clientService.getClientID().getClientName()}</label>
                                                            </td>
                                                            <td>
                                                                <label class="control-label">Dịch Vụ:</label>
                                                            </td>
                                                            <td>
                                                                <label>${clientService.getServiceID().getServiceType()}</label>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label class="control-label">Số điện thoại:</label>
                                                            </td>
                                                            <td>
                                                                <label>${clientService.getClientID().getPhoneNumber()}</label>
                                                            </td>
                                                            <td>
                                                                <label class="control-label">Biển số xe:</label>
                                                            </td>
                                                            <td>
                                                                <label>${clientService.getNumberPlate()}</label>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label class="control-label">Ngày Hẹn đến làm dịch vụ:</label>
                                                            </td>
                                                            <td>
                                                                <label>${clientService.getDateService()}</label>
                                                            </td>
                                                            <td>
                                                                <label class="control-label">Email:</label>
                                                            </td>
                                                            <td>
                                                                <label>${clientService.getClientID().getEmail()}</label>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label for="dropdown">Chọn nhân viên thợ máy:</label>
                                                <select class="form-control" id="listMechanicInfo">
                                                    <c:forEach items="${listMechanicInfo}" var="mechanic">
                                                        <!-- cái này đẩy id của 1 thằng employee (thợ máy xuống để biết thằng nào làm dịch vụ cho khách) -->
                                                        <option value="${mechanic.getEmployeeID()}">${mechanic.getFirstName()} ${mechanic.getLastName()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-12">
                                                <h4>THÔNG TIN ĐƠN HÀNG</h4>
                                            </div>
                                            <p class="text-right"><label>Ngày chốt đơn:  ${currentDateTime}</label></p>
                                            <!---------------------------AJAX--------------------------->

                                            <div class="form-group col-md-12 border-box row">
                                                <div class="col-md-12">
                                                    <label for="type" style="width: 100px;">Loại hàng:</label>
                                                    <select name="type" id="type" onchange="typeSelect()">
                                                        <option value="service">Service</option>
                                                        <option value="auto-part">Auto Part</option>
                                                        <option value="other">Other</option>
                                                    </select>
                                                </div>
                                                <!--                                                 For 'Other' option -->
                                                <div class="col-md-12" id="productType" style="display: none;">
                                                    <label style="width: 100px;">Tên hàng:</label>
                                                    <input type="text" id="product" name="product" placeholder="Nhập tên hàng">
                                                </div>

                                                <div class="col-md-12" id="otherPrice" style="display: none;">
                                                    <label style="width: 100px;">Đơn giá:</label>
                                                    <input type="text" id="unitPrice" name="unitPrice" placeholder="Nhập đơn giá">vnđ
                                                </div>

                                                <!--                                                 For 'Service' option -->
                                                <div class="col-md-12" id="serviceType" style="display: none;">
                                                    <label style="width: 100px;">Tên hàng:</label>
                                                    <select name="serviceSelected" id="serviceSelected" onchange="changeService(this)">
                                                        <c:forEach var="service" items="${serviceList}">
                                                            <option value="${service.getServiceType()}" data-service-price="${service.servicePrice}">${service.getServiceType()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-md-12" id="servicePrice" style="display: none;">
                                                    <label style="width: 100px;">Đơn giá:</label>
                                                    <input type="text" id="serviceUnitPrice" name="unitPrice" value="" readonly>vnđ
                                                </div>

                                                <script>
                                                    function changeService(selectElement) {
                                                        var selectedOption = selectElement.options[selectElement.selectedIndex];
                                                        var servicePrice = selectedOption.getAttribute('data-service-price');

                                                        // Set the value of the unitPrice input field with the extracted servicePrice
                                                        var unitPriceInput = document.querySelector('#serviceUnitPrice');
                                                        unitPriceInput.value = servicePrice;
                                                    }
                                                </script>

                                                <!--                                                 For 'Auto Part' option -->
                                                <div class="col-md-12" id="autopart" style="display: none;">
                                                    <label style="width: 100px;">Tên hàng:</label>
                                                    <select name="autoPartSelected" id="autoPartSelected" onchange="changeAutoPart(this)">
                                                        <c:forEach var="autopart" items="${listAutoPart}">
                                                            <option value="${autopart.getPartName()}" data-auto-part-price="${autopart.getPrice()}">${autopart.getPartName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-md-12" id="autoPartPrice" style="display: none;">
                                                    <label style="width: 100px;">Đơn giá:</label>
                                                    <input type="text" id="autoPartUnitPrice" name="unitPrice" value="" readonly>vnđ
                                                </div>

                                                <script>
                                                    function changeAutoPart(selectElement) {
                                                        var selectedOption = selectElement.options[selectElement.selectedIndex];
                                                        var autoPartPrice = selectedOption.getAttribute('data-auto-part-price');

                                                        // Set the value of the unitPrice input field with the extracted autoPartPrice
                                                        var unitPriceInput = document.querySelector('#autoPartUnitPrice');
                                                        unitPriceInput.value = autoPartPrice;
                                                    }
                                                </script>

                                                <script>
                                                    function typeSelect() {
                                                        var typeSelect = document.querySelector('#type').value;

                                                        // Hide all elements first
                                                        document.querySelector('#productType').style.display = 'none';
                                                        document.querySelector('#otherPrice').style.display = 'none';
                                                        document.querySelector('#serviceType').style.display = 'none';
                                                        document.querySelector('#servicePrice').style.display = 'none';
                                                        document.querySelector('#autopart').style.display = 'none';
                                                        document.querySelector('#autoPartPrice').style.display = 'none';

                                                        // Show the elements based on the selected type
                                                        if (typeSelect === 'other') {
                                                            document.querySelector('#productType').style.display = 'block';
                                                            document.querySelector('#otherPrice').style.display = 'block';
                                                        } else if (typeSelect === 'service') {
                                                            document.querySelector('#serviceType').style.display = 'block';
                                                            document.querySelector('#servicePrice').style.display = 'block';
                                                        } else if (typeSelect === 'auto-part') {
                                                            document.querySelector('#autopart').style.display = 'block';
                                                            document.querySelector('#autoPartPrice').style.display = 'block';
                                                        }
                                                    }
                                                </script>
                                                <div class="col-md-12">
                                                    <label for="quantity" style="width: 100px;">Số lượng:</label>
                                                    <input type="number" id="quantity" name="quantity" placeholder="Enter quantity" step="1">
                                                </div>

                                                <div class="col-md-12">
                                                    <button onclick="addRow()">Add</button> 
                                                </div>

                                                <table id="invoiceTable" border="1">
                                                    <tr>
                                                        <th>Tên Sản phẳm</th>
                                                        <th>Giá</th>
                                                        <th>Số Lượng</th>
                                                        <th>Tổng</th>
                                                        <th>chức năng</th>
                                                    </tr>
                                                </table>

                                                <script>
                                                    let productList = [];

                                                    // Add an event listener to the form's submit event
                                                    document.getElementById('myForm').addEventListener('submit', function (event) {
                                                        event.preventDefault(); // Prevent the default form submission
                                                        addRow();
                                                    });

                                                    function addRow() {
                                                        // Lấy thông tin từ form
                                                        var selectedType = $("#type").val();
                                                        var productName, unitPrice, serviceType, serviceUnitPrice, autoPartType, autoPartUnitPrice, quantity;

                                                        if (selectedType === "other") {
                                                            productName = $("#product").val();
                                                            unitPrice = parseFloat($("#unitPrice").val());
                                                        } else if (selectedType === "service") {
                                                            productName = $("#serviceSelected").val();
                                                            unitPrice = parseFloat($("#serviceUnitPrice").val());
                                                        } else if (selectedType === "auto-part") {
                                                            productName = $("#autoPartSelected").val();
                                                            unitPrice = parseFloat($("#autoPartUnitPrice").val());
                                                        }
                                                        quantity = parseInt($("#quantity").val());

                                                        if (productName && !isNaN(unitPrice) && !isNaN(quantity)) {
                                                            const newRow = document.getElementById("invoiceTable").insertRow(-1); // Append the row at the end
                                                            const cell1 = newRow.insertCell(0);
                                                            const cell2 = newRow.insertCell(1);
                                                            const cell3 = newRow.insertCell(2);
                                                            const cell4 = newRow.insertCell(3);
                                                            const cell5 = newRow.insertCell(4);

                                                            cell1.innerHTML = productName;
                                                            cell2.innerHTML = unitPrice + " vnd";
                                                            cell3.innerHTML = quantity + " vnd";
                                                            const total = unitPrice * quantity;
                                                            cell4.innerHTML = total + " vnd";
                                                            cell5.innerHTML = '<button onclick="deleteRow(this)">Delete</button>';

                                                            const detail = {productName, unitPrice, quantity, total};
                                                            productList.push(detail);

                                                            // Recalculate the total
                                                            calculateTotal();
                                                        }
                                                    }

                                                    function deleteRow(btn) {
                                                        const row = btn.parentNode.parentNode;
                                                        const index = row.rowIndex - 1;
                                                        productList.splice(index, 1);
                                                        document.getElementById("invoiceTable").deleteRow(row.rowIndex);
                                                        calculateTotal();
                                                    }

                                                    function calculateTotal() {
                                                        let total = 0;
                                                        for (const detail of productList) {
                                                            total += detail.total;
                                                        }
                                                        document.getElementById("totalAmount").textContent = total + " vnd";
                                                    }
                                                </script>
                                            </div>
                                            <div>
                                                <p>Tổng tiền thanh toán: <span id="totalAmount">0</span></p>
                                            </div>
                                            <style>
                                                #totalAmount{
                                                    color: red;
                                                }
                                            </style>
                                            <!---------------------------form gửi thông tin của hóa đơn--------------------------->
                                            <!-- form gửi 4 trường data để add vào clientService 2 trường là nhân viên làm xe cho khách và hóa đơn-->
                                            <form id="dataInvoice" method="POST" action="manageserviceclient">
                                                <!--lưu cái clientServiceID vì nó đại diện ho 1 service trên 1 khách này xuống bảng historyServiceInvoice-->
                                                <input name="clientServiceID" value="${clientService.getClientServiceID()}" type="hidden"/>
                                                <!--lấy id thằng thợ máy -->
                                                <!--lấy ngày duyệt đơn-->
                                                <input name="dateDoInvoice" value="${currentDateTime}" type="hidden">
                                                <!--lấy list product-->
                                                <input name="productList" id="productList" type="hidden" value="">
                                                <!--lấy email trong hóa đơn để tạo tài khoản cho khách hoặc gửi mail-->
                                                <input name="clientEmail" type="hidden" value="${clientService.getClientID().getEmail()}">
                                                <!--lấy cái clientID cho phần nếu tạo tài khoản mới cho khách đăng nhập thì phải lưu clientID-->
                                                <input name="clientID" type="hidden" value="${clientService.getClientID().getClientID()}">                                               
                                                <!--cái này để gửi action-->
                                                <input type="hidden" name="action" value="CompleteServiceAppointment"/>
                                                <!-- cái này để biết là nhân viên đã điền invoice và hóa đơn sau đó chạy vào logic gửi mail thông báo khách hoàn thành dịch vụ -->
                                                <input type="hidden" name="isCompleteInvoice" value="true" />
                                            </form>
                                            <!-------------------------------------------------------------->
                                        </div>
                                        <div class="modal-footer">
                                            <a href="manageserviceclient" class="btn btn-secondary" >Cancel</a>
                                            <button class="btn btn-primary" onclick="submitFormInvoice()">Submit</button>
                                            <!--                                            <script>
                                                                                            function submitFormInvoice() {
                                                                                                // Set the JSON value within the form before submission
                                                                                                document.getElementById("productList").value = JSON.stringify(productList);
                                                                                                // Check if productList is empty or null
                                                                                                if (!productList || productList.length === 0) {
                                                                                                    alert("Thông tin hóa đơn không được trống");
                                                                                                    return; // Exit the function
                                                                                                }
                                                                                                // Find the form element by its ID
                                                                                                var form = document.getElementById("dataInvoice");
                                            
                                                                                                if (form) {
                                                                                                    // Use the submit() method to submit the form
                                                                                                    form.submit();
                                                                                                } else {
                                                                                                    console.error("Form not found.");
                                                                                                }
                                                                                            }
                                                                                        </script>-->
                                            <script>
                                                function submitFormInvoice() {
                                                    // Set the JSON value within the form before submission
                                                    document.getElementById("productList").value = JSON.stringify(productList);

                                                    // Get the selected value from the dropdown
                                                    var selectElement = document.getElementById("listMechanicInfo");
                                                    var selectedValue = selectElement.value;

                                                    // Check if productList is empty or null
                                                    if (!productList || productList.length === 0) {
                                                        alert("Thông tin hóa đơn không được trống");
                                                        return; // Exit the function
                                                    }

                                                    if (selectedValue) {
                                                        // Add a hidden input field to the form to include the selected mechanic ID
                                                        var form = document.getElementById("dataInvoice");

                                                        if (form) {
                                                            // Create a hidden input field
                                                            var mechanicInput = document.createElement("input");
                                                            mechanicInput.type = "hidden";
                                                            mechanicInput.name = "selectedMechanicID"; // Set the name to be used in the servlet
                                                            mechanicInput.value = selectedValue;

                                                            // Append the hidden input field to the form
                                                            form.appendChild(mechanicInput);

                                                            // Use the submit() method to submit the form
                                                            form.submit();
                                                        } else {
                                                            console.error("Form not found.");
                                                        }
                                                    } else {
                                                        console.error("No option selected.");
                                                    }
                                                }
                                            </script>
                                        </div>
                                    </div>
                                </div>
                            </div>                            
                        </div>
                    </div>
                </div>
            </div>

            <!--
            MODAL MESS
            -->

            <!-- <div class="modal fade" id="exampleModal" tabindex="-1" 
                             aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <p style="font-weight: bold; font-size: 32px; line-height: 1.2; letter-spacing: 1px;">${mess}</p>
                                    </div>
                                </div>
                            </div>
                        </div>--
            <!--
           MODAL
            -->
            <!-- Essential javascripts for application to work-->
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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


    </body>

</html>
