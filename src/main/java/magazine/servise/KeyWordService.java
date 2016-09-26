package magazine.servise;

import magazine.domain.Publication;
import magazine.domain.PublicationKeyWord;

import java.util.List;
import java.util.Set;

/**
 * Created by pvc on 17.09.2016.
 */
public interface KeyWordService {
    public List<PublicationKeyWord> splitKeyWords (String keyWordsStr, Publication publication);
}
