package dev.be.loan.service;

import dev.be.loan.domain.Terms;
import dev.be.loan.dto.TermsDTO;
import dev.be.loan.dto.TermsDTO.Request;
import dev.be.loan.dto.TermsDTO.Response;
import dev.be.loan.repository.TermsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TermsServiceTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void 새로운_TermsEntity로_생성요청을_보내면_Response객체를_반환한다() {
        Terms entity = Terms.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://test/test")
                .build();

        Request request = Request.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://test/test")
                .build();

        when(termsRepository.save(ArgumentMatchers.any(Terms.class))).thenReturn(entity);

        Response actual = termsService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());
    }

    @Test
    void 모든_약관을_요청했을때_모든TermsEntity를_리스트로반환한다() {
        Terms entity1 = Terms.builder()
                .name("대출 약관1")
                .termsDetailUrl("https://test-teat1/test1")
                .build();

        Terms entity2 = Terms.builder()
                .name("대출 약관1")
                .termsDetailUrl("https://test-teat1/test1")
                .build();

        List<Terms> list = new ArrayList<>(Arrays.asList(entity1, entity2));

        when(termsRepository.findAll()).thenReturn(list);

        List<Response> actual = termsService.getAll();

        assertThat(actual.size()).isSameAs(list.size());
    }
}

