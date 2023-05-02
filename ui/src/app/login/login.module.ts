import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {LoginFormComponent} from "./login-form.component";
import {ImplicitLoginComponent} from "./implicit-login.component";
import {ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {LoginService} from "./login.service";

@NgModule({
  declarations: [LoginFormComponent, ImplicitLoginComponent],
  imports: [
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule
  ],
  providers: [LoginService],
  exports: [],
  bootstrap: []
})
export class LoginModule {
}
