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
    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;ОпублікуватиОн-лайн журнал &quot;Енергетика,
        автоматика і енергозбереження&quot;</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/jquery-ui.css"/></title>
    <link rel="stylesheet" type="text/css" href="../../css/administrator.css"/>

    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery-ui.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/siteGlobal.js"></script>
    <script src="../../js/administrator.js"></script>


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
    <div class="leftPanel" id="leftPanel">
        <div class="currentDateHeader">
            <p id="currentDate"></p>
        </div>
        <div class="accordionMenuWrapper">
            <ul class="accordionMenu">

                <li>
                    <div class="menuHeader" id="itemCreationHeader">
                        <p>Створити</p>
                    </div>
                    <div class="menuContent" id="itemCreationContent">
                        <ul class="leftMenuList" id="itemCreationList">
                            <li id="seminarCreation">Подати інформацію про семінар</li>
                        </ul>
                    </div>
                </li>
                <li class="AccordionPanel" id="itemEditing">
                    <div class="menuHeader" id="itemEditingHeader">
                        <p>Редагувати</p>
                    </div>
                    <div class="menuContent" id="itemEditingContent">
                        <ul class="leftMenuList" id="itemEditingList">
                            <li id="articleEditing">Статтю</li>
                            <li id="annotationEditing">Анотацію</li>
                            <li id="reviewEditing">Рецензію</li>
                            <li id="seminarEditing">Семінар</li>
                            <li id="userRoleEditing">Надати права адміністратора</li>
                        </ul>
                    </div>
                </li>
                <li class="AccordionPanel" id="itemDeleting">
                    <div class="menuHeader" id="itemDeletingHeader">
                        <p>Видалити</p>
                    </div>
                    <div class="menuContent" id="itemDeletingContent">
                        <ul class="leftMenuList" id="itemDeletingList">
                            <li id="deleteUser">Користувача</li>
                            <li id="deleteArticle">Статтю</li>
                            <li id="deleteSeminar">Семінар</li>
                        </ul>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div id="information">
        <c:if test="${applyiedSeminars.size() != 0}">

            <div class="mainContentHeader" id="SeminarMessageHeader">
                <h2 id="informationDisplay">Повідомлення</h2>
            </div>

            <div class="menuContent" id="SeminarMessageContent">
                <div class="seminarMessage">
                    <p>Надійшли заявки на часть в семінарі:</p>
                    <c:forEach var="seminar" items="${applyiedSeminars}">
                        <span>від ${seminar.user.toString()}</span>
                        <span>на тему "${seminar.seminarName}".</span>
                        <span>Заявлена дата: ${seminar.seminarDateToString()}</span>
                        <br>
                    </c:forEach>
                </div>
            </div>
        </c:if>

    </div>


    <div class="contentContainer" id="contentContainer">

        <div class="mainContentHeader">
            <p class="innerHeaderText">Оголошення про участь в семінарі</p>
        </div>
        <div class="menuContent">
            <div id="seminarCreationContainer">

                <input class="newSeminarChBox" type="checkbox" id="newSeminarChBox" name="newSeminarChBox" value="newSeminar" >Новий семінар</input>
                <form class="publishForm" name="publishForm" id ="seminarPublishForm" method="post" action="null">

                    <div class="existedSeminar" id="existedSeminar">
                        <div>
                            <select class="textField" id="chooseSeminarName">
                                <option value="" selected="selected"  class="selected">Виберіть семінар зі списку</option>
                                <c:forEach var="seminar" items="${applyiedSeminars}">
                                    <option name="selectedName" value="${seminar.seminarId}">${seminar.seminarName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <input class="changeSemNameChBox" type="checkbox" id="changeSemNameChBox" name="changeSemNameChBox" value="newSeminar">Редагувати назву семінару</input>

                        <div id="changedSemName">
                            <label for="existedSeminarName" class="label"></label>
                            <textarea class="textField" name="existedSeminarName" id="existedSeminarName" placeholder="Нова назва."></textarea>
                        </div>
                    </div>

                    <div class="newSeminar" id="newSeminar">
                        <div>
                            <label for="userName" class="label"></label>
                            <select class="textField" id="userName"></select>
                        </div>
                        <span>або</span>
                        <div>
                            <label for="unRegUserName" class="label"></label>
                            <textarea class="textField" id="unRegUserName" placeholder="Введіть і'мя доповідача"></textarea>
                        </div>
                        <div class="newSeminarNameContainer">
                            <label for="newSeminarName" class="label"></label>
                            <textarea class="textField" name="newSeminarName" id="newSeminarName" placeholder="Назва доповіді семінару."></textarea>
                        </div>
                    </div>

                    <div id="datepickerContainer" class="textField">
                        <p>Дата доповіді: <input type="text" id="datepicker" placeholder="дд.мм.рррр"></p>
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


            <div id="deleteUserContainer">
                <label for="selectUserForDelete" class="label"></label>
                <select class="textField" id="selectUserForDelete"></select>
                <input class="button" id="deleteUserBtn" onclick="deleteUser()" type="button" value="Видалити"/>
            </div>
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