<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="html" lang="uk-UA">
<head>


    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <meta name="description" content="Журнал енергетика, автоматика і енергозбереження, список публікацій журналу."/>
    <meta name="generator" content="2015.0.2.310"/>
    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;ПублікаціїОн-лайн журнал &quot;Енергетика,
        автоматика і енергозбереження&quot;</title>
    <!-- Scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>
    <script src="../../js/articles.js" type="text/javascript"></script>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/articles.css"/>
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

        <div class="leftSideBar">
            <div class="barHeader">
                Рубрики:
            </div>
            <div class="barContent" name="section" data-type="group" id="sections">
                <input type="radio" name="section" id="allArticles" value="ALL" checked>
                <label for="allArticles">Всі статті</label>
                <input type="radio" name="section" id="automationArt" value="AUTOMATION">
                <label for="automationArt">Автоматика та робототехнічні системи</label>
                <input type="radio" name="section" id="exploitationArt" value="EXPLOITATION">
                <label for="exploitationArt">Експлуатація електрообладнання</label>
                <input type="radio" name="section" id="machinesArt" value="MACHINES">
                <label for="machinesArt">Електричні машини</label>
                <input type="radio" name="section" id="supplyingArt" value="SUPPLYING">
                <label for="supplyingArt">Електропостачання</label>
                <input type="radio" name="section" id="drivingsArt" value="DRIVING">
                <label for="drivingsArt">Електропривід</label>
                <input type="radio" name="section" id="mathematicsArt" value="MATHEMATICS">
                <label for="mathematicsArt">Математика</label>
                <input type="radio" name="section" id="heatEnergyArt" value="HEATENERGY">
                <label for="heatEnergyArt">Теплоенергетика</label>
                <input type="radio" name="section" id="physicsArt" value="PHYSIC">
                <label for="physicsArt">Фізика</label><%--todo для адміна додати всі що чекають на рецензування--%>
            </div>
        </div>

        <div class="mainContent">
            <div class="barHeader" id="TableNameLabel">
                <h2 id="tableName">&nbsp;</h2>
            </div>

            <table align="center" width="700px" height="100%" class="innerTable" id="innerTable">
                <thead>
                <tr>
                    <th id="number" class="innerTableHead" width="35px">№</th>
                    <th id="publishDate" class="innerTableHead" width="80px">Дата публікації</th>
                    <th id="author" class="innerTableHead" width="150px">Автор</th>
                    <th id="theme" class="innerTableHead" width="400px">Тема</th>
                </tr>
                </thead>
                <tbody class="innerTableBody" name="tableContent" id="tableContent">&nbsp;</tbody>
            </table>

        </div>

        <div class="clear"></div>

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
