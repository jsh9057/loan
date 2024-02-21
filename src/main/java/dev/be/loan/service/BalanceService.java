package dev.be.loan.service;

import dev.be.loan.dto.BalanceDTO.RepaymentRequest;
import dev.be.loan.dto.BalanceDTO.Request;
import dev.be.loan.dto.BalanceDTO.Response;
import dev.be.loan.dto.BalanceDTO.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, Request request);

    Response update(Long applicationId, UpdateRequest request);

    Response repaymentUpdate(Long applicationId, RepaymentRequest request);
}
