package example.api.rest.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * Campaign status types enumerate.
 */
public enum CampaignStatusType {
    OPEN ("open"),
    EXPIRED ("expired"),
    CLOSED ("closed");

    private String value;

    CampaignStatusType(String value) {
        this.value = value;
    }

    @JsonValue
    public String toValue() {
        return value;
    }

    @JsonCreator
    public static CampaignStatusType fromValue(@JsonProperty("campaignStatus") String value) {
        return Arrays.stream(values())
                .filter(campaignStatus -> campaignStatus.value.equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "CampaignStatus{" +
                "value='" + value + '\'' +
                "} " + super.toString();
    }

}
