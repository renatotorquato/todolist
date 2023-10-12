package br.com.renatotorquato.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Os controllers precisam estar junto com a classe principal, por causa do springBoot fazer a leitura recursiva, ou seja depois de encontrar a classe principal ele vai para as mais proximas.
@RestController
@RequestMapping("/users")
public class UserController {
    // quando coloco @requestBody significa que os dados virão no corpo da requisição.
     @PostMapping("/")
    public void create(@RequestBody UserModel userModel){
        System.out.println(userModel.name);
    }
}
