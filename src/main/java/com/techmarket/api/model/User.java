package com.techmarket.api.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "db_user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User extends Auditable<String>{

    private Date birthday;
    private Integer gender;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private Integer memberShip;
    private Integer point=0;
}
