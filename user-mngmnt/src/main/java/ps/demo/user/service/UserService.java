package ps.demo.user.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.user.entity.Permission;
import ps.demo.user.entity.Role;
import ps.demo.user.entity.User;
import ps.demo.user.repository.PermissionRepository;
import ps.demo.user.repository.RoleRepository;
import ps.demo.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Transactional
    public User saveUser(User user) {

        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                permissionRepository.save(permission);
            }
            roleRepository.save(role);
        }
        return userRepository.save(user);

    }



}
