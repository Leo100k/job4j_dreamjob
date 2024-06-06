package dreamjob.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dreamjob.repository.MemoryCandidateRepository;
import dreamjob.repository.CandidateRepository;


@Controller
@RequestMapping("/candidates") /* Работать с кандидатами будем по URI /vacancies/** */
public class CandidateController {

    private final CandidateRepository candidateRepository = MemoryCandidateRepository.getInstance();

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/list";
    }
}