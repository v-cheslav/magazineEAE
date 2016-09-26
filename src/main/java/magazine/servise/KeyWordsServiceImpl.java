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
    public List<PublicationKeyWord> splitKeyWords (String keyWordsStr, Publication publication){
        log.info("splitKeyWords.method");

        List<PublicationKeyWord> keyWordsSet = new ArrayList<>();
        if (keyWordsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
            String[] keyWordsArrStr = keyWordsStr.split("\\,");
            for (String keyWordStr : keyWordsArrStr) {
                if (keyWordStr.charAt(0) == ' ') {
                    keyWordStr = keyWordStr.replaceFirst(" ", "");
                }
                PublicationKeyWord publicationKeyWord;
                try {
                    publicationKeyWord = publicationKeyWordDao.getKeyWord(keyWordStr.toLowerCase());
                    Set <Publication> publications = publicationKeyWord.getPublications();
                    publications.add(publication);
                    publicationKeyWordDao.update(publicationKeyWord);
                } catch (NullPointerException e) {//todo
                    publicationKeyWord = new PublicationKeyWord(keyWordStr.toLowerCase());
                    Set<Publication> publications = new HashSet<>();
                    publicationKeyWord.setPublications(publications);
                    publications.add(publication);
                    publicationKeyWordDao.create(publicationKeyWord);
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

}
