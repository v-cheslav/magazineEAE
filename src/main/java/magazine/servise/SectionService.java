package magazine.servise;

import magazine.domain.Section;

/**
 * Created by pvc on 01.12.2015.
 */
public interface SectionService {
    public void fillDBWihSections();
    public Long createSection(Section section);
    public Section getSection(Long id);
    public void changeSection(Section section);
    public void removeSection(Section section);
    public Section getSectionByName(Enum sectionName);
}
