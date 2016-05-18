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
    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/login.js"></script>
    <script src="../../js/registration.js" type="text/javascript"></script>
</head>
<body>
<div class="header">
    <h1 id="magazineName">ЕНЕРГЕТИКА, АВТОМАТИКА І ЕНЕРГОЗБЕРЕЖЕННЯ</h1>
    <nav id="MenuBar">
        <ul>
            <li><a href="index.html" class="menuButton">ГОЛОВНА</a></li>
            <li><a href="publication.html" class="menuButton">ПУБЛІКАЦІЇ</a></li>
            <li><a href="seminar.html" class="menuButton">СЕМІНАР</a></li>
            <li><a href="publish.html" class="menuButton">ОПУБЛІКУВАТИ</a></li>
            <li><a href="contacts.html" class="menuButton">КОНТАКТИ</a></li>
        </ul>
    </nav>
    <hr class="horizontalLine">
    <div class="banner">
        <div class="bannerTop">
            <h3 class="headerUniversityName">Національний університет біоресурсів і природокористування України</h3>
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">
            <div class="instituteLogo"></div>
            <h3 class="instituteHeaderName">ННІ Енергетики, автоматики <br>і енергозбереження</h3>
            <a href="advancedSearch.html" class="menuButton" id="searchingButton">Пошук</a>
        </div>
    </div>
    <hr class="horizontalLine">
</div>
<div class="clearfix" id="page">

    <div class="contentLeft">
        <h3 class="loginTitle">
            <p id="titleText">Увійдіть використовуючи електронну пошту та пароль!</p>
        </h3>

        <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
            <div class="errorBlock">
                Помилка авторизації.
                <br/> Причина:
                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>

        <form class="loginForm" name="f" action="<c:url value='/j_spring_security_check'/>"
              method="post">
            <div class="login">
                <div class="tips">Електронна пошта:</div>
                <div class="userFiller">
                    <input type="text" name="j_username" id="j_username" value="v_cheslav@ukr.net">
                </div>
            </div>

            <div class="password">
                <div class="tips">Пароль:</div>
                <div class="userFiller">
                    <input type="password" name="j_password" value="QwQw1212">
                </div>
            </div>

            <div class="buttons">
                <input class="button" type="submit" name="submit" value="Увійти">
                <input class="button" type="reset" name="reset" value="Скинути">
                <input class="button" type="button" onclick="registrationPage()"
                       value="Реєстрація">
            </div>
        </form>
    </div>

    <div class="contentRight">
        <input id="_spring_security_remember_me"
               name="_spring_security_remember_me" type="checkbox"/>
        <label for="_spring_security_remember_me">Запам'ятати?</label>

        <div id="remindPassword">Нагадати пароль.</div>


    </div>

    <hr class="horizontalLine">
    <div class="clearfix colelem" id="footer"><!-- column -->
        <div class="clearfix colelem" id="footerUniversityInform"><!-- content -->
            <p>Національний університет біоресурсів і природокористування України&nbsp; ННІ Енергетики, автоматики і
                енергозбереження</p>
        </div>
        <div class="clearfix colelem" id="footerContacts"><!-- group -->
            <div class="grpelem" id="addressIcon"><!-- simple frame --></div>
            <div class="clearfix grpelem" id="footerAdsressInform"><!-- column -->
                <div class="clearfix colelem" id="addressFooter"><!-- content -->
                    <p>03041, м Київ, вул Героїв Оборони 12, навчальний корпус №8</p>
                </div>
                <div class="clearfix colelem" id="designerName"><!-- content -->
                    <p>Дизайн та програмування: Гаврилюк В.В.</p>
                </div>
            </div>
            <div class="grpelem" id="footerVline"><!-- simple frame --></div>
            <div class="grpelem" id="callIcon"><!-- simple frame --></div>
            <div class="clearfix grpelem" id="fotterPhones"><!-- column -->
                <div class="clearfix colelem" id="institutePhone"><!-- content -->
                    <p>(044) 527-85-80</p>
                </div>
                <div class="clearfix colelem" id="designerPhone"><!-- content -->
                    <p>(096) 115-00-83</p>
                </div>
            </div>
            <div class="grpelem" id="fotterVline2"><!-- simple frame --></div>
            <div class="grpelem" id="emailIcon"><!-- simple frame --></div>
            <div class="clearfix grpelem" id="footerEmails"><!-- column -->
                <div class="clearfix colelem" id="instituteEmail"><!-- content -->
                    <p>epafort1@ukr.net</p>
                </div>
                <div class="clearfix colelem" id="designerEmail"><!-- content -->
                    <p>v_cheslav@ukr.net</p>
                </div>
            </div>
        </div>
        <div class="clearfix colelem" id="allRightsInform"><!-- content -->
            <p>© All rights reserved</p>
        </div>
    </div>
</div>
<div class="uploading" id="uploading"></div>

</body>
</html>



