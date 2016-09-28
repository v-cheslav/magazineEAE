<%@ page import="java.util.Date" %>
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
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/articlePage.js"></script>
    <script src="../../js/siteGlobal.js"></script>

</head>

<body>
<div class="header">
    <div class="banner">
        <div class="bannerTop">
            <h3 class="sectionName">
                ${article.articleSection.sectionStr}
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
                        <a href="/myPage">${userDetails.name} ${userDetails.surname}</a>
                        <a href="/j_spring_security_logout">Вийти</a>
                    </p>
                </sec:authorize>
            </div>
        </div>
        <hr class="horizontalLine" id="bannerLine">
        <div class="bannerBottom">
            <img class="userBannerPhoto" id="imgContainer" src="getFile?name=${article.user.photoName}&type=img"
                 alt="Photo">

            <div class="userDetails">
                <p>
                    <a href="authorPage?authorId=${article.user.userId}">${article.user.toString()}</a>
                </p>

                <p>
                    <c:if test="${article.user.sciDegree!=null}">
                        ${article.user.sciDegree},
                    </c:if>
                    ${article.user.acadStatus}
                </p>

                <p> ${article.user.university}</p>
            </div>

            <sec:authorize access="hasRole('ADMIN')">
                <p class="adminLink">
                    <a href="/administrator">Сторінка адміністратора</a>
                </p>
            </sec:authorize>
            <p class="currentDate">Опубліковано ${article.publicationDateToString()}</p>
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

