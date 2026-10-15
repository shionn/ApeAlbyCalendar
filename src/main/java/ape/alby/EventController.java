package ape.alby;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ape.alby.db.dao.RegisterDao;
import ape.alby.db.dbo.EventRegistration;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EventController {
	private final SqlSession session;

	@GetMapping("/event/{id}/register")
	public ModelAndView openRegistration(@PathVariable("id") int id) {
		return new ModelAndView("register")
				.addObject("event", session.getMapper(RegisterDao.class).readEventById(id));
	}

	@PostMapping("/event/{id}/register")
	public ModelAndView register(@PathVariable("id") int id, @ModelAttribute() EventRegistration registration) {
		RegisterDao dao = session.getMapper(RegisterDao.class);
		dao.register(id, registration);
		session.commit();
		return new ModelAndView("redirect:/");
	}

}
