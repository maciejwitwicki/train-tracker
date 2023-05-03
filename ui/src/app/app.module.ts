import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {UserModule} from "./user/user.module";
import {FormsModule} from "@angular/forms";
import {HomeComponent} from "./home.component";
import {AuthModule} from "./auth/auth.module";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {AuthInterceptor} from "./auth/auth.interceptor";
import {LoginModule} from "./login/login.module";
import {TrainModule} from "./train/train.module";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    UserModule,
    AuthModule,
    LoginModule,
    TrainModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
