package com.koren.digitaltwin.services;

import com.koren.digitaltwin.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepository extends MongoRepository<Entry, String> {
}
