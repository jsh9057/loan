package dev.be.loan.service;

import dev.be.loan.dto.CounselDTO.Request;
import dev.be.loan.dto.CounselDTO.Response;

public interface CounselService {

    Response create(Request request);
}
