import {Component} from '@angular/core';
import {UserService} from "./user/user.service";
import {Router} from "@angular/router";
import {UserCredentials} from "./user/user.model";
import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  appName = "train-tracker"

  constructor(private authService: AuthService) {  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  logout() {
    this.authService.logout();
  }


}
