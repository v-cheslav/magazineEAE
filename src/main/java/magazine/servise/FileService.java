package magazine.servise;

import magazine.Exeptions.ArticleCreationException;
import magazine.Exeptions.PublicationException;
import magazine.Exeptions.RegistrationException;
import magazine.domain.Article;
import magazine.domain.Publication;
import magazine.domain.Seminar;
import magazine.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

        String articlePath = getAbsolutePath(publication);
        String relativePath = getRelativePath(publication);

        try {
            fileWriter(articlePath, multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ArticleCreationException("Не вдалося завантажити файл статті!");
        }
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

    private void fileWriter(String path, MultipartFile file) throws IOException{
        String fileName = file.getOriginalFilename();
        file.transferTo(new File(path + fileName));
    }


    public void saveUserImage (User user, MultipartFile userImage)throws RegistrationException {

        String imageName = userImage.getOriginalFilename();

        int index = imageName.lastIndexOf(".");
        String fileType = imageName.substring(index);
        System.out.println("fileType " +fileType);
        String newImageName = user.getUsername() + fileType;
        user.setPhotoAddress(newImageName);
        System.out.println("newImageName " + newImageName);

        String userImagePath = initialPath + "userPhotos/" + newImageName;

        try {
//            fileWriter(userImagePath, userImage);
            userImage.transferTo(new File(userImagePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RegistrationException("Перевірте вірність вказаної адреси!");
        }
    }

    public void changeUserImage(User user, MultipartFile multipartFile)throws RegistrationException {
        String imagePath = initialPath + "userPhotos/" + user.getPhotoAddress();
        deleteImage(imagePath);
        saveUserImage(user, multipartFile);
    }

    private void deleteImage (String path){
        File file = new File(path);
        try {
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
