//package magazine.servise;
//
//import magazine.Exeptions.RegistrationException;
//import magazine.dao.UserRoleDao;
//import magazine.domain.ListRole;
//import magazine.domain.SuperAdministrator;
//import magazine.domain.User;
//import magazine.domain.UserRole;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
///**
// * Created by pvc on 22.02.2016.
// */
//@Service
//public class SuperAdministratorServiceImpl implements SuperAdministratorService {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    private UserRoleDao userRoleDao;
//
//    public SuperAdministratorServiceImpl() {
//    }
//
//    @Override
//    public Long createUser() throws RegistrationException {
//        User superAdmin = new SuperAdministrator();
//        List<UserRole> userRoles = new ArrayList<>();
//        UserRole userRole = userRoleDao.getUserRole(ListRole.USER);
////        userRole.setListRole(ListRole.USER);
//        userRoles.add(userRole);
//        UserRole adminRole = userRoleDao.getUserRole(ListRole.ADMIN);
////        adminRole.setListRole(ListRole.ADMIN);
//        userRoles.add(adminRole);
//        UserRole superAdminRole = userRoleDao.getUserRole(ListRole.SUPERADMIN);
////        superAdminRole.setListRole(ListRole.SUPERADMIN);
//        userRoles.add(superAdminRole);
//
//        superAdmin.setUserRoles(userRoles);
//
//        return (Long) sessionFactory.getCurrentSession().save(superAdmin);
//    }
//
//}
