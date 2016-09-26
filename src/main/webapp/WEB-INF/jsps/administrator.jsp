<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html class="html" lang="uk-UA">
<head>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <title></title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/administrator.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/publish.css"/>

    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css"/>

    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery-ui.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/siteGlobal.js"></script>
    <script src="../../js/administrator.js"></script>


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



<div class="content" id="content">
    <div class="whiteBox">

        <div class="leftSideBar" id="leftSideBar">
            <div class="barHeader">
                Панель адміністратора:
            </div>

            <div class="barContent">
                <ul>
                    <li class="menuItem" id="articleMenuBtn">
                        <div>Статті на рецензування</div>
                    </li>
                    <li class="menuItem" id="seminarMenuBtn">
                        <div>Заявки на семінар</div>
                    </li>
                    <li class="menuItem" id="adminMenuBtn">
                        <div>Надати права адміністратора</div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="mainContent" id="articleContent">

            <c:if test="${unpublishedArticles.size() != 0}">
                <script>getArticlesWithoutReviewers();</script>
                <div class="barHeader" id="TableNameLabel">
                    <h2>Статті для призначення рецензентів</h2>
                </div>
                <div class="form" id="ArticleMessageContent">

                    <table align="center" width="700px" height="100%" class="innerTable" id="articlesTable">
                        <thead>
                        <tr>
                            <th id="number" class="innerTableHead" width="35px">№</th>
                            <th id="publishDate" class="innerTableHead" width="80px">Дата публікації</th>
                            <th id="author" class="innerTableHead" width="150px">Автор</th>
                            <th id="theme" class="innerTableHead" width="400px">Тема</th>
                        </tr>
                        </thead>
                        <tbody class="innerTableBody" name="tableContent" id="articlesTableContent">&nbsp;</tbody>
                    </table>


                    <div class="formField">
                        <label for="selectArticle" class="label">
                            Обрати статтю
                            <span>*</span>
                        </label>
                        <select class="textField" id="selectArticle" name="selectArticle"
                                onChange='selectArticleForAddReviewers();'> </select>

                    </div>

                    <div class="addReviewers" id="addReviewers">
                    </div>
                </div>
            </c:if>
        </div>


        <div class="mainContent" id="seminarContent">

            <div class="mainContentHeader">
                <p class="innerHeaderText">Оголошення про участь в семінарі</p>
            </div>
            <div class="menuContent">
                <div id="seminarCreationContainer">
                    <input class="newSeminarChBox" type="checkbox" id="newSeminarChBox" name="newSeminarChBox"
                           value="newSeminar">
                    <label for="newSeminarChBox"><h3>Подати інформацію про новий семінар</h3></label>

                    <c:if test="${applyiedSeminars.size() != 0}">
                        <script>getDeclaredSeminars();</script>
                        <div class="menuContent" id="SeminarMessageContent">
                            <div class="seminarMessage">

                                <h3>Надійшли заявки на участь у семінарі:</h3>
                            </div>
                            <table align="center" width="700px" height="100%" class="innerTable" id="seminarsTable">
                                <thead>
                                <tr>
                                    <th class="innerTableHead" width="35px">№</th>
                                    <th class="innerTableHead" width="80px">Заявлена дата</th>
                                    <th class="innerTableHead" width="150px">Автор</th>
                                    <th class="innerTableHead" width="auto">Тема</th>
                                </tr>
                                </thead>
                                <tbody class="innerTableBody" id="seminarsTableContent"></tbody>
                            </table>
                        </div>
                    </c:if>



                    <form class="form" name="publishForm" id="seminarPublishForm" method="post" action="null">



                        <div id="existedSeminar">
                            <div class="formField">
                                <label for="chooseSeminarName">Вибрати семінар зі списку</label>
                                <select class="textField" id="chooseSeminarName">
                                    <option value="" selected="selected" class="selected">Cемінар
                                    </option>
                                    <c:forEach var="seminar" items="${applyiedSeminars}">
                                        <option name="selectedName"
                                                value="${seminar.id}">${seminar.publicationName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <input class="changeSemNameChBox" type="checkbox" id="changeSemNameChBox"
                                   name="changeSemNameChBox" value="newSeminar">
                            <label for="changeSemNameChBox">Редагувати назву семінару</label>

                            <div  class="formField" id="changedSemName">
                                <label for="existedSeminarName" class="label"></label>
                                <textarea class="textField" name="existedSeminarName" id="existedSeminarName"
                                          placeholder="Нова назва."></textarea>
                            </div>
                        </div>

                        <div class="newSeminar" id="newSeminar">
                            <div class="formField">
                                <label for="userName" class="label"></label>
                                <select class="textField" id="userName"></select>
                            </div>
                            <span>або</span>

                            <div class="formField">
                                <label for="unRegUserName" class="label"></label>
                                <textarea class="textField" id="unRegUserName"
                                          placeholder="Введіть і'мя доповідача"></textarea>
                            </div>
                            <div class="formField">
                                <label for="newSeminarName" class="label"></label>
                                <textarea class="textField" name="newSeminarName" id="newSeminarName"
                                          placeholder="Назва доповіді семінару."></textarea>
                            </div>
                        </div>

                        <div id="datepickerContainer" class="formField">
                            <label for="datepicker" class="label">Дата доповіді:
                                <span>*</span>
                            </label>
                            <input type="text" class="textField" id="datepicker" placeholder="дд.мм.рррр">
                        </div>



                        <div class="messageField">
                            <p id="seminarRegErrorMessage" name="message" class="error">
                                <c:if test="${message!=null}">
                                    <c:out value="${message}"/>
                                </c:if>
                            </p>
                        </div>

                        <input class="button" id="publSeminarBtn" name="seminar" type="button" value="Опублікувати"/>
                    </form>
                </div>

            </div>
        </div>

        <div class="mainContent" id="addAdminRights">

            <div class="mainContentHeader">
                <p class="innerHeaderText">Надати права адміністарора</p>
            </div>
            <div class="menuContent">

            </div>
        </div>

        <div class="clear">
        </div>

    </div>


</div>

<hr class="horizontalLine">
<div class="footer" id="footer">
    <div class="" id="footerUniversityInform">
        <p>Національний університет біоресурсів і природокористування України&nbsp; ННІ Енергетики, автоматики і
            енергозбереження</p>
    </div>
    <div class="" id="footerContacts">
        <div class="" id="addressIcon"></div>
        <div class="" id="footerAdsressInform">
            <div class="" id="addressFooter">
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