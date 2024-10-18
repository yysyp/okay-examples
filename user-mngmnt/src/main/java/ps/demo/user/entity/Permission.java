package ps.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="permission_tbl")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String permissionName;
    private String displayName;
    private String description;
    private LocalDateTime createdTime;
    private String createdBy;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getPermissions().add(this);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getPermissions().remove(this);
    }


}
