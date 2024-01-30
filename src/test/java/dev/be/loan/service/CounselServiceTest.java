package dev.be.loan.service;

import dev.be.loan.domain.Counsel;
import dev.be.loan.dto.CounselDTO.Request;
import dev.be.loan.dto.CounselDTO.Response;
import dev.be.loan.exception.BaseException;
import dev.be.loan.exception.ResultType;
import dev.be.loan.repository.CounselRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;
    @Spy
    private ModelMapper modelMapper;

    @Test
    void 상담요청이_왔을때_CounselEntityResponse를_리턴해야합니다() {
        Counsel entity = Counsel.builder()
                .name("Member 1")
                .cellPhone("010-1111-1234")
                .email("test@email.com")
                .memo("대출 상담 요청")
                .zipCode("12345")
                .address("인천시 ㅇㅇ구 ㅂㅂ동")
                .addressDetail("ㅇㅇㅇ동 ㅅㅅㅅ호")
                .build();

        Request request = Request.builder()
                .name("Member 1")
                .cellPhone("010-1111-1234")
                .email("test@email.com")
                .memo("대출 상담 요청")
                .zipCode("12345")
                .address("인천시 ㅇㅇ구 ㅂㅂ동")
                .addressDetail("ㅇㅇㅇ동 ㅅㅅㅅ호")
                .build();
        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @Test
    void counselId로_조회요청했을떄_counselEntityResponse를_리턴합니다() {
        Long findId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.get(findId);

        assertThat(actual.getCounselId()).isSameAs(findId);
    }

    @Test
    void 없는counselId로_조회요청했을때_에외를_반환합니다() {
        Long findId = 2L;

        when(counselRepository.findById(findId)).thenThrow(new BaseException(ResultType.SYSTEM_ERROR));

        Assertions.assertThrows(BaseException.class, () -> counselService.get(findId));
    }

    @Test
    void counselId와Request로_수정요청했을떄_counselEntityResponse를_리턴합니다() {
        Long updateId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .name("origin")
                .build();

        Request request = Request.builder()
                .name("update")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        when(counselRepository.findById(updateId)).thenReturn(Optional.ofNullable(entity));

        Response actual = counselService.update(updateId, request);

        assertThat(actual.getCounselId()).isSameAs(updateId);
        assertThat(actual.getName()).isSameAs(request.getName());
    }

    @Test
    void counselId로_삭제요청했을떄_CounselEntity를_삭제합니다() {
        Long deleteId = 1L;

        Counsel entity = Counsel.builder()
                .counselId(1L)
                .build();

        when(counselRepository.findById(deleteId)).thenReturn(Optional.ofNullable(entity));
        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        counselService.delete(deleteId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }
}
