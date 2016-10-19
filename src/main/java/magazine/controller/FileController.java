package magazine.controller;

/**
 * Created by pvc on 14.11.2015.
 */
import java.io.*;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import magazine.servise.ArticleService;
import magazine.servise.ReviewService;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
public class FileController {
    public static final Logger log = Logger.getLogger(FileController.class);

    @Value("${initialPath}")
    private String initialPath;

    @Autowired
    ArticleService articleService;

    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody HashMap<String, Object> imageFile(MultipartHttpServletRequest request,
                                                          HttpServletResponse response) throws Exception {//todo Exception
        log.info("/saveImage controller");
        MultipartFile multipartFile = null;
        try {
            multipartFile = request.getFile("file");
        } catch (Exception e){
            e.printStackTrace();
        }
        Long size = multipartFile.getSize();
        String contentType = multipartFile.getContentType();
        InputStream stream = multipartFile.getInputStream();
        byte[] bytes = IOUtils.toByteArray(stream);

        String fileName = multipartFile.getOriginalFilename();
        String filePath = initialPath + "userPhotos/" + fileName;

        multipartFile.transferTo(new File(filePath));

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("fileoriginalsize", size);
        map.put("contenttype", contentType);
        map.put("base64", new String(Base64Utils.encode(bytes)));

        return map;
    }


    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public void getImage (HttpServletRequest request, HttpServletResponse response, @RequestParam String name, String type) throws IOException {
        log.info("/getFile controller");

        ServletContext cntx = request.getSession().getServletContext();

        String filePath = null;
        if (type.equals("img")) {
            filePath = initialPath + "userPhotos/" + name;
        } else if (type.equals("article") || type.equals("seminar")) {
            filePath = initialPath + name;
            System.err.println("filePath/" + filePath);
        }

        String mime = cntx.getMimeType(filePath);
        if (mime == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.setContentType(mime);
        File file = new File(filePath);
        response.setContentLength((int)file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buf = new byte[1024];
        int count = 0;
        while ((count = in.read(buf)) >= 0) {
            out.write(buf, 0, count);
        }
        out.close();
        in.close();

    }


//    @RequestMapping(value = "/saveArticleFile", method = RequestMethod.POST, produces = {"application/json"})
//    public @ResponseBody
//    HashMap<String, Object> pdfFile(MultipartHttpServletRequest request,
//                                    HttpServletResponse response)  {
//        log.info("/saveArticleFile controller");
//        MultipartFile multipartFile = request.getFile("articleFile");
//        Long size = multipartFile.getSize();
//        String contentType = multipartFile.getContentType();
//        byte[] bytes = new byte[0];
//        try {
//            InputStream stream = multipartFile.getInputStream();
//            bytes = IOUtils.toByteArray(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String fileName = multipartFile.getOriginalFilename();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        String folderPath = initialPath + "userArticles/" + currentUser.getUserId();
//        File folder = new File(folderPath);
//        if (!folder.exists()){
//            folder.mkdir();
//        }
//        int publNumber = currentUser.getPublicationNumber();
//        File newFolder = new File(folderPath + "/" + publNumber);
//        newFolder.mkdir();
//        String filePath = newFolder + "/" + fileName;
//
//        try {
//            multipartFile.transferTo(new File(filePath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("fileoriginalsize", size);
//        map.put("contenttype", contentType);
//        map.put("base64", new String(Base64Utils.encode(bytes)));
//        return map;
//    }





//    @RequestMapping(value = "/savePresentation", method = RequestMethod.POST, produces = {"application/json"})
//    public @ResponseBody
//    HashMap<String, Object> swfFile(MultipartHttpServletRequest request,
//                                    HttpServletResponse response) throws Exception {
//        log.info("/savePresentation controller");
//        MultipartFile multipartFile = request.getFile("presentation");
//        Long size = multipartFile.getSize();
//        String contentType = multipartFile.getContentType();
//        InputStream stream = multipartFile.getInputStream();
//        byte[] bytes = IOUtils.toByteArray(stream);
//        String fileName = multipartFile.getOriginalFilename();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//
//        String folderPath = initialPath + "userSeminars/" + currentUser.getUserId();
//        File folder = new File(folderPath);
//        if (!folder.exists()){
//            folder.mkdir();
//        }
//        int publNumber = currentUser.getPublicationNumber();
//        File newFolder = new File(folderPath + "/" + publNumber);
//        newFolder.mkdir();
//        String filePath = newFolder + "/" + fileName;
//        multipartFile.transferTo(new File(filePath));
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("fileoriginalsize", size);
//        map.put("contenttype", contentType);
//        map.put("base64", new String(Base64Utils.encode(bytes)));
//        return map;
//    }

//    @RequestMapping(value = "/saveReport", method = RequestMethod.POST, produces = {"application/json"})
//    public @ResponseBody
//    HashMap<String, Object> reportPdfFile(MultipartHttpServletRequest request,
//                                          HttpServletResponse response) throws Exception {
//        log.info("/saveReport controller");
//        MultipartFile multipartFile = request.getFile("report");
//        Long size = multipartFile.getSize();
//        String contentType = multipartFile.getContentType();
//        InputStream stream = multipartFile.getInputStream();
//        byte[] bytes = IOUtils.toByteArray(stream);
//        String fileName = multipartFile.getOriginalFilename();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) authentication.getPrincipal();
//        String folderPath = initialPath + "userSeminars/" + currentUser.getUserId();
//        System.err.println(folderPath);
//        File folder = new File(folderPath);
//        if (!folder.exists()){
//            folder.mkdir();
//        }
//        int publNumber = currentUser.getPublicationNumber();
//        File newFolder = new File(folderPath + "/" + publNumber);
//        newFolder.mkdir();
//        String filePath = newFolder + "/" + fileName;
//        multipartFile.transferTo(new File(filePath));
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("fileoriginalsize", size);
//        map.put("contenttype", contentType);
//        map.put("base64", new String(Base64Utils.encode(bytes)));
//        return map;
//    }

}
