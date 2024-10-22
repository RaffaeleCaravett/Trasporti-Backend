package com.example.TrasportiBackend.Security;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.User.UserService;
import com.example.TrasportiBackend.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 0. Questo metodo verrà eseguito per ogni request che richieda di essere autenticati
        // 1. Verifichiamo se c'è l'header Authorization e estraiamo il token da esso
        if (!request.getRequestURI().startsWith("/socket.io")) {
        String authHeader = request.getHeader("Authorization"); // authHeader --> Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjk5ODczNTI3LCJleHAiOjE3MDA0NzgzMjd9.bCJaensC-bddAiDfU6Jt6JNN8Wooo6lEzypQkylEnUY
        if ((authHeader == null || !authHeader.startsWith("Bearer ")) && !request.getRequestURI().startsWith("/trasporti-chat")) {
            throw new UnauthorizedException("Per favore passa il Bearer Token nell'Authorization header");
        } else {

                String token = authHeader.substring(7);
//            System.out.println("TOKEN -> " + token);
                // 2. Verifico che il token non sia nè scaduto nè sia stato manipolato

                String requestURI = request.getRequestURI();

                boolean isTrasportatoreEndpoint = requestURI.startsWith("/trasportatore/");
                boolean isAziendaEndpoint = requestURI.startsWith("/azienda/");
                boolean isAdminEndpoint = requestURI.startsWith("/admin/");

                if (isTrasportatoreEndpoint) {
                    jwtTools.verifyTrasportatoreToken(token);
                } else if (isAziendaEndpoint) {
                    jwtTools.verifyAziendaToken(token);
                } else if (isAdminEndpoint) {
                    jwtTools.verifyAdminToken(token);
                } else {
                    throw new UnauthorizedException("Accesso negato");
                }

                // 3. Se è tutto OK
                // 3.1 Cerco l'utente nel database tramite id (l'id sta nel payload del token, quindi devo estrarlo da lì)
//                String id = jwtTools.extractIdFromToken(token);
//                Utente currentUser = usersService.findById(Integer.parseInt(id));
                // 3.2 Segnalo a Spring Security che l'utente ha il permesso di procedere
                // Se non facciamo questa procedura, ci verrà comunque tornato 403
//                System.out.println(currentUser);

//                Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 3.3 Procediamo (vuol dire andare al prossimo blocco della filter chain)
//                filterChain.doFilter(request, response);

                // 4. Se non è OK -> 401

                String id = jwtTools.extractIdFromToken(token);
                if (isTrasportatoreEndpoint) {
                    Trasportatore trasportatore = userService.getTrasportatoreById(Long.parseLong(id));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(trasportatore, null, trasportatore.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    Azienda azienda = userService.getAziendaById(Long.parseLong(id));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(azienda, null, azienda.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                // currentUtente.getAuthorities().forEach(System.out::println);


                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Questo metodo serve per specificare quando il filtro JWTAuthFilter non debba entrare in azione
        // Ad es tutte le richieste al controller /auth/** non devono essere filtrate
        String pathWithArguments = request.getServletPath() + request.getQueryString();

        List<String> excludedPaths = Arrays.asList("/auth");

        return excludedPaths.stream().anyMatch(pathWithArguments::startsWith);
    }
}
