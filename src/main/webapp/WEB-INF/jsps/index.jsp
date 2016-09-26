<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html class="html" lang="uk-UA">
<head>


    <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
    <meta name="description" content="Журнал енергетика, автоматика і енергозбереження, список публікацій журналу."/>
    <meta name="generator" content="2015.0.2.310"/>
    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;Публікації Он-лайн журнал &quot;Енергетика,
        автоматика і енергозбереження&quot;</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/index.css" id="pagesheet"/>

    <!-- Scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>
    <script src="../../js/index.js" type="text/javascript"></script>


</head>
<body>
<div class="header">
    <h1 class="magazineName">ЕНЕРГЕТИКА, АВТОМАТИКА І ЕНЕРГОЗБЕРЕЖЕННЯ</h1>

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
            <li class="active"><a href="index.html">Головна</a></li>
            <li><a href="articles.html">Статті</a></li>
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
                <h2>Останні публікації</h2>
            </div>
            <c:if test="${message != null}">
                <c:out value="${message}"></c:out>
            </c:if>

            <c:if test="${articles != null}">
                <c:set var="count" value="${articles.size()-1}"/>
                <c:forEach var="i" begin="0" end="${count}">
                    <div class="article">

                        <div class="articleName">
                            <a href="/articlePage?publicationId=${articles.get(i).id}">
                                <c:out value="${articles.get(i).publicationName}"/>
                            </a>
                        </div>

                        <div class="articleAuthor">
                            <p>Автор:</p>
                            <a href="/authorPage?authorId=${articles.get(i).user.userId}">
                                <c:out value="${articles.get(i).user.toString()}"/>.
                            </a>

                            <p>&nbsp;Ключові слова:</p>
                        <span>
                              <c:forEach var="keyWord" items="${articles.get(i).publicationKeyWords}">
                                  <span class="keyWords">${keyWord.artKeyWord};</span>
                              </c:forEach>
                        </span>
                        </div>


                        <div class="annotation">
                             ${articles.get(i).articleAnnotations.annotationUa}
                        </div>


                        <div class="articlePubDate">
                            <c:out value="${articles.get(i).publicationDateToString()}"/>
                        </div>

                    </div>
                </c:forEach>
            </c:if>
        </div>


        <div id="rightBars">
            <%--rightPanel--%>
            <c:if test="${nearestSeminars != null}">

                <div class="rightSideBar">
                    <div id="barHeader" class="barHeader">
                        <div id="annotationHeader">Найближчий семінар</div>
                        <h5>
                            <c:out value="${nearestSeminars.get(0).publicationDateToString()}"/>
                        </h5>
                        <p class="seminars">доповідають:</p>
                    </div>
                    <div class="barContent" id="annotationText">

                        <c:forEach var="nearestSeminar" items="${nearestSeminars}">
                            <div class="seminar">
                                <p class="nearestSeminarUser">
                                    <c:if test="${nearestSeminar.user != null}">
                                        <a href="/authorPage?authorId=${nearestSeminar.user.userId}">
                                            <c:out value="${nearestSeminar.user.toString()}"/>
                                        </a>
                                    </c:if>
                                    <c:if test="${nearestSeminar.user == null}">
                                        <c:out value="${nearestSeminar.unRegUserName}"/>
                                    </c:if>
                                </p>

                                <%--<p class="theme">Тема доповіді:</p>--%>

                                <p class="nearestSeminarName">
                                    "${nearestSeminar.publicationName}"</p>
                            </div>
                            <hr class="delimiter">
                        </c:forEach>
                    </div>
                </div>

            </c:if>
                <%--rightPanel--%>
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
