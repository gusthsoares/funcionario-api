package com.gustavo.funcionarioapi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControle {

    @GetMapping("/hello")
    public String Hello(){
        return "Sprint Boot funcionando! Olá, Gustavo!";
    }
}
