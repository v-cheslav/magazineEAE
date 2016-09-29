package magazine.servise;

import magazine.domain.Publication;
import magazine.domain.PublicationKeyWord;

import java.util.List;
import java.util.Set;

/**
 * Created by pvc on 17.09.2016.
 */
public interface KeyWordService {
    public boolean isStringCorrect (String keyWords);
    public String removeBlanksFromBeginning(String keyWord);
    public List<PublicationKeyWord> getKeyWordsFromString(String keyWordsStr, Publication publication);
}
