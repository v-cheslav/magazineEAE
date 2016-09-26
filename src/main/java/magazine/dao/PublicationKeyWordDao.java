package magazine.dao;

import magazine.domain.PublicationKeyWord;

/**
 * Created by pvc on 17.09.2016.
 */
public interface PublicationKeyWordDao {
    public Long create(PublicationKeyWord publicationKeyWord);
    public PublicationKeyWord reade(Long id);
    public void update(PublicationKeyWord publicationKeyWord);
    public void delete(PublicationKeyWord publicationKeyWord);
    public PublicationKeyWord getKeyWord(String articleKeyWord);
}
