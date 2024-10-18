package ps.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.demo.user.entity.Country;
import ps.demo.user.entity.Region;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface CountryRepository extends JpaRepository<Country, Long> {


}
