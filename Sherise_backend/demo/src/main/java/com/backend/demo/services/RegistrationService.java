package com.backend.demo.services;

import com.backend.demo.Entity.Registration;
import com.backend.demo.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@EnableAsync
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final EmailService emailService; // Inject the EmailService

    public Registration postregistration(Registration registration) {
        // Save registration details to the repository
        Registration savedRegistration = registrationRepository.save(registration);

        // Send a confirmation email after registration
        String subject = "Registration Confirmation";
        String text = "Dear " + registration.getName() + ",\n\nThank you for registering!";
        emailService.sendEmail(registration.getEmail(), subject, text, false);

        return savedRegistration;
    }
}
