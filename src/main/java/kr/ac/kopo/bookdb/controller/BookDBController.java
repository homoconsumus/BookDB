package kr.ac.kopo.bookdb.controller;

import kr.ac.kopo.bookdb.dto.BookDBDTO;
import kr.ac.kopo.bookdb.dto.PageRequestDTO;
import kr.ac.kopo.bookdb.service.BookDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/bookdb")
@Log4j2
@RequiredArgsConstructor // 자동 주입
public class BookDBController {

    private final BookDBService service; // service는 final로 선언

    @GetMapping("/")
    public String index(){
        return "redirect:/bookdb/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list..................." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(Model model){
        log.info("register get.........");
    }

    @PostMapping("/register")
    public String registerPost(BookDBDTO dto, @RequestParam("releasedate") LocalDate releaseDate, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);

        dto.setReleasedate(dto.getReleasedate());

        // 새로 추가된 entity번호
        Long bno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/bookdb/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("bno: " + bno + "조회 수행");
        BookDBDTO dto = service.read(bno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){
        log.info("bno: " + bno + "삭제 수행");
        service.remove(bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/bookdb/list";
    }

    @PostMapping("/modify")
    public String modify(long bno, BookDBDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("bno: " + bno + "수정 수행");
        service.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("bno", dto.getBno());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword_title", requestDTO.getKeyword_title());
        return "redirect:/bookdb/read";
    }
}
