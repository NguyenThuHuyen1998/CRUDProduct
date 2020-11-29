package com.example.crud.service;

import com.example.crud.entity.FeedBack;

import java.util.Optional;

public interface FeedbackService {
    void save(FeedBack feedBack);
    FeedBack getFeedback(long feedbackId);
    void updateFeedback(FeedBack feedBack);
    void deleteFeedback(FeedBack feedback);
}
