package example.api.rest.repositories;

import example.api.rest.models.Campaign;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CampaignsRepositoryTest {

    @Autowired
    CampaignsRepository campaignsRepository;
    
    @Before
    public void setupBeforeEachTest() throws Exception {
        List<Campaign> campaignList = new ArrayList<>();
        Campaign campaign1 = new Campaign();
        campaign1.setId("1");
        campaign1.setName("test1");
        campaign1.setDescription("desc1");
        campaign1.setUrl("http://test1.test1");
        campaign1.setEmail("test1@test1");
        campaignList.add(campaign1);

        Campaign campaign2 = new Campaign();
        campaign2.setId("2");
        campaign1.setName("test2");
        campaign1.setDescription("desc2");
        campaign1.setUrl("http://test2.test2");
        campaign1.setEmail("test2@test2");
        campaignList.add(campaign2);

        campaignsRepository.saveAll(campaignList);
    }

    @Test
    public void shouldFindAll() {
        List<Campaign> campaignListAll = campaignsRepository.findAll();

        assertThat(campaignListAll).isNotEmpty();
        assertThat(campaignListAll.size()).isEqualTo(2);

        Page<Campaign> campaignPage = campaignsRepository.findAll(PageRequest.of(0, 1));

        assertThat(campaignPage.getContent()).isNotEmpty();
        assertThat(campaignPage.getContent().size()).isEqualTo(1);
    }

    @Test
    public void shouldFindById() {
        Optional<Campaign> campaign = campaignsRepository.findById("1");

        assertThat(campaign.isPresent());
        assertThat(campaign.get().getId()).isEqualTo("1");

        campaign = campaignsRepository.findById("3");

        assertThat(!campaign.isPresent());
    }

    @Test
    public void shouldDeleteById() {
        campaignsRepository.deleteById("2");

        assertThat(!campaignsRepository.existsById("2"));
    }

    // TODO create other tests if needed!
}