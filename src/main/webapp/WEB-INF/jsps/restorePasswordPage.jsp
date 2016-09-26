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
    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/login.css" charset="utf-8"/>
    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/login.js"></script>
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
        <div class="mainContent restorePassContent">
            <div class="barHeader">
                <h2>Відновлення паролю</h2>
            </div>

            <div class="restoreMessage">
                На вашу електронну адресу було надіслано лист з номером.
                Для відновлення паролю введіть отриманий листом номер та новий пароль!
            </div>
            <form class="form" name="restorePasswordForm" id="restorePasswordForm"/>

            <div class="formField">
                <label for="username" class="label">
                    Електронна адреса
                    <span>*</span>
                </label>
                <div class="textField">
                    <input type="text" name="username" id="username" value="v_cheslav@ukr.net">
                </div>
            </div>

            <div class="formField">
                <label for="restoreCodeStr" class="label">
                    Отриманий код:
                    <span>*</span>
                </label>
                <div class="textField">
                    <input type="password" name="restoreCodeStr" id="restoreCodeStr">
                </div>
            </div>

            <div class="formField">
                <label for="newPassword" class="label">
                    Пароль:
                    <span>*</span>
                </label>
                <div class="textField">
                    <input type="password" name="newPassword" id="newPassword">
                </div>
            </div>

            <div class="formField">
                <label for="newPasswordConfirm" class="label">
                    Підтвердження паролю:
                    <span>*</span>
                </label>
                <div class="textField">
                    <input type="password" name="newPasswordConfirm" id="newPasswordConfirm">
                </div>
            </div>

            <div class="buttons">
                <input class="button" type="submit" id="restorePasswordBtn" value="Відновити">
            </div>

            </form>
        </div>
            <div class="clear"></div>
    </div>

</div>
<div class="uploading" id="uploading"></div>

</body>
</html>