<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html class="html" lang="uk-UA">
<head>
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/articlePage.css"/>
    <!-- Other scripts -->
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
            <h3 class="sectionName">
                ${article.articleSection.sectionStr}
            </h3>
            <h3 class="headerPublishDate">
                ${article.articleDateToString()}
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
            <img class="userPhoto" id="imgContainer" src="getFile?name=${article.user.photoAddress}&type=img" alt="Photo">

            <div id="userDetails">
                <p class="paddingBottom">
                    <a href="authorPage?authorId=${article.user.userId}">${article.user.toString()}</a>
                </p>
                <p class="paddingBottom">
                    <c:if test="${article.user.sciDegree!=null}">
                        ${article.user.sciDegree},
                    </c:if>
                    ${article.user.acadStatus}
                </p>
                <p class="paddingBottom"> ${article.user.university}</p>
            </div>
            <div id="articleNameHeader">
                ${article.articleName}
            </div>
            <a href="advancedSearch.html" class="menuButton" id="searchingButton">Пошук</a>
        </div>
    </div>
    <hr class="horizontalLine">
</div>

<div class="content" id="content">
    <div class="mainContent">
        <div class="mainContentHeader">
            <h2 id="annotationUaHeader">Анотація</h2>
        </div>
        <div class="menuContent" id="annotationUaContent">
            <c:forEach var="annotatonLine" items="${annotationUa}">
                <p>${annotatonLine}</p>
            </c:forEach>
        </div>

        <div class="articleContent">
            <p id="articleSizeSwitcher">Розгорнути статтю на всю сторінку</p>
            <object id="articlePdf" data="getFile?name=${article.articleAddress}}&type=article" type="application/pdf">
                <embed src="getFile?name=${article.articleAddress}&type=article" type="application/pdf"/>
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
                        <p>Annotation</p>
                    </div>
                    <div class="menuContent" id="annotationUkContent">
                        <c:forEach var="annotatonLine" items="${annotationEng}">
                            <p>${annotatonLine}</p>
                        </c:forEach>
                    </div>
                </li>
                <li>
                    <div class="menuHeader" id="annotationRuHeader">
                        <p>Аннотация</p>
                    </div>
                    <div class="menuContent" id="annotationRuContent">
                        <c:forEach var="annotatonLine" items="${annotationRu}">
                            <p>${annotatonLine}</p>
                        </c:forEach>
                    </div>
                </li>
                <li>
                    <div class="menuHeader"  id="reviewersMenuHeader">
                        <p>Рецензенти</p>
                    </div>
                    <div class="menuContent" id="reviewersMenuContent">

                        <c:if test="${article.articleReviewers.get(0).status != null}">

                            <img class="reviewerPhoto" src="getFile?name=${article.articleReviewers.get(0).user.photoAddress}&type=img"
                                     alt="Photo">

                            <div class="reviewerDetails">
                                <div class="paddingBottom">
                                    <a href="authorPage?authorId=${article.articleReviewers.get(0).user.userId}">${article.articleReviewers.get(0).user.toString()}</a>
                                </div>
                                <p class="paddingBottom">
                                    <c:if test="${article.articleReviewers.get(0).user.sciDegree!=null}">
                                        ${article.articleReviewers.get(0).user.sciDegree},
                                    </c:if>
                                    ${article.articleReviewers.get(0).user.acadStatus}
                                </p>

                                <p class="paddingBottom"> ${article.articleReviewers.get(0).user.university}</p>
                            </div>

                            <c:if test="${article.articleReviewers.get(0).status == true }">
                                <p class="readReviewLink"  id="readReview1" onclick="getReview(${article.articleReviewers.get(0).reviewId})"><< Читати рецензію</p>
                            </c:if>
                            <c:if test="${article.articleReviewers.get(0).status == false}">
                                <p class="readReviewLink" id="giveReview2" onclick="checkReviewer(${article.articleReviewers.get(0).user.userId})"><< Надати рецензію  (відмовити)</p>
                            </c:if>
                        </c:if>

                        <hr class="delimiter">

                        <c:if test="${article.articleReviewers.get(1).status != null}">

                            <img class="reviewerPhoto" src="getFile?name=${article.articleReviewers.get(1).user.photoAddress}&type=img"
                                 alt="Photo">

                            <div class="reviewerDetails">
                                <div class="paddingBottom">
                                    <a href="authorPage?authorId=${article.articleReviewers.get(1).user.userId}">${article.articleReviewers.get(1).user.toString()}</a>
                                </div>
                                <p class="paddingBottom">
                                    <c:if test="${article.articleReviewers.get(1).user.sciDegree!=null}">
                                        ${article.articleReviewers.get(1).user.sciDegree},
                                    </c:if>
                                        ${article.articleReviewers.get(1).user.acadStatus}
                                </p>

                                <p class="paddingBottom"> ${article.articleReviewers.get(1).user.university}</p>
                            </div>

                            <c:if test="${article.articleReviewers.get(1).status == true}">
                                <p class="readReviewLink" id="readReview2" onclick="getReview(${article.articleReviewers.get(1).reviewId})"><< Читати рецензію</p>
                            </c:if>
                            <c:if test="${article.articleReviewers.get(1).status == false}">
                                <p class="readReviewLink" id="giveReview2"
                                   onclick="checkReviewer(${article.articleReviewers.get(1).user.userId})"><< Надати
                                    рецензію (відмовити)</p>
                            </c:if>
                        </c:if>

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
                <c:forEach var="comment" items="${article.articleCommentsSet}">
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
                            onclick="addComment(${article.articleId}, 'article')">Коментувати
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

<div class="addReview" id="addReview" >
    <form name="addReviewForm" method="post" action="null">
        <div>
            <label for="reviewText" class="label"></label>
                                            <textarea class="textField" id="reviewText" name="reviewText"
                                                      placeholder="Текст рецензії" ></textarea>
        </div>

            <input class="button" id="setReviewBtn" type="button" value="Надати" onclick="setReview(${article.articleId})"/>
            <input class="button" id="cancelBtn" type="button" value="Відмінити"/>
            <p class="readReviewLink" id="dennyReviewBtn"<%-- value="${article.articleId}"--%> <%--onclick="confirm(${article.articleId})"--%>>Відмовити у наданні рецензії</p>

    </form>
</div>

<div class="confirmation" id="confirmation">
    <div class="confirmationMessage">
        <p>Ви дійсно хочете відмовити автору у наданні рецензії?</p>
        <div>
            <input class="button" id="confirmBtn" type="button" onclick="dennyReview(${article.articleId})" value="Відмовити"/>
            <input class="button" id="cancelConfBtn"  type="button" value="Відмінити"/>
        </div>
    </div>
</div>

<div class="reviewSection" id="reviewSection">
    <div class="readReview">
        <div class="closeSign" id="closeSign">x</div><%--todo get unicode of sign--%>
        <p id="reviewMessage">&nbsp;</p>
    </div>
</div>
<div class="uploading" id="uploading"></div>

</body>
</html>
