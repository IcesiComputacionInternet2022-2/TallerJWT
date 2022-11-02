package com.icesi.edu.users.api;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface ViewAPI {

    @GetMapping("/view")
    public String getServerTime(Model model);

}
