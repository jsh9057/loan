package dev.be.loan.service;

import dev.be.loan.dto.EntryDTO.Request;
import dev.be.loan.dto.EntryDTO.Response;
import dev.be.loan.dto.EntryDTO.UpdateResponse;

public interface EntryService {

    Response create(Long applicationId, Request request);

    Response get(Long applicationId);

    UpdateResponse update(Long entryId, Request request);
}
