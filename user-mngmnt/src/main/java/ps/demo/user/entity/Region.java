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
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String regionName;
    private String displayName;

    @OneToMany(mappedBy = "region")
    @Builder.Default
    private Set<Country> countries = new HashSet<>();

    public void addCountry(Country country) {
        this.getCountries().add(country);
        country.setRegion(this);
    }

    public void removeCountry(Country country) {
        this.getCountries().remove(country);
        country.setRegion(null);
    }


}
