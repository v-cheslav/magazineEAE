package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.ArticleNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.dao.*;
import magazine.domain.*;
import magazine.utils.DateService;
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


    @Override
    public Long createArticle(Article article) throws ArticleCreationException{
        if (findUnPublishedByUser(article.getUser()) != null){
            throw new ArticleCreationException("Ви не можете подавати заявку на публікацію ще однієї статті." +
                    "Дочекайтесь рецензування та публікації попередньої.");
        }
        Calendar instance = Calendar.getInstance();//todo try to put here only date without time
        article.setPublicationDate(instance);

        User user = article.getUser();
        userService.increaseUserPublications(user);

        return articleDao.create(article);
    }





//    @Override
//    public Long createByString(String articleStr, User currentUser) throws ArticleCreationException{//todo change to void
//        log.info("createByString method");
//
//        /**
//         * Здійснює перевірку чи є неопублікована стаття в користувача.
//         * Стаття заноситьсься в список публікованих після надання 2-х рецензій.
//         */
//        if (findUnPublishedByUser(currentUser) != null){
//            throw new ArticleCreationException("Ви не можете подавати заявку на публікацію ще однієї статті." +
//                    "Дочекайтесь рецензування та публікації попередньої.");
//        }
//
//        Long articleLong = null;
//        try {
//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse(articleStr);
//            JSONObject jsonObj = (JSONObject) obj;
//
//            String articleName = (String) jsonObj.get("articleName");
//            String fileName = (String) jsonObj.get("fileName");
//            String articleSectionStr = (String) jsonObj.get("articleSection");
//            String annotationEng = (String) jsonObj.get("annotationEng");
//            String annotationUkr = (String) jsonObj.get("annotationUkr");
//            String annotationRu = (String) jsonObj.get("annotationRu");
//            String keyWordsStr = (String) jsonObj.get("keyWords");

//            String firstReviewerStr = (String) jsonObj.get("firstReviewer");
//            Long firstReviewerLong = Long.parseLong(firstReviewerStr);
//            String secondReviewerStr = (String) jsonObj.get("secondReviewer");
//            Long secondReviewerLong = Long.parseLong(secondReviewerStr);

//            Article article = new Article();
//
//            article.setPublicationPath(articleName);
//
//            int publicationNumber = currentUser.getPublicationNumber();
//
//            String articleRelativePath =currentUser.getUserId() + "/" + publicationNumber + "/";
//
//            String articleAbsolutePath = initialPath//todo - rewrite using StringBuilder
//                    + "userArticles/"
//                    + currentUser.getUserId() + "/"
//                    + publicationNumber + "/";
//
//            article.setPublicationPath(articleRelativePath + fileName);
//            currentUser.setPublicationNumber(++publicationNumber);
//            userService.changeUser(currentUser);
//            article.setUser(currentUser);

//            String annotationPathEng = null;
//            String annotationPathUkr = null;
//            String annotationPathRu = null;
//            try {
//                annotationPathEng = annotationWriter(annotationEng, articleAbsolutePath, "annotationEng", articleRelativePath);
//                annotationPathUkr = annotationWriter(annotationUkr, articleAbsolutePath, "annotationUkr", articleRelativePath);
//                annotationPathRu = annotationWriter(annotationRu, articleAbsolutePath, "annotationRu", articleRelativePath);
//            } catch (FileNotFoundException e) {
//                log.error("File not found", e);
//                throw new ArticleCreationException(errorMessage);
//            } catch (UnsupportedEncodingException e) {
//                log.error("UnsupportedEncodingException", e);
//                throw new ArticleCreationException(errorMessage);
//            }

//            Annotation annotation = new Annotation(annotationPathEng, annotationPathUkr, annotationPathRu, article);
//            article.setArticleAnnotations(annotation);
//            annotationDao.create(annotation);

//            Set<PublicationKeyWord> publicationKeyWords = userInterestFormer(keyWordsStr, article);
//            article.setPublicationKeyWords(publicationKeyWords);


//            System.err.println(article.toString());
//            articleLong = articleDao.create(article);

//            Section articleSection = sectionService.getSectionByName(ListSection.valueOf(articleSectionStr));
//            Set <Article> articles = articleSection.getArticles();
//            articles.add(article);
//            sectionService.changeSection(articleSection);
//            article.setArticleSection(articleSection);

