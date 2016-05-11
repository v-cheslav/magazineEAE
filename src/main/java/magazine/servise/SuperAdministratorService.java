package magazine.servise;

import magazine.Exeptions.RegistrationException;

/**
 * Created by pvc on 22.02.2016.
 */
public interface SuperAdministratorService {
    public Long createUser()throws RegistrationException;
}
