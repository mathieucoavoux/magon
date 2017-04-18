package fr.comprehensiveit.magon.ws.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.comprehensiveit.magon.ws.model.Book;
import fr.comprehensiveit.magon.ws.util.JsonUtil;


public class BookDao {

	private static final Logger logger = LoggerFactory.getLogger(BookDao.class);
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("manager1");
	
	@SuppressWarnings("unchecked")
	public String findAllBook() {
		logger.debug("find all book called");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("from " + Book.class.getName());
		List<Book> result = query.getResultList();
		return JsonUtil.toJson(result);
	}
}
