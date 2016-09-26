package magazine.servise;

import magazine.dao.AnnotationDao;
import magazine.domain.Annotation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pvc on 17.09.2016.
 */
@Service
public class AnnotationServiceImpl implements AnnotationService {
    private static Logger log = Logger.getLogger(ArticleServiceImpl.class);

    @Autowired
    AnnotationDao annotationDao;

    public AnnotationServiceImpl() {
    }

    @Override
    public Long createAnnotation(Annotation annotation) {
        return annotationDao.create(annotation);
    }

    @Override
    public Annotation getAnnotation(Long id) {
        return annotationDao.reade(id);
    }

    @Override
    public void changeAnnotation(Annotation annotation) {
        annotationDao.update(annotation);
    }

    @Override
    public void removeAnnotation(Annotation annotation) {
        annotationDao.delete(annotation);
    }
}
