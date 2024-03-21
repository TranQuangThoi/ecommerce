package com.techmarket.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name = "db_voucher")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Voucher extends Auditable<String> {

    private String title;
    @Column(columnDefinition = "text")
    private String content;
    private Integer percent;
    private Date expired;
    private Integer kind;
    private Integer amount;
}
