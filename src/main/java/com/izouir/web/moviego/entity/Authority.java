package com.izouir.web.moviego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_id_generator")
    @SequenceGenerator(name = "authority_id_generator", sequenceName = "authority_id_generator", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "authority")
    private String authority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = Collections.emptySet();

    public Authority() {
    }

    public Authority(final String authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(final String authority) {
        this.authority = authority;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
    }
}
