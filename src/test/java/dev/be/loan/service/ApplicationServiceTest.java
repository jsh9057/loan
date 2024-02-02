package dev.be.loan.service;

import dev.be.loan.domain.AcceptTerms;
import dev.be.loan.domain.Application;
import dev.be.loan.domain.Terms;
import dev.be.loan.dto.ApplicationDTO;
import dev.be.loan.dto.ApplicationDTO.Request;
import dev.be.loan.dto.ApplicationDTO.Response;
import dev.be.loan.exception.BaseException;
import dev.be.loan.repository.AcceptTermsRepository;
import dev.be.loan.repository.ApplicationRepository;
import dev.be.loan.repository.TermsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private TermsRepository termsRepository;

    @Mock
    private AcceptTermsRepository acceptTermsRepository;

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

    @Test
    void Application의_AcceptTerms를_동의하면_AcceptTerms를_추가한다() {
        Terms entity1 = Terms.builder()
                .termsId(1L)
                .name("약관 1")
                .termsDetailUrl("https://test.test")
                .build();

        Terms entity2 = Terms.builder()
                .termsId(2L)
                .name("약관 2")
                .termsDetailUrl("https://test2.test2")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L, 2L);

        ApplicationDTO.AcceptTerms request = ApplicationDTO.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );

        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entity1, entity2));
        when(acceptTermsRepository.save(ArgumentMatchers.any(AcceptTerms.class))).thenReturn(AcceptTerms.builder().build());

        Boolean actual = applicationService.acceptTerms(findId, request);
        assertThat(actual).isTrue();
    }

    @Test
    void Application의_AcceptTerms를_동의하지_않으면_예외를_던진다() {
        Terms entity1 = Terms.builder()
                .termsId(1L)
                .name("약관 1")
                .termsDetailUrl("https://test.test")
                .build();

        Terms entity2 = Terms.builder()
                .termsId(2L)
                .name("약관 2")
                .termsDetailUrl("https://test2.test2")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L);

        ApplicationDTO.AcceptTerms request = ApplicationDTO.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );

        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entity1, entity2));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(findId, request));
    }

    @Test
    void 존재하지않는_Application의_AcceptTerms를_동의하면_예외를_던진다() {
        Terms entity1 = Terms.builder()
                .termsId(1L)
                .name("약관 1")
                .termsDetailUrl("https://test.test")
                .build();

        Terms entity2 = Terms.builder()
                .termsId(2L)
                .name("약관 2")
                .termsDetailUrl("https://test2.test2")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L, 3L);

        ApplicationDTO.AcceptTerms request = ApplicationDTO.AcceptTerms.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );

        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entity1, entity2));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(findId, request));
    }
}
