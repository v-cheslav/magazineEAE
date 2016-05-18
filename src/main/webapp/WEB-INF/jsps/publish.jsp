<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html class="html" lang="uk-UA">
<head>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <meta name="description"
          content="Розміщення матеріалів семінару під егідою НАН Українии та внутрішніх публікацій журналу ННІ Енергетики, автоматики і енергозбереження"/>
    <%--<title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;ОпублікуватиОн-лайн журнал &quot;Енергетика,--%>
    <%--автоматика і енергозбереження&quot;</title>--%>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/publish.css" id="pagesheet"/>
    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/publish.js" type="text/javascript"></script>


</head>
<body>
    <div class="header">
        <h1 id="magazineName">ЕНЕРГЕТИКА, АВТОМАТИКА І ЕНЕРГОЗБЕРЕЖЕННЯ</h1>
        <nav id="MenuBar">
            <ul>
                <li><a href="index.html" class="menuButton">ГОЛОВНА</a></li>
                <li><a href="publication.html" class="menuButton">ПУБЛІКАЦІЇ</a></li>
                <li><a href="seminar.html" class="menuButton">СЕМІНАР</a></li>
                <li><a href="publish.html" class="menuButton menuActive">ОПУБЛІКУВАТИ</a></li>
                <li><a href="contacts.html" class="menuButton">КОНТАКТИ</a></li>
            </ul>
        </nav>
        <hr class="horizontalLine">
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
                    <sec:authorize access="hasRole('admin')">
                        <p>
                            <a href="/admin">Admin page</a>
                        </p>
                    </sec:authorize>
                </div>
            </div>
            <hr class="horizontalLine" id="bannerLine">
            <div class="bannerBottom">
                <div class="instituteLogo"></div>
                <h3 class="instituteHeaderName">ННІ Енергетики, автоматики <br>і енергозбереження</h3>
                <a href="advancedSearch.html" class="menuButton" id="searchingButton">Розширений</a>

                <form class="searchingForm" id="searchingForm">
                    <div class="searchingSign"></div>
                    <input class="searchingArea" type="text" id="searchingTxt" name="customSearch"/>
                </form>
            </div>
        </div>
        <hr class="horizontalLine">
    </div>

    <div class="content" id="content">

        <div class="leftButtonName">
            <input type="radio" name="tabs" id="articlePublCheck" checked>
            <label for="articlePublCheck">Опублікувати статтю</label>
            <input type="radio" name="tabs" id="seminarPublCheck">
            <label for="seminarPublCheck">Опублікувати семінар</label>
        </div>
    </div>
    <div class="publishContent" id="publishContent">

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

    <div class="uploading" id="uploading"></div>
</body>
</html>