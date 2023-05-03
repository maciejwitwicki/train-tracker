import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {AuthModule} from "../auth/auth.module";
import {TrainService} from "./train.service";
import {EventsService} from "./events.service";
import {TrainComponent} from "./train.component";
import {BrowserModule} from "@angular/platform-browser";
import {GoogleMapsModule} from "@angular/google-maps";
import {MapService} from "./map.service";

@NgModule({
  declarations: [TrainComponent],
  imports: [
    HttpClientModule,
    AuthModule,
    BrowserModule,
    GoogleMapsModule
  ],
  providers: [TrainService, EventsService, MapService],
  exports: [],
  bootstrap: []
})
export class TrainModule {
}
