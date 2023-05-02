import {Injectable} from "@angular/core";
import {HttpBackend, HttpClient} from "@angular/common/http";
import {Observable, tap} from "rxjs";
import {AccessTokenResponse} from "./login.model";
import {AuthService} from "../auth/auth.service";

@Injectable()
export class LoginService {

  private static readonly LOGIN_URL = 'http://localhost:8080/login';

  private loginHttpClient: HttpClient

  constructor(private backend: HttpBackend, private authService: AuthService) {
    this.loginHttpClient = new HttpClient(backend); //use custom client instead the default which has interceptors
  }

  login(user: string, password: string): Observable<AccessTokenResponse> {
    return this.loginHttpClient.post<AccessTokenResponse>(LoginService.LOGIN_URL, {
      username: user,
      password
    }).pipe(
      tap({
        next: tokenResponse => this.authService.setAccessToken(tokenResponse.accessToken, tokenResponse.expiresInSeconds),
        error: err => console.error('Error while trying to login', err)
      })
    );
  }

}
