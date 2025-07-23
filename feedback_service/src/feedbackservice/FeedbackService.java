package feedbackservice;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public FeedbackEntity save(FeedbackRequest request) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setFeedback(request.getFeedback());
        entity.setCustomer(request.getCustomer());
        entity.setProduct(request.getProduct());
        entity.setRating(request.getRating());
        entity.setVendor(request.getVendor());
        return repository.save(entity);
    }

    public Optional<FeedbackEntity> findById(String id) {
        return repository.findById(id);
    }

    public Page<FeedbackEntity> getAll(int page,
                                       int perPage,
                                       Integer rating,
                                       String customer,
                                       String product,
                                       String vendor) {
        FeedbackEntity probe = new FeedbackEntity(rating, customer, product, vendor);
        Example<FeedbackEntity> example = Example.of(probe);

        Pageable pageable = PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findAll(example, pageable);
    }
}
