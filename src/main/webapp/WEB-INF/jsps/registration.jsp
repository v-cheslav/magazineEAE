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
    <link rel="stylesheet" type="text/css" href="../../css/registration.css" id="pagesheet" charset="utf-8"/>
    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/jquery.ajax.upload.js"></script>
    <script src="../../js/registration.js" type="text/javascript"></script>
    <script src="../../js/upload.js" type="text/javascript"></script>
    <script src="../../js/siteGlobal.js"></script>

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
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">
            <div class="instituteLogo"></div>
            <h3 class="instituteHeaderName">ННІ Енергетики, автоматики <br>і енергозбереження</h3>
            <a href="advancedSearch.html" class="menuButton" id="searchingButton">Пошук</a>
        </div>
    </div>
    <hr class="horizontalLine">
</div>

<div class="regTitle" id="registTitle">
    <p id="titleText">Реєстрація</p>
</div>

<div id="regContent">

    <div class="objectFont">

        <form class="registrationForm" id="registrationForm" method="post" action="null">
            <div class="leftField">
                <div>
                    <label for="surname" class="label"></label>
                    <input class="textField" type="text"
                           id="surname" name="surname" placeholder="Прізвище" value="Surname"/>
                </div>

                <div>
                    <label for="name" class="label"></label>
                    <input class="textField" type="text"
                           id="name" name="name" placeholder="Ім'я" value="Name"/>
                </div>

                <div>
                    <label for="middleName" class="label"></label>
                    <input class="textField" type="text"
                           id="middleName" name="middleName" placeholder="По батькові" value="middleName"/>
                </div>

                <div>
                    <label for="university" class="label"></label>
                    <input class="textField" type="text"
                           id="university" name="university" placeholder="Університет" value="university"/>
                </div>

                <div>
                    <label for="institute" class="label"></label>
                    <input class="textField" type="text"
                           id="institute" name="institute" placeholder="Інститут" value="Insitute"/>
                </div>

                <div>
                    <label for="chair" class="label"></label>
                    <input class="textField" type="text"
                           id="chair" name="chair" placeholder="Кафедра" value="Chair"/>
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
                           id="position" name="position" placeholder="Посада" value="Position"/>
                </div>

                <div>
                    <label for="phone" class="label"></label>
                    <input class="textField" type="text"
                           id="phone" name="phone" placeholder="Телефон"/>
                </div>

                <div>
                    <label for="username" class="label" require="true"></label>
                    <input class="textField" type="text"
                           id="username" name="username" placeholder="Електронна адреса" value="user@com"/>
                </div>

                <div>
                    <label for="password" class="label"></label>
                    <input class="textField" type="password" required="true"
                           id="password" name="password" placeholder="Пароль" value="QwQw1212"/>
                </div>

                <div>
                    <label for="passwordConfirm" class="label"></label>
                    <input class="textField" type="password" required="true" value="QwQw1212"
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
                        <span><input type="radio" class="radioButtons" name="userSex" value="MALE">Чоловіча</span><br>
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

                <div class="adminCheck">
                    <input class="adminChBox" type="checkbox" id="adminChBox" name="adminChBox" value="Administrator">Адміністратор</input>
                </div>
                <input class="button" id="regBtn" type="button" value="Зареєструватись"/>
            </div>
        </form>
        <form id="fileForm" class="fileForm">
            <div>
                <label id="newImgContainer" for="photo" class="photoFrame">
                    <img src="../images/noPhotoMan.png">
                </label>
                <input type="file" class="photoAddress" id="photo" name="file" accept="image/*">
                <button id="btnUpload" class="button" type="button">Завантажити фото</button>
                <button id="btnClear" class="button" type="button">Очистити</button>
            </div>
        </form>
    </div>

    <div class="regInf" id="regInf">
        <p>Особисту інформацію можна буде доповнити або змінити в налаштуваннях профілю.</p>
    </div>

</div>
<div id="message">
    На вашу електронну адресу було відправлено повідомлення.
    Для завершення реєстрації перейдійть за надісланим листом посиланням.
</div>

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
