package dev.be.loan.service;

import dev.be.loan.dto.JudgmentDTO.Request;
import dev.be.loan.dto.JudgmentDTO.Response;

public interface JudgmentService {

    Response create(Request request);

    Response get(Long judgmentId);

    Response getJudgmentOfApplication(Long applicationId);

    Response update(Long judgmentId, Request request);

    void delete(Long judgmentId);
}
