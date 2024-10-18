package ps.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String displayName;
    private String description;
    private LocalDateTime createdTime;
    private String createdBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<Role> roles = new HashSet<Role>();

    public void addRole(Role role) {
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }

}
