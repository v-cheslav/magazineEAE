package magazine.servise;

import magazine.domain.Annotation;

/**
 * Created by pvc on 17.09.2016.
 */
public interface AnnotationService {
    public Long createAnnotation (Annotation annotation);
    public Annotation getAnnotation(Long id);
    public void changeAnnotation(Annotation annotation);
    public void removeAnnotation(Annotation annotation);
}
