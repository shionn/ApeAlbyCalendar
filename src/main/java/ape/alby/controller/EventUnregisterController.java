package ape.alby.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ape.alby.db.dao.RegisterDao;
import ape.alby.db.dbo.Event;
import ape.alby.db.dbo.EventRegistration;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EventUnregisterController {
	private final SqlSession session;

	@GetMapping("/event/{id}/unregister")
	public ModelAndView openUnregister(@PathVariable("id") int id,
			@RequestParam(value = "arrivalHour", required = false) String arrivalHourParam) {
		int arrivalHour = arrivalHourParam == null || arrivalHourParam.isEmpty() ? -1 : Integer
				.parseInt(arrivalHourParam);
		Event event = session.getMapper(RegisterDao.class).readEventById(id);
		return new ModelAndView("unregister")
				.addObject("event", event)
				.addObject("arrivalHour", arrivalHour);
	}

	@PostMapping("/event/{id}/unregister")
	public ModelAndView unregister(@PathVariable("id") int id, @ModelAttribute() EventRegistration form) {
		// TODO : suppression des inscriptions correspondantes en base (mise à jour BDD à faire plus tard)
		return new ModelAndView("redirect:/");
	}
}
