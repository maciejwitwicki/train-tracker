import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home.component";
import {ImplicitLoginComponent} from "./login/implicit-login.component";
import {LoginFormComponent} from "./login/login-form.component";
import {RegisterComponent} from "./user/register.component";
import {UserDetailsComponent} from "./user/user-details.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'home'},
  { path: 'home', component: HomeComponent},
  { path: 'login-implicit', component: ImplicitLoginComponent},
  { path: 'login-form', component: LoginFormComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'user-details', component: UserDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: false})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
