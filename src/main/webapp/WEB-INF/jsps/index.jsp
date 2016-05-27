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
    <title>Он-лайн журнал &quot;Енергетика, автоматика і енергозбереження&quot;ПублікаціїОн-лайн журнал &quot;Енергетика,
        автоматика і енергозбереження&quot;</title>
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../css/site_global.css"/>
    <link rel="stylesheet" type="text/css" href="../../css/index.css" id="pagesheet"/>

    <!-- Scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/siteGlobal.js" type="text/javascript"></script>

</head>
<body>
<%--<%= new java.util.Date() %>--%>
<div class="header">
    <h1 id="magazineName">ЕНЕРГЕТИКА, АВТОМАТИКА І ЕНЕРГОЗБЕРЕЖЕННЯ</h1>
    <nav id="MenuBar">
        <ul>
            <li><a href="index.html" class="menuButton menuActive">ГОЛОВНА</a></li>
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
                <sec:authorize access="hasRole('admin')">
                    <p>
                        <a href="/admin">Admin page</a>
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
            <div class="instituteLogo"></div>
            <h3 class="instituteHeaderName">ННІ Енергетики, автоматики <br>і енергозбереження</h3>
            <a href="advancedSearch.html" class="menuButton" id="searchingButton">Пошук</a>
        </div>
    </div>
    <hr class="horizontalLine">
</div>

<div class="content" id="content">

    <%--leftPanel--%>
    <div class="leftSideBar" id="leftPanel">
        <div class="currentDateHeader">
            <p id="currentDate"></p>
        </div>
        <div class="accordionMenuWrapper">
            <ul class="accordionMenu">
                <li>
                    <div class="menuHeader" id="sectionsMenuHeader">
                        <p>Інформація</p>
                    </div>
                    <div class="menuContent" id="sectionsMenuContent">
                        <ul class="leftMenuList">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                            labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                            laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                            voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                            cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </ul>
                    </div>
                </li>

            </ul>
        </div>
    </div>
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

                    <div class="articlePubDate">
                        <c:out value="${articles.get(i).publicationDateToString()}"/>
                    </div>

                    <c:forEach var="annotation" items="${annotations.get(i)}">
                        <div class="annotation">
                                ${annotation}
                        </div>
                    </c:forEach>

                    <div class="articleAuthor">
                        <a href="/authorPage?authorId=${articles.get(i).user.userId}">
                            <c:out value="${articles.get(i).user.toString()}"/>
                        </a>
                    </div>

                </div>
            </c:forEach>
        </c:if>
    </div>

    <c:if test="${nearestSeminars != null}">

        <div class="rightPanel">
            <div class="menuHeader">
                <p>Найближчий семінар відбудеться: <br/>
                    <c:out value="${nearestSeminars.get(0).publicationDateToString()}"/>
                </p>
            </div>

            <div class="menuContent">

                <div>
                    <p class="seminars">Доповідають:</p>
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

                            <p class="theme">Тема доповіді:</p>

                            <p class="nearestSeminarName">
                                "${nearestSeminar.publicationName}"</p><%--todo зробити перевірку на дату другого доповідача--%>
                        </div>
                    </c:forEach>
                </div>
            </div>

        </div>
    </c:if>
    <c:if test="${nearestSeminars == null}">
        <script>
            $('.mainContentHeader').css({
                "width": "749px"
            });
            $('.article').css({
                "width": "729px"
            });
        </script>
    </c:if>
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
