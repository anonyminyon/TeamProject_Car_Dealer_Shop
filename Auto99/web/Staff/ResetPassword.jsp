<%-- 
    Document   : ResetPassword
    Created on : May 22, 2023, 9:49:45 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <title>Reset Password</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/Staff/css/Login.css">

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
                                    <a href="LoginPage.jsp" class="btn btn-white btn-outline-white">Login</a>
                                </div>
                            </div>
                            <div class="login-wrap p-4 p-lg-5">
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4">Reset Password</h3>
                                    </div>
                                </div>
                                <form action="resetpass" method="post"  class="signin-form">

                                    <div class="form-group mb-3">
                                        <label class="label" for="Pass">Password: </label>
                                        <input type="password" id="Pass" class="form-control"
                                               name="Pass" value=""
                                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}" 
                                               title="Must contain at least one number, one uppercase, one lowercase letter, one special character, and at least 8 or more characters"
                                               required = "required"
                                               placeholder="Password" />
                                    </div>

                                    <div class="form-group mb-3">
                                        <label class="label"  for="rePass">Retype Password: </label>
                                        <input type="password" id="rePass" class="form-control"
                                               name="rePass" value="" 
                                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}" 
                                               title="Must contain at least one number, one uppercase, one lowercase letter, one special character, and at least 8 or more characters" 
                                               required = "required"
                                               placeholder="Retype Password" />
                                    </div>

                                    <input type="hidden" id="email" class="form-control"
                                           name="email" value="<%= request.getAttribute("email") %>"/>

                                    <div class="text-danger">${mess}</div>


                                    <div class="form-group">
                                        <button type="submit" class="form-control btn btn-primary submit px-3">Reset</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="./js/jquery.min.js"></script>
        <script src="./js/popper.js"></script>
        <script src="./js/bootstrap.min.js"></script>
        <script src="./js/main.js"></script>

    </body>
</html>

