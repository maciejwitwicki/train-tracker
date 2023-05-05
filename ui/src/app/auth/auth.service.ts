import {Injectable} from "@angular/core";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {filter, map} from "rxjs";

@Injectable()
export class AuthService {

  private static readonly AUTH_BASE_URL = 'http://localhost:28080/realms/train-station/protocol/openid-connect';
  private static readonly AUTH_URL = AuthService.AUTH_BASE_URL + '/auth'
  private static readonly LOGOUT_URL = AuthService.AUTH_BASE_URL + '/logout'
  private static readonly ACCESS_TOKEN_URL_PARAM = 'access_token';
  private static readonly EXPIRES_IN_URL_PARAM = 'expires_in';
  private static readonly CLIENT_ID = 'train-tracker';
  private static readonly POST_LOGIN_REDIRECT_URI = 'http://localhost:4200/user-details';
  private static readonly POST_LOGOUT_REDIRECT_URI = 'http://localhost:4200';

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private httpClient: HttpClient,
    private tokenService: TokenService) {
    this.listenToNavigationEndEvent(router);
  }

  redirectToLoginPage() {
    const params = [
      'scope=openid',
      `client_id=${AuthService.CLIENT_ID}`,
      `redirect_uri=${AuthService.POST_LOGIN_REDIRECT_URI}`,
      'response_type=token'
    ].join('&');

    window.location.href = `${AuthService.AUTH_URL}?${params}`;
  }

  isAuthenticated(): boolean {
    return this.tokenService.hasAccessToken();
  }

  getToken() {
    return this.tokenService.getAccessToken();
  }

  logout() {
    this.tokenService.removeToken();
    const params = [
      `post_logout_redirect_uri=${AuthService.POST_LOGOUT_REDIRECT_URI}`,
      `client_id=${AuthService.CLIENT_ID}`
    ].join('&');

    window.location.href = `${AuthService.LOGOUT_URL}?${params}`;
  }

  setAccessToken(token: string, expiresInSeconds: number) {
    this.updateAccessToken(token, expiresInSeconds);
  }


  private updateAccessToken(token: string, expiresIn: number | null) {
    this.tokenService.saveAccessToken(token, expiresIn);
  }

  private listenToNavigationEndEvent(router: Router) {
    router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.activatedRoute),
    ).subscribe({
      next: route => this.onRouteChanged(route)
    });
  }

  private onRouteChanged(route: ActivatedRoute) {
    // when redirected back from keycloak, the parameters are after the # symbol (fragment)
    route.fragment.subscribe({
      next: fragment => {
        if (fragment) {
          const params = new URLSearchParams(fragment);
          const accessToken = params.get(AuthService.ACCESS_TOKEN_URL_PARAM);
          const expiresIn = params.get(AuthService.EXPIRES_IN_URL_PARAM);
          if (accessToken) {
            this.updateAccessToken(accessToken, expiresIn ? +expiresIn : null);
          }
        }
      }
    })
  }
}
