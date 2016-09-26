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
    <link rel="stylesheet" type="text/css" href="../../css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/myPageNew.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/formsAndButtons.css"/>

    <!-- Other scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery-ui.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/jquery.ajax.upload.js"></script>
    <script src="../../js/myPage.js" type="text/javascript"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>

</head>
<body>

<div class="header">
    <div class="banner">
        <div class="bannerTop">
            <h3 class="headerUserName">
                ${userDetails.name} ${userDetails.middleName} ${userDetails.surname}
            </h3>

            <h3 class="headerUniversityName">${userDetails.university}</h3>

            <div class="authForm" id="authForm">
                <p><a href="/j_spring_security_logout">Вийти</a></p>
            </div>
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">
            <img class="userPhoto" id="imgContainer" src="getFile?name=${userDetails.photoAddress}&type=img"
                 alt="Photo">

            <div id="aStatAndSDegree">
                <p class="paddingBottom"> ${userDetails.acadStatus.toString()}</p>

                <p class="paddingBottom"> ${userDetails.sciDegree.toString()}</p>

                <p class="paddingBottom"> ${userDetails.chair}</p>
            </div>
            <div id="instituteNameHeader">
                <p> ${userDetails.institute}</p>
            </div>
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
                Меню:
            </div>

            <div class="barContent" name="section" data-type="group" id="sections">
                <input type="radio" name="section" id="massages" value="MASSAGES" checked>
                <label for="massages">Повідомлення</label>
                <input type="radio" name="section" id="myPublications" value="PUBLICATIONS"
                       onclick="getUserArticles(${userDetails.userId})">
                <label for="myPublications">Мої публікації</label>
                <input type="radio" name="section" id="myReports" onclick="getUserSeminars(${userDetails.userId})"
                       value="REPORTS">
                <label for="myReports">Мої доповіді</label>
                <input type="radio" name="section" id="myReviews"
                       onclick="getReviewersArticles(${userDetails.userId})"
                       value="REVIEW">
                <label for="myReviews">Рецензовані статті</label>
                <input type="radio" name="section" id="applySeminar" value="APPLY">
                <label for="applySeminar">Подати заявку на участь в семінарі</label>
                <input type="radio" name="section" id="editPage" value="EDIT">
                <label for="editPage">Редагувати сторінку</label>
            </div>
        </div>

        <div class="mainContent">

            <div class="barHeader" id="TableNameLabel">
                <h2 id="mainContentHeader">&nbsp;</h2>
            </div>

            <div id="information">

                <div class="menuContent">
                    <c:if test="${seminarMessage!=null}">
                        <p>
                            <c:out value="${seminarMessage}"/>
                        </p>
                        <hr>
                    </c:if>

                    <c:if test="${article != null}">
                        <p>
                            Заявка на публікацію статті
                            "<c:out value="${article.publicationName}"/>"
                            прийнята.
                        </p>
                        <c:if test="${article.articleReviews.size() != 0}">
                            <p>
                                Рецензія 1 від
                                <c:out value="${article.articleReviews.get(0).user.toString()}"/>
                                <c:if test="${article.articleReviews.get(0).status == false}">
                                    очікується.
                                </c:if>
                                <c:if test="${article.articleReviews.get(0).status == true}">
                                    надана.
                                </c:if>
                            </p>

                            <p>
                                Рецензія 2 від
                                <c:out value="${article.articleReviews.get(1).user.toString()}"/>
                                <c:if test="${article.articleReviews.get(1).status == false}">
                                    очікується.
                                </c:if>
                                <c:if test="${article.articleReviews.get(1).status == true}">
                                    надана.
                                </c:if>
                                <c:if test="${article.articleReviews.get(1).status == null}">
                                    відхилена. Ви можете обрати іншого рецензента.
                                        <span class="selectReviewer">
                                            <select name="secondReviewer" id="secondReviewer">
                                                <option selected='selected' name="secondOption" id="secondOption"
                                                        value=''>
                                                    Рецензент
                                                </option>
                                            </select>
                                        </span>
                                    <span class="newReviewer" id="newReviewer2">Надіслати нову заявку.</span>
                                </c:if>
                            </p>
                        </c:if>
                        <hr>
                    </c:if>

                    <c:if test="${reviews != null}">
                        <p>
                            Вам надійшла заявка на рецензування статті:<br>
                            <c:forEach var="reviewName" items="${reviews}">
                                <a href="articlePage?publicationId=${reviewName.article.id}">
                                    <c:out value="${reviewName.article.publicationName}"/> <%--todo--%>
                                </a>
                                <br>
                            </c:forEach>

                        </p>
                        <hr>
                    </c:if>

                </div>
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


            <div class="applySeminarContainer" id="applySeminarContainer">
                <form class="form" id="applySeminarForm" action="null">
                    <div class="formField">
                        <label for="newSeminarTheme" class="label">
                            Тема семінару<span>*</span>
                        </label>
                        <textarea class="textField" name="newSeminarTheme" id="newSeminarTheme"> </textarea>
                    </div>

                    <div id="datepickerContainer" class="formField">
                        <label for="datepicker" class="label">Дата доповіді:
                            <span>*</span>
                        </label>
                        <input type="text" class="textField" id="datepicker" placeholder="дд.мм.рррр">
                    </div>

                    <input class="button" id="applySeminarBtn" onclick="applySeminar()" type="button"
                           value="Подати заявку"/>

                    <div id="seminarErrorMessage" class="error"></div>
                </form>
            </div>


            <div class="updateUser" id="updateUser">

                <form class="form" id="userUpdatingForm" action="null">


                    <div class="formField">
                        <label for="surname" class="label"> Прізвище </label>
                        <div class="textField">
                            <input class="textField" type="text" id="surname" name="surname" value="Surname"/>
                        </div>
                    </div>

                    <div class="formField">
                        <label for="name" class="label"> Ім'я </label>
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
                        <label for="university" class="label"> Університет </label>
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
                        <label for="username" class="label"> Електронна адреса </label>
                        <div class="textField">
                            <input type="text" name="username" id="username">
                        </div>
                    </div>

                    <div class="formField">
                        <label for="password" class="label">
                            Пароль: <span>*</span>
                        </label>
                        <div class="textField">
                            <input type="password" name="password" id="password" required="true">
                        </div>
                    </div>

                    <div class="formField">
                        <label for="passwordConfirm" class="label">
                            Підтвердження паролю: <span>*</span>
                        </label>
                        <div class="textField">
                            <input type="password" name="passwordConfirm" id="passwordConfirm" required="true">
                        </div>
                    </div>

                    <div class="formField">
                        <label for="keyWords" class="label"> Наукові інтереси (ключові слова) </label>
                        <textarea class="textField" id="keyWords" name="keyWords"></textarea>
                    </div>


                    <div class="formField">
                        <label for="photo" class="label"> Фото
                            <img id="curentUserImg" src="getFile?name=${userDetails.photoAddress}&type=img">
                        </label>
                        <input type="file" class="textField" id="photo" name="userPhoto" accept="image/*">
                    </div>

                    <div class="formField" id="sexField">
                        <label for="userSex" class="label" id="userSexLabel"> Стать: </label>
                        <div class="radioButtons" id="userSex" name="userSex" data-required="false" data-type="group">
                            <span><input type="radio" class="radioButtons" name="userSex" value="MALE">Чоловіча</span>
                            <span><input type="radio" class="radioButtons" name="userSex" value="FEMALE">Жіноча</span>
                        </div>
                    </div>
                    <div class="clear"></div>

                    <div id="buttons">
                        <input id="regBtn" class="button" type="button" value="Редагувати"/>
                        <input id="btnClear" class="button" type="button" value="Очистити"/>
                    </div>
                </form>
                <div class="clear"></div>
                <div class="messageField">
                    <p id="regErrorMessage" class="error" name="message"> </p>
                </div>

            </div>

        </div>

        <div class="clear"></div>
    </div>
</div>


</div>

<hr class="horizontalLine">

<div class="uploading" id="uploading"></div>

</body>
</html>