<div class="content" id="content">

    <div class="whiteBox">

        <div class="mainContent">
            <div class="mainContentHeader">
                ${article.publicationName}
                <img id="toLargeSize" title="Розгонути на всю сторінку">
            </div>

            <div class="articleContent">
                <%--<object id="articlePdf" data="getFile?name=${article.publicationPath}}&type=article" type="application/pdf">--%>
                <%--<embed src="getFile?name=${article.publicationPath}&type=article" type="application/pdf"/>--%>
                <%--</object>--%>

                <iframe id="articlePdf"
                        src="getFile?name=${article.publicationPath}${article.articleFileName}&type=article"></iframe>
            </div>

            <div id="comments">
                <h2>Коментарі</h2>

                <ul>
                    <c:forEach var="comment" items="${article.articleCommentsSet}">

                        <li>
                            <article>
                                <header>
                                    <figure class="avatar"><img src="getFile?name=${comment.user.photoName}&type=img"
                                                                alt=""></figure>
                                    <address>
                                        <a href="authorPage?authorId=${comment.user.userId}">${comment.user.toString()}</a>
                                    </address>
                                    <time>${comment.commentDateToString()}</time>
                                </header>
                                <div class="comcont">
                                    <p>${comment.comment}</p>
                                </div>
                                <sec:authorize access="hasRole('ADMIN')">
                                    <div class="delComment" onclick="deleteComment(${comment.commentId})">Видалити
                                        коментар
                                    </div>
                                </sec:authorize>
                                <div class="clear"></div>
                            </article>
                        </li>
                    </c:forEach>
                </ul>
                <sec:authorize access="isAuthenticated()">
                    <form class="addCommentForm" <%--id="addComment"--%>>
                        <div class="formField">
                            <label for="newComment" class="label">
                                Новий коментар
                                <span>*</span>
                            </label>
                            <textarea class="textField" id="newComment" name="newComment"></textarea>
                        </div>
                        <div>
                            <input class="button" type="button"
                                   onclick="addComment(${article.id}, 'article')" value="Коментувати">
                            </input>
                        </div>
                    </form>

                </sec:authorize>
            </div>

        </div>

        <div id="rightBars">
            <%--rightPanel--%>
            <div class="rightSideBar">
                <div class="barHeader">
                    <span id="annotationHeader">Анотація</span>

                    <div class="annotationLanguageSign">
                        <span class="languageSign" id="ru">Ru</span>
                        <span class="languageSign" id="eng">Eng</span>
                        <span class="languageSign" id="ua">Ua</span>
                    </div>
                </div>

                <div class="barContent" id="annotationText">
                    <div id="annotationRuContent">
                        ${article.articleAnnotations.annotationRu}
                    </div>
                    <div id="annotationEngContent">
                        ${article.articleAnnotations.annotationEng}
                    </div>
                    <div id="annotationUkrContent">
                        ${article.articleAnnotations.annotationUa}
                    </div>
                </div>
            </div>


            <%--rightPanel--%>
            <div class="rightSideBar">
                <c:if test="${article.articleReviews.size() != 0}">
                    <div class="barHeader">
                        <span id="reviewersMenuHeader">Рецензенти</span>
                    </div>

                    <div class="barContent" id="reviewersMenuContent">

                        <c:if test="${article.articleReviews.get(0).status != null}">

                            <img class="reviewerPhoto"
                                 src="getFile?name=${article.articleReviews.get(0).user.photoName}&type=img"
                                 alt="Photo">

                            <div class="reviewerDetails">
                                <div class="paddingBottom">
                                    <a class="authorLink"
                                       href="authorPage?authorId=${article.articleReviews.get(0).user.userId}">${article.articleReviews.get(0).user.toString()}</a>
                                </div>
                                <p class="paddingBottom">
                                    <c:if test="${article.articleReviews.get(0).user.sciDegree != null}">
                                        ${article.articleReviews.get(0).user.sciDegree},
                                    </c:if>
                                        ${article.articleReviews.get(0).user.acadStatus}
                                </p>

                                <p class="paddingBottom"> ${article.articleReviews.get(0).user.university}</p>
                            </div>

                            <c:if test="${article.articleReviews.get(0).status == true }">
                                <p class="reviewLink" id="readReview1"
                                   onclick="getReview('${article.articleReviews.get(0).reviewAddress}')"><< Читати
                                    рецензію</p>
                            </c:if>
                            <c:if test="${article.articleReviews.get(0).status == false && article.articleReviews.get(0).user.userId == userDetails.userId}">
                                <p class="reviewLink" id="giveReview1"
                                   onclick="checkReviewer(${article.articleReviews.get(0).user.userId})"><< Надати
                                    рецензію (відмовити)</p>
                            </c:if>
                        </c:if>

                        <hr class="delimiter">

                        <c:if test="${article.articleReviews.get(1).status != null}">

                            <img class="reviewerPhoto"
                                 src="getFile?name=${article.articleReviews.get(1).user.photoName}&type=img"
                                 alt="Photo">

                            <div class="reviewerDetails">
                                <div class="paddingBottom">
                                    <a class="authorLink"
                                       href="authorPage?authorId=${article.articleReviews.get(1).user.userId}">${article.articleReviews.get(1).user.toString()}</a>
                                </div>
                                <p class="paddingBottom">
                                    <c:if test="${article.articleReviews.get(1).user.sciDegree!=null}">
                                        ${article.articleReviews.get(1).user.sciDegree},
                                    </c:if>
                                        ${article.articleReviews.get(1).user.acadStatus}
                                </p>

                                <p class="paddingBottom"> ${article.articleReviews.get(1).user.university}</p>
                            </div>

                            <c:if test="${article.articleReviews.get(1).status == true}">
                                <p class="reviewLink" id="readReview2"
                                   onclick="getReview('${article.articleReviews.get(1).reviewAddress}')"><< Читати
                                    рецензію</p>
                            </c:if>
                            <c:if test="${article.articleReviews.get(1).status == false && article.articleReviews.get(1).user.userId == userDetails.userId}">
                                <p class="reviewLink" id="giveReview2"
                                   onclick="checkReviewer(${article.articleReviews.get(1).user.userId})"><< Надати
                                    рецензію (відмовити)</p>
                            </c:if>
                        </c:if>

                    </div>
                </c:if>
            </div>


            <div class="rightSideBar">
                <c:if test="${similarArticles.size() != 0}"> <%--rightPanel--%>

                    <div class="barHeader">
                        <span>Схожі публікації</span>
                    </div>
                    <div class="barContent">
                        <ul>
                            <c:forEach var="similarArticle" items="${similarArticles}">
                                <li class="similarPublicationList">
                                    <a href="articlePage?publicationId=${similarArticle.publicationId}">${similarArticle.publicationName}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </div>


        </div>
        <div class="clear"></div>

    </div>

    <hr class="horizontalLine">

    <div class="addReview" id="addReview">
        <div class="closeSign" id="cancelBtn"></div>
        <form id="pdfUploadForm" class="fileForm">
            <div id="fileUploadingContent">
                <label id="articleImgContainer" for="reviewFile" class="pickFile"></label>
                <label id="pickArticleLabel" for="reviewFile" class="pickFile ">Обрати файл рецензії</label>
                <input type="file" class="fileAddress" id="reviewFile" name="reviewFile">
                <button id="btnUpload" class="button" type="button" onclick="addReview()">Завантажити</button>
                <input type="hidden" name="articleId" value="${article.id}">
            </div>
            <p class="reviewLink"
               id="dennyReviewBtn"<%-- value="${article.articleId}"--%> <%--onclick="confirm(${article.articleId})"--%>>
                Відмовити у наданні рецензії</p>

            <div class="error" id="reviewErrorMassage">
            </div>
        </form>
    </div>

    <div class="confirmation" id="confirmation">
        <div class="confirmationMessage">
            <p>Ви дійсно хочете відмовити автору у наданні рецензії?</p>

            <div>
                <input class="button" id="confirmBtn" type="button" onclick="dennyReview(${article.id})"
                       value="Відмовити"/>
                <input class="button" id="cancelConfBtn" type="button" value="Відмінити"/>
            </div>
        </div>
    </div>

    <div class="reviewSection" id="reviewSection">
        <div class="readReview">
            <div class="closeSign" id="closeSign">x</div>
            <div id="reviewPdf">
                <%--<iframe id="reviewPdf"></iframe>--%>
            </div>
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
