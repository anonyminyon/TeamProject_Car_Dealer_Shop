<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Boxicons CDN Link-->
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <title>Change Password</title>
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
            <form id="passwordForm" action="changepassword" method="post">
                <input type="hidden" name="action" value="updatePass">
                <h1>Đổi mật khẩu</h1>

                <div class="input-box">
                    <input id="oldpassword" name="oldpassword" type="password" placeholder="Nhập mật khẩu cũ" required>
                    <i class='bx bxs-envelope'></i>
                </div>
                <div class="input-box">
                    <input id="newPassword" name="newPassword" type="password" placeholder="Nhập mật khẩu mới" required>
                    <i class='bx bxs-envelope'></i>
                </div>
                <div class="input-box">
                    <input id="confirmPassword" name="confirmPassword" type="password" placeholder="Nhập lại mật khẩu mới" required>
                    <i class='bx bxs-envelope'></i>
                </div>
                <div class="alertmassgae"> ${error}</div>
                <button type="submit" class="btn">Cập nhật</button>
            </form>
        </div>
        <script>
            const passwordForm = document.getElementById("passwordForm");
            const oldPassWordinput = document.getElementById("oldpassword");
            const newPasswordInput = document.getElementById("newPassword");
            const confirmPasswordInput = document.getElementById("confirmPassword");
            const message = document.getElementById("message");

            passwordForm.addEventListener("submit", function (event) {
                const oldPassword = oldPassWordinput.value;
                const newPassword = newPasswordInput.value;
                const confirmPassword = confirmPasswordInput.value;
                if (oldPassWord === newPassword) {
                    message.textContent = "Mật khẩu mới khoong được giống mật khẩu cũ.";
                    message.style.color = "red";
                    event.preventDefault();
                }
                if (newPassword !== confirmPassword) {
                    message.textContent = "Mật khẩu nhập lại không trùng khớp.";
                    message.style.color = "red";
                    event.preventDefault();
                }
                if (!validatePassword(newPassword)) {
                    message.textContent = "Mật khẩu mới không đáp ứng yêu cầu về định dạng.";
                    message.style.color = "red";
                    event.preventDefault();
                }
            });

            function validatePassword(password) {
                if (password.length < 8) {
                    return false;
                }

                if (!/[A-Z]/.test(password)) {
                    return false;
                }

                if (!/[^a-zA-Z0-9]/.test(password)) {
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>
