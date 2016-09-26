<%@page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="uk-UA">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;Реєстрація</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/login.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css"/>

    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/login.js"></script>
    <%--<script src="../../js/registration.js" type="text/javascript"></script>--%>
</head>
<body>
<div class="header">
    <div class="banner">
        <div class="bannerTop">
            <h3 class="headerUniversityName">Національний університет біоресурсів і природокористування України</h3>

            <div class="authForm" id="authForm">
                <sec:authorize access="isAnonymous()">
                    <p>
                        <a href="/login">Увійти</a>
                        <a href="/registration">Зареєструватись</a>
                    </p>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <p>
                        <a href="/myPage">${userDetails.name} ${userDetails.surname}</a>
                        <a href="/j_spring_security_logout">Вийти</a>
                    </p>
                </sec:authorize>
            </div>
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">
            <div class="instituteLogo"></div>
            <h3 class="instituteHeaderName">ННІ Енергетики, автоматики <br>і енергозбереження</h3>
            <sec:authorize access="hasRole('ADMIN')">
                <p class="adminLink">
                    <a href="/administrator">Сторінка адміністратора</a>
                </p>
            </sec:authorize>
            <p id="currentDate"></p>
        </div>
    </div>
    <hr>
    <div id="topnav">
        <ul class="nav">
            <li><a href="index.html">Головна</a></li>
            <li class="active"><a href="articles.html">Статті</a></li>
            <li><a href="seminars.html">Семінари</a></li>
            <li><a href="conference.html">Конференції</a></li>
            <li><a href="#">Опублікувати</a>
                <ul>
                    <li><a href="publishArticle.html">Статтю</a></li>
                    <li><a href="publishSeminar.html">Cемінар</a></li>
                    <li><a href="publishConference.html">Корференцію</a></li>
                </ul>
            </li>
            <li><a href="advancedSearch.html">Пошук</a></li>
            <li><a href="contacts.html">Контакти</a></li>
        </ul>
    </div>
    <hr>
</div>


<div class="content">

    <div class="whiteBox">

        <div class="mainContent">
            <div class="barHeader">
                <%--<h2>Увійдіть використовуючи електронну пошту та пароль!</h2>--%>
            </div>


            <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                <div class="errorBlock">
                    Помилка авторизації.
                    <br/>
                        ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                </div>
            </c:if>

            <form class="form" id="loginForm" name="f" action="<c:url value='/j_spring_security_check'/>"
                  method="post">


                <div class="formField">
                    <label for="j_username" class="label">
                        Електронна адреса
                        <span>*</span>
                    </label>

                    <div class="textField">
                        <input type="text" name="j_username" id="j_username" value="v_cheslav@ukr.net">
                    </div>
                </div>

                <div class="formField">
                    <label for="j_password" class="label">
                        Пароль
                        <span>*</span>
                    </label>

                    <div class="textField">
                        <input type="password" id="j_password" name="j_password" value="QwQw1212">
                    </div>
                </div>

                <div class="formField">
                    <input id="_spring_security_remember_me"
                           name="_spring_security_remember_me" type="checkbox"/>
                    <label for="_spring_security_remember_me">Запам'ятати?</label>
                </div>

                <div id="buttons">
                    <input class="button" type="submit" name="submit" value="Увійти">
                    <input class="button" type="reset" name="reset" value="Скинути">
                </div>


            </form>


        </div>

        <div class="rightSideBar">
            <input class="button" type="button" onclick="registrationPage()"
                   value="Реєстрація">
            <input class="button" type="button" id="remindPassword" value="Відновити пароль">
        </div>
        <div class="clear"></div>
    </div>

</div>
</div>

<hr class="horizontalLine">
<div class="footer" id="footer"><!-- column -->
    <div class="" id="footerUniversityInform"><!-- content -->
        <p>Національний університет біоресурсів і природокористування України&nbsp; ННІ Енергетики, автоматики і
            енергозбереження</p>
    </div>
    <div class="" id="footerContacts"><!-- group -->
        <div class="" id="addressIcon"><!-- simple frame --></div>
        <div class="" id="footerAdsressInform"><!-- column -->
            <div class="" id="addressFooter"><!-- content -->
                <p>03041, м Київ, вул Героїв Оборони 12, навчальний корпус №8</p>
            </div>
            <div class="" id="designerName"><!-- content -->
                <p>Дизайн та програмування: Гаврилюк В.В.</p>
            </div>
        </div>
        <div class="" id="footerVline"><!-- simple frame --></div>
        <div class="" id="callIcon"><!-- simple frame --></div>
        <div class="" id="fotterPhones"><!-- column -->
            <div class="" id="institutePhone"><!-- content -->
                <p>(044) 527-85-80</p>
            </div>
            <div class="" id="designerPhone"><!-- content -->
                <p>(096) 115-00-83</p>
            </div>
        </div>
        <div class="" id="fotterVline2"><!-- simple frame --></div>
        <div class="" id="emailIcon"><!-- simple frame --></div>
        <div class="" id="footerEmails"><!-- column -->
            <div class="" id="instituteEmail"><!-- content -->
                <p>epafort1@ukr.net</p>
            </div>
            <div class="" id="designerEmail"><!-- content -->
                <p>v_cheslav@ukr.net</p>
            </div>
        </div>
    </div>
    <div class="" id="allRightsInform"><!-- content -->
        <p>© All rights reserved</p>
    </div>
</div>

<div class="uploading" id="uploading"></div>

</body>
</html>



