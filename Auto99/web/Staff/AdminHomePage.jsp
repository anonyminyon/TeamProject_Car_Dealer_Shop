<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <meta charset="UTF-8">    
        <!----======== CSS ======== -->
        <link rel="stylesheet" href="../assets/Staff/css/AdminPage.css">
<!--        <link rel="stylesheet" href="../assets/Staff/css/Table.css">-->


        <!----===== font awesome CSS ===== -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"/>

<!--        <link rel="stylesheet" href="../assets/Staff/css/main.css">-->
        <script src="https://cdn.ckeditor.com/ckeditor5/39.0.2/classic/ckeditor.js"></script>
        <!-- Main CSS-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <title>Admin Dashboard</title>
    </head>
    <body>
        <div class="wrapper">

            <!--    Navbar-->
            <%@ include file="NavbarForStaffPage.jsp" %>

            <div class="main-body">
                <div class="header">
                    <h1>Dashboard</h1>
                </div>

                <div class="following-wrapper">
                    <div class ="card">
                        <a href="carlist">
                            <i class="fa-solid fa-car fa-3x align-items-center"></i>
                            <h3>${carCount}</h3>
                            <h2>Mẫu Xe</h2>
                        </a>
                    </div>
                    
                    <div class ="card">
                        <a href="autopart">
                            <i class="fa-solid fa-screwdriver-wrench fa-3x align-items-center"></i>
                            <h3>${partCount}</h3>
                            <h2>Phụ kiện</h2>
                        </a>
                    </div>
                    
                    <div class ="card">
                        <a href="servicemanagement">
                            <i class="fa-solid fa-briefcase fa-3x align-items-center"></i>
                            <h3>${serCount}</h3>
                            <h2>Dịch vụ</h2>
                        </a>
                    </div>
                    
                    
                    <div class ="card">
                        <a href="bloglist">
                            <i class="fa-solid fa-newspaper fa-3x align-items-center"></i>
                            <h3>${blogCount}</h3>
                            <h2>Số lượng blog</h2>
                        </a>
                    </div>
                    
                    <div class ="card">
                        <a href="clientlist">
                            <i class="fa-solid fa-user fa-3x align-items-center"></i>
                            <h3>${clientCount}</h3>
                            <h2>Khách hàng</h2>
                        </a>
                    </div>
                    
                    <div class ="card">
                        <a href="employeelist">
                            <i class="fa-solid fa-people-group fa-3x align-items-center"></i>
                            <h3>${accCount}</h3>
                            <h2>Nhân viên</h2>
                        </a>
                    </div>
                </div>
                    

                    

<!--                <div class="graphBox">
                    <div id="chart_div" style="width: 1000px; height: 450px;"></div>
                </div>-->

            </div>
        </div>
    </body>
</html>