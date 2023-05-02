import {Injectable} from "@angular/core";
import {HttpBackend, HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserDetails} from "./user.model";

@Injectable()
export class UserService {

  private static readonly USERS_URL = 'http://localhost:8080/users';
  private static readonly REGISTER_URL = 'http://localhost:8080/register';

  private registerHttpClient: HttpClient;

  constructor(private http: HttpClient, private httpBackend: HttpBackend) {
    this.registerHttpClient = new HttpClient(httpBackend); //use custom client instead the default which has interceptors
  }

  getUserDetails(): Observable<UserDetails> {
    return this.http.get<UserDetails>(UserService.USERS_URL);
  }

  createUser(username: any, password: any, email: any) {
    const request = {
      username,
      password,
      email
    };
    return this.registerHttpClient.post<UserDetails>(UserService.REGISTER_URL, request);
  }
}
