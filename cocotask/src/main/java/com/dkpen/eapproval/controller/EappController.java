package com.dkpen.eapproval.controller;

import com.dkpen.eapproval.dto.EappApproveDTO;
import com.dkpen.eapproval.dto.EappLineDTO;
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

    @RequestMapping(value = "/view/eappLeft", method = RequestMethod.GET)
    public String viewEappLeft() {
        return "eapproval/eappLeft";
    }

    @RequestMapping(value = "/paper/waitList", method = RequestMethod.GET)
    public String viewWaitList(Model model) {
        UserDTO loginUserDTO = approveService.getUser(1);     // anna
        List<EappPaperDTO> waitPaperDTOList = approveService.getWaitPaperList(loginUserDTO);    //TODO: resultPaperDTOList

        model.addAttribute("waitPaperDTOList", waitPaperDTOList);   //TODO: paperDTOList
        model.addAttribute("eappPaperDTO", new EappPaperDTO());

        return "eapproval/eappPaperWaitList";
    }

    @RequestMapping(value = "/paper/view", method = RequestMethod.POST)
    public String viewPaper(@ModelAttribute EappPaperDTO eappPaperDTO, Model model) {
        UserDTO loginUserDTO = approveService.getUser(1);     // anna
        EappPaperDTO resultPaperDTO = approveService.getViewPaper(eappPaperDTO);
        model.addAttribute("paperDTO", resultPaperDTO);
        model.addAttribute("eappApproveDTO", new EappApproveDTO());

        return "eapproval/eappPaperForm";
    }

    // TODO: 결재처리 "/paper/approve"
    @RequestMapping(value = "/paper/approve", method = RequestMethod.POST)
    public String approvePaper(@ModelAttribute EappApproveDTO eappApproveDTO, Model model) {
        UserDTO loginUserDTO = approveService.getUser(1);     // anna
        approveService.approvePaper(eappApproveDTO, loginUserDTO);   // TODO: exception / message 보완


        List<EappPaperDTO> waitPaperDTOList = approveService.getWaitPaperList(loginUserDTO);    //TODO: resultPaperDTOList
        model.addAttribute("waitPaperDTOList", waitPaperDTOList);   //TODO: paperDTOList
        model.addAttribute("eappPaperDTO", new EappPaperDTO());
        return "eapproval/eappPaperWaitList";
    }

    // TODO: 결재문서 수정 "/paper/edit"
}
