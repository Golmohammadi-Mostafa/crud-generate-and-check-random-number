package com.felixin.random;

import com.felixin.random.dto.SingUpDTO;
import com.felixin.random.enums.RoleType;
import com.felixin.random.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private final UserService userService;


    public Application(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        LOGGER.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
                        + "External: \t{}://{}:{}\n\t"
                        + "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
                InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getActiveProfiles());
    }


    @Override
    public void run(String... params) {
        userService.deleteAllUsers();
        SingUpDTO admin = new SingUpDTO();
        admin.setUsername("admin");
        admin.setPassword("123");
        Set<RoleType> adminRole = new HashSet();
        adminRole.add(RoleType.ROLE_ADMIN);
        admin.setRoleType(adminRole);

        userService.signUp(admin);

        SingUpDTO user1 = new SingUpDTO();
        user1.setUsername("user");
        user1.setPassword("123");

        Set<RoleType> userRole = new HashSet();
        userRole.add(RoleType.ROLE_USER);
        user1.setRoleType(userRole);
        userService.signUp(user1);

    }

}