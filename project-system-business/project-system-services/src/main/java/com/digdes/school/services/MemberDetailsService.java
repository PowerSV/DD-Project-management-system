package com.digdes.school.services;

import com.digdes.school.models.Member;
import com.digdes.school.models.MemberDetails;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberRepository;

    public MemberDetailsService(MemberJpaRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow();
        return new MemberDetails(member);
    }
}
