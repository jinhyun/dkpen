package com.dkpen.eapproval.controller;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.EappApproveDTO;
import com.dkpen.eapproval.dto.EappArchivePaperDTO;
import com.dkpen.eapproval.dto.EappPaperDTO;
import com.dkpen.eapproval.dto.UserDTO;
import com.dkpen.eapproval.service.EappApproveService;
import com.dkpen.user.domain.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        long paperUid = approveService.registerPaper(eappPaperDTO);
        String result;

        if (paperUid > 0) {
            result = "결재문서를 작성하였습니다.";
        } else {
            result = "실패하였습니다.";
        }

        model.addAttribute("message", result);
        model.addAttribute("eappPaperDTO", new EappPaperDTO());

        return "eapproval/eappResultMessage";
    }

    @RequestMapping(value = "/line/userList", method = RequestMethod.GET)
    public String getLineUserList(Model model) {
        User loginUser = CurrentUser.getCurrentUser();
        UserDTO loginUserDTO = approveService.getUser(loginUser.getUid());
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
        User loginUser = CurrentUser.getCurrentUser();
        UserDTO loginUserDTO = approveService.getUser(loginUser.getUid());
        List<EappPaperDTO> waitPaperDTOList = approveService.getWaitPaperList(loginUserDTO);    //TODO: resultPaperDTOList

        model.addAttribute("waitPaperDTOList", waitPaperDTOList);   //TODO: paperDTOList
        model.addAttribute("eappPaperDTO", new EappPaperDTO());

        return "eapproval/eappPaperWaitList";
    }

    /**
     * 결재 진행
     * @param model
     * @return
     */
    @RequestMapping(value = "/paper/progressList", method = RequestMethod.GET)
    public String viewProgressList(Model model) {
        User loginUser = CurrentUser.getCurrentUser();
        UserDTO loginUserDTO = approveService.getUser(loginUser.getUid());

        List<EappPaperDTO> waitPaperDTOList = approveService.getWaitPaperList(loginUserDTO);
        List<EappPaperDTO> progressPaperDTOList = approveService.getProgressPaperList(loginUserDTO);
        List<EappArchivePaperDTO> archivePaperDTOList = approveService.getArchivePaperList(loginUserDTO);

        model.addAttribute("waitPaperDTOList", waitPaperDTOList);
        model.addAttribute("progressPaperDTOList", progressPaperDTOList);
        model.addAttribute("archivePaperDTOList", archivePaperDTOList);
        model.addAttribute("eappPaperDTO", new EappPaperDTO());

        return "eapproval/eappPaperProgressList";
    }

    @RequestMapping(value = "/paper/view", method = RequestMethod.POST)
    public String viewPaper(@ModelAttribute EappPaperDTO eappPaperDTO, Model model) {
        User loginUser = CurrentUser.getCurrentUser();
        UserDTO loginUserDTO = approveService.getUser(loginUser.getUid());
        EappPaperDTO resultPaperDTO = approveService.getViewPaper(eappPaperDTO);
        model.addAttribute("paperDTO", resultPaperDTO);
        model.addAttribute("eappApproveDTO", new EappApproveDTO());
        model.addAttribute("moduleName", eappPaperDTO.getModuleName());

        if (eappPaperDTO.getWindowType().equals(EappPaperDTO.WINDOW_TYPE_MODAL)){
            return "eapproval/eappPaperForm :: resultsList";
        } else {
            return "eapproval/eappPaperForm";
        }
    }

    // TODO: 결재처리 "/paper/approve"
    @RequestMapping(value = "/paper/approve", method = RequestMethod.POST)
    public String approvePaper(@ModelAttribute EappApproveDTO eappApproveDTO, Model model) {
        User loginUser = CurrentUser.getCurrentUser();
        UserDTO loginUserDTO = approveService.getUser(loginUser.getUid());
        approveService.approvePaper(eappApproveDTO, loginUserDTO);   // TODO: exception / message 보완


        List<EappPaperDTO> waitPaperDTOList = approveService.getWaitPaperList(loginUserDTO);    //TODO: resultPaperDTOList
        model.addAttribute("waitPaperDTOList", waitPaperDTOList);   //TODO: paperDTOList
        model.addAttribute("eappPaperDTO", new EappPaperDTO());
        return "redirect:/eapp/paper/progressList";
    }

    // TODO: 결재문서 수정 "/paper/edit"

    /**
     * 저장소 결재완료 문서 조회
     * @param model
     * @return
     */
    @RequestMapping(value = "/archive/list", method = RequestMethod.GET)
    public String viewArchiveList(Model model) {
        UserDTO loginUserDTO = CurrentUser.getCurrentUserDTO();
        List<EappArchivePaperDTO> archivePaperDTOList = approveService.getArchivePaperList(loginUserDTO);

        model.addAttribute("waitPaperDTOList", archivePaperDTOList);    // TODO: eappPaperWaitList와 통합하거나 다른 방법으로 변경
        model.addAttribute("eappPaperDTO", new EappPaperDTO());
        model.addAttribute("moduleName", "archive");    // TODO: enum으로 변경

        return "eapproval/eappArchiveList";
    }
}
