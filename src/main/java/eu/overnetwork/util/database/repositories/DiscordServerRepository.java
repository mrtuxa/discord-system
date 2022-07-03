package eu.overnetwork.util.database.repositories;

import eu.overnetwork.util.database.models.DiscordServer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiscordServerRepository extends MongoRepository<DiscordServer, String> {
}
