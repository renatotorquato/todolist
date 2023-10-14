package br.com.renatotorquato.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.renatotorquato.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Este @Component é para que o Spring gerencie minha classe, assim como o @RestController. Pois o RestController é um component.
@Component
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        // para separar
        var serveletPath = request.getServletPath();
        if (serveletPath.startsWith("/tasks/")) {
            // Pegar a autorização do usuário.
            var autorization = request.getHeader("Authorization");
            var authEncode = autorization.substring("Basic".length()).trim();

            //Para passar o código que vem na authenticação básica e tranformar em string e separar.
            byte[] authDecode = Base64.getDecoder().decode(authEncode);
            var authString = new String(authDecode);
            String[] credentials = authString.split(":");
            String userName = credentials[0];
            String userPassword = credentials[1];

            var user = this.userRepository.findByUsername(userName);
            if (user == null) {
                response.sendError(401, userName);
            } else {
                var passwordVerify = BCrypt.verifyer().verify(userPassword.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    //Essa linha esta setanto o atributo request passando o id para ser controlado pelo Controller
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401, "Usuário: "+ userName + "não autorizado!");
                }
            }

            System.out.println(userName);
            System.out.println(userPassword);

        }else{
            filterChain.doFilter(request, response);
        }

    }

}
