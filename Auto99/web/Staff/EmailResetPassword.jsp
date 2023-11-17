<%-- 
    Document   : ResetPassword
    Created on : May 22, 2023, 9:49:45 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/Staff/css/Login.css">
        <title>Reset Password</title>
    </head>
    <body>
        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 text-center mb-5">
                        <h2 class="heading-section">Reset Password</h2>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-12 col-lg-10">
                        <div class="wrap d-md-flex">
                            <div class="text-wrap p-4 p-lg-5 text-center d-flex align-items-center order-md-last">
                                <div class="text w-100">
                                    <h2>Welcome to reset password</h2>
                                    <p>Don't want to reset your password anymore</p>
                                    <a href="javascript:history.back()" class="btn btn-white btn-outline-white">Login</a>
                                </div>
                            </div>
                            <div class="login-wrap p-4 p-lg-5">
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4">Reset Password</h3>
                                    </div>
                                </div>
                                <form action="emailresetpass" method="post" >

                                    <div class="form-group mb-3">
                                        <label class="label" for="Email">Enter your email: </label>
                                        <input type="email" id="Email" class="form-control"
                                               name="Email" value="" required = "required"
                                               placeholder="Email address" />

                                    </div>

                                    <div class="text-danger">${mess}</div>

                                    <div class="text-center pt-1 mb-3 pb-1">
                                        <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" type="submit">
                                            Reset</button>
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="js/jquery.min.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>

    </body>
</html>
