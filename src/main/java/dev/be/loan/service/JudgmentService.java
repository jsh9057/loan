package dev.be.loan.service;

import dev.be.loan.dto.JudgmentDTO.Request;
import dev.be.loan.dto.JudgmentDTO.Response;

public interface JudgmentService {

    Response create(Request request);
}
