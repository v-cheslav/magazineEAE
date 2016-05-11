package magazine.servise;

import magazine.Exeptions.DataNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.Exeptions.SeminarCreationException;
//import magazine.dao.PublishedSeminarDao;
import magazine.dao.SeminarDao;
import magazine.dao.SeminarKeyWordDao;
import magazine.dao.UserDao;
import magazine.domain.*;
import magazine.utils.DateParser;
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
    public List<Seminar> findAllAppyied(){
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
    public void applySeminar(User currentUser, String seminarStr) throws SeminarCreationException {
        log.info("createApplyiedSeminar method");

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(seminarStr);
            JSONObject jsonObj = (JSONObject) obj;

            String seminarName = (String) jsonObj.get("seminarName");
            String reportDateStr = (String) jsonObj.get("reportDate");

            Seminar seminar = new Seminar();
            seminar.setSeminarName(seminarName);
            seminar.setUser(currentUser);
            seminar.setIsPublished(null);

            try {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date reportDate = format.parse(reportDateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(reportDate);
                seminar.setSeminarPublicationDate(cal);
            } catch (java.text.ParseException ex){
                throw new SeminarCreationException("Не коректна дата!");
            }
            seminarDao.create(seminar);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new SeminarCreationException(errorMessage);
        }
    }

    @Override
    public void advertiseSeminar(String seminarStr) throws SeminarCreationException {
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
                seminar.setSeminarName(seminarName);
            }
            if (seminar.getSeminarName() == null){
                throw new SeminarCreationException("Назва семінару не вказана.");
            }

            try {
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date reportDate = format.parse(reportDateStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(reportDate);
                seminar.setSeminarPublicationDate(cal);
            } catch (java.text.ParseException ex){
                throw new SeminarCreationException("Не коректна дата!");
            }
            seminar.setIsPublished(false);
            seminarDao.createOrUpdate(seminar);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new SeminarCreationException(errorMessage);
        }

    }

    @Override
    public Long publishSeminar(String seminarStr, User currentUser) throws SeminarCreationException {
        log.info("createByString method");
        Long seminarLong = null;
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(seminarStr);
            JSONObject jsonObj = (JSONObject) obj;

            String seminarId = (String) jsonObj.get("seminarId");
            String swfFileName = (String) jsonObj.get("swfFileName");
            String pdfFileName = (String) jsonObj.get("pdfFileName");
            String keyWordsStr = (String) jsonObj.get("seminarKeyWords");

            Seminar seminar = seminarDao.read(Long.parseLong(seminarId));

            /**
             *  Перевірка на право публікації доповіді семінару.
             *  Має право публікувати користувач що зазначений як доповідач
             *  або користувач не зареєстрований на момент подачі адміністратором
             *  заявки на участь у семінарі (getUser() == null).
             */
            if(seminar.getUser() != null) {
                if (!currentUser.getUserId().equals(seminar.getUser().getUserId())) {
                    throw new SeminarCreationException("Ви не є доповідачем цього семінару!");
                }
            }

            int publicationNumber = currentUser.getPublicationNumber();


            String seminarPresentationPath =
//                    "../../userResources/userSeminars/" +
                    currentUser.getUserId() + "/"
                    + publicationNumber + "/";
            seminar.setSeminarPresentationAddress(seminarPresentationPath + swfFileName);

            String seminarReportPath =
                    currentUser.getUserId() + "/"
                    + publicationNumber + "/";
            seminar.setSeminarReportAddress(seminarReportPath + pdfFileName);

            currentUser.setPublicationNumber(++publicationNumber);

            userService.changeUser(currentUser);
            seminar.setUser(currentUser);

            seminar.setIsPublished(true);

            Set<SeminarKeyWord> articleKeyWords = userInterestFormer(keyWordsStr, seminar);
            seminar.setSeminarKeyWords(articleKeyWords);

            Calendar instance = Calendar.getInstance();
            seminar.setSeminarPublicationDate(instance);

            seminar.setIsPublished(true);
            seminarDao.update(seminar);


        } catch (ParseException e) {
            e.printStackTrace();
            throw new SeminarCreationException(errorMessage);
        }

        return seminarLong;
    }

    //todo дати в utils, переписати клас з дженеріками
    private Set<SeminarKeyWord> userInterestFormer (String keyWordsStr, Seminar seminar){
        Set<SeminarKeyWord> keyWordsSet = new HashSet<>();
        if (keyWordsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
            String[] keyWordsArrStr = keyWordsStr.split("\\,");
            for (String keyWordStr : keyWordsArrStr) {
                if (keyWordStr.charAt(0) == ' ') {
                    keyWordStr = keyWordStr.replaceFirst(" ", "");
                }
                SeminarKeyWord seminarKeyWord;
                try {
                    seminarKeyWord = seminarKeyWordDao.getKeyWord(keyWordStr.toLowerCase());
                    Set <Seminar> seminars = seminarKeyWord.getSeminars();
                    seminars.add(seminar);
                    seminarKeyWordDao.update(seminarKeyWord);
                } catch (NullPointerException e) {//todo
                    seminarKeyWord = new SeminarKeyWord(keyWordStr.toLowerCase());
                    Set<Seminar> seminars = new HashSet<>();
                    seminarKeyWord.setSeminars(seminars);
                    seminars.add(seminar);
                    seminarKeyWordDao.create(seminarKeyWord);
//                    e.printStackTrace();
                }
                keyWordsSet.add(seminarKeyWord);
            }
        } else {
            keyWordsSet = null;
        }
        return keyWordsSet;
    }


    @Override
    public List<Seminar> findAllAnnounced() {
        return seminarDao.findAllAnnounced();
    }


    @Override
    public List<Seminar> findNearestSeminars() {
        Calendar calendar = Calendar.getInstance();
        return seminarDao.findNearestSeminars(calendar);
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
                System.err.println(dateFromCal);

                searchQueryMap.put("dateFrom", dateFromCal);
            }

            if (!dateToStr.equals("")) {
                Calendar dateToCal = dateParser.parseDate(dateToStr);
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
