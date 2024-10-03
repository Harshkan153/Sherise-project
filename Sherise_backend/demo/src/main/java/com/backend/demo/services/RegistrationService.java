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

        // Prepare email content for the user
        String subject = "Workshop Registration Confirmation";
        String text = "Dear " + registration.getName() + ",\n\n" +
                "Thank you for registering for the " + registration.getWorkshop() + " workshop.\n\n" +
                "Here are your registration details:\n" +
                "Workshop: " + registration.getWorkshop() + "\n" +

                "We are excited to have you join us. If you have any questions, feel free to reach out.\n\n" +
                "Best regards,\n" +
                "[SheRise]";

        // Send a confirmation email after registration
        emailService.sendEmail(registration.getEmail(), subject, text, false);

        return savedRegistration;
    }
}
