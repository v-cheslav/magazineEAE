package magazine.Exeptions;

/**
 * Created by pvc on 10.05.2016.
 */
public class ArticleNotFoundException extends Exception {
    public ArticleNotFoundException() {
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
