//package magazine.utils;
//
//import magazine.dao.ArticleKeyWordDao;
//import magazine.domain.Article;
//import magazine.domain.ArticleKeyWord;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Created by pvc on 08.01.2016.
// */
//public class UserInterestFormer <K, P> {
//
//    @Autowired
//    ArticleKeyWordDao articleKeyWordDao;
//
//    private K keyWord;
//    private P publication;
//
//    public UserInterestFormer() {
//    }
//
//    public UserInterestFormer(K keyWord, P publication) {
//        this.keyWord = keyWord;
//        this.publication = publication;
//    }
//
//    private Set<K> userInterestFormer (String keyWordsStr, P publication){
//        Set<K> keyWordsSet = new HashSet<>();
//        if (keyWordsStr.length() >= 2) {//якщо пусто, пробіл або 2, або менше 2 символів то в БД не додається
//            String[] keyWordsArrStr = keyWordsStr.split("\\,");
//            for (String keyWordStr : keyWordsArrStr) {
//                keyWordStr = keyWordStr.replaceFirst(" ", "");
//                ArticleKeyWord articleKeyWord;
//                try {
//                    articleKeyWord = articleKeyWordDao.getKeyWord(keyWordStr.toLowerCase());
//                    Set <Article> articles = articleKeyWord.getArticles();
//                    articles.add(article);
//                    articleKeyWordDao.update(articleKeyWord);
//                } catch (NullPointerException e) {//todo
//                    articleKeyWord = new ArticleKeyWord(keyWordStr.toLowerCase());
//                    Set<Article> articles = new HashSet<>();
//                    articleKeyWord.setArticles(articles);
//                    articles.add(article);
//                    articleKeyWordDao.create(articleKeyWord);
////                    e.printStackTrace();
//                }
//                keyWordsSet.add(articleKeyWord);
//            }
//        } else {
//            keyWordsSet = null;
//        }
//        return keyWordsSet;
//    }
//}
