package com.koren.digitaltwin.repositories;

import com.koren.digitaltwin.models.message.WifiMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataRepository extends MongoRepository<WifiMessage, ObjectId> {
    Optional<WifiMessage> findMessageByMac(String mac);
}
