package magazine.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
* Created by pvc on 27.10.2015.
*/
@Entity
@DiscriminatorValue("PublKW")
public class PublicationKeyWord extends KeyWord {

    @ManyToMany(mappedBy = "publicationKeyWords", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Publication> publications = new HashSet<>();

//    @ManyToMany(mappedBy = "seminarKeyWords", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    private Set<Seminar> seminarSet = new HashSet<>();


    public PublicationKeyWord(){
    }

    public PublicationKeyWord(String keyWord){
        super(keyWord);
    }

    public Set<Publication> getPublications() {
        return publications;
    }

//    public Set<Seminar> getSeminars() {
//        return seminarSet;
//    }



    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

//    public void setSeminars(Set<Seminar> seminarSet) {
//        this.seminarSet = seminarSet;
//    }
}
