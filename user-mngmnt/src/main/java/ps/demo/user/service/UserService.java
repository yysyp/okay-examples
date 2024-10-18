package ps.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.user.repository.RoleRepository;
import ps.demo.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;




}
