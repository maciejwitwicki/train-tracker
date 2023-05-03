import {NgModule} from "@angular/core";
import {UserService} from "./user.service";
import {HttpClientModule} from "@angular/common/http";
import {RegisterComponent} from "./register.component";
import {ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {UserDetailsComponent} from "./user-details.component";

@NgModule({
  declarations: [RegisterComponent, UserDetailsComponent],
  imports: [
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule
  ],
  providers: [UserService],
  exports: [],
  bootstrap: []
})
export class UserModule {
}
