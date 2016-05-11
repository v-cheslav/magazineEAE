package magazine.Exeptions;

/**
 * Created by pvc on 30.11.2015.
 */
public class ArticleCreationException extends Exception {

    public ArticleCreationException() {
    }

    public ArticleCreationException(String message) {
        super(message);
    }
}
