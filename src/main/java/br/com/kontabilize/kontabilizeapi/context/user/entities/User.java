package br.com.kontabilize.kontabilizeapi.context.user.entities;

import br.com.kontabilize.kontabilizeapi.context.user.entities.enums.Role;
import br.com.kontabilize.kontabilizeapi.shared.utility.DateUtil;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@ToString
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @Column(columnDefinition = "varchar(160)", length = 160, unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(60)", length = 60, nullable = false)
    private String password;

    @Column(columnDefinition = "boolean", nullable = false)
    private boolean active;

    @Column(columnDefinition = "timestamp", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(10)", nullable = false)
    private Role role;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority("ROLE_" + role));

        return list;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User() {
    }

    public User(String email, String password, Role role) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.active = false;
        this.createAt = DateUtil.now();
        this.role = role;
    }

    public void activate(){
        this.active = true;
    }
}
