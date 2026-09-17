package keenay.education.config;

import keenay.education.security.CustomUserDetail;
import keenay.education.security.jwt.JwtService;
import keenay.education.service.impl.CustomUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CustomUserServiceImpl customUserService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader == null || authHeader.isBlank()) {
                throw new AuthenticationCredentialsNotFoundException("Missing Authorization header on CONNECT");
            }

            try {
                String token = jwtService.getJwtToken(authHeader);
                String login = jwtService.getLoginFromToken(token);

                UserDetails userDetails = customUserService.getUserByEmail(login);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                accessor.setUser(auth);
            } catch (IllegalArgumentException ex) {
                log.warn("WS auth failed: {}", ex.getMessage());
                throw new AuthenticationCredentialsNotFoundException("Invalid or expired token");
            }
        }

        return message;
    }
}