<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <footer>
            <div class="box">
                <ul>

                    <li class="first">
                        <c:forEach items="${sessionScope.Footer}" var="f" varStatus="loop">
                            <c:if test="${loop.index==0}">
                                ${f.content}
                            </c:if>
                        </c:forEach>
                    </li>
                    <li class="cont">
                        <c:forEach items="${sessionScope.Footer}" var="f" varStatus="loop">
                            <c:if test="${loop.index==1}">
                                ${f.content}
                            </c:if>
                        </c:forEach>
                    </li>

                    <li class="sub">
                        <c:forEach items="${sessionScope.Footer}" var="f" varStatus="loop">
                            <c:if test="${loop.index==2}">
                                ${f.content}
                            </c:if>
                        </c:forEach>
                    </li>
                </ul>

            </div>
        </footer>