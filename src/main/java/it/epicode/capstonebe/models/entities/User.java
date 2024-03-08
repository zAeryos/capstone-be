package it.epicode.capstonebe.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.capstonebe.models.enums.UserRoles;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private     UUID            id;
    private     String          name;
    private     String          surname;
    @Column(unique = true)
    private     String          email;
    @Column(unique = true, name = "phone_number")
    private     String          phoneNumber;
    @Column(unique = true)
    private     String          username;
    private     String          password;
    private     String          avatar;
    private     List<UserRoles> roles = List.of(UserRoles.USER);
    private     Timestamp       createdAt;

    public User(String name, String surname, String email, String phoneNumber, String username, String password) {
        this.name           = name;
        this.surname        = surname;
        this.email          = email;
        this.phoneNumber    = phoneNumber;
        this.username       = username;
        this.password       = password;
        createdAt           = Timestamp.valueOf(LocalDateTime.now());
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(roles.size());

        for (UserRoles r : roles)
            authorities.add(new SimpleGrantedAuthority(r.name()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}