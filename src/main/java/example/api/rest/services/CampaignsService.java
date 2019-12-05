package example.api.rest.services;

import example.api.rest.models.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Campaigns service interface.
 */
public interface CampaignsService {

   Page<Campaign> getAllCampaigns(Pageable pageable);

   Campaign getCampaignById(String campaignId);

   Campaign createCampaign(Campaign campaign);

   Campaign updateCampaign(Campaign campaign);

   void deleteCampaignById(String campaignId);

}
