package magazine.dao;

import magazine.domain.Annotation;

/**
* Created by pvc on 20.10.2015.
*/
public interface AnnotationDao {
    public Long create(Annotation annotation);
    public Annotation reade(Long id);
    public void update(Annotation annotation);
    public void delete(Annotation annotation);
}
