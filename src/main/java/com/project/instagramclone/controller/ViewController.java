package com.project.instagramclone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/user/login")
    public String loginView() {
        return "user/login";
    }

    @GetMapping("/user/register")
    public String registerView() {
        return "user/register";
    }

    @GetMapping("/board/list")
    public String listView(Model model) {
        return "board/list";
    }

    @GetMapping("/board/detail" )
    public String detailView(int board_no, Model model) {
        return  "board/detail";
    }

    @GetMapping("/board/write")
    public String writeView() {
        return "board/write";
    }

    @GetMapping("/board/edit" )
    public String editView(int board_no, Model model) {
        return "board/edit";
    }
}