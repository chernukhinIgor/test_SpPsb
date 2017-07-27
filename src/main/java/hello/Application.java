package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);
//        System.out.println(passwordEncoder.encode("1111"));
//        System.out.println(passwordEncoder.encode("1111"));
//        passwordEncoder.


    }
}
