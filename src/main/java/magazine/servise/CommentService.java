package magazine.servise;

import magazine.Exeptions.CommentCreationException;
import magazine.domain.Comment;
import magazine.domain.User;

/**
 * Created by pvc on 30.03.2016.
 */
public interface CommentService {
    public Long createComment(Comment comment);
    public Comment getComment(Long id);
    public void changeComment(Comment comment);
    public void removeComment(Comment comment);
    public void createComment(User user, String comment) throws CommentCreationException;
}
