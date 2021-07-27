package br.com.astronaomia.astronaomia.services;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PostOnIgService {

    @Value("${instagran.login}")
    private String login;

    @Value("${instagran.password}")
    private String password;

    public IGClient loginOnInstagran(){
        IGClient igClient = null;
        try {
            igClient = IGClient.builder().username(login).password(password).login();
        }
        catch (IGLoginException e){
            System.out.println("Error: "+e.getStackTrace());
        }
        return igClient;
    }

    public AtomicReference<Boolean> postOnInstagran(IGClient igClient){
        AtomicReference<Boolean> post = new AtomicReference<>(false);
        igClient.actions().timeline()
                .uploadPhoto(new File("/home/jonas/Documentos/Projetos Jonas/Aulas/Alura/JPA-Hibernate/astronaomia/src/main/resources/templates/NorAmNeb_dd.gif"), "Um teste")
                .thenAccept(response->{
                    post.set(true);
                }).join();
        return post;
    }

}
