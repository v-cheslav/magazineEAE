package magazine.servise;

import magazine.controller.ApplicationController;
import magazine.dao.PublicationKeyWordDao;
import magazine.domain.Article;
import magazine.domain.Publication;
import magazine.domain.PublicationKeyWord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pvc on 17.09.2016.
 */
@Service
public class KeyWordsServiceImpl implements KeyWordService {

    public static final Logger log = Logger.getLogger(ApplicationController.class);

    @Autowired
    PublicationKeyWordDao publicationKeyWordDao;


    @Override
    public List<PublicationKeyWord> getKeyWordsFromString(String keyWordsStr, Publication publication){
        log.info("getKeyWordsFromString.method");

        if (!isStringCorrect(keyWordsStr)) return null;
        String[] keyWordsArrStr = keyWordsStr.split("\\,");

        List<PublicationKeyWord> keyWordsSet = new ArrayList<>();
        for (String keyWordStr : keyWordsArrStr) {
            keyWordStr = removeBlanksFromBeginning(keyWordStr);
            PublicationKeyWord publicationKeyWord = getKeyWordByString(keyWordStr, publication);
            keyWordsSet.add(publicationKeyWord);
        }
        return keyWordsSet;
    }

    @Override
    public boolean isStringCorrect (String keyWords){
        log.info("isStringCorrect.method");
        if (keyWords.length() >= 2) return true;
        log.info("key words string isn't correct");
        return false;
    }


    @Override
    public String removeBlanksFromBeginning(String keyWord){
        log.info("removeBlanksFromBeginning.method");
        while (keyWord.charAt(0) == ' ') {
            keyWord = keyWord.replaceFirst(" ", "");
            log.info("blank was removed");
        }
        return keyWord;
    }


    private PublicationKeyWord getKeyWordByString(String keyWordStr, Publication publication) {
        log.info("getKeyWordByString.method");
        try {
            return getKeyWordFromDbAndAddUser(keyWordStr, publication);
        } catch (NullPointerException e) {//якщо в БД немає KeyWord
            return createKeyWordAndAddUser(keyWordStr, publication);
        }
    }

    private PublicationKeyWord getKeyWordFromDbAndAddUser(String keyWordStr, Publication publication) {
        log.info("getting key word from DB");
        PublicationKeyWord publicationKeyWord = publicationKeyWordDao.getKeyWord(keyWordStr.toLowerCase());
        Set<Publication> publications = publicationKeyWord.getPublications();
        publications.add(publication);
        publicationKeyWordDao.update(publicationKeyWord);
        return publicationKeyWord;
    }

    private PublicationKeyWord createKeyWordAndAddUser(String keyWordStr, Publication publication) {
        log.info("creating a new key word");
        PublicationKeyWord publicationKeyWord = new PublicationKeyWord(keyWordStr.toLowerCase());
        Set<Publication> publications = new HashSet<>();
        publicationKeyWord.setPublications(publications);
        publications.add(publication);
        publicationKeyWordDao.create(publicationKeyWord);
        return publicationKeyWord;
    }

}
