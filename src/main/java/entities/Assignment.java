package entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "contact_info")
    private String contactInfo;

    @ManyToOne
    @JoinColumn(name = "dinnerevent_id")
    private DinnerEvent dinnerevent;



    @ManyToMany(mappedBy = "assignmentList")
    private Set<User> users = new HashSet<>();


    public Assignment(String familyName, String createDate, String contactInfo) {
        this.familyName = familyName;
        this.createDate = createDate;
        this.contactInfo = contactInfo;
    }
}
