package dev.be.loan.service;

import dev.be.loan.dto.CounselDTO.Request;
import dev.be.loan.dto.CounselDTO.Response;

public interface CounselService {

    Response create(Request request);

    Response get(Long counselId);

    Response update(Long counselId, Request request);

    void delete(Long counselId);
}
