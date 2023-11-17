<%-- 
    Document   : ChangePasswordForm
    Created on : Sep 13, 2023, 8:41:57 AM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/Staff/css/Login.css">
        <title>JSP Page</title>
    </head>
    <body style="text-align: center">
        <!--<h1>Change password</h1>-->
<!--        <form action="change" method="GET">

            Username:
            <br/><input type="text" name="user"><br/>
            Old password:
            <br/><input type="password" name="opass"><br/>
            New password:
            <br/><input type="password" name="pass"><br/>
            Confirm new password:
            <br/><input type="password" name="rpass"><br/>
            <input type="submit" value="Change" />
        </form>-->

        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 text-center mb-5">
                        <h2 class="heading-section">Change password</h2>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-12 col-lg-10">
                        <div class="wrap d-md-flex">
                            <div class="text-wrap p-4 p-lg-5 text-center d-flex align-items-center order-md-last">
                                <div class="text w-100">
                                    <h2>Welcome to change password</h2>
                                    <p>Don't want to change your password anymore</p>
                                    <a href="LoginPage.jsp" class="btn btn-white btn-outline-white">Login</a>
                                </div>
                            </div>
                            <div class="login-wrap p-4 p-lg-5">
                                <div class="d-flex">
                                    <div class="w-100">
                                        <h3 class="mb-4">Enter information here</h3>
                                    </div>
                                </div>
                                <form action="change" method="POST"  class="signin-form">
                                    
                                    <h3 style="color: red">${requestScope.ms}</h3>
                                    <input type="hidden" name="user" value="${sessionScope.acc.accName}">
                                    <div class="form-group mb-3">
                                        <label class="label"  for="rePass">Old Password: </label>
                                        <input type="password" id="rePass" class="form-control"
                                               name="opass" value="" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}" 
                                               title="Must contain at least one number, one uppercase, one lowercase letter, one special character, and at least 8 or more characters" 
                                               required = "required"
                                               placeholder="Old Password" />
                                    </div>

                                    <div class="form-group mb-3">
                                        <label class="label"  for="rePass">New Password: </label>
                                        <input type="password" id="rePass" class="form-control"
                                               name="pass" value="" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}" 
                                               title="Must contain at least one number, one uppercase, one lowercase letter, one special character, and at least 8 or more characters" 
                                               required = "required"
                                               placeholder="New Password" />
                                    </div>

                                    <div class="form-group mb-3">
                                        <label class="label"  for="rePass">Retype New Password: </label>
                                        <input type="password" id="rePass" class="form-control"
                                               name="rpass" value="" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}" 
                                               title="Must contain at least one number, one uppercase, one lowercase letter, one special character, and at least 8 or more characters" 
                                               required = "required"
                                               placeholder="Retype New Password" />
                                    </div>

                                    <div class="form-group">
                                        <input type="submit" class="form-control btn btn-primary submit px-3" value="Change"></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
