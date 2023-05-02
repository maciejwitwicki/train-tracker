import {Injectable} from "@angular/core";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {TokenService} from "./token.service";
import {filter, map} from "rxjs";

@Injectable()
export class AuthService {

  private static readonly AUTH_BASE_URL = 'http://localhost:28080/realms/master/protocol/openid-connect';
  private static readonly AUTH_URL = AuthService.AUTH_BASE_URL + '/auth'
  private static readonly LOGOUT_URL = AuthService.AUTH_BASE_URL + '/logout'
  private static readonly ACCESS_TOKEN_URL_PARAM = 'access_token';
  private static readonly EXPIRES_IN_URL_PARAM = 'expires_in';
  private static readonly CLIENT_ID = 'train-tracker';
  private static readonly REDIRECT_URI = 'http://localhost:4200/home';

  private loggedInListeners: Array<Function> = [];

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
      `redirect_uri=${AuthService.REDIRECT_URI}`,
      'response_type=token'
    ].join('&');

    window.location.href = `${AuthService.AUTH_URL}?${params}`;
  }

  isAuthenticated(): boolean {
    return this.tokenService.hasAuthToken();
  }

  getAuthHeaders() {
    return this.tokenService.getAuthHeaders();
  }

  logout() {
    this.tokenService.removeToken();
    const params = [
      `post_logout_redirect_uri=${AuthService.REDIRECT_URI}`,
      `client_id=${AuthService.CLIENT_ID}`
    ].join('&');

    window.location.href = `${AuthService.LOGOUT_URL}?${params}`;
  }

  onLoggedInEvent(callback: Function) {
    this.loggedInListeners.push(callback);
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
            this.tokenService.saveAuthToken(accessToken, expiresIn);
            this.loggedInListeners.forEach(callback => callback());
          }
        }
      }
    })
  }

}
