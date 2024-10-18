package ps.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="role_tbl")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long parentId;
    private String roleName;
    private String displayName;
    private String description;
    private LocalDateTime createdTime;
    private String createdBy;

    @ManyToMany(mappedBy = "roles")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

    public void addUser(User user) {
        this.getUsers().add(user);
        user.getRoles().add(this);
    }

    public void removeUser(User user) {
        this.getUsers().remove(user);
        user.getRoles().remove(this);
    }

    public void addPermission(Permission permission) {
        this.getPermissions().add(permission);
        permission.getRoles().add(this);
    }

    public void removePermission(Permission permission) {
        this.getPermissions().remove(permission);
        permission.getRoles().remove(this);
    }

}
