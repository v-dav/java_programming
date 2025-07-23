package feedbackservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class Controller {

    private final FeedbackService feedbackService;
    private final Mapper mapper;

    public Controller(FeedbackService feedbackService, Mapper mapper) {
        this.feedbackService = feedbackService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> createFeedback(@Valid @RequestBody FeedbackRequest request) {
        FeedbackEntity savedEntity = feedbackService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Location", "/feedback/" + savedEntity.getId().toString())
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable String id) {
        return feedbackService.findById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<AllPageableResponse> getAll(@RequestParam(defaultValue = "1")  int page,
                                                      @RequestParam(defaultValue = "10") int perPage,
                                                      @RequestParam(required = false) Integer rating,
                                                      @RequestParam(required = false) String customer,
                                                      @RequestParam(required = false) String product,
                                                      @RequestParam(required = false) String vendor) {
        page = (page < 1) ? 1 : page;
        perPage = (perPage < 5) ? 10 : (perPage > 20) ? 10 : perPage;

        Page<FeedbackEntity> pages = feedbackService.getAll(
                page - 1,
                perPage,
                rating,
                customer,
                product,
                vendor);

        AllPageableResponse response = new AllPageableResponse(
                pages.getTotalElements(),
                pages.isFirst(),
                pages.isLast(),
                pages.getContent().stream()
                        .map(mapper::toDTO)
                        .toList()
        );

        return ResponseEntity.ok(response);
    }

    public record AllPageableResponse(

            @JsonProperty("total_documents")
            Long totalDocuments,

            @JsonProperty("is_first_page")
            boolean isFirstPage,

            @JsonProperty("is_last_page")
            boolean isLastPage,

            List<FeedbackDTO> documents)

    {}
}
