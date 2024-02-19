package dev.be.loan.service;

import dev.be.loan.domain.Balance;
import dev.be.loan.dto.BalanceDTO.Request;
import dev.be.loan.dto.BalanceDTO.Response;
import dev.be.loan.dto.BalanceDTO.UpdateRequest;
import dev.be.loan.exception.BaseException;
import dev.be.loan.exception.ResultType;
import dev.be.loan.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService{

    private final BalanceRepository balanceRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        if (balanceRepository.findByApplicationId(applicationId).isPresent()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Balance balance = modelMapper.map(request, Balance.class);

        BigDecimal entryAmount = request.getEntryAmount();
        balance.setApplicationId(applicationId);
        balance.setBalance(entryAmount);

        balanceRepository.save(balance);

        return modelMapper.map(balance, Response.class);
    }

    @Override
    public Response update(Long applicationId, UpdateRequest request) {
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
        BigDecimal afterEntryAmount = request.getAfterEntryAmount();
        BigDecimal updatedBalance = balance.getBalance();

        updatedBalance = updatedBalance.subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.setBalance(updatedBalance);

        Balance updated = balanceRepository.save(balance);
        return modelMapper.map(updated, Response.class);
    }
}
