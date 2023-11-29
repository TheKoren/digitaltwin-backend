package com.koren.digitaltwin.repositories;

import com.koren.digitaltwin.models.message.AbstractWifiMessage;
import com.koren.digitaltwin.models.message.NodeWifiMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Wi-Fi messages in MongoDB.
 */
@Repository
public interface DataRepository extends MongoRepository<NodeWifiMessage, ObjectId> {

    /**
     * Retrieves an optional Wi-Fi message by its MAC address.
     *
     * @param mac MAC address of the Wi-Fi message.
     * @return An optional containing the Wi-Fi message if found, otherwise an empty optional.
     */
    Optional<NodeWifiMessage> findMessageByMac(String mac);

    /**
     * Retrieves a list of messages by MAC address.
     *
     * @param mac MAC address of the messages.
     * @return A list of messages with the specified MAC address.
     */
    List<AbstractWifiMessage> findMessagesByMac(String mac);
}
