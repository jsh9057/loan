package dev.be.loan.service;

import dev.be.loan.domain.Counsel;
import dev.be.loan.dto.CounselDTO.Request;
import dev.be.loan.dto.CounselDTO.Response;
import dev.be.loan.exception.BaseException;
import dev.be.loan.exception.ResultType;
import dev.be.loan.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService{

    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    public Response create(Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);

        return modelMapper.map(created, Response.class);
    }

    @Override
    public Response get(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(counsel, Response.class);
    }

    @Override
    public Response update(Long counselId, Request request) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        counsel.setName(request.getName());
        counsel.setCellPhone(request.getCellPhone());
        counsel.setEmail(request.getEmail());
        counsel.setMemo(request.getMemo());
        counsel.setAddress(request.getAddress());
        counsel.setAddressDetail(request.getAddressDetail());
        counsel.setZipCode(request.getZipCode());

        counselRepository.save(counsel);

        return modelMapper.map(counsel, Response.class);
    }

    @Override
    public void delete(Long counselId) {
        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        counsel.setIsDeleted(true);

        counselRepository.save(counsel);
    }
}
