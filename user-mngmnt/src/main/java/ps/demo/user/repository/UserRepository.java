package ps.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ps.demo.user.entity.Role;
import ps.demo.user.entity.User;

import java.util.List;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface UserRepository extends JpaRepository<User, Long> {


}
