package com.example.tpo7.Services;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.springframework.stereotype.Service;

@Service
public class CodeFormatterService {
    public String format(String code) throws FormatterException {
        return new Formatter().formatSource(code);
    }
}
