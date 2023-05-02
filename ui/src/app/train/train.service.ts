import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";

@Injectable()
export class TrainService {

  private static readonly TRAINS_URL = 'http://localhost:8080/trains';

  constructor(private httpClient: HttpClient, private authService: AuthService) {
  }

  getTrains() {
    const headers = this.authService.getAuthHeaders();

    this.httpClient.get(TrainService.TRAINS_URL, {headers: headers})
      .subscribe({
        next: resp => console.log('Got resp!', resp),
        error: err => console.error('Error retrieving trains', err)
      });
  }
}
