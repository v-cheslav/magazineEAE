package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.ArticleNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.dao.*;
import magazine.domain.*;
import magazine.utils.DateParser;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by pvc on 29.11.2015.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private static Logger log = Logger.getLogger(ArticleServiceImpl.class);

    @Autowired
    ArticleDao articleDao;

    @Autowired
    SectionDao sectionDao;

    @Autowired
    SectionService sectionService;

    @Autowired
    UserService userService;

    @Autowired
    AcadStatusService acadStatusService;

    @Autowired
    SciDegreeService sciDegreeService;

    @Autowired
    ArticleKeyWordDao articleKeyWordDao;

    @Autowired
    AnnotationDao annotationDao;

    @Autowired
    ReviewDao reviewDao;

    @Value("${initialPath}")
    private String initialPath;

    private String errorMessage = "Виникли проблеми з додаванням статті.\" +\n" +
            "\"Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора";

    public ArticleServiceImpl() {
    }

    @Override
    public Long createArticle(Article article) {
        return articleDao.create(article);
    }

    @Override
    public Article getArticle(Long id) {
        return articleDao.read(id);
    }

    @Override
    public void changeArticle(Article article) {
        articleDao.update(article);
    }

    @Override
    public void removeArticle(Article article) {
//        String articleAddress = article.getArticleAddress();
//
//        int lastIndex = articleAddress.lastIndexOf("/");
//        String articleFolder = articleAddress.substring(lastIndex);
//        System.err.println(articleFolder);
//
//        File folder = new File(articleFolder);
//        try {
//            folder.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        articleDao.delete(article);
    }

    @Override
    public List<Article> findPublishArticleBySection(String sectionStr) {
        sectionStr = sectionStr.replace("\"", "");
        if (sectionStr.equals("ALL")){
            return articleDao.findAllPublished();
        } else {
            Section articleSection = sectionService.getSectionByName(ListSection.valueOf(sectionStr));
            return articleDao.findAllPublishedBySection(articleSection);
        }
    }

    @Override
    public List<Article> findByReviewerId(Long userId) {
        return articleDao.findByReviewerId(userId);
    }

    @Override
    public List<Article> findNewestArticles() throws ArticleNotFoundException{
        List<Article> articles = articleDao.findNewest();
        if (articles.size() != 0){
            return articles;
        } else {
            throw new ArticleNotFoundException("Опублікованих статтей ще немає.");
        }

    }

    /**
     * Створює статтю на основі даних JSON отриманих від користувача.
     */
    @Override
    public Long createByString(String articleStr, User currentUser) throws ArticleCreationException{
        log.info("createByString method");

        /**
         * Здійснює перевірку чи є неопублікована стаття в користувача.
         * Стаття заноситьсься в список публікованих після надання 2-х рецензій.
         */
        if (findUnPublishedByUser(currentUser) != null){
            throw new ArticleCreationException("Ви не можете подавати заявку на публікацію ще однієї статті." +
                    "Дочекайтесь рецензування та публікації попередньої.");
        }

        Long articleLong = null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(articleStr);
            JSONObject jsonObj = (JSONObject) obj;

            String articleName = (String) jsonObj.get("articleName");
            String fileName = (String) jsonObj.get("fileName");
            String articleSectionStr = (String) jsonObj.get("articleSection");
            String annotationEng = (String) jsonObj.get("annotationEng");
            String annotationUkr = (String) jsonObj.get("annotationUkr");
            String annotationRu = (String) jsonObj.get("annotationRu");
            String keyWordsStr = (String) jsonObj.get("keyWords");
            String firstReviewerStr = (String) jsonObj.get("firstReviewer");
            Long firstReviewerLong = Long.parseLong(firstReviewerStr);
            String secondReviewerStr = (String) jsonObj.get("secondReviewer");
            Long secondReviewerLong = Long.parseLong(secondReviewerStr);

            Article article = new Article();

            article.setArticleName(articleName);

            int publicationNumber = currentUser.getPublicationNumber();

            String articleRelativePath =currentUser.getUserId() + "/" + publicationNumber + "/";

            String articleAbsolutePath = initialPath//todo - rewrite using StringBuilder
                    + "userArticles/"
                    + currentUser.getUserId() + "/"
                    + publicationNumber + "/";

            article.setArticleAddress(/*"../../userResources/" +*/ articleRelativePath + fileName);
            currentUser.setPublicationNumber(++publicationNumber);
            userService.changeUser(currentUser);
            article.setUser(currentUser);

            String annotationPathEng = null;
            String annotationPathUkr = null;
            String annotationPathRu = null;
            try {
                annotationPathEng = annotationWriter(annotationEng, articleAbsolutePath, "annotationEng", articleRelativePath);
                annotationPathUkr = annotationWriter(annotationUkr, articleAbsolutePath, "annotationUkr", articleRelativePath);
                annotationPathRu = annotationWriter(annotationRu, articleAbsolutePath, "annotationRu", articleRelativePath);
            } catch (FileNotFoundException e) {
                log.error("File not found", e);
                throw new ArticleCreationException(errorMessage);
            } catch (UnsupportedEncodingException e) {
                log.error("UnsupportedEncodingException", e);
                throw new ArticleCreationException(errorMessage);
            }

            Annotation annotation = new Annotation(annotationPathEng, annotationPathUkr, annotationPathRu, article);
            article.setArticleAnnotations(annotation);
            annotationDao.create(annotation);

            Set<ArticleKeyWord> articleKeyWords = userInterestFormer(keyWordsStr, article);
            article.setArticleKeyWords(articleKeyWords);

            Section articleSection = sectionService.getSectionByName(ListSection.valueOf(articleSectionStr));
            Set <Article> articles = articleSection.getArticles();
            articles.add(article);
            sectionService.changeSection(articleSection);
            article.setArticleSection(articleSection);

            Calendar instance = Calendar.getInstance();//todo try to put here only date without time
            article.setArticlePublicationDate(instance);


            articleLong = articleDao.create(article);

            User firstReviewer = userService.getUser(firstReviewerLong);
            firstReviewer.setIsReviewer(true);
            userService.changeUser(firstReviewer);
            Review firstReview = new Review(article, firstReviewer);

            User secondReviewer = userService.getUser(secondReviewerLong);
            secondReviewer.setIsReviewer(true);
            userService.changeUser(secondReviewer);
            Review secondReview = new Review(article, secondReviewer);

            List <Review> reviews = article.getArticleReviewers();
            reviews.add(firstReview);
            reviews.add(secondReview);
            reviewDao.create(firstReview);
            reviewDao.create(secondReview);
            article.setArticleReviewers(reviews);

            articleDao.update(article);

        } catch (ParseException e) {
            //todo видалити завантажені файли і зробити reduce кількості публікацій
            e.printStackTrace();
            throw new ArticleCreationException(errorMessage);
        }
        return articleLong;
    }

    private String annotationWriter (String annotation, String articleAbsolutePath, String fileName, String articleRelativePath) throws FileNotFoundException, UnsupportedEncodingException, ArticleCreationException {
        String annotationAddress = articleAbsolutePath + fileName + ".xml";
        FileOutputStream fileOutputStream = new FileOutputStream(annotationAddress);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
        try {
            bufferedWriter.append(annotation);
            bufferedWriter.flush();
        } catch (IOException ex) {
            log.error("File not found", ex);
            throw new ArticleCreationException(errorMessage);
        } finally {

            try {
                bufferedWriter.close();
            } catch (IOException e) {
                log.error("BufferedWriter closing error", e);
                throw new ArticleCreationException(errorMessage);
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                log.error("FileOutputStream closing error", e);
                throw new ArticleCreationException(errorMessage);
            }
        }//todo the same with other files
        annotationAddress = articleRelativePath + fileName + ".xml";
        return annotationAddress;
    }

    @Override//todo в AnnotationService
    public List<String> annotationReader (String annotationPath){
        String pathStr = initialPath + "userArticles/" + annotationPath;
        Path path = Paths.get(pathStr);
        List<String> annotationLines = null;
        try {
            annotationLines = Files.readAllLines(path);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return annotationLines;
    }

    //todo дати в utils, переписати клас з дженеріками
    private Set<ArticleKeyWord> userInterestFormer (String keyWordsStr, Article article){
        Set<ArticleKeyWord> keyWordsSet = new HashSet<>();
        if (keyWordsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
            String[] keyWordsArrStr = keyWordsStr.split("\\,");
            for (String keyWordStr : keyWordsArrStr) {
                if (keyWordStr.charAt(0) == ' ') {
                    keyWordStr = keyWordStr.replaceFirst(" ", "");
                }
                ArticleKeyWord articleKeyWord;
                try {
                    articleKeyWord = articleKeyWordDao.getKeyWord(keyWordStr.toLowerCase());
                    Set <Article> articles = articleKeyWord.getArticles();
                    articles.add(article);
                    articleKeyWordDao.update(articleKeyWord);
                } catch (NullPointerException e) {//todo
                    articleKeyWord = new ArticleKeyWord(keyWordStr.toLowerCase());
                    Set<Article> articles = new HashSet<>();
                    articleKeyWord.setArticles(articles);
                    articles.add(article);
                    articleKeyWordDao.create(articleKeyWord);
//                    e.printStackTrace();
                }
                keyWordsSet.add(articleKeyWord);
            }
        } else {
            keyWordsSet = null;
        }
        return keyWordsSet;
    }

    @Override
    public Article findUnPublishedByUser(User user) {
        return articleDao.findUnPublishedByUser(user);
    }

    @Override
    public List<Article> findByUserId(Long userId) {
        return articleDao.findByUserId(userId);
    }

    @Override
    public List<Article> searchArticles(String articleStr) throws SearchException  {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(articleStr);
            JSONObject jsonObj = (JSONObject) obj;

            String acadStatusStr = (String) jsonObj.get("acadStatus");
            String dateFromStr = (String) jsonObj.get("dateFrom");
            String dateToStr = (String) jsonObj.get("dateTo");
            String keyWordsStr = (String)jsonObj.get("keyWords");
            String nameOfArticleStr = (String) jsonObj.get("nameOfArticle");
            String nameOfUserStr = (String) jsonObj.get("nameOfUser");
            String sciDegreeStr = (String)jsonObj.get("sciDegree");
            String sectionStr = (String)jsonObj.get("section");

            Map<String, Object> searchQueryMap = new HashMap<>();

            if (!acadStatusStr.equals("")) {
                UserAcadStatus acadStatus = null;
                try {
                    acadStatus = acadStatusService.findByString(acadStatusStr);
                } catch (Exception e) {
                    acadStatus = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("acadStatus", acadStatus);
            }

            if (!sciDegreeStr.equals("")) {
                UserSciDegree sciDegree = null;
                try {
                    sciDegree = sciDegreeService.findByString(sciDegreeStr);
                } catch (Exception e) {
                    sciDegree = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("sciDegree", sciDegree);
            }
            DateParser dateParser = new DateParser();
            if (!dateFromStr.equals("")) {
                Calendar dateFromCal = dateParser.parseDate(dateFromStr);
                searchQueryMap.put("dateFrom", dateFromCal);
            }

            if (!dateToStr.equals("")) {
                Calendar dateToCal = dateParser.parseDate(dateToStr);
                searchQueryMap.put("dateTo", dateToCal);
            }

            if (!keyWordsStr.equals("")) {
                searchQueryMap.put("keyWords", keyWordsStr);
            }

            if (!nameOfArticleStr.equals("")) {
                searchQueryMap.put("nameOfArticle", nameOfArticleStr);
            }

            if (!nameOfUserStr.equals("")) {
                searchQueryMap.put("nameOfUser", nameOfUserStr);
            }

            if (!sectionStr.equals("")) {
                Section section = null;
                try {
                    section = sectionService.getSectionByName(ListSection.valueOf(sectionStr));
                } catch (Exception e) {
                    section = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("section", section);
            }

            return articleDao.findBySearchQuery(searchQueryMap);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SearchException ("Не вдалося виконати пошук. " + e.getMessage());
        }
    }

//    private Calendar dateParser (String date) throws SearchException {
//        if (date.equals("")) return null;
//        Calendar calendar;
//        try {
//            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//            Date reportDate = format.parse(date);
//            calendar = Calendar.getInstance();
//            calendar.setTime(reportDate);
//        } catch (java.text.ParseException ex) {
//            throw new SearchException("Не коректна дата!");
//        }
//        return calendar;
//    }

}


