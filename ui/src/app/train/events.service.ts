import {Injectable} from "@angular/core";
import {AuthService} from "../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  constructor(private authService: AuthService) {
  }

  getEventSource(url: string): EventSource {
    const token = this.authService.getToken();
    const urlWithToken = `${url}?access_token=${token}`;
    return new EventSource(urlWithToken);
  }
}
