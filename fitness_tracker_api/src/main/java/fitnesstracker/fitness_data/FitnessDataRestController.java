package fitnesstracker.fitness_data;

import fitnesstracker.config.AccessTokenAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/tracker")
public class FitnessDataRestController {

    private final FitnessDataService dataService;

    public FitnessDataRestController(FitnessDataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping
    public ResponseEntity<Void> uploadData(@RequestBody UploadDataRequest request, AccessTokenAuthentication authentication) {
        dataService.saveData(request, authentication.getCredentials().toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<FitnessDataDto>> getAllData() {
        return new ResponseEntity<>(dataService.getAllData(), HttpStatus.OK);
    }
}
