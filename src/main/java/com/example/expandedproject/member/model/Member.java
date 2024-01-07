package com.example.expandedproject.member.model;

import com.example.expandedproject.product.model.Product;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Member")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
        private Long idx;

        최종적으로 Controller에 반환되는 Response Dto의 id에만
        idx로 해주면 된다.
        (프론트엔드 코드에서 idx로 되어 있기 때문)
    */

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 30, unique = true)
    private String nickname;

    private String authority;

    private Boolean status;

    @OneToMany(mappedBy = "brandIdx")
    private List<Product> memberList = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public String getUsername() {
        return email;
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
        return status;
    }
    /*
        SpringSecurity의 로그인 등의 인증, 인가 과정 상,
        그리고 우리는 이메일을 유저의 아이디로 사용하고 있고
        이 이메일 인증 과정이 있기 때문에
        이 status 값을 반환하도록 하므로써
        이메일 인증을 할 경우 status 값을 true가 되고
        이메일 인증을 하지 않았을 꼉우 status 값은 false가 된다.
    */
}
