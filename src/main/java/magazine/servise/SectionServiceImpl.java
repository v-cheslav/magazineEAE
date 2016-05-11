package magazine.servise;

import magazine.dao.SectionDao;
import magazine.domain.ListSection;
import magazine.domain.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pvc on 01.12.2015.
 */
@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    SectionDao sectionDao;

    public SectionServiceImpl() {
    }

    @Override
    public void fillDBWihSections() {
        for(ListSection sectionList: ListSection.values()){
            Section section = new Section(sectionList);
            sectionDao.create(section);
        }
    }

    @Override
    public Long createSection(Section section) {
        return null;//todo
    }

    @Override
    public Section getSection(Long id) {
        return null;
    }

    @Override
    public void changeSection(Section section) {
        sectionDao.update(section);
    }

    @Override
    public void removeSection(Section section) {

    }

    @Override
    public Section getSectionByName(Enum sectionName) {
        return sectionDao.getSectionByName(sectionName);
    }
}
