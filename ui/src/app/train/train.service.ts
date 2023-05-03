import {Injectable, NgZone} from "@angular/core";
import {EventsService} from "./events.service";
import {filter, map, Observable} from "rxjs";
import {ServerEvent} from "./train.model";

@Injectable()
export class TrainService {

  private static readonly TRAINS_URL = 'http://localhost:8080/trains';
  private static readonly TRAIN_LOCATION_EVENT_TYPE = 'TRAIN_LOCATION';


  constructor(private eventsService: EventsService, private zone: NgZone) {
  }

  getServerSentEvent(): Observable<any> {
    const observable = new Observable<MessageEvent>(observer => {
      const eventSource: EventSource = this.eventsService.getEventSource(TrainService.TRAINS_URL);
      eventSource.onmessage = event => this.zone.run(() => observer.next(event));
      eventSource.onerror = error => this.zone.run(() => observer.error(error));
    })

    return observable.pipe(
      map(this.toServerEvent),
      filter(this.isTrainLocationEvent),
      map(this.toTrainLocationEvent)
    );
  }

  private toServerEvent(event: MessageEvent): ServerEvent {
    return JSON.parse(event.data);
  }


  private isTrainLocationEvent(event: ServerEvent) {
    return event.type === TrainService.TRAIN_LOCATION_EVENT_TYPE;
  }

  private toTrainLocationEvent(event: ServerEvent) {
    return event.value!;
  }

}
