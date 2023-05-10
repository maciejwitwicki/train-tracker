export interface UserDetails {
  firstname: string,
  lastname: string,
  username: string,
  email: string,
}

export interface CreateUserRequest extends UserDetails {
  password: string
}
