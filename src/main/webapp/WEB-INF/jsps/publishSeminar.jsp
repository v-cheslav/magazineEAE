<%@page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title></title>
    <!-- scripts -->
    <script src="../../js/jquery-1.11.2.min.js"></script>
    <script src="../../js/jquery.validate.min.js"></script>
    <script src="../../js/publishSeminar.js"></script>
</head>
<body>
<div class="innerContent">
    <div class="publishRulesField">
        <div class="publRulesHeader" id="seminarPublRulesHeader">
            <p>Правила публікації семінару</p>
        </div>
        <div class="publRulesContent" id="seminarPublRulesContent">
            Описати правила розміщення.
        </div>
    </div>

    <form class="publishForm" name="publishForm" id="seminarPublishForm" method="post" action="null">
        <div class="mainField">
            
            <div>
                <label for="seminarName" class="label"></label>
                <select class="textField" id="seminarName" name="seminarName"></select>
            </div>

            <div>
                <label for="seminarKeyWords" class="label"></label>
    <textarea class="textField" id="seminarKeyWords" name="seminarKeyWords"
              placeholder="Введіть не більше 5-ти ключових слів."></textarea>
            </div>

            <div class="messageField">
                <p id="seminarErrorMessage" name="message">
                    <c:if test="${message!=null}">
                        <c:out value="${message}"/>
                    </c:if>
                </p>
            </div>

            <input class="button" id="publSeminarBtn" name="seminar" type="button" value="Опублікувати"/>

        </div>

    </form>

    <%--UploadForm--%>
    <form id="presentationUploadForm" class="fileForm">
        <div id="presentationUploadingContent">
            <label id="presentationImgContainer" for="presentation" class="pickFile"></label>
            <label id="pickPresentationLabel" for="presentation" class="pickFile <%--button--%>">Обрати файл
                презентації</label>
            <input type="file" class="photoAddress" id="presentation" name="presentation">
            <button id="btnPresentationUpload" class="button" type="button">Завантажити</button>
            <button id="btnPresentationClear" class="button" type="button">Відмінити</button>
        </div>
        <div id="presentationUploadMessage" class="uploadMessage">
            Презентацію завантажено!
        </div>
    </form>

    <form id="reportUploadForm" class="fileForm">
        <div id="reportUploadingContent">
            <label id="reportImgContainer" for="report" class="pickFile"></label>
            <label id="pickReportLabel" for="report" class="pickFile">Обрати файл доповіді</label>
            <input type="file" class="photoAddress" id="report" name="report"><%--todo rename to uploadForm--%>
            <button id="btnReportUpload" class="button" type="button">Завантажити</button>
            <button id="btnReportClear" class="button" type="button">Відмінити</button>
        </div>
        <div id="reportUploadMessage" class="uploadMessage">
            Доповідь завантажено!
        </div>
    </form>
</div>

</body>
</html>