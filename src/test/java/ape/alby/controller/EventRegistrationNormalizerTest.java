package ape.alby.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ape.alby.db.dbo.EventRegistration;

class EventRegistrationNormalizerTest {

    private EventRegistrationNormalizer normalizer;

    @BeforeEach
    void setUp() {
        normalizer = new EventRegistrationNormalizer();
    }

    @Test
    void testNormalizeWithHyphenatedLowercase() {
        EventRegistration input = EventRegistration.builder()
                .firstName("jean-paul")
                .lastName("dupont")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration expected = EventRegistration.builder()
                .firstName("Jean-Paul")
                .lastName("Dupont")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration result = normalizer.normalize(input);
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());
    }

    @Test
    void testNormalizeWithAlreadyCapitalized() {
        EventRegistration input = EventRegistration.builder()
                .firstName("Jean-Pierre")
                .lastName("Dupont")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration expected = EventRegistration.builder()
                .firstName("Jean-Pierre")
                .lastName("Dupont")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration result = normalizer.normalize(input);
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());
    }

    @Test
    void testNormalizeWithMixedCaseAndSpaces() {
        EventRegistration input = EventRegistration.builder()
                .firstName("jEaN pAuL")
                .lastName("mICHeL")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration expected = EventRegistration.builder()
                .firstName("Jean Paul")
                .lastName("Michel")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration result = normalizer.normalize(input);
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());
    }

    @Test
    void testNormalizeWithMultipleSeparators() {
        EventRegistration input = EventRegistration.builder()
                .firstName("jean--paul  dupont")
                .lastName("martin")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration expected = EventRegistration.builder()
                .firstName("Jean-Paul Dupont")
                .lastName("Martin")
                .email("a@b.com")
                .arrivalHour(10).departureHour(12).comment("")
                .build();

        EventRegistration result = normalizer.normalize(input);
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getLastName(), result.getLastName());
    }
}