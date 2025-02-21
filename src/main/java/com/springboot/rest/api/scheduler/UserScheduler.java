package com.springboot.rest.api.scheduler;

import com.springboot.rest.api.cache.AppCache;
import com.springboot.rest.api.entity.JournalEntry;
import com.springboot.rest.api.entity.User;
import com.springboot.rest.api.enums.Sentiment;
import com.springboot.rest.api.repository.UserRepositoryImpl;
import com.springboot.rest.api.service.EmailService;
import com.springboot.rest.api.service.SentimentAnalysisService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    private EmailService emailService;
    private UserRepositoryImpl userRepository;
    private SentimentAnalysisService sentimentAnalysisService;
    private AppCache appCache;

    public UserScheduler(EmailService emailService, UserRepositoryImpl userRepository, SentimentAnalysisService sentimentAnalysisService, AppCache appCache) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.appCache = appCache;
    }

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendMail(){
        List<User> users = userRepository.getUserForSA();
        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                    emailService.sendEmail(user.getEmail(), "Sentiment for previous week", mostFrequentSentiment.toString());

            }

        }

    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearCacheApp(){
        appCache.init();
    }
}
