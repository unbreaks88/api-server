package com.kakaopay.controller;

import com.kakaopay.dto.response.MunicipalityInfoResponse;
import com.kakaopay.service.MunicipalityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MunicipalityControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MunicipalityController municipalityController;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(municipalityController).build();
    }

    @Autowired
    private MunicipalityService municipalityService;


    @Test
    public void uploadFile() {
    }

    @Test
    public void getMunicipalityList() throws Exception {
        MunicipalityInfoResponse expectedResponse = new MunicipalityInfoResponse("강원도", "강원도 소재 중소기업으로서 강원도지사가 추천한 자", "운전", "8억원 이내", "3%~12%", "강원도", "춘천지점", "강원도 소재 영업점");
        given(this.municipalityController.getMunicipalityInfo("제주도")).willReturn(expectedResponse);

        mockMvc.perform(get("/api/municipality/제주도"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getMunicipalityInfo() {
    }

    @Test
    public void updateMunicipality() {
    }
}