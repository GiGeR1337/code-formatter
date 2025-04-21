package com.example.tpo7.Controllers;

import com.example.tpo7.Models.Code;
import com.example.tpo7.Services.CodeFormatterService;
import com.example.tpo7.Services.CodeService;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CodeController {
    private final CodeFormatterService codeFormatterService;
    private final CodeService codeService;

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
            model.addAttribute("error", "Cannot format code, " + e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/getCode/{id}")
    public String getFormattedCode(@PathVariable String id, Model model) {
        Code code = codeService.get(id);
        if (code == null) {
            model.addAttribute("error", "Code not found or expired");
            return "error";
        }

        model.addAttribute("getCode", code.getFormattedCode());
        return "getCode";
    }
}
