package magazine.dao;

import magazine.domain.SeminarKeyWord;

/**
 * Created by pvc on 25.01.2016.
 */
public interface SeminarKeyWordDao {
    public Long create(SeminarKeyWord seminarKeyWord);
    public SeminarKeyWord reade(Long id);
    public void update(SeminarKeyWord seminarKeyWord);
    public void delete(SeminarKeyWord seminarKeyWord);
    public SeminarKeyWord getKeyWord(String seminarKeyWord);
}
