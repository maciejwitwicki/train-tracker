package io.mwi.traintracker.api.train;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trains")
@RequiredArgsConstructor
public class TrainController {


    @GetMapping
    public String getTrain() {
        return "Success";
    }


}
