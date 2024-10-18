package ps.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ps.demo.user.entity.Permission;
import ps.demo.user.entity.User;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface PermissionRepository extends JpaRepository<Permission, Long> {


}
