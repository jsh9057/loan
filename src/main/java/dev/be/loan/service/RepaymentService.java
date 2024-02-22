package dev.be.loan.service;

import dev.be.loan.dto.RepaymentDTO.ListResponse;
import dev.be.loan.dto.RepaymentDTO.Request;
import dev.be.loan.dto.RepaymentDTO.Response;
import dev.be.loan.dto.RepaymentDTO.UpdateResponse;

import java.util.List;

public interface RepaymentService {

    Response create(Long applicationId, Request request);

    List<ListResponse> get(Long applicationId);

    UpdateResponse update(Long repaymentId, Request request);

    void delete(Long repaymentId);
}
