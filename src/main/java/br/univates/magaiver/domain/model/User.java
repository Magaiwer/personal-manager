package br.univates.magaiver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString
@Entity
@Table(name = "users", schema = "public")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private boolean enabled;

    @CreationTimestamp
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @ManyToMany
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups;

    public boolean isNew() {
        return this.getId() == null;
    }

    public boolean removeGroup(Long groupId) {
        Optional<Group> group = this.getGroups()
                .stream()
                .filter(g -> g.getId().equals(groupId))
                .findFirst();

        return this.getGroups().remove(group.get());
    }


    public boolean addGroup(Group group) {
        return this.getGroups().add(group);
    }
}
