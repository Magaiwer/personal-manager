package br.univates.magaiver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Data
@Entity
@Table(name = "groups", schema = "public")
@DynamicUpdate
public class Group {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_permission",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    public boolean removePermission(Long permissionId) {
        Permission permission = this.getPermissions()
                .stream().filter(p -> p.getId().equals(permissionId))
                .findFirst().orElse(null);
        return this.permissions.remove(permission);
    }

    public boolean addPermission(Permission permission) {
        return this.permissions.add(permission);
    }
}