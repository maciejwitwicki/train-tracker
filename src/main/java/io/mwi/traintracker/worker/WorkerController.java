package io.mwi.traintracker.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerFeatureFlagService workerFeatureFlagService;

    @PutMapping
    void enableWorker(@RequestBody FeatureFlagRequest request) {
        workerFeatureFlagService.setFeatureFlagValue(request.numberOfItems);
    }

    record FeatureFlagRequest(int numberOfItems) {}

}
