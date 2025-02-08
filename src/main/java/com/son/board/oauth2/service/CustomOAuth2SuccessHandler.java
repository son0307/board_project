package com.son.board.oauth2.service;

import com.son.board.domain.User;
import com.son.board.oauth2.domain.CustomOAuth2User;
import com.son.board.oauth2.dto.OAuth2RegistrationDto;
import com.son.board.repository.UserRepository;
import com.son.board.service.SecurityContextService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final SecurityContextService securityContextService;
    private final HttpSession httpSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String oAuthId = oAuth2User.getoAuthid();
        String snsName = oAuth2User.getSnsName();

        // 로컬 회원가입 여부 확인 (username 필드가 OAuth2의 고유 ID라고 가정)
        Optional<User> userOptional = userRepository.findByUsername(oAuthId);
        if (userOptional.isEmpty()) {
            // 회원가입 미완료 → 세션에 OAuth2 정보를 저장하고, SecurityContext 초기화
            // (즉, 인증된 상태를 해제)
            OAuth2RegistrationDto registrationDto = OAuth2RegistrationDto.builder()
                    .username(oAuthId)
                    .nickname(snsName)
                    .build();
            httpSession.setAttribute("oauth2Registration", registrationDto);

            // 경고 메시지도 세션에 저장
            httpSession.setAttribute("registrationWarning", "회원 정보가 없습니다. 회원 가입을 먼저 해주세요.");

            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.setInvalidateHttpSession(false); // 세션 무효화 방지
            logoutHandler.logout(request, response, authentication);

            // 회원가입 페이지로 리다이렉트 (이 경우, isAuthenticated()는 false)
            response.sendRedirect("/oauth2/signup");
        } else {
            // 회원가입 완료 → DB에서 사용자 정보 로드
            User user = userOptional.get();
            securityContextService.refreshUserDetails(user.getUsername());

            response.sendRedirect("/");
        }
    }
}
