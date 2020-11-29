package com.example.crud.service.impl;

import com.example.crud.entity.FeedBack;
import com.example.crud.repository.FeedbackRepository;
import com.example.crud.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository){
        this.feedbackRepository= feedbackRepository;
    }


    @Override
    public void save(FeedBack feedBack) {
        feedbackRepository.save(feedBack);
    }

    @Override
    public FeedBack getFeedback(long feedbackId) {
        Optional<FeedBack> optionalFeedBack= feedbackRepository.findById(feedbackId);
        return optionalFeedBack.get();
    }

    @Override
    public void updateFeedback(FeedBack feedBack) {
        feedbackRepository.save(feedBack);
    }

    @Override
    public void deleteFeedback(FeedBack feedback) {
        feedbackRepository.delete(feedback);
    }
}
