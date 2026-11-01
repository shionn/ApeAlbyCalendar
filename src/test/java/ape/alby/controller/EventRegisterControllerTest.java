package ape.alby.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ape.alby.db.dao.RegisterDao;
import ape.alby.db.dbo.Event;
import ape.alby.db.dbo.EventRegistration;

@ExtendWith(MockitoExtension.class)
class EventRegisterControllerTest {

	@Mock
	private SqlSession session;

	@Mock
	private RegisterDao registerDao;

	@Mock
	private RedirectAttributes redirectAttributes;

	private EventRegisterController controller;

	@BeforeEach
	public void setUp() {
		when(session.getMapper(RegisterDao.class)).thenReturn(registerDao);
		controller = new EventRegisterController(session, new EventRegistrationNormalizer());
	}

	@Test
	public void testOpenRegistrationWithoutArrivalHourParam() {
		Event event = Event.builder()
				.id(5)
				.name("Atelier")
				.day(new Date())
				.start(18).end(20)
				.build();
		when(registerDao.readEventById(anyInt())).thenReturn(event);

		ModelAndView result = controller.openRegistration(5, null);

		assertThat(result.getViewName()).isEqualTo("register");
		assertThat(result.getModel().get("event")).isEqualTo(event);
		assertThat(result.getModel().get("arrivalHour")).isEqualTo(-1);
	}

	@Test
	public void testOpenRegistrationWithArrivalHourParam() {
		Event event = Event.builder()
				.id(5)
				.name("Atelier")
				.day(new Date())
				.start(18).end(20)
				.build();
		when(registerDao.readEventById(anyInt())).thenReturn(event);

		ModelAndView result = controller.openRegistration(5, "19");

		assertThat(result.getViewName()).isEqualTo("register");
		assertThat(result.getModel().get("arrivalHour")).isEqualTo(19);
	}

	@Test
	public void testRegisterSplitsIntoHourlySlotsAndNormalizes() {
		EventRegistration form = EventRegistration.builder()
				.firstName("Michel")
				.lastName("Vaillant")
				.email("Michel.V@Exemple.COM")
				.arrivalHour(18).departureHour(20)
				.comment("test")
				.build();

		ModelAndView result = controller.register(5, form, redirectAttributes);

		ArgumentCaptor<EventRegistration> registrationCaptor = ArgumentCaptor.forClass(EventRegistration.class);
		verify(registerDao, times(2)).register(eq(5), registrationCaptor.capture());

		List<EventRegistration> registrations = registrationCaptor.getAllValues();
		assertThat(registrations).hasSize(2);

		// Créneaux 18-19 et 19-20
		assertThat(registrations.get(0).getArrivalHour()).isEqualTo(18).as("premier créneau 18-19");
		assertThat(registrations.get(0).getDepartureHour()).isEqualTo(19).as("départ du premier créneau");
		assertThat(registrations.get(1).getArrivalHour()).isEqualTo(19).as("deuxième créneau 19-20");
		assertThat(registrations.get(1).getDepartureHour()).isEqualTo(20).as("départ du deuxième créneau");

		// Données normalisées et commentaire conservé pour chaque créneau
		for (EventRegistration reg : registrations) {
			assertThat(reg.getFirstName()).isEqualTo("michel").as("prénom normalisé");
			assertThat(reg.getLastName()).isEqualTo("vaillant").as("nom normalisé");
			assertThat(reg.getEmail()).isEqualTo("michel.v@exemple.com").as("email normalisé");
			assertThat(reg.getComment()).isEqualTo("test").as("commentaire conservé");
		}

		verify(session).commit();
		assertThat(result.getViewName()).isEqualTo("redirect:/");
	}

	@Test
	public void testRegisterSingleSlot() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean")
				.lastName("dupont")
				.email("jean.dupont@example.com")
				.arrivalHour(18).departureHour(19)
				.comment("")
				.build();

		ModelAndView result = controller.register(5, form, redirectAttributes);

		ArgumentCaptor<EventRegistration> registrationCaptor = ArgumentCaptor.forClass(EventRegistration.class);
		verify(registerDao, times(1)).register(eq(5), registrationCaptor.capture());

		EventRegistration reg = registrationCaptor.getValue();
		assertThat(reg.getArrivalHour()).isEqualTo(18).as("créneau unique 18-19");
		assertThat(reg.getDepartureHour()).isEqualTo(19).as("départ du créneau unique");
		assertThat(result.getViewName()).isEqualTo("redirect:/");
	}

	@Test
	public void testRegisterDoesNotCommitWhenNoSlots() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean")
				.lastName("dupont")
				.email("jean.dupont@example.com")
				.arrivalHour(20).departureHour(20)
				.comment("")
				.build();

		ModelAndView result = controller.register(5, form, redirectAttributes);

		ArgumentCaptor<EventRegistration> registrationCaptor = ArgumentCaptor.forClass(EventRegistration.class);
		verify(registerDao, times(0)).register(eq(5), registrationCaptor.capture());

		verify(session).commit();
		assertThat(result.getViewName()).isEqualTo("redirect:/");
	}
	@Test
	public void testRegisterAddsSuccessFlashAttribute() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean")
				.lastName("dupont")
				.email("jean.dupont@example.com")
				.arrivalHour(18).departureHour(19)
				.comment("")
				.build();

		ModelAndView result = controller.register(5, form, redirectAttributes);

		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(redirectAttributes).addFlashAttribute(eq("success"), messageCaptor.capture());
		assertThat(messageCaptor.getValue()).isEqualTo("Votre inscription a bien été enregistrée.");
		assertThat(result.getViewName()).isEqualTo("redirect:/");
	}
	@Test
	public void testRegisterBlockedWhenAlreadyRegistered() {
		EventRegistration form = EventRegistration.builder()
				.firstName("jean")
				.lastName("dupont")
				.email("jean.dupont@example.com")
				.arrivalHour(18).departureHour(19)
				.comment("")
				.build();

		when(registerDao.existsByPersonAndEvent(anyInt(), any(EventRegistration.class))).thenReturn(1);

		ModelAndView result = controller.register(5, form, redirectAttributes);

		verify(registerDao, times(0)).register(anyInt(), any(EventRegistration.class));
		verify(session, never()).commit();

		ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
		verify(redirectAttributes).addFlashAttribute(eq("error"), messageCaptor.capture());
		assertThat(messageCaptor.getValue()).isEqualTo("Vous êtes déjà inscrit à cet événement.");
		assertThat(result.getViewName()).isEqualTo("redirect:/event/5/register");
	}


}
