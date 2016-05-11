package magazine.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by pvc on 18.02.2016.
 */
@Entity
//@Table(name = "Users")
public class SuperAdministrator extends User/* implements UserDetails*/ {

    @Value("${adminPassword}")
    private static String adminPassword;

//    private static String username = "SuperAdministrator";


    public SuperAdministrator() {
    }

}
