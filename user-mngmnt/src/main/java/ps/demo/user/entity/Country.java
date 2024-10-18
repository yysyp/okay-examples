package ps.demo.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryName;

    @OneToMany(mappedBy = "country")
    @Builder.Default
    private Set<Site> sites = new HashSet<>();

    @ManyToOne
    private Region region;

    public void addSite(Site site) {
        this.getSites().add(site);
        site.setCountry(this);
    }

    public void removeSite(Site site) {
        this.getSites().remove(site);
        site.setCountry(null);
    }

}
