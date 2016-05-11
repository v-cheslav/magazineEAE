package magazine.servise;

import magazine.Exeptions.DataNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.Exeptions.SeminarCreationException;
import magazine.domain.Seminar;
import magazine.domain.User;

import java.util.Calendar;
import java.util.List;

/**
 * Created by pvc on 02.01.2016.
 */
public interface SeminarService  {
    public Seminar getSeminar(Long seminarId);
    public void removeSeminar (Seminar seminar);
    public List<Seminar> findAllAnnounced();
    public Seminar findAnnouncedByUser(User user);
    public List<Seminar> findSeminarsBySection(String section)throws DataNotFoundException;//todo throw Exception connection error or so.
    public Long publishSeminar(String seminar, User currentUser) throws SeminarCreationException;
    public void advertiseSeminar(String seminar) throws SeminarCreationException;
    public List<Seminar> findPublishedSeminarByUserId(Long userId);
    public List<Seminar> findAllAppyied();
    public void applySeminar(User currentUser, String seminarStr) throws SeminarCreationException;
    public List<Seminar> findNearestSeminars();
    public List<Seminar> searchSeminars(String seminarStr) throws SearchException;

}
