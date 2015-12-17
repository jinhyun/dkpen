package com.dkpen.eapproval.controller;

import com.dkpen.eapproval.dto.EappPaperDTO;
import com.dkpen.eapproval.dto.UserDTO;
import com.dkpen.eapproval.service.EappApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

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

    @RequestMapping(value = "/line/userList", method = RequestMethod.GET)
    public String getLineUserList(Model model) {
        UserDTO loginUserDTO = approveService.getUser(3);     // olaf
        List <UserDTO> userDTOList = approveService.getUserListExceptLoginUser(loginUserDTO);

        model.addAttribute("userDTOList", userDTOList);
        model.addAttribute("loginUserDTO", loginUserDTO);

        return "eapproval/eappUserList :: resultsList";
    }
}
