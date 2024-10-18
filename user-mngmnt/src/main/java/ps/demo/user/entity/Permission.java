package ps.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private Set<Role> roles;


}
