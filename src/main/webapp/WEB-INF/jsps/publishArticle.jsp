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
    <script src="../../js/publishArticle.js" type="text/javascript"></script>
</head>
<body>
<div class="innerContent">

    <div class="publishRulesField">
        <div class="publRulesHeader" id="articlePublRulesHeader"><!-- content -->
            <p>Правила публікації статті</p>
        </div>
        <div class="publRulesContent" id="artcilePublRulesContent">
            Описати правила розміщення.
        </div>
    </div>

    <form class="publishForm" name="publishForm" id="articlePublishForm" method="post" action="null">

        <div class="mainField">

            <div>
                <label for="selectSection" class="label"></label>
                <select class="textField" id="selectSection" name="selectSection">
                    <option value="" selected="selected" class="selected">Оберіть рубрику</option>
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

            <div>
                <label for="articleName" class="label"></label>
                                        <textarea class="textField" id="articleName" name="articleName"
                                                  placeholder="Назва статті (коротко)"></textarea>
            </div>

            <div>
                <label for="keyWords" class="label"></label>
                <textarea class="textField" id="keyWords" name="keyWords" placeholder="Введіть не більше 5-ти ключових слів."></textarea>
            </div>


            <div>
                <label for="firstReviewer" class="label"></label>
                <select class="textField" id="firstReviewer" name="firstReviewer"></select>
            </div>

            <div>
                <label for="secondReviewer" class="label"></label>
                <select class="textField" id="secondReviewer" name="secondReviewer"></select>
            </div>

            <div class="messageField">
                <p id="articleErrorMessage" class="regMessage" name="message">
                    <c:if test="${message!=null}">
                        <c:out value="${message}"/>
                    </c:if>
                </p>
            </div>

            <input class="button" id="publArticleBtn" name="article" type="button" value="Опублікувати"/>

        </div>

        <div class="annotationField">

            <div>
                <label for="annotationUkr" class="label"></label>
                                        <textarea class="textField annotation" id="annotationUkr" name="annotationUkr"
                                                  placeholder="Анотація."></textarea>
            </div>
            <div>
                <label for="annotationEng" class="label"></label>
                                        <textarea class="textField annotation" id="annotationEng" name="annotationEng"
                                                  placeholder="Annotation."></textarea>
            </div>
            <div>
                <label for="annotationRu" class="label"></label>
                                        <textarea class="textField annotation" id="annotationRu" name="annotationRu"
                                                  placeholder="Аннотация."></textarea>
            </div>
        </div>
    </form>

    <%--pdfUploadForm--%>
    <form id="pdfUploadForm" class="fileForm">
        <div id="fileUploadingContent">
            <label id="articleImgContainer" for="article" class="pickFile"></label>
            <label id="pickArticleLabel" for="article" class="pickFile <%--button--%>">Обрати статтю</label>
            <input type="file" class="photoAddress" id="article" name="articleFile">
            <button id="btnUpload" class="button" type="button">Завантажити</button>
            <button id="btnClear" class="button" type="button">Відмінити</button>
        </div>
        <br>

        <div id="pdfMessage">
            Статтю завантажено!
        </div>
    </form>

</div>

</body>
</html>
