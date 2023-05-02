import {NgModule} from "@angular/core";
import {AuthService} from "./auth.service";
import {HttpClientModule} from "@angular/common/http";
import {CookieService} from "ng2-cookies";
import {TokenService} from "./token.service";

@NgModule({
  declarations: [],
  imports: [HttpClientModule],
  providers: [AuthService, TokenService, CookieService],
  exports: [],
  bootstrap: []
})
export class AuthModule {
}
