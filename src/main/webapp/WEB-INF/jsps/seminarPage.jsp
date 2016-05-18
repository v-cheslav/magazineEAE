<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html class="html" lang="uk-UA">
<head>
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <%--<!-- CSS -->--%>
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/articlePage.css"/>
    <%--<!-- scripts -->--%>
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/siteGlobal.js"></script>
    <script src="../../js/articlePage.js"></script>
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

            <h3 class="headerPublishDateSem">
                ${seminar.seminarDateToString()}
            </h3>

            <div id="authForm">
                <sec:authorize access="isAnonymous()">
                    <p>
                        <a href="/login">Увійти</a>
                        <a href="/registration">Зареєструватись</a>
                    </p>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <p>
                        <a href="/myPageNew">${userDetails.name} ${userDetails.surname}</a>
                        <a href="/j_spring_security_logout">Вийти</a>
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
            <img class="userPhoto" id="imgContainer" src="getFile?name=${seminar.user.photoAddress}&type=img" alt="Photo">

            <div id="userDetails">
                <p class="paddingBottom">
                    <a href="authorPage?authorId=${seminar.user.userId}">${seminar.user.toString()}</a>
                </p>
                <p class="paddingBottom">
                    <c:if test="${seminar.user.sciDegree!=null}">
                        ${seminar.user.sciDegree},
                    </c:if>
                    ${seminar.user.acadStatus}
                </p>
                <p class="paddingBottom"> ${seminar.user.university}</p>
            </div>
            <div id="articleNameHeader">
                ${seminar.seminarName}
            </div>
            <a href="advancedSearch.html" class="menuButton" id="searchingButton">Пошук</a>
        </div>
    </div>
    <hr class="horizontalLine">
</div>

<div class="content" id="content">
    <div class="mainContent">
        <div class="mainContentHeader">
            <h2 id="presentationHeader">Презентація</h2>
        </div>

        <div class="articleContent">
            <object type="application/x-shockwave-flash" data="getFile?name=${seminar.seminarPresentationAddress}&type=seminar"
                        width="753" height="613">
                <param name="movie" value="getFile?name=${seminar.seminarPresentationAddress}&type=seminar"/>
                <param name="quality" value="high"/>
                <param name="swfversion" value="6.0.65.0"/>
                <param name="wmode" value="transparent"/>
                <param name="expressinstall" value="Scripts/expressInstall.swf"/>

                <object type="application/x-shockwave-flash" data="getFile?name=${seminar.seminarPresentationAddress}&type=seminar"
                                width="753" height="613">

                    <param name="quality" value="high"/>
                    <param name="swfversion" value="6.0.65.0"/>
                    <param name="wmode" value="transparent"/>
                    <param name="expressinstall" value="Scripts/expressInstall.swf"/>
                    <div>
                        <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
                        <p><a href="http://www.adobe.com/go/getflashplayer">
                            <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif"
                                alt="Get Adobe Flash player" width="112" height="33"/>
                        </a></p>
                    </div>
                </object>
            </object>
        </div>
        <div class="articleContent">
            <p id="articleSizeSwitcher">Розгорнути статтю на всю сторінку</p>
            <object id="articlePdf" data="getFile?name=${seminar.seminarReportAddress}}&type=seminar" type="application/pdf">
                <embed src="getFile?name=${seminar.seminarReportAddress}&type=seminar" type="application/pdf"/>
            </object>
        </div>
    </div>
    <%--leftPanel--%>
    <div class="leftSideBar" id="leftPanel"><!-- group -->
        <div class="currentDateHeader">
            <p id="currentDate">&nbsp;</p>
        </div>
        <div class="accordionMenuWrapper">
            <ul class="accordionMenu">
                <li>
                    <div class="menuHeader" id="annotationUkHeader">
                        <p>Інформація</p>
                    </div>
                    <div class="menuContent" id="annotationUkContent">
                        Тема семінару, одне, або кілька ключових слів якого відповідають даному семінару.
                        Ще одна тема семінару..........
                        Автор 1
                        Автор 2
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div class="comments">
        <div class="mainContentHeader">
            <h2 id="commentsHeader">Коментарі</h2>
        </div>
        <div class="menuContent" id="commentsContent">

            <div id="allComments">
                <c:forEach var="comment" items="${seminar.seminarCommentsSet}">
                    <div class="commentBox">
                        <span class="commentator">
                            <a href="authorPage?authorId=${comment.user.userId}">${comment.user.toString()}</a>
                        </span>
                        <span class="commentDate">
                                ${comment.commentDateToString()}
                        </span>
                        <p class="allCommentsText">${comment.comment}</p>
                        </form>
                        <sec:authorize access="hasRole('ADMIN')">
                            <div class="deleteComment" onclick="deleteComment(${comment.commentId})">Видалити коментар</div>
                        </sec:authorize>
                    </div>
                </c:forEach>
            </div>

            <sec:authorize access="isAuthenticated()">
                <form class="addComment" id="addComment">
                    <textarea class="newComment" id="newComment" name="newComment"></textarea>
                    <button id="addCommentBtn" class="button" type="button"
                            onclick="addComment(${seminar.seminarId}, 'seminar')">Коментувати
                    </button>
                </form>
            </sec:authorize>

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