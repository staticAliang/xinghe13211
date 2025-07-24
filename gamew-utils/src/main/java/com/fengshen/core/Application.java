package com.fengshen.core;

import org.springframework.boot.autoconfigure.*;
import org.mybatis.spring.annotation.*;
import org.springframework.boot.*;

@SpringBootApplication(scanBasePackages = { "com.fengshen.db", "com.fengshen.core" })
@MapperScan({ "com.fengshen.db.dao" })
public class Application
{
    public static void main(final String[] args) {
        SpringApplication.run((Class)Application.class, args);
    }
}
