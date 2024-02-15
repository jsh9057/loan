package dev.be.loan.service;

import dev.be.loan.dto.ApplicationDTO;
import dev.be.loan.dto.ApplicationDTO.AcceptTerms;
import dev.be.loan.dto.ApplicationDTO.Request;
import dev.be.loan.dto.ApplicationDTO.Response;

public interface ApplicationService {

    Response create(Request request);

    Response get(Long applicationId);

    Response update(Long applicationId, Request request);

    void delete(Long applicationId);

    Boolean acceptTerms(Long applicationId, AcceptTerms request);

    Response contract(Long applicationId);
}
