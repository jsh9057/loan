package dev.be.loan.controller;

import dev.be.loan.dto.EntryDTO.Request;
import dev.be.loan.dto.EntryDTO.Response;
import dev.be.loan.dto.ResponseDTO;
import dev.be.loan.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController {

    private final EntryService entryService;

    @PostMapping("{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }
}
