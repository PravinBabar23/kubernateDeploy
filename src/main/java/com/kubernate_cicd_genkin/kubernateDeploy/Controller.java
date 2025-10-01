package com.kubernate_cicd_genkin.kubernateDeploy;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @GetMapping("/home")
    public String Show()
    {

        return "Hi my first kubernate deployment";
    }
}
