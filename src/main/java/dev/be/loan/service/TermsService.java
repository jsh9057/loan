package dev.be.loan.service;

import dev.be.loan.dto.TermsDTO.Request;
import dev.be.loan.dto.TermsDTO.Response;

import java.util.List;

public interface TermsService {

    Response create(Request request);

    List<Response> getAll();
}
