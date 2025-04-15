package com.java.app.enterprise_application.web.user;

import com.java.app.enterprise_application.model.User;
import com.java.app.enterprise_application.service.UserService;
import com.java.app.enterprise_application.utils.aspects.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.java.app.enterprise_application.utils.ValidationUtil.assureIdConsistent;
import static com.java.app.enterprise_application.utils.ValidationUtil.checkIsNew;

@Log
public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    public List<User> getAll() {
        return service.getAll();
    }

    public User get(int id) {
        return service.get(id);
    }

    public User create(User user) {
        checkIsNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public void update(User user, int id) {
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getByMail(String email) {
        return service.getByEmail(email);
    }
}