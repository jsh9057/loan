package dev.be.loan.service;

import dev.be.loan.dto.RepaymentDTO.Request;
import dev.be.loan.dto.RepaymentDTO.Response;

public interface RepaymentService {

    Response create(Long applicationId, Request request);
}
