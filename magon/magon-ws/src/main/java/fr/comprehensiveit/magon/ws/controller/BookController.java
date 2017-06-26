package fr.comprehensiveit.magon.ws.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.comprehensiveit.magon.ws.dao.BookDao;

@RestController
@RequestMapping("/api/book")
public class BookController {

	/**
	 * Dispatch request to BookDao.findAllBook
	 * @param title
	 * @return
	 */
	@RequestMapping(
			value = "/searchByTitle",
			params = {"title"},
			method = RequestMethod.GET)
	@ResponseBody
	public String searchByTitle(@RequestParam("title") String title) {
		BookDao bd = new BookDao();
		return bd.findAllBook();
		
	}
}
