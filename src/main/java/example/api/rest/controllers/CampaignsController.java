package example.api.rest.controllers;

import example.api.rest.models.Campaign;
import example.api.rest.services.CampaignsService;
import example.api.rest.services.SequenceGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static example.api.rest.utils.ApiConstants.CAMPAIGNS_PAGE_DEFAULT;
import static example.api.rest.utils.ApiConstants.CAMPAIGNS_SIZE_DEFAULT;

/**
 * REST controller to campaigns.
 */
@RestController
@Api(value = "Campaigns")
@RequestMapping("/campaigns")
public class CampaignsController {

    private static final Logger LOGGER = LogManager.getLogger(CampaignsController.class);

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private CampaignsService campaignsService;

    @ApiOperation(value = "Get all campaigns")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<Campaign> getAllCampaigns(@RequestParam(value="page", required = false, defaultValue = CAMPAIGNS_PAGE_DEFAULT) int page,
                                          @RequestParam(value="size", required = false, defaultValue = CAMPAIGNS_SIZE_DEFAULT) int size) {
        LOGGER.info("Entering in rest endpoint to get all campaigns");
        PageRequest pageRequest = PageRequest.of(page, size);
        return campaignsService.getAllCampaigns(pageRequest);
    }

    @ApiOperation(value = "Get a campaign by id")
    @RequestMapping(value = "/{campaignId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Campaign getCampaignById(@PathVariable("campaignId") String campaignId) {
        LOGGER.info("Entering in rest endpoint to get a campaign by id");
        return campaignsService.getCampaignById(campaignId);
    }

    @ApiOperation(value = "Create a new campaign")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Campaign createCampaign(@Valid @RequestBody Campaign campaign) {
        LOGGER.info("Entering in rest endpoint to create a new campaign");
        campaign.setId(sequenceGeneratorService.generateSequence(Campaign.SEQUENCE_NAME));
        return campaignsService.createCampaign(campaign);
    }

    @ApiOperation(value = "Update an existing campaign by id")
    @RequestMapping(value = "/{campaignId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Campaign updateCampaign(@Valid @RequestBody Campaign campaign, @PathVariable("campaignId") String campaignId) {
        LOGGER.info("Entering in rest endpoint to update campaign");
        campaign.setId(campaignId);
        return campaignsService.updateCampaign(campaign);
    }

    @ApiOperation(value = "Delete a campaign by id")
    @RequestMapping(value = "/{campaignId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable("campaignId") String campaignId) {
        LOGGER.info("Entering in rest endpoint to delete the campaign");
        campaignsService.deleteCampaignById(campaignId);
    }

}