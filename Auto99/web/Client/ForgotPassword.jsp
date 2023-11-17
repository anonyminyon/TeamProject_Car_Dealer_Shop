<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Boxicons CDN Link-->
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <title>Quên mật khẩu</title>
        <link rel="stylesheet" href="./assets/css/Login.css">
        <style>
            h3{
                text-align: center;
                font-weight: bold;
            }
            h4{
                text-align: center;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="wrapper">
            <c:if test="${!sendSucces}">
                <form action="forgotpassword" method="post">
                    <h1>Quên mật khẩu</h1>
                    <div class="alertmassgae"> ${error}</div>
                    <div class="input-box">
                        <input name="email" type="email" placeholder="Email" required>
                        <i class='bx bxs-envelope'></i>
                    </div>
                    <button type="submit" class="btn">Gửi</button>
                </form>
            </c:if>

            <c:if test="${sendSucces}">
                <h3>OTP đã được gửi đến email của bạn</h1>
                <br>
                <h4>Vui lòng kiểm tra email!</h2>
            </c:if>
        </div>
    </body>
</html>
