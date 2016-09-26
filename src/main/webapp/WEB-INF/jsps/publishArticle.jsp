<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="uk-UA">
<head>
    <title></title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/publish.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css"/>

    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/siteGlobal.js"></script>
    <script src="../../js/publishArticle.js" type="text/javascript"></script>
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
            <li class="active"><a href="#">Опублікувати</a>
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
                Правила публікації
            </div>
            <div class="barContent">
                Описати правила розміщення.
            </div>
        </div>

        <div class="mainContent">
            <form class="form" name="publishForm" id="articlePublishForm" method="post" action="null">

                <div class="barHeader">
                    <h2>Публікація статті</h2>
                </div>
                <div class="formField">
                    <label for="selectSection" class="label">
                        Рубрика
                        <span>*</span>
                    </label>
                    <select class="textField" id="selectSection" name="articleSection">
                        <option value="" selected="selected" class="selected">Обрати рубрику</option>
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

                <div class="formField">
                    <label for="articleName" class="label">
                        Назва статті
                        <span>*</span>
                    </label>
                    <textarea class="textField" id="articleName" name="articleName"></textarea>
                </div>

                <div class="formField">
                    <label for="keyWords" class="label">
                        Ключові слова
                        <span>*</span>
                    </label>
                    <textarea class="textField" id="keyWords" name="keyWords"></textarea>
                </div>

                <div class="formField">
                    <label for="annotationUa" class="label">
                        Анотація українською
                        <span>*</span>
                    </label>
                    <textarea class="textField annotation" id="annotationUa" name="annotationUa"></textarea>
                </div>
                <div class="formField">
                    <label for="annotationEng" class="label">
                        Анотація англійською
                        <span>*</span>
                    </label>
                    <textarea class="textField annotation" id="annotationEng" name="annotationEng"></textarea>
                </div>
                <div class="formField">
                    <label for="annotationRu" class="label">
                        Анотація російською
                        <span>*</span>
                    </label>
                    <textarea class="textField annotation" id="annotationRu" name="annotationRu"></textarea>
                </div>

                <div class="formField">
                    <label id="pickArticleLabel" for="articleFile" class="label">
                        Обрати статтю
                        <span>*</span>
                    </label>
                    <input type="file" class="textField" id="articleFile" name="articleFile">
                </div>

                <div id="buttons">
                    <input id="publArticleBtn" class="button" name="article" type="button" value="Опублікувати"/>
                    <input id="btnClear" class="button" type="button" value="Очистити"/>
                </div>
            </form>
            <div class="messageField">
                <p id="articleErrorMessage" class="regMessage"></p>
            </div>
        </div>

        <div class="clear"></div>
    </div>
</div>

</body>
</html>
