package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.CommentCreationException;
import magazine.dao.ArticleDao;
import magazine.dao.CommentDao;
import magazine.dao.SeminarDao;
import magazine.domain.Article;
import magazine.domain.Comment;
import magazine.domain.Seminar;
import magazine.domain.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by pvc on 30.03.2016.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    SeminarDao seminarDao;

    public CommentServiceImpl() {
    }

    @Override
    public Long createComment(Comment comment) {
        return commentDao.create(comment);
    }

    @Override
    public Comment getComment(Long id) {
        return commentDao.read(id);
    }

    @Override
    public void changeComment(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public void removeComment(Comment comment) {
        commentDao.delete(comment);
    }



    @Override
    public void createComment(User user, String commentJson) throws CommentCreationException{


        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(commentJson);
            JSONObject jsonObj = (JSONObject) obj;

            Long articleId = (Long) jsonObj.get("articleId");
            Long seminarId = (Long) jsonObj.get("seminarId");
            String newComment = (String) jsonObj.get("newComment");

            Calendar instance = Calendar.getInstance();//todo try to put here only date without time

            Comment comment = new Comment(instance, newComment, user);
            Article article;
            Seminar seminar;
            if (articleId != null) {
                article = articleDao.read(articleId);
                comment.setArticle(article);
            } else if (seminarId != null) {
                seminar = seminarDao.read(seminarId);
                comment.setSeminar(seminar);
            }

            commentDao.create(comment);

//            List<Comment> commentSet = article.getArticleCommentsSet();
//            commentSet.add(comment);
//            articleDao.update(article);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new CommentCreationException("Виникла помилка");
        }

    }
}
