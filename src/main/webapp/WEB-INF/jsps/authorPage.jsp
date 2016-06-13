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
    <link rel="stylesheet" type="text/css" href="../../css/jquery-ui.css"/>
    </title>
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/registration.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/myPageNew.css"/>
    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery-ui.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/jquery.ajax.upload.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>
    <script src="../../js/upload.js" type="text/javascript"></script>
    <script src="../../js/myPage.js" type="text/javascript"></script>
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
            <h3 class="headerUserName">
                ${userDetails.name} ${userDetails.middleName} ${userDetails.surname}
            </h3>

            <h3 class="headerUniversityName">Національний університет біоресурсів <br> і природокористування України
            </h3>

            <div class="authForm" id="authForm">
                <p><a href="/j_spring_security_logout">Вийти</a></p>
            </div>
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">

            <img class="userPhoto" id="imgContainer" src="getFile?name=${userDetails.photoAddress}&type=img" alt="Photo">

            <div id="aStatAndSDegree">
                <p class="paddingBottom"> ${userDetails.acadStatus.toString()}</p>

                <p class="paddingBottom"> ${userDetails.sciDegree.toString()}</p>

                <p class="paddingBottom"> ${userDetails.chair}</p>

            </div>
            <div id="instituteNameHeader">
                <p> ${userDetails.institute}</p>
            </div>
            <a href="advancedSearch.html" class="menuButton" id="searchingButton">Пошук</a>
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
                <input type="radio" name="section" id="myPublications" value="PUBLICATIONS"
                       onclick="getUserArticles(${userDetails.userId})" checked>
                <label for="myPublications">Публікації</label>
                <input type="radio" name="section" id="myReports" onclick="getUserSeminars(${userDetails.userId})"
                       value="REPORTS">
                <label for="myReports">Доповіді семінару</label>
                <input type="radio" name="section" id="myReviews" onclick="getReviewersArticles(${userDetails.userId})"
                       value="REVIEW">
                <label for="myReviews">Рецензовані статті</label>
            </div>
        </div>
    </div>

    <div class="mainContent"<%-- id="TablesContent"--%>>

        <div class="mainContentHeader" id="TableNameLabel">
            <h2 id="mainContentHeader">&nbsp;</h2>
        </div>
        <table border="1" align="center" width="700px" height="100%" class="innerTable" id="innerTable">
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

        <div class="updateUser" id="updateUser">

            <form class="registrationForm" id="userUpdatingForm" action="null">
                <%--<fieldset>--%>
                <div class="leftField">
                    <div>
                        <label for="surname" class="label"></label>
                        <input class="textField" type="text"
                               id="surname" name="surname" placeholder="Прізвище"/>
                    </div>

                    <div>
                        <label for="name" class="label"></label>
                        <input class="textField" type="text"
                               id="name" name="name" placeholder="Ім'я"/>
                    </div>

                    <div>
                        <label for="middleName" class="label"></label>
                        <input class="textField" type="text"
                               id="middleName" name="middleName" placeholder="По батькові"/>
                    </div>

                    <div>
                        <label for="university" class="label"></label>
                        <input class="textField" type="text"
                               id="university" name="university" placeholder="Університет"/>
                    </div>

                    <div>
                        <label for="institute" class="label"></label>
                        <input class="textField" type="text"
                               id="institute" name="institute" placeholder="Інститут"/>
                    </div>

                    <div>
                        <label for="chair" class="label"></label>
                        <input class="textField" type="text"
                               id="chair" name="chair" placeholder="Кафедра"/>
                    </div>

                    <div>
                        <label for="acadStatus" class="label"></label>
                        <select class="textField" id="acadStatus" name="acadStatus">
                            <option value="" selected="selected" class="selected">Вчене звання</option>
                            <option value="DOCENT">Доцент</option>
                            <option value="RESEARCHER">Старший науковий співробітник</option>
                            <option value="PROFESSOR">Професор</option>
                        </select>
                    </div>

                    <div>
                        <label for="sciDegree" class="label"></label>
                        <select class="textField" id="sciDegree" name="sciDegree">
                            <option value="" selected="selected" id="selected">Науковий ступінь</option>
                            <option value="CANDIDATE">Кандидат наук</option>
                            <option value="DOCTOR">Доктор наук</option>
                            <option value="PHD">Doctor of Philosophy</option>
                        </select>
                    </div>

                    <div>
                        <label for="position" class="label"></label>
                        <input class="textField" type="text"
                               id="position" name="position" placeholder="Посада"/>
                    </div>

                    <div>
                        <label for="phone" class="label"></label>
                        <input class="textField" type="text"
                               id="phone" name="phone" placeholder="Телефон"/>
                    </div>

                    <div>
                        <label for="username" class="label"></label>
                        <input class="textField" type="text"
                               id="username" name="username" placeholder="Електронна адреса"/>
                    </div>

                    <div>
                        <label for="password" class="label"></label>
                        <input class="textField" type="password" id="password" name="password" placeholder="Пароль"/>
                    </div>

                    <div>
                        <label for="passwordConfirm" class="label"></label>
                        <input class="textField" type="password"
                               id="passwordConfirm" name="passwordConfirm" placeholder="Підтвердження паролю"/>
                    </div>
                </div>

                <div class="rightField">

                    <div>
                        <label for="keyWords" class="wordLabels">Наукові інтереси</label>
                        <textarea class="textField" id="keyWords" name="keyWords"
                                  placeholder="Введіть не більше 5-ти ключових слів."></textarea>
                    </div>

                    <div>
                        <label for="userSex" class="wordLabels">Стать</label>

                        <div class="radioButtons" id="userSex" name="userSex" data-required="false" data-type="group">
                            <span><input type="radio" class="radioButtons" name="userSex"
                                         value="MALE">Чоловіча</span><br>
                            <span><input type="radio" class="radioButtons" name="userSex" value="FEMALE">Жіноча  </span>
                        </div>
                    </div>

                    <div class="messageField">
                        <p id="regErrorMessage" class="regMessage" name="message">
                            <c:if test="${message!=null}">
                                <c:out value="${message}"/>
                            </c:if>
                        </p>
                    </div>

                    <input class="button" id="regBtn" type="button" value="Редагувати"/>
                </div>
                </fieldset>
            </form>
            <form id="fileForm" class="fileForm">
                <div>
                    <label id="newImgContainer" for="photo" class="photoFrame">
                        <img src="getFile?name=${userDetails.photoAddress}&type=img">
                    </label>
                    <input type="file" class="photoAddress" id="photo" name="file" accept="image/*">
                    <button id="btnUpload" class="button" type="button">Завантажити фото</button>
                    <br>
                    <button id="btnClear" class="button" type="button">Очистити</button>
                </div>
            </form>

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