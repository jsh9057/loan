package dev.be.loan.controller;

import dev.be.loan.dto.ResponseDTO;
import dev.be.loan.dto.TermsDTO;
import dev.be.loan.dto.TermsDTO.Request;
import dev.be.loan.dto.TermsDTO.Response;
import dev.be.loan.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/terms")
public class TermsController extends AbstractController {

    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(termsService.create(request));
    }

    @GetMapping
    public ResponseDTO<List<Response>> getAll() {
        return ok(termsService.getAll());
    }
}
