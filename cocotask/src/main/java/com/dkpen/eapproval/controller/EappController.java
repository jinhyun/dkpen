package com.dkpen.eapproval.controller;

import com.dkpen.eapproval.dto.EappPaperDTO;
import com.dkpen.eapproval.service.EappApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/eapp")
public class EappController {
    @Autowired
    EappApproveService approveService;

    @RequestMapping(value = "")
    public String eappMain() {
        return "/eapproval/eapp";
    }

    @RequestMapping(value = "/paper/createForm", method = RequestMethod.GET)
    public String registerPaperForm(Model model) {
        model.addAttribute("eappPaperDTO", new EappPaperDTO());
        return "eapproval/eappPaperCreateForm";
    }

    @RequestMapping(value = "/paper/create", method = RequestMethod.POST)
    public String registerPaper(@ModelAttribute EappPaperDTO eappPaperDTO, Model model) {
        String result = approveService.registerPaper(eappPaperDTO);

        model.addAttribute("message", result);
        model.addAttribute("eappPaperDTO", new EappPaperDTO());

        return "eapproval/eappResultMessage";
    }
}
