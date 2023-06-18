package com.digdes.school.security.config;

import com.digdes.school.dto.authenticate.AuthenticationRequest;
import com.digdes.school.dto.authenticate.AuthenticationResponse;
import com.digdes.school.dto.authenticate.RegisterRequest;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.models.Member;
import com.digdes.school.models.MemberDetails;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final MemberJpaRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Member member = memberMapper.create(request);
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        memberRepository.save(member);

        UserDetails memberDetails = new MemberDetails(member);
        String jwtToken = jwtService.generateToken(memberDetails);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow();
        UserDetails memberDetails = new MemberDetails(member);
        String jwtToken = jwtService.generateToken(memberDetails);
        return new AuthenticationResponse(jwtToken);
    }
}
