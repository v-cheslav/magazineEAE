package magazine.dao;

import magazine.domain.Section;

/**
 * Created by pvc on 01.12.2015.
 */
public interface SectionDao {
    public Long create(Section section);
    public Section read(Long id);
    public void update(Section section);
    public void delete(Section section);
    public Section getSectionByName (Enum sectionName);
}
