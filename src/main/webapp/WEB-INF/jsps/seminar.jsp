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
          content="Журнал енергетика, автоматика і енергозбереження, список матеріалів семінару під егідою НАН Українии що проходить в ННІ Енергетики, автоматики і енергозбереження НУБіП України"/>
    <meta name="generator" content="2015.0.2.310"/>
    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;СемінарОн-лайн журнал &quot;Енергетика,
        автоматика і енергозбереження&quot;</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>
    <script src="../../js/seminar.js" type="text/javascript"></script>

</head>
<body>
<div class="header">
    <h1 id="magazineName">ЕНЕРГЕТИКА, АВТОМАТИКА І ЕНЕРГОЗБЕРЕЖЕННЯ</h1>
    <nav id="MenuBar">
        <ul>
            <li><a href="index.html" class="menuButton">ГОЛОВНА</a></li>
            <li><a href="publication.html" class="menuButton">ПУБЛІКАЦІЇ</a></li>
            <li><a href="seminar.html" class="menuButton menuActive">СЕМІНАР</a></li>
            <li><a href="publish.html" class="menuButton">ОПУБЛІКУВАТИ</a></li>
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

<div class="content" id="mainContent">

    <div class="leftSideBar" id="leftSideBar">
        <div class="currentDateHeader" id="currentDateContainer">
            <p id="currentDate">&nbsp;</p>
        </div>

        <div class="leftButtonsMenu" id="publicationsContent">

            <div class="leftButtonsHeader" id="rubricsName">
                <p id="rubrics">Рубрики:</p>
            </div>

            <div class="leftButtonName" name="section" data-type="group" id="sections">
                <input type="radio" name="section" id="allSeminars" value="ALL" checked>
                <label for="allSeminars">Доповіді семінару</label>
                <input type="radio" name="section" id="nextSeminars" value="NEXT">
                <label for="nextSeminars">Розклад наступних доповідей</label>
                <input type="radio" name="section" id="aboutSeminar" value="ABOUT">
                <label for="aboutSeminar">Про семінар</label>
            </div>
        </div>
    </div>

    <div class="mainContent" id="TablesContent">
        <div class="mainContentHeader" id="TableNameLabel">
            <h2 id="mainContentHeader">&nbsp;</h2>
        </div>
        <table border="1" align="center" width="98%" height="100%" class="innerTable" id="innerTable">
            <thead>
            <tr>
                <th id="number" class="innerTableHead" width="35px">№</th>
                <th id="publishDate" class="innerTableHead" width="80px">Дата доповіді</th>
                <th id="author" class="innerTableHead" width="150px">Автор</th>
                <th id="theme" class="innerTableHead" width="400px">Тема доповіді</th>
            </tr>
            </thead>
            <tbody class="innerTableBody" name="tableContent" id="tableContent">&nbsp;</tbody>
        </table>
        <div class="seminarInformation" id="seminarInformation">
            Seminar information
        </div>
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
