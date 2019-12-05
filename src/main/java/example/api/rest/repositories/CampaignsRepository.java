package example.api.rest.repositories;

import example.api.rest.models.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Campaigns DAO/repository.
 */
@Repository
public interface CampaignsRepository extends MongoRepository<Campaign, String> {

}
