import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from "./user.service";

@Component({templateUrl: 'register.component.html'})
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  submitted = false;
  created = false;
  error: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required]
    });
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.error = null;
    const username = this.getUsernameField().value;
    const password = this.getPasswordField().value;
    const email = this.getEmailField().value;
    this.userService.createUser(username, password, email)
      .subscribe({
        next: (tokenResponse) => {
          this.created = true;
        },
        error: (err) => {
          this.loading = false;
          this.error = `Failed to register user: ${err.message}`;
          console.error('Create user error', err.message, err.error.errorMessage);
        }
      });
  }

  getUsernameField() {
    return this.getFormField('username');
  }

  getPasswordField() {
    return this.getFormField('password');
  }

  getEmailField() {
    return this.getFormField('email');
  }

  getFormPasswordRequired(): boolean {
    return this.getFormFieldErrors(this.getPasswordField());
  }

  getFormUsernameRequired(): boolean {
    return this.getFormFieldErrors(this.getUsernameField());
  }

  getFormEmailRequired(): boolean {
    return this.getFormFieldErrors(this.getEmailField());
  }

  private getFormField(field: string) {
    return this.form.controls[field];
  }

  private getFormFieldErrors(field: AbstractControl<any, any>) {
    return field.errors!['required'];
  }
}
