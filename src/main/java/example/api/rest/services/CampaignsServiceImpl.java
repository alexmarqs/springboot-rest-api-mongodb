package example.api.rest.services;

import example.api.rest.errors.ApiException;
import example.api.rest.errors.ErrorType;
import example.api.rest.models.Campaign;
import example.api.rest.repositories.CampaignsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CampaignsServiceImpl implements CampaignsService {

    private static final Logger LOGGER = LogManager.getLogger(CampaignsServiceImpl.class);

    @Autowired
    private CampaignsRepository campaignsRepositoryDAO;

    @Override
    public Page<Campaign> getAllCampaigns(Pageable pageable) {
        LOGGER.debug("Getting all campaigns for page size: " + pageable.getPageSize() +
                ", page number: " + pageable.getPageNumber()+ ", sort: " + pageable.getSort().toString());
        return campaignsRepositoryDAO.findAll(pageable);
    }

    @Override
    public Campaign getCampaignById(String campaignId) {
        LOGGER.debug("Getting the campaign by id: " + campaignId);
        return campaignsRepositoryDAO.findById(campaignId).orElseThrow(() ->  {
           LOGGER.warn("Campaign id: " + campaignId + " not found!");
           return new ApiException(ErrorType.CAMPAIGN_NOT_FOUND, campaignId);
       });
    }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        LOGGER.debug("Creating new campaign with id: " + campaign.getId());
        return campaignsRepositoryDAO.save(campaign);
    }

    @Override
    public Campaign updateCampaign(Campaign campaign) {
        String campaignId = campaign.getId();
        if (campaignsRepositoryDAO.existsById(campaignId)) {
            LOGGER.debug("Updating campaign with id: " + campaignId);
            return campaignsRepositoryDAO.save(campaign);
        } else {
            LOGGER.warn("Campaign id: " + campaignId + " not found!");
            throw new ApiException(ErrorType.CAMPAIGN_NOT_FOUND, campaignId);
        }
    }

    @Override
    public void deleteCampaignById(String campaignId) {
        LOGGER.debug("Deleting campaign with id: " + campaignId);
        campaignsRepositoryDAO.delete(getCampaignById(campaignId));
    }

}
