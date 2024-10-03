package com.backend.demo.services;

import com.backend.demo.Entity.Appointmentuser;
import com.backend.demo.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    private final EmailService emailService; // Inject EmailService

    public Appointmentuser postAppointment(Appointmentuser appointmentuser) {
        // Save the appointment to the repository
        Appointmentuser savedAppointment = appointmentRepository.save(appointmentuser);

        // Prepare email content for appointment confirmation
        String subject = "Appointment Confirmation";
        String text = "Dear " + appointmentuser.getName() + ",\n\n" +
                "Your appointment has been confirmed.\n" +
                "Appointment Details:\n" +
                "Date: " + appointmentuser.getDate() + "\n" +

                "We look forward to meeting you. If you need to reschedule, please contact us.\n\n" +
                "Best regards,\n" +
                "[SheRise]";

        // Send confirmation email
        emailService.sendEmail(appointmentuser.getEmail(), subject, text, false);

        return savedAppointment;
    }

    public List<Appointmentuser> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
