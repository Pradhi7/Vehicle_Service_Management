package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Appointment;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.repository.AppointmentRepo;
import com.examly.springapp.repository.FeedbackRepo;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepo feedbackRepo;
    private final AppointmentRepo appointmentRepo;

    public FeedbackServiceImpl(FeedbackRepo feedbackRepo,AppointmentRepo appointmentRepo ) {
        this.feedbackRepo = feedbackRepo;
        this.appointmentRepo = appointmentRepo;
    }
  
    @Override
    public Feedback createFeedback(Feedback feedback,long appointmentId) {
        Appointment app = appointmentRepo.findById(appointmentId).orElse(null);
        app.setStatus("Completed");
        Feedback savedFeedback=feedbackRepo.save(feedback);
        return savedFeedback;
    }

    @Override
    public Feedback getFeedbackById(Long feedbackId) {
        Optional<Feedback> optional=feedbackRepo.findById(feedbackId);
        if(optional.isEmpty()){
            throw new EntityNotFoundException();
        }
        return optional.get();
    }

    @Override
    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList=feedbackRepo.findAll();
        return feedbackList;
    }


    @Override
    public Feedback deleteFeedback(Long feedbackId) {
        if (feedbackRepo.existsById(feedbackId)) {
            Feedback feedback = feedbackRepo.findById(feedbackId).orElse(null);
            if (feedback != null) {
                feedbackRepo.deleteById(feedbackId);
                return feedback;
            }
        }
        return null;
    }
    

    @Override
    public List<Feedback> getFeedbackByUserId(int userId) {
        List<Feedback> feedbackList=feedbackRepo.findByUserId(userId);
        return feedbackList;
    }
    
}
