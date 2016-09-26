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
    <link rel="stylesheet" type="text/css" href="../../css/articles.css"/>

    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>
    <script src="../../js/seminars.js" type="text/javascript"></script>

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
            <li><a href="articles.html">Статті</a></li>
            <li class="active"><a href="seminars.html">Семінари</a></li>
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

        <div class="leftSideBar">
            <div class="barHeader">
                Рубрики:
            </div>
            <div class="barContent" name="section" data-type="group" id="sections">
                <input type="radio" name="section" id="allSeminars" value="ALL" checked>
                <label for="allSeminars">Доповіді семінару</label>
                <input type="radio" name="section" id="nextSeminars" value="NEXT">
                <label for="nextSeminars">Розклад наступних доповідей</label>
                <input type="radio" name="section" id="aboutSeminar" value="ABOUT">
                <label for="aboutSeminar">Про семінар</label>
            </div>

        </div>

        <div class="mainContent">
            <div class="barHeader" id="TableNameLabel">
                <h2 id="tableName">&nbsp;</h2>
            </div>
            <table border="1" align="center" width="700px" height="100%" class="innerTable" id="innerTable">
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

        <div class="clear"></div>

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
