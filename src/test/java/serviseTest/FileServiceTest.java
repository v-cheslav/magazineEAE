//package serviseTest;
//import magazine.domain.Article;
//import magazine.domain.Publication;
//import magazine.domain.Seminar;
//import magazine.servise.FileService;
//import org.apache.commons.fileupload.disk.DiskFileItem;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class FileServiceTest {
//    private FileService module;
//    private MultipartFile reportFile;
//    private MultipartFile presentationFile;
//    private String reportDestinationPath;
//    private String presentationDestinationPath;
//    private Article article;
//    private Seminar seminar;
//
//    @BeforeClass
//    public void setUp(){
//        String reportPath = "src/test/filesForTesting/source/report.swf";
//        String presentationPath = "src/test/filesForTesting/source/presentation.swf";
//
//        reportFile = getMyMultipartFile(reportPath);
//        presentationFile = getMyMultipartFile(presentationPath);
//
//        article = new Article();
//        seminar = new Seminar();
//        module = new FileService();
//    }
//
//
//
//
//
//    public MultipartFile getMyMultipartFile (String path) {
//        File file = new File(path);
//        DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length() , file.getParentFile());
//        try {
//            fileItem.getOutputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new CommonsMultipartFile(fileItem);
//    }
//
//
//
//
//}
