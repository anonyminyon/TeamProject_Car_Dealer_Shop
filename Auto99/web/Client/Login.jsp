<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--Boxicons CDN Link-->
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <title>Login</title>
        <link rel="stylesheet" href="./assets/css/Login.css">
    </head>

    <body>
        <div class="wrapper">
            <form action="login" method="post">
                <h1>Login</h1>
                <div class="alertmassgae"> ${error}</div>
                <div class="input-box">
                    <input name="email" value="${email}" type="email" placeholder="Email" required>
                    <i class='bx bxs-envelope'></i>
                </div>
                <div class="input-box">
                    <input name="pass" value="${pass}" type="password" placeholder="Password" required>
                    <i class='bx bxs-lock-alt'></i>
                </div>

                <div class="remember-forgot">
                    <label><input name="remember" value="1" type="checkbox"> Remember me</label>
                    <a href="forgotpassword">Forgot password?</a>
                </div>
                <button type="submit" class="btn">Login</button>
            </form>
        </div>
    </body>

    </html>