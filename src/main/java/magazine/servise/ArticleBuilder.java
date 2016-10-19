package magazine.servise;

import magazine.Exeptions.PublicationException;
import magazine.domain.Article;
import org.springframework.web.multipart.MultipartHttpServletRequest;



public interface ArticleBuilder {
    public Article buildPublication(MultipartHttpServletRequest request) throws PublicationException;
}
