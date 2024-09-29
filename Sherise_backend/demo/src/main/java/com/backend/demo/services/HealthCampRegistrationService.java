package com.backend.demo.services;

import com.backend.demo.Entity.HealthCampRegistration;
import com.backend.demo.repository.HealthCampRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthCampRegistrationService {

    private final HealthCampRegistrationRepository healthCampRegistrationRepository;
    private final EmailService emailService;  // Injecting EmailService

    public HealthCampRegistration postHealthCampRegistration(HealthCampRegistration healthCampRegistration) {
        // Save the registration details
        HealthCampRegistration savedRegistration = healthCampRegistrationRepository.save(healthCampRegistration);

        // Send confirmation email
        sendConfirmationEmail(savedRegistration);

        return savedRegistration;
    }

    private void sendConfirmationEmail(HealthCampRegistration registration) {
        String to = registration.getEmail();  // Assuming HealthCampRegistration has an email field
        String subject = "Health Camp Registration Confirmation";
        String text = "Dear " + registration.getName() + ",\n\n" +
                "Thank you for registering for our health camp on " + registration.getDate() + ".\n" +
                "We are looking forward to your participation.\n\n" +
                "Best regards,\nHealth Camp Team";

        // Call the email service to send the email
        emailService.sendEmail(to, subject, text, false);
    }
}
