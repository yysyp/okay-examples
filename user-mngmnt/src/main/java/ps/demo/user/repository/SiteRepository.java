package ps.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.demo.user.entity.Country;
import ps.demo.user.entity.Site;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface SiteRepository extends JpaRepository<Site, Long> {


}
