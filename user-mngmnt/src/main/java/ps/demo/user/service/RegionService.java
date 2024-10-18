package ps.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.user.repository.RegionRepository;
import ps.demo.user.repository.UserRepository;

@Service
public class RegionService {

    @Autowired
    RegionRepository regionRepository;




}