//            Calendar instance = Calendar.getInstance();//todo try to put here only date without time
//            article.setPublicationDate(instance);


///////////---------
//            User firstReviewer = userService.getUser(firstReviewerLong);
//            firstReviewer.setIsReviewer(true);
//            userService.changeUser(firstReviewer);
//            Review firstReview = new Review(article, firstReviewer);
//
//            User secondReviewer = userService.getUser(secondReviewerLong);
//            secondReviewer.setIsReviewer(true);
//            userService.changeUser(secondReviewer);
//            Review secondReview = new Review(article, secondReviewer);
//
//            List <Review> reviews = article.getArticleReviews();
//            reviews.add(firstReview);
//            reviews.add(secondReview);
//            reviewDao.create(firstReview);
//            reviewDao.create(secondReview);
//            article.setArticleReviews(reviews);

//            articleDao.update(article);
//
//
//        } catch (ParseException e) {
//            //todo видалити завантажені файли і зробити reduce кількості публікацій
//            e.printStackTrace();
//            throw new ArticleCreationException(errorMessage);
//        }
//        return articleLong;
//    }

//todo Не видаляти переписати зберігання файлу
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

    @Override
    public Set<PublicationKeyWord> userInterestFormer (String keyWordsStr, Article article){
        Set<PublicationKeyWord> keyWordsSet = new HashSet<>();
        if (keyWordsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
            String[] keyWordsArrStr = keyWordsStr.split("\\,");
            for (String keyWordStr : keyWordsArrStr) {
                if (keyWordStr.charAt(0) == ' ') {
                    keyWordStr = keyWordStr.replaceFirst(" ", "");
                }
                PublicationKeyWord publicationKeyWord;
                try {
                    publicationKeyWord = articleKeyWordDao.getKeyWord(keyWordStr.toLowerCase());
                    Set <Publication> articles = publicationKeyWord.getPublications();
                    articles.add(article);
                    articleKeyWordDao.update(publicationKeyWord);
                } catch (NullPointerException e) {//todo
                    publicationKeyWord = new PublicationKeyWord(keyWordStr.toLowerCase());
                    Set<Publication> articles = new HashSet<>();
                    publicationKeyWord.setPublications(articles);
                    articles.add(article);
                    articleKeyWordDao.create(publicationKeyWord);
//                    e.printStackTrace();
                }
//                catch (Exception ex) {
//                    System.err.println("Exception");
//                    ex.printStackTrace();
//                    return null;
//                }

                keyWordsSet.add(publicationKeyWord);
            }
        } else {
            keyWordsSet = null;
        }
        return keyWordsSet;
    }

    @Override
    public List<Article> findAllUnpublished() {
        return articleDao.findAllUnpublished();
    }

    @Override
    public List<Article> findWithoutReviewers() {
        return articleDao.findWithoutReviewers();
    }

    @Override
    public Article findUnPublishedByUser(User user) {
        return articleDao.findUnPublishedByUser(user);
    }

    @Override
    public Article findUnPublishedByReviewer(User reviewer) {
        return articleDao.findUnPublishedByReviewer(reviewer);
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
                    acadStatus = acadStatusService.findAcadStatus(acadStatusStr);
                } catch (Exception e) {
                    acadStatus = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("acadStatus", acadStatus);
            }

            if (!sciDegreeStr.equals("")) {
                UserSciDegree sciDegree = null;
                try {
                    sciDegree = sciDegreeService.finSciDegree(sciDegreeStr);
                } catch (Exception e) {
                    sciDegree = null;
                    e.printStackTrace();
                }
                searchQueryMap.put("sciDegree", sciDegree);
            }
            DateService dateService = new DateService();
            if (!dateFromStr.equals("")) {
                Calendar dateFromCal = null;
                try {
                    dateFromCal = dateService.parseDate(dateFromStr);
                } catch (java.text.ParseException e) {
                    throw new SearchException("Не коректна дата!");
                }
                searchQueryMap.put("dateFrom", dateFromCal);
            }

            if (!dateToStr.equals("")) {
                Calendar dateToCal = null;
                try {
                    dateToCal = dateService.parseDate(dateToStr);
                } catch (java.text.ParseException e) {
                    throw new SearchException("Не коректна дата!");
                }
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

    @Override
    public List<Article> findArticlesByKeywords(Article article) {
        return articleDao.findArticlesByKeywords(article);
    }
}


