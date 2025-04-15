package com.java.app.enterprise_application.web.user;

import com.java.app.enterprise_application.model.User;
import org.springframework.stereotype.Controller;

import static com.java.app.enterprise_application.web.SecurityUtil.authUserId;


@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}