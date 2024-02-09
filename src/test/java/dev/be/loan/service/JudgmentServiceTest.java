package dev.be.loan.service;

import dev.be.loan.domain.Application;
import dev.be.loan.domain.Judgment;
import dev.be.loan.dto.JudgmentDTO.Request;
import dev.be.loan.dto.JudgmentDTO.Response;
import dev.be.loan.repository.ApplicationRepository;
import dev.be.loan.repository.JudgmentRepository;
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
public class JudgmentServiceTest {

    @InjectMocks
    private JudgmentServiceImpl judgmentService;

    @Mock
    private JudgmentRepository judgmentRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void JudgmentRequest로_대출심사_요청을하면_JudgmentEntity를_저장하고_JudgmentReponse를_반환한다() {
        Judgment judgment = Judgment.builder()
                .applicationId(1L)
                .name("test")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        Request request = Request.builder()
                .applicationId(1L)
                .name("test")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();



        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));

        when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(judgment);

        Response actual = judgmentService.create(request);

        assertThat(actual.getName()).isSameAs(judgment.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgment.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());
    }

    @Test
    void JudgmentId로_요청했을때_존재하는경우_ResponseOfJudgmentEntity를_반환한다() {
        Judgment entity = Judgment.builder()
                .judgmentId(1L)
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));

        Response actual = judgmentService.get(1L);

        assertThat(actual.getJudgmentId()).isSameAs(1L);
    }

    @Test
    void ApplicationId로_요청했을때_존재하는경우_ResponseOfJudgmentEntity를_반환한다() {
        Judgment judgmentEntity = Judgment.builder()
                .judgmentId(1L)
                .build();

        Application applicationEntity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(applicationEntity));
        when(judgmentRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(judgmentEntity));

        Response actual = judgmentService.getJudgmentOfApplication(1L);

        assertThat(actual.getJudgmentId()).isSameAs(1L);
    }

    @Test
    void 존재하는Judgment정보로_수정요청이왔을때_수정된_JudgmentEntity를_반환한다() {

        Judgment entity = Judgment.builder()
                .judgmentId(1L)
                .name("test")
                .approvalAmount(BigDecimal.valueOf(1000000))
                .build();

        Request request = Request.builder()
                .name("테스트")
                .approvalAmount(BigDecimal.valueOf(10000000))
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
        when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(entity);

        Response actual = judgmentService.update(1L, request);

        assertThat(actual.getJudgmentId()).isSameAs(1L);
        assertThat(actual.getName()).isSameAs(request.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
    }

    @Test
    void 존재하는Judgment정보로_삭제요청을하면_JudgmentEntity를_삭제한다() {
        Judgment entity = Judgment.builder()
                .judgmentId(1L)
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
        when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(entity);

        judgmentService.delete(1L);

        assertThat(entity.getIsDeleted()).isTrue();
    }
}
