package ape.alby.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ape.alby.db.dao.UnregisterDao;
import ape.alby.db.dbo.EventRegistration;

@ExtendWith(MockitoExtension.class)
class EventUnregisterControllerTest {

	@Mock
	private SqlSession session;

	@Mock
	private UnregisterDao unregisterDao;

	@Mock
	private RedirectAttributes redirectAttributes;

	private EventUnregisterController controller;

	@BeforeEach
	public void setUp() {
		when(session.getMapper(UnregisterDao.class)).thenReturn(unregisterDao);
		controller = new EventUnregisterController(session, new EventRegistrationNormalizer());
	}

	@Test
	public void testUnregisterDeletesWhenRegistrationExists() {
		EventRegistration form = EventRegistration.builder()
				.firstName("Michel")
				.lastName("Vaillant")
				.email("Michel.V@Exemple.COM")
				.arrivalHour(18).departureHour(19)
				.build();

		when(unregisterDao.existsByCriteria(anyInt(), any(EventRegistration.class))).thenReturn(1);
		when(redirectAttributes.addFlashAttribute(eq("success"), anyString())).thenReturn(redirectAttributes);

		ModelAndView result = controller.unregister(5, form, redirectAttributes);

		ArgumentCaptor<EventRegistration> registrationCaptor = ArgumentCaptor.forClass(EventRegistration.class);
		verify(unregisterDao).existsByCriteria(eq(5), registrationCaptor.capture());

		EventRegistration criteria = registrationCaptor.getValue();
		assertThat(criteria.getFirstName()).isEqualTo("michel").as("prénom normalisé");
		assertThat(criteria.getLastName()).isEqualTo("vaillant").as("nom normalisé");
		assertThat(criteria.getEmail()).isEqualTo("michel.v@exemple.com").as("email normalisé");
		assertThat(criteria.getArrivalHour()).isEqualTo(18).as("heure d'arrivée");

		verify(unregisterDao).deleteByCriteria(eq(5), registrationCaptor.capture());
		EventRegistration deleted = registrationCaptor.getValue();
		assertThat(deleted.getFirstName()).isEqualTo("michel").as("prénom supprimé");
		assertThat(deleted.getEmail()).isEqualTo("michel.v@exemple.com").as("email supprimé");
		assertThat(deleted.getArrivalHour()).isEqualTo(18).as("heure supprimée");
		verify(session).commit();
		assertThat(result.getViewName()).isEqualTo("redirect:/");
		verify(redirectAttributes).addFlashAttribute("success", "Votre désinscription a bien été enregistrée.");
	}

	@Test
	public void testUnregisterKeepsFormWhenNoRegistration() {
		EventRegistration form = EventRegistration.builder()
				.firstName("Michel")
				.lastName("Vaillant")
				.email("Michel.V@Exemple.COM")
				.arrivalHour(18).departureHour(19)
				.build();

		when(unregisterDao.existsByCriteria(anyInt(), any(EventRegistration.class))).thenReturn(0);
		when(redirectAttributes.addFlashAttribute(eq("error"), anyString())).thenReturn(redirectAttributes);

		ModelAndView result = controller.unregister(5, form, redirectAttributes);

		ArgumentCaptor<EventRegistration> registrationCaptor = ArgumentCaptor.forClass(EventRegistration.class);
		verify(unregisterDao).existsByCriteria(eq(5), registrationCaptor.capture());

		EventRegistration criteria = registrationCaptor.getValue();
		assertThat(criteria.getFirstName()).isEqualTo("michel").as("prénom normalisé");
		assertThat(criteria.getLastName()).isEqualTo("vaillant").as("nom normalisé");
		assertThat(criteria.getEmail()).isEqualTo("michel.v@exemple.com").as("email normalisé");
		assertThat(criteria.getArrivalHour()).isEqualTo(18).as("heure d'arrivée");

		verify(unregisterDao, never()).deleteByCriteria(anyInt(), any(EventRegistration.class));
		verify(session, never()).commit();
		verify(redirectAttributes).addFlashAttribute("error",
				"Aucun enregistrement trouvé pour ce prénom, ce nom et cet email à cette heure.");
		assertThat(result.getViewName()).isEqualTo("redirect:/event/5/unregister");
	}
}
