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
    <div class="banner">
        <div class="bannerTop">
            <%--<h3 class="sectionName">--%>
                <%--${seminar.articleSection.sectionStr}--%>
            <%--</h3>--%>

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
            <img class="userBannerPhoto" id="imgContainer" src="getFile?name=${seminar.user.photoName}&type=img" alt="Photo">

            <div class="userDetails">
                <p>
                    <a href="authorPage?authorId=${seminar.user.userId}">${seminar.user.toString()}</a>
                </p>
                <p>
                    <c:if test="${seminar.user.sciDegree!=null}">
                        ${seminar.user.sciDegree},
                    </c:if>
                    ${seminar.user.acadStatus}
                </p>
                <p> ${seminar.user.university}</p>
            </div>

            <sec:authorize access="hasRole('ADMIN')">
                <p class="adminLink">
                    <a href="/administrator">Сторінка адміністратора</a>
                </p>
            </sec:authorize>
            <p class="currentDate">Опубліковано ${seminar.publicationDateToString()}</p>
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
                ${seminar.publicationName}
            </div>

            <div class="articleContent">
                <object id="presentation" type="application/x-shockwave-flash" data="getFile?name=${seminar.publicationPath}${seminar.presentationFileName}&type=seminar"
                            width="630" height="413">
                    <param name="movie" value="getFile?name=${seminar.publicationPath}${seminar.presentationFileName}&type=seminar"/>
                    <param name="quality" value="high"/>
                    <param name="swfversion" value="6.0.65.0"/>
                    <param name="wmode" value="transparent"/>
                    <param name="expressinstall" value="Scripts/expressInstall.swf"/>

                    <object type="application/x-shockwave-flash" data="getFile?name=${seminar.publicationPath}${seminar.presentationFileName}&type=seminar"
                                    width="630" height="413">

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
                <div class="mainContentHeader">
                    Доповідь семінару
                    <img id="toLargeSize" title="Розгонути на всю сторінку">
                </div>

                <iframe id="articlePdf" src="getFile?name=${seminar.publicationPath}${seminar.reportFileName}&type=article"></iframe>

                <%--<object id="articlePdf" data="getFile?name=${seminar.seminarReportAddress}&type=seminar" type="application/pdf">--%>
                    <%--<embed src="getFile?name=${seminar.seminarReportAddress}&type=seminar" type="application/pdf"/>--%>
                <%--</object>--%>
            </div>


            <div id="comments">
                <h2>Коментарі</h2>

                <ul>
                    <c:forEach var="comment" items="${seminar.seminarCommentsSet}">

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
                    <form class="addCommentForm">
                        <div class="formField">
                            <label for="newComment" class="label">
                                Новий коментар
                                <span>*</span>
                            </label>
                            <textarea class="textField" id="newComment" name="newComment"></textarea>
                        </div>
                        <div>
                            <input class="button" type="button"
                                   onclick="addComment(${seminar.id}, 'seminar')" value="Коментувати">
                            </input>
                        </div>
                    </form>

                </sec:authorize>
            </div>

        </div>

        <div id="rightBars">

            <div class="rightSideBar"><%--rightPanel--%>
                <c:if test="${similarSeminars.size() != 0}">

                    <div class="barHeader">
                        <span>Схожі семінари</span>
                    </div>
                    <div class="barContent">
                        <ul id="similarPublicationList">
                            <c:forEach var="similarSeminar" items="${similarSeminars}">
                                <li>
                                    <a href="seminarPage?publicationId=${similarSeminar.publicationId}">${similarSeminar.publicationName}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="clear"></div>

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