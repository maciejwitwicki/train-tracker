import {Injectable} from "@angular/core";
import {HttpHeaders} from "@angular/common/http";
import {CookieService} from "ng2-cookies";

@Injectable()
export class TokenService {

  private static readonly TOKEN_COOKIE_NAME = 'access_token';
  private static readonly DEFAULT_TOKEN_EXPIRES_MILLIS = 900000;

  constructor(private cookieService: CookieService) {
  }

  saveAccessToken(token: string, expiresIn: number | null) {
    const expiresValue = expiresIn ? expiresIn * 1000 : TokenService.DEFAULT_TOKEN_EXPIRES_MILLIS;
    const expireDate = new Date().getTime() + expiresValue;
    console.log(`Setting access token to expire in  ${expiresValue} ms`);
    this.cookieService.set(TokenService.TOKEN_COOKIE_NAME, token, expireDate);
  }

  getAuthHeaders() {
    const cookieValue = `Bearer ${this.getAccessToken()}`;
    return new HttpHeaders({'Authorization': cookieValue});
  }

  hasAccessToken(): boolean {
    return this.cookieService.check(TokenService.TOKEN_COOKIE_NAME);
  }

  removeToken() {
    this.cookieService.delete(TokenService.TOKEN_COOKIE_NAME);
  }

  getAccessToken(): string | null {
    if (this.hasAccessToken()) {
      return this.cookieService.get(TokenService.TOKEN_COOKIE_NAME);
    } else {
      throw new Error("No access token");
    }
  }

}
