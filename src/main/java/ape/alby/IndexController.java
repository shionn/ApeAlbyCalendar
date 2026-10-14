package ape.alby;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import ape.alby.db.dao.CalendarDao;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

	private final SqlSession session;

	@GetMapping("/")
	public ModelAndView root() {
			
		return new ModelAndView("calendar").addObject("event", session.getMapper(CalendarDao.class).readCurrentEvent());
	}

}
