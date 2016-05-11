package magazine.dao;

import magazine.domain.Comment;

/**
* Created by pvc on 20.10.2015.
*/
public interface CommentDao {
    public Long create(Comment comment);
    public Comment read(Long id);
    public void update(Comment comment);
    public void delete(Comment comment);
}
