package com.digdes.school.security.config;

import com.digdes.school.dto.authenticate.RegisterRequest;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.models.Member;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final MemberMapper memberMapper;
    private final MemberJpaRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void createAdmin() {
        Optional<Member> admin = memberRepository.findByEmail("admin@admin.com");
        if (admin.isEmpty()) {
            RegisterRequest registerAdmin = new RegisterRequest();
            registerAdmin.setFirstName("admin");
            registerAdmin.setLastName("admin");
            registerAdmin.setEmail("admin@admin.com");
            registerAdmin.setPassword("admin");

            Member member = memberMapper.create(registerAdmin);
            member.setAuthoritiesRole("ROLE_ADMIN");
            member.setPassword(passwordEncoder.encode(registerAdmin.getPassword()));
            memberRepository.save(member);
        }
    }
}
