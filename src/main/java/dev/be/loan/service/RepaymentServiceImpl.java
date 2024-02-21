package dev.be.loan.service;

import dev.be.loan.domain.Application;
import dev.be.loan.domain.Entry;
import dev.be.loan.domain.Repayment;
import dev.be.loan.dto.BalanceDTO;
import dev.be.loan.dto.BalanceDTO.RepaymentRequest;
import dev.be.loan.dto.BalanceDTO.RepaymentRequest.RepaymentType;
import dev.be.loan.dto.RepaymentDTO.Request;
import dev.be.loan.dto.RepaymentDTO.Response;
import dev.be.loan.exception.BaseException;
import dev.be.loan.exception.ResultType;
import dev.be.loan.repository.ApplicationRepository;
import dev.be.loan.repository.EntryRepository;
import dev.be.loan.repository.RepaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;

    private final ApplicationRepository applicationRepository;

    private final EntryRepository entryRepository;

    private final BalanceService balanceService;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {

        if (!isRepayableApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Repayment repayment = modelMapper.map(request, Repayment.class);
        repayment.setApplicationId(applicationId);

        repaymentRepository.save(repayment);

        BalanceDTO.Response updatedBalance = balanceService.repaymentUpdate(applicationId,
                RepaymentRequest.builder()
                        .repaymentAmount(request.getRepaymentAmount())
                        .type(RepaymentType.REMOVE)
                        .build());

        Response response = modelMapper.map(repayment, Response.class);
        response.setBalance(updatedBalance.getBalance());

        return response;
    }

    private boolean isRepayableApplication(Long applicationId) {
        Optional<Application> existedApplication = applicationRepository.findById(applicationId);

        if(existedApplication.isEmpty()) {
            return false;
        }

        if(existedApplication.get().getContractedAt() == null) {
            return false;
        }

        Optional<Entry> existedEntry = entryRepository.findByApplicationId(applicationId);
        return existedEntry.isPresent();
    }
}
