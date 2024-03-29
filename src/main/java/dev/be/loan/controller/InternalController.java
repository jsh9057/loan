package dev.be.loan.controller;

import dev.be.loan.dto.EntryDTO.Request;
import dev.be.loan.dto.EntryDTO.Response;
import dev.be.loan.dto.EntryDTO.UpdateResponse;
import dev.be.loan.dto.RepaymentDTO;
import dev.be.loan.dto.RepaymentDTO.ListResponse;
import dev.be.loan.dto.ResponseDTO;
import dev.be.loan.service.EntryService;
import dev.be.loan.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController {

    private final EntryService entryService;

    private final RepaymentService repaymentService;

    @PostMapping("/{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }

    @GetMapping("/{applicationId}/entries")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(entryService.get(applicationId));
    }

    @PutMapping("/entries/{entryId}")
    public ResponseDTO<UpdateResponse> update(@PathVariable Long entryId, @RequestBody Request request) {
        return ok(entryService.update(entryId,request));
    }

    @DeleteMapping("/entries/{entryId}")
    public ResponseDTO<Void> delete(@PathVariable Long entryId) {
        entryService.delete(entryId);
        return ok();
    }

    @PostMapping("/{applicationId}/repayments")
    public ResponseDTO<RepaymentDTO.Response> create(@PathVariable Long applicationId, @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.create(applicationId, request));
    }

    @GetMapping("/{applicationId}/repayments")
    public ResponseDTO<List<ListResponse>> getPayments(@PathVariable Long applicationId) {
        return ok(repaymentService.get(applicationId));
    }

    @PutMapping("/{repaymentId}/repayments")
    public ResponseDTO<RepaymentDTO.UpdateResponse> update(@PathVariable Long repaymentId,
                                                           @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.update(repaymentId, request));
    }

    @DeleteMapping("/repayments/{repaymentId}")
    public ResponseDTO<Void> deleteRepayment(@PathVariable Long repaymentId) {
        repaymentService.delete(repaymentId);
        return ok();
    }
}
