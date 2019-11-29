package example.api.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static example.api.rest.utils.ApiConstants.CAMPAIGNS_SEQUENCE_NAME;

@Document(collection = "campaigns")
@JsonIgnoreProperties(value="campaignStatus", allowGetters= true, ignoreUnknown = true)
public class Campaign extends BaseDocument {

    @Transient
    public static final String SEQUENCE_NAME = CAMPAIGNS_SEQUENCE_NAME;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @URL(message = "Url should be valid")
    private String url;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private CampaignStatusType campaignStatus = CampaignStatusType.OPEN;

    public Campaign () {
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCampaignStatus(CampaignStatusType campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public CampaignStatusType getCampaignStatus() {
        return campaignStatus;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", campaignStatus=" + campaignStatus +
                "} " + super.toString();
    }

}
