package dev.be.loan.service;

import dev.be.loan.domain.Counsel;
import dev.be.loan.dto.CounselDTO.Request;
import dev.be.loan.dto.CounselDTO.Response;
import dev.be.loan.repository.CounselRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
}
