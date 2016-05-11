//package magazine.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.RowCallbackHandler;
//import org.springframework.jdbc.core.support.JdbcDaoSupport;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
///**
// * Created by pvc on 14.04.2016.
// */
//public class DatabasePasswordSecurerBean extends JdbcDaoSupport {
//
//    @Autowired
//    private PasswordEncoder encoder;
//
//    public void secureDatabase() {
//        getJdbcTemplate().query("select username, password from users",
//                new RowCallbackHandler(){
//                    @Override
//                    public void processRow(ResultSet rs) throws SQLException {
//                        String username = rs.getString(1);
//                        String password = rs.getString(2);
//                        String encodedPassword =
//                                encoder.encode(password, null);
//                        getJdbcTemplate().update("update users set password = ?
//                                where username = ?", encodedPassword,username);
//                        logger.debug("Updating password for username:
//                                "+username+" to: "+encodedPassword);
//                    }
//                });
//    }
//}