package br.univates.magaiver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Magaiver Santos
 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Data
@Entity
@Table(name = "category", schema = "public")
@DynamicUpdate
public class Category implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String icon;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaction> transactions;

}
