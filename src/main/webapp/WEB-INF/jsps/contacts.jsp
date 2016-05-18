<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

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
    <link rel="stylesheet" type="text/css" href="../../css/contacts.css" id="pagesheet"/>

    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>


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
            <li><a href="contacts.html" class="menuButton menuActive">КОНТАКТИ</a></li>
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


    <div class="information"><!-- content -->
        <p>Журнал діє на базі навчально-наукового інституту &quot;Енергетики, автоматики і
            енергозбереження&quot; Національного університету біоресурсів і природокористування України.
        </p>
    </div>
    <div class="information"><!-- content -->
        <p id="address">Наша адреса: 03041, м Київ, вул Героїв Оборони 12, навчальний корпус №8</p>

        <p id="phone">Тел.: (044) 527-85-80.</p>

        <p id="mail">E-mail: epafort1@ukr.net</p>
    </div>
    <div class="mapContainer" id="mapContainer"><!-- custom html -->

        <iframe class="actAsDiv" width="1020" height="400" frameborder="0" scrolling="no" marginheight="0"
                marginwidth="0"
                src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=uk&amp;q=%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D0%B0%2C%20%D0%9A%D0%B8%D1%97%D0%B2%20%D0%B2%D1%83%D0%BB%20%D0%93%D0%B5%D1%80%D0%BE%D1%97%D0%B2%20%D0%9E%D0%B1%D0%BE%D1%80%D0%BE%D0%BD%D0%B8%2012&amp;aq=0&amp;ie=UTF8&amp;t=m&amp;z=15&amp;iwloc=A&amp;output=embed"></iframe>

    </div>



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