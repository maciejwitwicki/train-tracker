import {Component, NgZone, OnInit} from '@angular/core';
import {UserDetails} from "./user.model";
import {AuthService} from "../auth/auth.service";
import {UserService} from "./user.service";

@Component({
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']

})
export class UserDetailsComponent implements OnInit {

  userDetails: UserDetails = {} as UserDetails;
  error: any

  constructor(private authService: AuthService, private userService: UserService, private zone: NgZone) {
  }

  ngOnInit(): void {
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
        next: ud => {
          this.zone.run(() => {
            this.userDetails = ud;
          })
        },
        error: err => this.error = err.message
      });
  }

}
