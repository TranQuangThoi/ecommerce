package com.techmarket.api.model;




import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "db_category")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Category extends Auditable<String>{

    private String name;
    @Column(name = "description" ,  columnDefinition = "TEXT")
    private String description;
    private String image;
    private Integer ordering;
    private Integer kind;
}
