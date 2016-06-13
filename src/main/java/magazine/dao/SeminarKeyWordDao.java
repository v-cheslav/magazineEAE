package magazine.dao;

import magazine.domain.PublicationKeyWord;
//import magazine.domain.SeminarKeyWord;

/**
* Created by pvc on 25.01.2016.
*/
public interface SeminarKeyWordDao {
    public Long create(PublicationKeyWord seminarKeyWord);
    public PublicationKeyWord reade(Long id);
    public void update(PublicationKeyWord seminarKeyWord);
    public void delete(PublicationKeyWord seminarKeyWord);
    public PublicationKeyWord getKeyWord(String seminarKeyWord);
}
