package dev.be.loan.service;

import dev.be.loan.domain.Application;
import dev.be.loan.dto.ApplicationDTO;
import dev.be.loan.dto.ApplicationDTO.Request;
import dev.be.loan.dto.ApplicationDTO.Response;
import dev.be.loan.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void 새로운_대출신청_요청이왔을때_ApplicationEntity를_반환한다() {
        Application entity = Application.builder()
                .name("test")
                .cellPhone("010-1111-2222")
                .email("test@email.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        Request request = Request.builder()
                .name("test")
                .cellPhone("010-1111-2222")
                .email("test@email.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();


        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

        Response actual = applicationService.create(request);

        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @Test
    void 존재하는_ApplicationId로_조회했을때_ApplicationEntity를_반환한다(){
        Long findId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = applicationService.get(findId);

        assertThat(actual.getApplicationId()).isSameAs(findId);
    }

    @Test
    void 대출신청_수정요청이왔을때_수정된ApplicationEntity를_반환한다() {
        Long findId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        Request request = Request.builder()
                .hopeAmount(BigDecimal.valueOf(5000000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = applicationService.update(findId, request);

        assertThat(actual.getApplicationId()).isSameAs(findId);
        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
    }

    @Test
    void 존재하는_ApplicationId로_삭제요청을했을때_ApplicationEntity를_삭제한다(){
        Long targetId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        applicationService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }

}
