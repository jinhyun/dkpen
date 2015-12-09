package com.dkpen.eapproval;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/eapp")
public class EappController {

    @RequestMapping(value = "")
    public String eappMain() {
        return "/eapproval/eapp";
    }
}
