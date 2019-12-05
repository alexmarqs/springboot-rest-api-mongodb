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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests to campaigns controller.
 */
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

    private static final String ENDPOINT_CAMPAIGNS = "/campaigns";

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
    public void shouldReturnCampaignsPage_StatusOK() throws Exception {
        when(campaignsService.getAllCampaigns(any())).thenReturn(campaignPage);

        MvcResult mvcResult = mockMvc.perform(get(ENDPOINT_CAMPAIGNS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value("1"))
                .andReturn();

        assertThat(objectMapper.writeValueAsString(campaignPage))
                .isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldCreateNewCampaign_StatusCREATED() throws Exception {
        Campaign campaign = campaignPage.getContent().get(0);
        when(sequenceGeneratorService.generateSequence(any())).thenReturn(campaign.getId());
        when(campaignsService.createCampaign(any())).thenReturn(campaign);

        MvcResult mvcResult = mockMvc.perform(post(ENDPOINT_CAMPAIGNS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campaign)))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(objectMapper.writeValueAsString(campaign))
                .isEqualToIgnoringWhitespace(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotCreateNewCampaign_StatusBADREQUEST() throws Exception {
        Campaign campaign = campaignPage.getContent().get(0);
        campaign.setName(null);

        MvcResult mvcResult = mockMvc.perform(post(ENDPOINT_CAMPAIGNS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campaign)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()
                .contains("Name is mandatory"));
    }

    // TODO create other unit tests!
}