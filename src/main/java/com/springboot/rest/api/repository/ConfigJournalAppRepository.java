package com.springboot.rest.api.repository;

import com.springboot.rest.api.entity.ConfigJournalApp;
import com.springboot.rest.api.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalApp, ObjectId>{

}
