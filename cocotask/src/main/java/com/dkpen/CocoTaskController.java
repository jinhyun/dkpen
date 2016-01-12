package com.dkpen;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CocoTaskController {

    @RequestMapping(value = "/")
    public String cocotask() {
        return "cocotask";
    }

    @RequestMapping(value = "/view/footer")
    public String viewFooter() {
        return "fragments/footer";
    }
}
