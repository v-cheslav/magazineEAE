package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.PublicationException;
import magazine.Exeptions.RegistrationException;
import magazine.domain.Article;
import magazine.domain.Publication;
import magazine.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;


@Service
public class FileService {
    public static final Logger log = Logger.getLogger(FileService.class);
// todo створити при ініціалізації всієї програми папки UserResourses та ін. необхідні.
//     todo Замінити абсолютне адресування на відносте, інакше в Unix не працюватиме
//     todo Замінити всюди слеші та початкові на File.separator  File.pathSeparator
    @Value("${initialPath}")
    private String initialPath;


    @Autowired
    UserService userService;


    public void saveAndSetArticleFile (Article article, MultipartHttpServletRequest request)throws PublicationException{
        log.info("saveAndSetArticleFile");
        MultipartFile multipartFile = request.getFile("articleFile");
        String relativePath = saveFile(article, multipartFile);
        article.setPublicationPath(relativePath);
        article.setArticleFileName(multipartFile.getOriginalFilename());
    }




    public String saveFile(Publication publication, MultipartFile multipartFile) throws PublicationException {
        log.info("saveFile");
        String articlePath = getAbsolutePath(publication) + multipartFile.getOriginalFilename();
        log.info("articlePath " + articlePath);
        try {
            writeFile(articlePath, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ArticleCreationException("Не вдалося завантажити файл статті!");
        }
        return getRelativePath(publication);
    }

    private String getAbsolutePath(Publication publication){
        log.info("getAbsolutePath");
        User user = publication.getUser();

        String userFolderPath = initialPath + "publications" + File.separator + user.getUserId();

        File userFolder = new File(userFolderPath);
        if (!userFolder.exists()){
            userFolder.mkdir();
        }//todo спробувати чи буде працювати без цього

        int publicationNumber = user.getPublicationNumber();

        String publicationPath = userFolderPath + File.separator + publicationNumber;

        File publicationFolder = new File(publicationPath);
        publicationFolder.mkdir();

        return publicationPath + File.separator;
    }

    private String getRelativePath(Publication publication){
        User user = publication.getUser();
        int publicationNumber = user.getPublicationNumber();

        String publicationRelativePath = "publications" + File.separator
                + user.getUserId() + File.separator
                + publicationNumber + File.separator;

        return publicationRelativePath;
    }

    private void writeFile(String path, MultipartFile file) throws IOException{
        log.info("writeFile");
        file.transferTo(new File(path));
    }


    public void saveAndSetUserPhoto(User user, MultipartHttpServletRequest request)throws RegistrationException {
        log.info("saveAndSetUserPhoto.method");

        MultipartFile userImage = request.getFile("userPhoto");
        if (photoNeedToUpdate(userImage)){

            String imageName = userImage.getOriginalFilename();
            String newImageName = getNewImageName(user, imageName);
            log.info("newImageName \'" + newImageName + "\'");

            String userImagePath = initialPath + "userPhotos/" + newImageName;
            log.info("userImagePath \'" + userImagePath + "\'");

            try {
                writeFile(userImagePath, userImage);
            } catch (IOException e) {
                log.warn("User image wasn't saved.", e);
                user.setPhotoName("default.png");
            }
            user.setPhotoName(newImageName);
        } else {
            user.setPhotoName("default.png");
            log.info("Photo name set default");
        }

    }

    private String getNewImageName (User user, String oldImageName){
        int index = oldImageName.lastIndexOf(".");
        String fileType = oldImageName.substring(index);
        return user.getUsername() + fileType;
    }

    private boolean photoNeedToUpdate(MultipartFile userImage) {
        if (userImage.getSize() != 0){
            log.info("New file exists");
            return true;
        } else {
            log.info("New file don't exists");
            return false;
        }
    }

    public void changeUserPhoto(User user, MultipartHttpServletRequest request)throws RegistrationException {
        log.info("changeUserPhoto.method");
        String imagePath = initialPath + "userPhotos" + File.separator + user.getPhotoName();
        log.info("imagePath " + imagePath);

        MultipartFile userImageFile = request.getFile("userPhoto");
        if (photoNeedToUpdate(userImageFile)){
            deleteOldImage(imagePath);
            saveAndSetUserPhoto(user, request);
        }
    }


    private void deleteOldImage (String path){
        log.info("deleteOldImage.method");
        try {
            new File(path).delete();
            log.info("image is deleted");
        } catch (Exception e) {
            log.error("Failed to delete file " + path);
            e.printStackTrace();
        }
    }

}
