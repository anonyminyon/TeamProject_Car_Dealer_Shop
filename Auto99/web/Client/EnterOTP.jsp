<%-- 
    Document   : EnterOTP
    Created on : Nov 1, 2023, 12:45:05 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <title>Enter OTP</title>
        <link rel="stylesheet" href="./assets/css/Login.css">
        <style>
            #countdown{
                text-align: right;
                padding-bottom: 40px;
                font-size: 16px;
                font-weight: bold;
            }

            #countdown span{
                color: red;
                font-size: 18px;
                font-weight: bold;
                margin-left: 5px;
            }
            #exit{
                display: none;
            }
            #exit h4 a{
                text-decoration: none;
                color: white;
            }
            #exit h1{
                text-align: center;
            }
            #exit h4 {
                text-align: center;
            }
        </style>
        <script>
            const startTime = new Date("${timeEndOtp}").getTime();

            function checkAndCloseForm() {
                const currentTime = new Date().getTime();
                const elapsedTime = (currentTime - startTime) / 1000; // Đổi ra giây
                const remainingTime = 10 * 60 - elapsedTime; // Số giây còn lại
                const minute = Math.floor(remainingTime / 60);
                const second = Math.floor(remainingTime % 60);
                console.log("currentTime:" + currentTime + "\n elapsedTime:" + elapsedTime + "\n remainingTime:" + remainingTime + "\n minute:" + minute + "\n second:" + second);
                if (remainingTime <= 0) {
                    // Đóng thẻ form
                    const formElement = document.getElementById("otpForm");
                    formElement.style.display = "none";
                    alert("Hết thời gian nhập OTP. Form đã tự đóng.");
                    document.getElementById("exit").style.display = "block";
                } else {
                    // Hiển thị thời gian còn lại
//                    document.getElementById("countdown").innerHTML = `OTP hết hạn sau: ${minute} : ${second}`;
                    // Tiếp tục kiểm tra sau mỗi giây
                    setTimeout(checkAndCloseForm, 1000);
                }
            }
            window.onload = checkAndCloseForm;
        </script>
    </head>
    <body>
        <div class="wrapper">
            <div id="countdown"></div>
            <form action="enterotp" method="post" id="otpForm">
                <h1>Nhập mã OTP</h1>
                <div class="alertmassgae"> ${error}</div>
                <div class="input-box">
                    <input type="text" id="otp" name="otp" placeholder="Nhập OTP" required>
                    <i class='bx bxs-envelope'></i>
                </div>
                <button type="submit" class="btn">Xác nhận</button>
            </form>
            <div id="exit">
                <h1>Đã hết hạn nhập OTP</h1>
                <br>
                <h4><a href="login">Quay lại trang đăng nhập</a></h4>
            </div>
        </div>

    </body>
</html>
