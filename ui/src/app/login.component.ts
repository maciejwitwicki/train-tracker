import {Component} from '@angular/core';
import {UserService} from "./user/user.service";
import {AuthService} from "./auth/auth.service";

@Component({
  templateUrl: './login.component.html'
})
export class LoginComponent {

  private static readonly LOGIN_DELAY: number = 1000;

  constructor(private userService: UserService, private authService: AuthService) {
  }

  ngOnInit() {
    if (!this.authService.isAuthenticated()) {
      setTimeout(this.authService.redirectToLoginPage, LoginComponent.LOGIN_DELAY);
    }
  }

}
