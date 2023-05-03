import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {LoginService} from "./login.service";

@Component({templateUrl: 'login-form.component.html'})
export class LoginFormComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService
  ) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  onSubmit() {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    const username = this.getUsernameField().value;
    const password = this.getPasswordField().value;
    this.loginService.login(username, password)
      .subscribe({
        next: (token) => {
          this.router.navigateByUrl('/user-details');
        },
        error: () => {
          this.loading = false;
        }
      });
  }

  getUsernameField() {
    return this.form.controls['username'];
  }

  getPasswordField() {
    return this.form.controls['password'];
  }

  getFormPasswordRequired(): boolean {
    return this.getPasswordField().errors!['required'];
  }

  getFormUsernameRequired(): boolean {
    return this.getUsernameField().errors!['required'];
  }
}
