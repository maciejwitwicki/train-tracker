import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {AuthModule} from "../auth/auth.module";
import {TrainService} from "./train.service";

@NgModule({
  declarations: [],
  imports: [
    HttpClientModule,
    AuthModule
  ],
  providers: [TrainService],
  exports: [],
  bootstrap: []
})
export class TrainModule {
}
