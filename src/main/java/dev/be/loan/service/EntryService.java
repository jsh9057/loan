package dev.be.loan.service;

import dev.be.loan.dto.EntryDTO.Request;
import dev.be.loan.dto.EntryDTO.Response;

public interface EntryService {

    Response create(Long applicationId, Request request);
}
