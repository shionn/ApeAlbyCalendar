package ape.alby.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ape.alby.db.dao.RegisterDao;
import ape.alby.db.dao.UnregisterDao;
import ape.alby.db.dbo.Event;
import ape.alby.db.dbo.EventRegistration;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EventUnregisterController {
	private final SqlSession session;
	private final EventRegistrationNormalizer normalizer;

	@GetMapping("/event/{id}/unregister")
	public ModelAndView openUnregister(@PathVariable("id") int id) {
		Event event = session.getMapper(RegisterDao.class).readEventById(id);
		return new ModelAndView("unregister").addObject("event", event);
	}

	@PostMapping("/event/{id}/unregister")
	public ModelAndView unregister(@PathVariable("id") int id, @ModelAttribute() EventRegistration form,
			RedirectAttributes redirectAttributes) {
		UnregisterDao dao = session.getMapper(UnregisterDao.class);
		EventRegistration normalized = normalizer.normalize(form);

		if (dao.existsByCriteria(id, normalized) > 0) {
			dao.deleteByCriteria(id, normalized);
			session.commit();
			return new ModelAndView("redirect:/");
		} else {
			redirectAttributes
					.addFlashAttribute("error",
							"Aucun enregistrement trouvé pour ce prénom, ce nom et cet email à cette heure.");
			return new ModelAndView("redirect:/event/" + id + "/unregister");
		}
	}
}
