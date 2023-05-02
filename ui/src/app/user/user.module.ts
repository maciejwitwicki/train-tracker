import {NgModule} from "@angular/core";
import {UserService} from "./user.service";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [],
  imports: [HttpClientModule],
  providers: [UserService],
  exports: [],
  bootstrap: []
})
export class UserModule {
}
