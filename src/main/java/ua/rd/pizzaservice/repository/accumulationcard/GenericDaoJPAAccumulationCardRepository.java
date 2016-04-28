package ua.rd.pizzaservice.repository.accumulationcard;

import java.util.List;

import javax.persistence.Persistence;

import org.springframework.stereotype.Repository;

import ua.rd.pizzaservice.domain.accumulationcard.AccumulationCard;
import ua.rd.pizzaservice.repository.GenericDaoJPAImpl;

@Repository
public class GenericDaoJPAAccumulationCardRepository extends GenericDaoJPAImpl<AccumulationCard, Integer>
		implements AccumulationCardRepository {

	public GenericDaoJPAAccumulationCardRepository() {
		emf = Persistence.createEntityManagerFactory("jpa_mysql");
	}
	
	public GenericDaoJPAAccumulationCardRepository(String persistenceUnitName) {
		emf = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	@Override
	public List<AccumulationCard> getAllAccumulationCards() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

}
