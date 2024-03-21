package com.techmarket.api.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "db_nation")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Nation extends Auditable<String> {

    private String name;
    private String postCode;
    private Integer kind;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Nation parent;

}
