package magazine.dao;

import magazine.domain.PublicationKeyWord;

/**
 * Created by pvc on 01.12.2015.
 */
public interface ArticleKeyWordDao {
    public Long create(PublicationKeyWord publicationKeyWord);
    public PublicationKeyWord reade(Long id);
    public void update(PublicationKeyWord publicationKeyWord);
    public void delete(PublicationKeyWord publicationKeyWord);
    public PublicationKeyWord getKeyWord(String articleKeyWord);
}
