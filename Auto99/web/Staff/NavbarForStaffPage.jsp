
<!--===================================HieuHT==============================================================================-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta charset="UTF-8">    
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="./assets/Staff/css/AdminPage.css">

        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>

        <!----===== Google chart ===== -->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <title>Admin Dashboard</title>
    </head>


    <!------------------------------------------thanh menu------------------------------------------>

    <nav>
        <div class="sidenavbar">
            <div class="logo">
                <img src="img/logo.png" alt=""/>
                <span>Auto99</span>
            </div>

            <a href="adminhome"><i class="fa-brands fa-dashcube"></i>Dashboard</a>
            <a href="employeelist"><i class="fa-solid fa-people-group"></i>Nhân viên</a>
            <a href="clientlist"><i class="fa-solid fa-user"></i>Khách hàng</a>
            <a href="servicemanagement"><i class="fa-solid fa-briefcase"></i>Dịch vụ</a>
            <a href="carlist"><i class="fa-solid fa-car"></i>Sản phẩm</a>
            <a href="autopart"><i class="fa-solid fa-screwdriver-wrench"></i>Phụ kiện</a>
            <a href="bloglist"><i class="fa-solid fa-newspaper"></i>Blog</a>
            <a href="voucherlist"><i class="fa-solid fa-ticket"></i>Voucher</a>
            <a href="carorderlist"><i class="fa-solid fa-cash-register"></i>Đặt cọc xe</a>
            <a href="locationlist"><i class="fa-solid fa-location-pin"></i>Địa điểm</a>
            <a href="policyfeelist"><i class="fa-solid fa-tag"></i>Phụ phí và bảo hiểm</a>
            <a href="myemployeeprofile"><i class="fa-solid fa-user"></i>My Profile</a>
            <a href="logout" class="logout">
                <i class="fa-solid fa-right-from-bracket"></i>Logout
            </a> 
        </div>
    </nav>

    <!------------------------------------------thanh menu------------------------------------------>
    <script src="./assets/js/adminPage.js"></script>

</html>
<!--===================================HieuHT==============================================================================-->