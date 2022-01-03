package com.team.app.backend.rest;


import com.team.app.backend.persistance.model.DefaultQuest;
import com.team.app.backend.persistance.model.Option;
import com.team.app.backend.persistance.model.SeqOption;
import com.team.app.backend.service.OptionService;
import com.team.app.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class OptionsController {

    @Autowired
    OptionService optionService;

    @GetMapping("/options/{id}")
    public List<Option> getOptions(@PathVariable("id") long id) {
        return optionService.getOptions(id);
    }

    @GetMapping("/default_options/{id}")
    public List<DefaultQuest> getDefOptions(@PathVariable("id") long id) {
        return optionService.getDefaultOption(id);
    }

    @GetMapping("/sequence_options/{id}")
    public List<SeqOption> getSeqOptions(@PathVariable("id") long id) {
        return optionService.getSeqOption(id);
    }

}
