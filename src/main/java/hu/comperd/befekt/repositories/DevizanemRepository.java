package hu.comperd.befekt.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import hu.comperd.befekt.collections.DevizanemCol;

public interface DevizanemRepository extends MongoRepository<DevizanemCol, String> {

	public DevizanemCol findByDevKod(String devKod);
	public List<DevizanemCol> findAllByOrderByDevKodAsc();
}
