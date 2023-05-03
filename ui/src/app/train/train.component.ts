import {Component, OnInit} from '@angular/core';
import {TrainService} from "./train.service";
import {TrainLocationEvent} from "./train.model";

@Component({
  templateUrl: './train.component.html',
  styleUrls: ['./train.component.scss']
})
export class TrainComponent implements OnInit {

  locations: Array<TrainLocationEvent> = [];

  constructor(private trainService: TrainService) {
  }

  ngOnInit(): void {
    this.trainService.getServerSentEvent()
      .subscribe({
        next: location => {
          this.locations.push(location);
        },
        error: this.handleError,
      })
  }

  private handleError(error: Error) {
    console.error(error);
  }
}
