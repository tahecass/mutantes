package co.com.mutant.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import co.com.mutant.entity.Humans;

@org.springframework.stereotype.Repository
public interface Repository extends  MongoRepository<Humans, String> {
	
	List<Humans> findByCategory(String category);

}
