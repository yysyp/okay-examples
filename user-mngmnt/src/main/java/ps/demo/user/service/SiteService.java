package ps.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.user.repository.CountryRepository;
import ps.demo.user.repository.SiteRepository;

@Service
public class SiteService {

    @Autowired
    SiteRepository siteRepository;




}
