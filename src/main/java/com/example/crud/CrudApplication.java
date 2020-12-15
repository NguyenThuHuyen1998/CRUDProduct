package com.example.crud;


/*
    created by HuyenNgTn on 09/12/2020
*/

import com.example.crud.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication

public class CrudApplication  implements CommandLineRunner {

    @Resource
    FilesStorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(CrudApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}