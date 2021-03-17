package br.univates.magaiver.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Magaiver Santos
 */

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Data
@Entity
@Table(name = "transaction", schema = "public")
@DynamicUpdate
public class Transaction {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private boolean enabled;
}
