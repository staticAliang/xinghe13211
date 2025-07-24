package com.fengshen.db;

import org.springframework.boot.autoconfigure.*;
import org.mybatis.spring.annotation.*;
import org.springframework.boot.*;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = { "com.fengshen.db" })
@MapperScan({ "com.fengshen.db.dao" })
//@ComponentScan({"com.fengshen.db"})
public class Application
{
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
