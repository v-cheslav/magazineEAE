package magazine.dao;

import magazine.Exeptions.SeminarNotFoundException;
import magazine.domain.Seminar;
import magazine.domain.User;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by pvc on 29.02.2016.
 */
public interface SeminarDao {
    public Long create(Seminar seminar);
    public Seminar read(Long id);
    public void update(Seminar seminar);
    public void delete(Seminar seminar);
    public List<Seminar> findAll();
    public List<Seminar> findAllPublished();
    public List<Seminar> findAllAnnounced();
    public List<Seminar> findAllAnnouncedByDate(Calendar date);
    public List<Seminar> findAllApplyied();
    public Seminar findNearestSeminar(Calendar currentDate)throws SeminarNotFoundException;
    public Seminar findSeminarByName(String seminarName);
    public Seminar findAnnouncedByUser(User user);
    public List<Seminar> findByUserId(Long userId);
    public void createOrUpdate(Seminar seminar);
    public List<Seminar> findBySearchQuery(Map query);
    public List<Seminar> findSeminarsByDate(Calendar date);
    public List<Seminar> findSeminarsByKeywords(Seminar seminar);
}
