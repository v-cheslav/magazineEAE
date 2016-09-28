package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.PublicationException;
import magazine.Exeptions.RegistrationException;
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

/**
 * Created by pvc on 01.07.2016.
 */
@Service
public class FileService {
    public static final Logger log = Logger.getLogger(FileService.class);

    @Value("${initialPath}")
    private String initialPath;


    @Autowired
    UserService userService;


    public String saveFile(Publication publication, MultipartFile multipartFile) throws PublicationException {
        log.info("saveFile");

        String articlePath = getAbsolutePath(publication) + multipartFile.getOriginalFilename();
        try {
            writeFile(articlePath, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ArticleCreationException("Не вдалося завантажити файл статті!");
        }
        String relativePath = getRelativePath(publication);
        return relativePath;
    }

    private String getAbsolutePath(Publication publication){
        User user = publication.getUser();

        String userFolderPath = initialPath + "publications/" + user.getUserId();

        File userFolder = new File(userFolderPath);
        if (!userFolder.exists()){
            userFolder.mkdir();
        }//todo спробувати чи буде працювати без цього

        int publicationNumber = user.getPublicationNumber();

        String publicationPath = userFolderPath + "/" + publicationNumber;

        File publicationFolder = new File(publicationPath);
        publicationFolder.mkdir();

        return publicationPath + "/" ;
    }

    private String getRelativePath(Publication publication){
        User user = publication.getUser();
        int publicationNumber = user.getPublicationNumber();

        String publicationRelativePath ="publications/"
                + user.getUserId() + "/"
                + publicationNumber + "/";

        return publicationRelativePath;
    }

    private void writeFile(String path, MultipartFile file) throws IOException{
        file.transferTo(new File(path));
    }


    public void saveAndSetUserPhoto(User user, MultipartHttpServletRequest request)throws RegistrationException {
        log.info("saveAndSetUserPhoto.method");

        MultipartFile userImage = request.getFile("userPhoto");
        if (isNewPhotoFileExists(userImage)){

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

    private boolean isNewPhotoFileExists(MultipartFile userImage) {
        if (userImage.getSize() != 0){
            return true;
        } else {
            return false;
        }
    }

    public void changeUserPhoto(User user, MultipartHttpServletRequest request)throws RegistrationException {
        log.info("changeUserPhoto.method");
        String imagePath = initialPath + "userPhotos/" + user.getPhotoName();
        log.info("imagePath " +imagePath);

        MultipartFile userImageFile = request.getFile("userPhoto");
        if (isNewPhotoFileExists(userImageFile) && !isCurrentPhotoDefault(user)){
            deleteOldImage(imagePath);
            saveAndSetUserPhoto(user, request);
        }
    }

    public boolean isCurrentPhotoDefault(User user) {
        if (user.getPhotoName().equals("default.png")){
            log.info("User photo is default");
            return true;
        }
        log.info("User photo isn't default");
        return false;
    }

    private void deleteOldImage (String path){
        log.info("deleteOldImage.method");
//        File file = new File(path);
        try {
            new File(path).delete();
            log.info("image is deleted");
        } catch (Exception e) {
            log.error("Failed to delete file " + path);
            e.printStackTrace();
        }
    }

}
