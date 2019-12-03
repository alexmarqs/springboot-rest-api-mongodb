package example.api.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.api.rest.models.Campaign;
import example.api.rest.services.CampaignsServiceImpl;
import example.api.rest.services.SequenceGeneratorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CampaignsController.class)
public class CampaignsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CampaignsServiceImpl campaignsService;

    @MockBean
    private SequenceGeneratorService sequenceGeneratorService;

    private Page<Campaign> campaignPage;

    @Before
    public void setupBeforeEachTest() {
        List<Campaign> campaignList = new ArrayList<>();
        Campaign campaign = new Campaign();
        campaign.setId("1");
        campaign.setName("test1");
        campaign.setDescription("desc1");
        campaign.setUrl("http://test1.test1");
        campaign.setEmail("test1@test1");
        campaignList.add(campaign);

        campaignPage = new PageImpl<>(campaignList, PageRequest.of(0, 2), campaignList.size());
    }

    @Test
    public void shouldReturnCampaignsPage() throws Exception {
        when(campaignsService.getAllCampaigns(any())).thenReturn(campaignPage);

        MvcResult mvcResult1 = mockMvc.perform(get("/campaigns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("1"))
                .andReturn();

        assertThat(objectMapper.writeValueAsString(campaignPage))
                .isEqualToIgnoringWhitespace(mvcResult1.getResponse().getContentAsString());
    }

    // TODO create others unit test cases
}