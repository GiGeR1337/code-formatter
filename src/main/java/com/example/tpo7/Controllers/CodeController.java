package com.example.tpo7.Controllers;

import com.example.tpo7.Services.CodeFormatterService;
import com.example.tpo7.Services.CodeService;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CodeController {
    private CodeFormatterService codeFormatterService;
    private CodeService codeService;

    public CodeController(CodeFormatterService codeFormatterService, CodeService codeService) {
        this.codeFormatterService = codeFormatterService;
        this.codeService = codeService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/format")
    public String formatCode(@RequestParam("code") String code,
                             @RequestParam("id") String id,
                             @RequestParam("durationSeconds") long durationSeconds,
                             Model model){

        try {
            String formattedCode = codeFormatterService.format(code);
            codeService.save(id, formattedCode, durationSeconds);
            model.addAttribute("original", code);
            model.addAttribute("formatted", formattedCode);

            return "index";
        } catch (FormatterException e) {
            model.addAttribute("error", "Could not format code. Make sure it's a complete and valid Java class. Details: " + e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "Something went wrong: " + e.getMessage());
            return "error";
        }
    }
}
