package com.techmarket.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "db_group")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Group extends Auditable<String> {

    @Column(name = "name", unique =  true)
    private String name;
    @Column(name = "description", length = 1000)
    private String description;
    @Column(name = "kind")
    private int kind;
    @Column(name = "is_system_role")
    private Boolean isSystemRole = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "db_permission_group",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id",
                    referencedColumnName = "id"))
    private List<Permission> permissions = new ArrayList<>();
}