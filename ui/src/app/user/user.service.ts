import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {finalize, map, Observable} from "rxjs";
import {UserCredentials, UserDetails} from "./user.model";
import {AuthService} from "../auth/auth.service";

@Injectable()
export class UserService {

  private static readonly USERS_URL = 'http://localhost:8080/users';

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  getUserDetails(): Observable<UserDetails> {
    const headers = this.authService.getAuthHeaders();
    return this.http.get<UserDetails>(UserService.USERS_URL, {headers: headers});
  }

}
