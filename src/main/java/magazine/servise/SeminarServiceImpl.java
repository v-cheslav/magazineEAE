package magazine.servise;

import magazine.Exeptions.DataNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.Exeptions.SeminarException;
//import magazine.dao.PublishedSeminarDao;
import magazine.Exeptions.SeminarNotFoundException;
import magazine.dao.ArticleKeyWordDao;
import magazine.dao.SeminarDao;
import magazine.dao.SeminarKeyWordDao;
import magazine.dao.UserDao;
import magazine.domain.*;
import magazine.utils.DateService;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.simple.parser.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Робота з семінаром.
 * Семінар може "знаходитись в трьох станах":
 * 1. Користувач надіслав заявку адміністратору на участь, створення семінару
 * (метод createApplyiedSeminar, поле isPublished=null;
 * 2. Адміністратор редагує (якщо потрібно) та подає оголошення про участь, створення або оновлення
 * (метод advertiseSeminar, поле isPublished=false);
 * 3. Юзер публікує семінар на сайті
 * (метод publishSeminar, поле isPublished=true);
 */
@Service
public class SeminarServiceImpl implements SeminarService {
    private static Logger log = Logger.getLogger(SeminarServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    SeminarKeyWordDao seminarKeyWordDao;

    @Autowired
    ArticleKeyWordDao articleKeyWordDao;

    @Autowired
    SectionService sectionService;

    @Autowired
    SeminarDao seminarDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AcadStatusService acadStatusService;

    @Autowired
    SciDegreeService sciDegreeService;



    @Value("${initialPath}")
    private String initialPath;

    private String errorMessage = "Виникли проблеми з додаванням статті.\" +\n" +
            "\"Спробуйте будь-ласка пізніше. Якщо проблема повториться - зверніться до адміністратора";

    public SeminarServiceImpl() {
    }

    @Override
    public Seminar getSeminar(Long seminarId) {
        return seminarDao.read(seminarId);
    }

    @Override
    public void removeSeminar(Seminar seminar) {
        seminarDao.delete(seminar);
    }

    @Override
    public List<Seminar> findSeminarsBySection(String sectionStr) throws DataNotFoundException {
        sectionStr = sectionStr.replace("\"", "");
        if (sectionStr.equals("ALL")){
            return seminarDao.findAllPublished();
        } else if (sectionStr.equals("NEXT")) {
            return seminarDao.findAllAnnounced();
        } else {
            throw new DataNotFoundException("");
        }
    }

    @Override
    public void changeSeminar(Seminar seminar) {
        seminarDao.update(seminar);
    }

    @Override
    public void changeSeminar(Seminar seminar, User user) throws SeminarException{

        if(seminar.getUser() != null) {
            if (!user.getUserId().equals(seminar.getUser().getUserId())) {
                throw new SeminarException("Ви не є доповідачем цього семінару");
            }
        } else {
            seminar.setUser(user);
        }

        Calendar instance = Calendar.getInstance();
        seminar.setPublicationDate(instance);

        userService.increaseUserPublications(user);

        seminar.setIsPublished(true);
        seminarDao.update(seminar);
    }


    @Override
    public List<Seminar> findAllDeclared(){
        return seminarDao.findAllApplyied();
    }

    @Override
    public List<Seminar> findPublishedSeminarByUserId(Long userId) {
        return seminarDao.findByUserId(userId);
    }

    @Override
    public Seminar findAnnouncedByUser(User user) {
        return seminarDao.findAnnouncedByUser(user);
    }

    @Override
    public void applySeminar(User currentUser, String seminarStr) throws SeminarException {
        log.info("createApplyiedSeminar method");

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(seminarStr);
            JSONObject jsonObj = (JSONObject) obj;

            String seminarName = (String) jsonObj.get("seminarName");
            String reportDateStr = (String) jsonObj.get("reportDate");

            Seminar seminar = new Seminar();
            seminar.setPublicationName(seminarName);
            seminar.setUser(currentUser);
            seminar.setIsPublished(null);

            try {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date reportDate = format.parse(reportDateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(reportDate);
                seminar.setPublicationDate(cal);
            } catch (java.text.ParseException ex){
                throw new SeminarException("Не коректна дата!");
            }
            seminarDao.create(seminar);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new SeminarException(errorMessage);
        }
    }

    @Override
    public void advertiseSeminar(String seminarStr) throws SeminarException {
        log.info("createUnPublishedSeminarByString method");
        Long seminarLong = null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(seminarStr);
            JSONObject jsonObj = (JSONObject) obj;

            String seminarId = (String) jsonObj.get("seminarId");
            String seminarName = (String) jsonObj.get("seminarName");
            String userId = (String) jsonObj.get("userId");
            String unRegUserName = (String) jsonObj.get("unRegUserName");
            String reportDateStr = (String) jsonObj.get("reportDate");


            Seminar seminar = null;

            if (!seminarId.equals("")){
                seminar = seminarDao.read(Long.parseLong(seminarId));
            } else {
                seminar = new Seminar();
            }

            if (!userId.equals("")){
                User user = userDao.read(Long.parseLong(userId));
                seminar.setUser(user);
            } else {
                seminar.setUnRegUserName(unRegUserName);
            }

            if (!seminarName.equals("")){
                seminar.setPublicationName(seminarName);
            }
            if (seminar.getPublicationName() == null){
                throw new SeminarException("Назва семінару не вказана.");
            }

            try {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date reportDate = format.parse(reportDateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(reportDate);
                seminar.setPublicationDate(cal);
            } catch (java.text.ParseException ex){
                throw new SeminarException("Не коректна дата!");
            }
            seminar.setIsPublished(false);
            seminarDao.createOrUpdate(seminar);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SeminarException(errorMessage);
        }

    }

    @Override
    public void publishSeminar(String seminarStr, User currentUser) throws SeminarException {
        log.info("publishSeminar method");
//        Long seminarLong = null;
//        try {
//            JSONParser parser = new JSONParser();
//            Object obj = parser.parse(seminarStr);
//            JSONObject jsonObj = (JSONObject) obj;
//
//            String seminarId = (String) jsonObj.get("seminarId");
//            String swfFileName = (String) jsonObj.get("swfFileName");
//            String pdfFileName = (String) jsonObj.get("pdfFileName");
//            String keyWordsStr = (String) jsonObj.get("seminarKeyWords");
//
//            Seminar seminar = seminarDao.read(Long.parseLong(seminarId));
//
//            /**
//             *  Перевірка на право публікації доповіді семінару.
//             *  Має право публікувати користувач що зазначений як доповідач
//             *  або користувач не зареєстрований на момент подачі адміністратором
//             *  заявки на участь у семінарі (getUser() == null).
//             */
//            if(seminar.getUser() != null) {
//                if (!currentUser.getUserId().equals(seminar.getUser().getUserId())) {
//                    throw new SeminarException("Ви не є доповідачем цього семінару!");
//                }
//            } else {
//                seminar.setUser(currentUser);
//            }
//
//            int publicationNumber = currentUser.getPublicationNumber();
//
//            String seminarPresentationPath =
//                    currentUser.getUserId() + "/"
//                    + publicationNumber + "/";
//            seminar.setSeminarPresentationAddress(seminarPresentationPath + swfFileName);
//
//            String seminarReportPath =
//                    currentUser.getUserId() + "/"
//                    + publicationNumber + "/";
//
//            seminar.setSeminarReportAddress(seminarReportPath + pdfFileName);
//            currentUser.setPublicationNumber(++publicationNumber);
//            userService.changeUser(currentUser);
//
//
//            Set<PublicationKeyWord> publicationKeyWords = userInterestFormer(keyWordsStr, seminar);
//            seminar.setSeminarKeyWords(publicationKeyWords);
//
//            Calendar instance = Calendar.getInstance();
//            seminar.setPublicationDate(instance);
//            seminar.setIsPublished(true);
//            seminarDao.update(seminar);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//            throw new SeminarException(errorMessage);
//        }

//        return seminarLong;
    }


    @Override
    public List<Seminar> findAllAnnounced() {
        return seminarDao.findAllAnnounced();
    }

    @Override
    public List<Seminar> findSeminarsByKeywords(Seminar seminar){
        return seminarDao.findSeminarsByKeywords(seminar);
    }


    @Override
    public List<Seminar> findNearestSeminars() throws SeminarNotFoundException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND, 0);

        Seminar seminar;
            seminar = seminarDao.findNearestSeminar(calendar);
            Calendar date = seminar.getPublicationDate();
            List<Seminar> seminars = seminarDao.findSeminarsByDate(date);
        return seminars;
    }

    @Override
    public List<Seminar> searchSeminars(String articleStr) throws SearchException {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(articleStr);
            JSONObject jsonObj = (JSONObject) obj;

            String acadStatusStr = (String) jsonObj.get("acadStatus");
            String dateFromStr = (String) jsonObj.get("dateFrom");
            String dateToStr = (String) jsonObj.get("dateTo");
            String keyWordsStr = (String)jsonObj.get("keyWords");
            String nameOfSeminarStr = (String) jsonObj.get("nameOfSeminar");
            String nameOfUserStr = (String) jsonObj.get("nameOfUser");
            String sciDegreeStr = (String)jsonObj.get("sciDegree");

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
                System.err.println(dateFromCal);

                searchQueryMap.put("dateFrom", dateFromCal);
            }

            if (!dateToStr.equals("")) {
                Calendar dateToCal = null;
                try {
                    dateToCal = dateService.parseDate(dateToStr);
                } catch (java.text.ParseException e) {
                    throw new SearchException("Не коректна дата!");
                }
                System.err.println(dateToCal);

                searchQueryMap.put("dateTo", dateToCal);
            }

            if (!keyWordsStr.equals("")) {
                System.err.println(keyWordsStr);

                searchQueryMap.put("keyWords", keyWordsStr);
            }

            if (!nameOfSeminarStr.equals("")) {
                System.err.println(nameOfSeminarStr);

                searchQueryMap.put("nameOfSeminar", nameOfSeminarStr);
            }

            if (!nameOfUserStr.equals("")) {
                System.err.println(nameOfUserStr);

                searchQueryMap.put("nameOfUser", nameOfUserStr);
            }

            return seminarDao.findBySearchQuery(searchQueryMap);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SearchException ("Не вдалося виконати пошук. " + e.getMessage());
        }
    }

}
