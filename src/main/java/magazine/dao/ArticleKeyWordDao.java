package magazine.dao;

import magazine.domain.ArticleKeyWord;

/**
 * Created by pvc on 01.12.2015.
 */
public interface ArticleKeyWordDao {
    public Long create(ArticleKeyWord articleKeyWord);
    public ArticleKeyWord reade(Long id);
    public void update(ArticleKeyWord articleKeyWord);
    public void delete(ArticleKeyWord articleKeyWord);
    public ArticleKeyWord getKeyWord(String articleKeyWord);
}
