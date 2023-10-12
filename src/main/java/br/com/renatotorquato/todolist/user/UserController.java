package br.com.renatotorquato.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Os controllers precisam estar junto com a classe principal, por causa do springBoot fazer a leitura recursiva, ou seja depois de encontrar a classe principal ele vai para as mais proximas.
@RestController
@RequestMapping("/users")
public class UserController {
    //com o Autowired eu estou pedindo para o Spring gerenciar o ciclo de vida do IUserRepository
    @Autowired
    private IUserRepository userRepository;
    // quando coloco @requestBody significa que os dados virão no corpo da requisição.
     @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null){
            System.out.println("Usuário já existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }
        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
