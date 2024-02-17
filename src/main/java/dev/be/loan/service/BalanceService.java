package dev.be.loan.service;

import dev.be.loan.dto.BalanceDTO.Request;
import dev.be.loan.dto.BalanceDTO.Response;

public interface BalanceService {

    Response create(Long applicationId, Request request);
}
