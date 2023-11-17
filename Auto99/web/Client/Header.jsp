<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <header class="">
        <nav class="navbar navbar-expand-lg">
            <div class="container">
                <a class="navbar-brand" href="home"><img src="img/logo.png">
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <c:forEach items="${sessionScope.ListBlog1}" var="category" varStatus="loop">
                            <c:if test="${category.blogCategoryID.blogCategoryID==1}">
                                <c:set var="categoryCount" value="${ParentIDCountMap[category.parentID]}" />

                                <c:choose>
                                    <c:when test="${categoryCount == 1}">
                                        <c:set var="isActive" value="${category.url eq pageUrl}" />
                                        <li class="nav-item active">
                                            <a class="nav-link" href="${category.url}">${category.title}</a>
                                        </li>
                                    </c:when>
                                    <c:when test="${categoryCount > 1}">
                                        <!-- Hiển thị menu trong dropdown menu nếu danh mục xuất hiện nhiều hơn 1 lần -->
                                        <c:set var="isActive" value="${category.url eq pageUrl}" />
                                        <li class="nav-item dropdown active">
                                            <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="${category.url}" role="button" aria-haspopup="true" aria-expanded="false">${category.title}</a>
                                            <div class="dropdown-menu">
                                                <c:forEach items="${ListBlog1}" var="subCategory">
                                                    <c:if test="${subCategory.parentID == category.parentID && subCategory != category}">
                                                        <a class="dropdown-item" href="${subCategory.url}">${subCategory.title.toUpperCase()}</a>
                                                    </c:if>
                                                </c:forEach>
                                            </div>
                                        </li>
                                    </c:when>
                                </c:choose>
                            </c:if>
                        </c:forEach>

                        <li class="nav-item">
                            <div class="login">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.clientacc}">
                                        <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i class="fa-regular fa-circle-user"></i>
                                            </a>

                                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                                                <a class="dropdown-item" href="profile">Thông tin tài khoản</a>
                                                <a class="dropdown-item" href="history">Lịch sử giao dịch</a>
                                                <a class="dropdown-item" href="myserviceform">Bảo dưỡng sửa chữa</a>
                                                <a class="dropdown-item" href="clientlogout">Đăng xuất</a>
                                            </div>
                                        </li>
                                    </c:when>

                                    <c:otherwise>
                                        <a class="nav-link" href="login">Login</a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </ul>

                </div>
            </div>
        </nav>
    </header>