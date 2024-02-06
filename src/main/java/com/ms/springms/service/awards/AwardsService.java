package com.ms.springms.service.awards;

import com.ms.springms.Exceptions.DuplicateEntryException;
import com.ms.springms.entity.Awards;
import com.ms.springms.repository.AwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class AwardsService {

    @Autowired
    private AwardRepository awardRepository;


    public String createEvent(Awards awards) {

        String nameAward = awards.getNameAward();
        String description = awards.getDescription();

        try {
            if (nameAward != null && !nameAward.isBlank()) {

                Awards event = new Awards();

                event.setNameAward(nameAward);
                event.setDescription(description);
                awardRepository.save(event);

                System.out.println(event);

                return "Awards Created";
            } else {
                return "Name RegistrationEvent cannot be blank";
            }

        } catch (DuplicateEntryException ex) {

            if (ex.getMessage().contains("Duplicate Entry")) {
                System.out.println("Duplicate Entry" + nameAward);
                throw new DuplicateEntryException("Award Already Exist");
            } else {
                System.out.println("Data integrity violation exception: " + ex.getMessage());
                return "Error: Unable to Register. Please try again later.";
            }

        }
    }
}
