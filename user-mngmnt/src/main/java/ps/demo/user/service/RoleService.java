package ps.demo.user.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.user.entity.Role;
import ps.demo.user.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;




}
