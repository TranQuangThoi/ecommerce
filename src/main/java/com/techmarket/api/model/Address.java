package com.techmarket.api.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "db_address")
public class Address extends Auditable<String>{

    private String address;
    @ManyToOne
    private Nation ward;
    @ManyToOne
    private Nation district;
    @ManyToOne
    private Nation province;
    private String name;
    private String phone;
    @ManyToOne
    private User user;
}