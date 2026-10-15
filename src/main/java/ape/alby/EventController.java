package ape.alby;

import java.util.stream.IntStream;

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
		// TODO verifier qu'un enregiostrement semblable n'existe pas, meme mail /
		// prenom / nom/ event sans prendre en compte la casse
		RegisterDao dao = session.getMapper(RegisterDao.class);
		IntStream
				.range(registration.getArrivalHour(), registration.getDepartureHour())
				.mapToObj(h -> EventRegistration
						.builder()
						.arrivalHour(h)
						.email(registration.getEmail())
						.comment(registration.getComment())
						.departureHour(h + 1)
						.firstName(registration.getFirstName())
						.lastName(registration.getLastName())
						.build())
				.forEach(r -> dao.register(id, r));
		session.commit();
		return new ModelAndView("redirect:/");
	}

}
