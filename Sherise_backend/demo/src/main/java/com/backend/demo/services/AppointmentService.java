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

        // Send a confirmation email after saving the appointment
        String subject = "Appointment Confirmation";
        String text = "Dear " + appointmentuser.getName() + ",\n\nYour appointment is confirmed for " + appointmentuser.getDate()+ ".";
        emailService.sendEmail(appointmentuser.getEmail(), subject, text, false);

        return savedAppointment;
    }

    public List<Appointmentuser> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
