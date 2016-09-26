package magazine.servise;

import magazine.Exeptions.DataNotFoundException;
import magazine.Exeptions.SearchException;
import magazine.Exeptions.SeminarException;
import magazine.Exeptions.SeminarNotFoundException;
import magazine.domain.PublicationKeyWord;
import magazine.domain.Seminar;
import magazine.domain.User;

import java.util.List;

/**
 * Created by pvc on 02.01.2016.
 */
public interface SeminarService  {
    public Seminar getSeminar(Long seminarId);
    public void changeSeminar (Seminar seminar);
    public void changeSeminar (Seminar seminar, User user)throws SeminarException;
    public void removeSeminar (Seminar seminar) ;
    public List<Seminar> findAllAnnounced();
    public Seminar findAnnouncedByUser(User user);
    public List<Seminar> findSeminarsBySection(String section)throws DataNotFoundException;//todo throw Exception connection error or so.
    public void publishSeminar(String seminar, User currentUser) throws SeminarException;
    public void advertiseSeminar(String seminar) throws SeminarException;
    public List<Seminar> findPublishedSeminarByUserId(Long userId);
    public List<Seminar> findAllDeclared();
    public void applySeminar(User currentUser, String seminarStr) throws SeminarException;
    public List<Seminar> findNearestSeminars()throws SeminarNotFoundException;
    public List<Seminar> searchSeminars(String seminarStr) throws SearchException;
    public List<Seminar> findSeminarsByKeywords(Seminar seminar);
}
