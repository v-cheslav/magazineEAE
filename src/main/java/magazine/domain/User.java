package magazine.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
* Created by pvc on 19.10.2015.
*/
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "USER_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @Column(name="USER_ID")
    private Long userId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore(true)
    private Set<UserRole> userRoles = new HashSet<>();

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="university")
    private String university;

    @Column(name="institute")
    private String institute;

    @Column(name="chair")
    private String chair;

    @Column(name="position")
    private String position;

    @Column(name="phone")
    private String phone;

    @Column(name="photo")
    private String photoAddress;

    @Column(name="publNumber")
    private Integer publicationNumber = 0;

    @Column(name = "isReviewer")
    private boolean isReviewer = false;

    @Column(name="restoreCode")
    private Integer restoreCode;

    @Column(name="isValid")
    private boolean isValid = false;


//    @OneToOne
//    @JoinColumn(name = "message")
//    @JsonIgnore(true)
//    private Message message;

    @ManyToOne
    @JsonIgnore(true)
    private UserAcadStatus acadStatus;

    @ManyToOne
    @JsonIgnore(true)
    private UserSciDegree sciDegree;

    @ManyToOne
    @JsonIgnore(true)
    private UserSex userSex;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_interest", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    @JsonIgnore(true)
    private Set<UserInterest> interests = new HashSet<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore(true)
    private Set<PublicationTemp> articlesSet = new HashSet<>();

//    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.SELECT)
//    @JsonIgnore(true)
//    private Set<Article> articlesSet = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch= FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore(true)
    private Set<Review> reviewSet = new HashSet<>();

//    @OneToMany(mappedBy = "user", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.SELECT)
//    @JsonIgnore(true)
//    private Set<Seminar> seminarsSet = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore(true)
    private Set<Comment> authorCommentsSet = new HashSet<>();

    public User() {
    }



    public User(String username, String password, String name, String surname, String middleName, String university, String institute, String chair, String position, String phone, String photo, UserAcadStatus acadStatus, UserSciDegree sciDegree, UserSex userSex, Set<UserInterest> interests) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.university = university;
        this.institute = institute;
        this.chair = chair;
        this.position = position;
        this.phone = phone;
        this.photoAddress = photo;
        this.acadStatus = acadStatus;
        this.sciDegree = sciDegree;
        this.userSex = userSex;
        this.interests = interests;
    }

//    public User getUserForTables (User user){
//        this.userId = user.getUserId();
//        this.name = user.getName();
//        this.surname = user.getSurname();
//        this.middleName = user.getMiddleName();
//        return this;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> result = new ArrayList<>();
        for(UserRole userRole: userRoles){
            result.add(new SimpleGrantedAuthority(userRole.getListRole().name()));
        }
        return result;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isValid;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public boolean getIsReviewer() {
        return isReviewer;
    }

    public void setIsReviewer(boolean isReviewer) {
        this.isReviewer = isReviewer;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public UserAcadStatus getAcadStatus() {
        return acadStatus;
    }

    public void setAcadStatus(UserAcadStatus acadStatus) {
        this.acadStatus = acadStatus;
    }

    public UserSciDegree getSciDegree() {
        return sciDegree;
    }

    public void setSciDegree(UserSciDegree sciDegree) {
        this.sciDegree = sciDegree;
    }

    public void setValid(boolean isValid){
        this.isValid = isValid;
    }

    public UserSex getUserSex() {
        return userSex;
    }

    public void setUserSex(UserSex userSex) {
        this.userSex = userSex;
    }

    public Integer getPublicationNumber() {
        return publicationNumber;
    }

    public void setPublicationNumber(Integer publicationNumber) {
        this.publicationNumber = publicationNumber;
    }

    public Set<UserInterest> getInterests() {
        return interests;
    }

    public void setInterests(Set<UserInterest> interests) {
        this.interests = interests;
    }
//    public List<ArticleKeyWord> getArticleKeyWords() {
//        return articleKeyWords;
//    }
//
//    public void setArticleKeyWords(List<ArticleKeyWord> articleKeyWords) {
//        this.articleKeyWords = articleKeyWords;
//    }
//
//    public List<SeminarKeyWord> getSeminarKeyWords() {
//        return seminarKeyWords;
//    }
//
//    public void setSeminarKeyWords(List<SeminarKeyWord> seminarKeyWords) {
//        this.seminarKeyWords = seminarKeyWords;
//    }

//    public Set<Article> getArticlesSet() {
//        return articlesSet;
//    }
//
//    public void setArticlesSet(Set<Article> articlesSet) {
//        this.articlesSet = articlesSet;
//    }


    public Set<PublicationTemp> getArticlesSet() {
        return articlesSet;
    }

    public void setArticlesSet(Set<PublicationTemp> articlesSet) {
        this.articlesSet = articlesSet;
    }

    public Set<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(Set<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }

//    public Set<Seminar> getSeminarsSet() {
//        return seminarsSet;
//    }

//    public void setSeminarsSet(Set<Seminar> seminarsSet) {
//        this.seminarsSet = seminarsSet;
//    }

    public Set<Comment> getAuthorCommentsSet() {
        return authorCommentsSet;
    }

    public void setAuthorCommentsSet(Set<Comment> authorCommentsSet) {
        this.authorCommentsSet = authorCommentsSet;
    }

//    public Message getMessage() {
//        return message;
//    }
//
//    public void setMessage(Message message) {
//        this.message = message;
//    }

    public Integer getRestoreCode() {
        return restoreCode;
    }

    public void setRestoreCode(Integer restoreCode) {
        this.restoreCode = restoreCode;
    }

    @Override
    public String toString() {
        return name + " " + middleName + " " + surname;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "userId=" + userId +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                ", middleName='" + middleName + '\'' +
//                ", university='" + university + '\'' +
//                ", institute='" + institute + '\'' +
//                ", chair='" + chair + '\'' +
//                ", position='" + position + '\'' +
//                ", phone='" + phone + '\'' +
//                ", photoAddress='" + photoAddress + '\'' +
//                ", acadStatus=" + acadStatus +
//                ", sciDegree=" + sciDegree +
//                ", userSex=" + userSex +
//                '}';
//    }
}
