package feedbackservice;

import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public FeedbackDTO toDTO(FeedbackEntity entity) {
        return new FeedbackDTO(
                entity.getId().toString(),
                entity.getRating(),
                entity.getFeedback(),
                entity.getCustomer(),
                entity.getProduct(),
                entity.getVendor()
        );
    }
}
