<%@page contentType="text/html;charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="uk-UA">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <title>Он-лайн журнал</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css" charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/registration.css" charset="utf-8"/>

    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/jquery.ajax.upload.js"></script>
    <script src="../../js/registration.js" type="text/javascript"></script>
    <script src="../../js/siteGlobal.js"></script>


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
        <div class="mainContent">
            <div class="barHeader">
                <h2>Реєстрація</h2>
            </div>
            <form class="form" id="registrationForm" method="post" action="null">

                <div class="formField">
                    <label for="surname" class="label">
                        Прізвище <span>*</span>
                    </label>
                    <div class="textField">
                        <input class="textField" type="text" id="surname" name="surname" value="Surname"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="name" class="label">
                        Ім'я <span>*</span>
                    </label>
                    <div class="textField">
                        <input class="textField" type="text" id="name" name="name" value="Name"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="middleName" class="label"> По батькові </label>
                    <div class="textField">
                        <input class="textField" type="text" id="middleName" name="middleName" value="middleName"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="university" class="label">
                        Університет <span>*</span>
                    </label>
                    <div class="textField">
                        <input class="textField" type="text" id="university" name="university" value="university"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="institute" class="label"> Інститут </label>
                    <div class="textField">
                        <input class="textField" type="text" id="institute" name="institute" value="institute"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="chair" class="label"> Кафедра </label>
                    <div class="textField">
                        <input class="textField" type="text" id="chair" name="chair" value="chair"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="acadStatus" class="label"> Вчене звання </label>
                    <select class="textField" id="acadStatus" name="acadStatus">
                        <option value="" selected="selected" class="selected">Вчене звання</option>
                        <option value="DOCENT">Доцент</option>
                        <option value="RESEARCHER">Старший науковий співробітник</option>
                        <option value="PROFESSOR">Професор</option>
                    </select>
                </div>

                <div class="formField">
                    <label for="sciDegree" class="label"> Науковий ступінь </label>
                    <select class="textField" id="sciDegree" name="sciDegree">
                        <option value="" selected="selected" id="selected">Науковий ступінь</option>
                        <option value="CANDIDATE">Кандидат наук</option>
                        <option value="DOCTOR">Доктор наук</option>
                        <option value="PHD">Doctor of Philosophy</option>
                    </select>
                </div>

                <div class="formField">
                    <label for="position" class="label"> Посада </label>
                    <div class="textField">
                        <input class="textField" type="text" id="position" name="position" value="position"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="phone" class="label"> Телефон </label>
                    <div class="textField">
                        <input class="textField" type="text" id="phone" name="phone"/>
                    </div>
                </div>

                <div class="formField">
                    <label for="username" class="label">
                        Електронна адреса <span>*</span>
                    </label>
                    <div class="textField">
                        <input type="text" name="username" id="username" value="v_cheslav@ukr.net">
                    </div>
                </div>

                <div class="formField">
                    <label for="password" class="label">
                        Пароль: <span>*</span>
                    </label>
                    <div class="textField">
                        <input type="password" name="password" id="password" value="QwQw1212" required="true">
                    </div>
                </div>

                <div class="formField">
                    <label for="passwordConfirm" class="label">
                        Підтвердження паролю: <span>*</span>
                    </label>
                    <div class="textField">
                        <input type="password" name="passwordConfirm" id="passwordConfirm" value="QwQw1212" required="true">
                    </div>
                </div>

                <div class="formField">
                    <label for="keyWords" class="label"> Наукові інтереси (ключові слова) </label>
                    <textarea class="textField" id="keyWords" name="keyWords"></textarea>
                </div>




                <div class="formField">
                    <label for="photo" class="label"> Обрати фото </label>
                    <input type="file" class="textField" id="photo" name="userPhoto" accept="image/*">
                </div>


                <div class="formField" id="sexField">
                    <label for="userSex" class="label" id="userSexLabel"> Стать: </label>
                    <div class="radioButtons" id="userSex" name="userSex" data-required="false" data-type="group">
                        <span><input type="radio" class="radioButtons" name="userSex" value="MALE">Чоловіча</span>
                        <span><input type="radio" class="radioButtons" name="userSex" value="FEMALE">Жіноча</span>
                    </div>
                </div>

                <div class="formField" id="adminField">
                    <label for="adminChBox" class="label"> Адміністратор? </label>
                    <input class="textField" type="checkbox" id="adminChBox" name="adminChBox"
                           value="Administrator">

                </div>

                <div class="clear"></div>

                <div id="buttons">
                    <input id="regBtn" class="button" type="button" value="Зареєструватись"/>
                    <input id="btnClear" class="button" type="button" value="Очистити"/>
                </div>

            </form>

            <div class="error">
                <p id="regErrorMessage" name="registrationMassage"></p>
            </div>

    </div>

    <div class="clear"></div>
</div>
</div>

<div id="message">
    На вашу електронну адресу було відправлено повідомлення.
    Для завершення реєстрації перейдійть за надісланим листом посиланням.
</div>


<div class="uploading" id="uploading"></div>

</body>
</html>
