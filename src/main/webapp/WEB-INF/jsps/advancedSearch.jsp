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

    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;ПублікаціїОн-лайн журнал &quot;Енергетика,
        автоматика і енергозбереження&quot;</title>

    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/registration.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/advancedsearch.css" charset="utf-8"/>


    <!-- Scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery-ui.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>
    <script src="../../js/advancedSearch.js?" type="text/javascript"></script>

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
                <sec:authorize access="hasRole('ADMIN')">
                    <p>
                        <a href="/administrator">Administrator page</a>
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

    <%--leftPanel--%>
    <div class="leftSideBar" id="leftPanel">
        <div class="currentDateHeader">
            <p id="currentDate"></p>
        </div>
        <div class="accordionMenuWrapper">

            <div class="menuHeader" id="sectionsMenuHeader">
                <p>Вкажіть необхідні вам деталі пошуку</p>
            </div>
            <div class="menuContent" id="sectionsMenuContent">

                <label for="searchTypeBt" class="wordLabels">Шукати</label>
                <div class="radioButtons" id="searchTypeBt" name="searchTypeBt" data-required="false" data-type="group">
                    <input type="radio" class="radioButtons" name="searchTypeBt" id="ARTICLE" value="ARTICLE" checked>
                    <label for="ARTICLE">статтю</label>
                    <input type="radio" class="radioButtons" name="searchTypeBt" id="SEMINAR" value="SEMINAR">
                    <label for="SEMINAR">семінар</label>
                    <input type="radio" class="radioButtons" name="searchTypeBt" id="AUTHORSrch" value="AUTHORSrch">
                    <label for="AUTHORSrch">автора</label>
                </div>

                <form id="advancedSearchingForm">

                    <div id="publicationName">
                        <label for="nameOfArticle"></label>
                        <input class="textField" type="text"
                               id="nameOfArticle" name="nameOfArticle" placeholder="Назва публікації"/>
                    </div>

                    <div>
                        <label for="nameOfUser"></label>
                        <input class="textField" type="text"
                               id="nameOfUser" name="nameOfUser" placeholder="Автор"/>
                    </div>

                    <div class="select">
                        <label for="acadStatus"></label>
                        <select class="textField" id="acadStatus" name="acadStatus">
                            <option value="" selected="selected" class="selected">Вчене звання</option>
                            <option value="DOCENT">Доцент</option>
                            <option value="RESEARCHER">Старший науковий співробітник</option>
                            <option value="PROFESSOR">Професор</option>
                        </select>
                    </div>

                    <div class="select">
                        <label for="sciDegree"></label>
                        <select class="textField" id="sciDegree" name="sciDegree">
                            <option value="" selected="selected" id="selected">Науковий ступінь</option>
                            <option value="CANDIDATE">Кандидат наук</option>
                            <option value="DOCTOR">Доктор наук</option>
                            <option value="PHD">Doctor of Philosophy</option>
                        </select>
                    </div>

                    <div id="datepickerContainer" class="textField">
                        Дата публікації:
                        <label for="dateFrom"></label>
                        <p>від<input type="text" id="dateFrom" placeholder="дд.мм.рррр"></p>
                        <label for="dateTo"></label>
                        <p>до<input type="text" id="dateTo" placeholder="дд.мм.рррр"></p>
                        </div>

                    <div id="keyWordsSearchContainer">
                        <label for="keyWordsSearch"></label>
                        <input class="textField" type="text"
                               id="keyWordsSearch" name="keyWordsSearch" placeholder="ключові слова"/>
                    </div>

                    <div class="select"  id="selectSectionContainer">
                        <label for="selectSection"></label>
                        <select class="textField" id="selectSection" name="selectSection">
                            <option value="" selected="selected" class="selected">Оберіть рубрику</option>
                            <option value="AUTOMATION">Автоматика та робототехнічні системи</option>
                            <option value="EXPLOITATION">Експлуатація електрообладнання</option>
                            <option value="MACHINES">Електричні машини</option>
                            <option value="SUPPLYING">Електропостачання</option>
                            <option value="DRIVING">Електропривід</option>
                            <option value="MATHEMATICS">Математика</option>
                            <option value="HEATENERGY">Теплоенергетика</option>
                            <option value="PHYSIC">Фізика</option>
                        </select>
                    </div>

                    <div class="errorMessage" id="errorMessage">
                    </div>

                    <input type="button" class="button" onclick="advancedSearch()" value="Шукати">
                </form>


            </div>

        </div>
    </div>
    <div class="mainContent">

        <div class="mainContentHeader" id="mainContentHeader">
            <h2>Статті</h2>
        </div>


        <div id="tables">
            <table border="1" align="center" width="98%" height="100%" class="innerTable" id="innerTable">
                <thead>
                <tr>
                    <th id="number" class="innerTableHead" width="35px">№</th>
                    <th id="publishDate" class="innerTableHead" width="80px">Дата</th>
                    <th id="author" class="innerTableHead" width="150px">Автор</th>
                    <th id="theme" class="innerTableHead" width="400px">Тема</th>
                </tr>
                </thead>
                <tbody class="innerTableBody" name="tableContent" id="tableContent">&nbsp;</tbody>
            </table>
        </div>

        <div id="listContent">


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
