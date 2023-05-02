import {Component, Optional} from '@angular/core';
import {UserService} from "./user/user.service";
import {UserDetails} from "./user/user.model";
import {AuthService} from "./auth/auth.service";

@Component({
  templateUrl: './home.component.html'
})
export class HomeComponent {

  title = 'Home page';
  userDetails: UserDetails = {} as UserDetails;
  error: any

  constructor(private authService: AuthService, private userService: UserService) {
    authService.onLoggedInEvent(() => {
     console.log("In home component log in listener");
     this.loadUserDetails();
    });
  }

  //TODO: remove
  forceLoadDetails() {
    this.loadUserDetails();
  }


  hasUserDetails() {
    return !!this.userDetails.name;
  }

  authenticated() {
    return this.authService.isAuthenticated();
  }

  hasError() {
    return !!this.error;
  }

  private loadUserDetails() {
    this.error = null;
    this.userService.getUserDetails()
      .subscribe({
        next: ud => this.userDetails = ud,
        error: err => this.error = err.message
      });
  }

}
