import { Component, effect, Host, HostListener, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { UserProfileService } from './service/user-profile/user-profile.service';
import Keycloak, { KeycloakProfile } from 'keycloak-js';
import { ThemeService } from './service/theme/theme.service';
import { MatMenuModule } from '@angular/material/menu';
import { NgIf, NgClass } from '@angular/common';
import { MatRippleModule } from '@angular/material/core';
import { CustomerPortalClient } from './integrations/CustomerPortalClient';


@Component({
  selector: 'app-root',
  imports: [
    MatToolbarModule, MatSidenavModule, MatButtonModule, MatIconModule, RouterOutlet, MatButtonModule, MatToolbarModule, MatIconModule, MatButtonModule, MatMenuModule, NgIf, MatRippleModule,
    NgClass
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
  userProfile = signal<KeycloakProfile | null>(null);
  authenticated = signal(false);
  roles = signal<string[]>([]);
  screenWidth = signal(window.innerWidth);

  @HostListener('window:resize')
  onResize() {
    this.screenWidth.set(window.innerWidth);
  }

  isDesktop() {
    return this.screenWidth() > 600;
  }

  async handleProfileClick() {
    return this.userProfileService.getProfileManagementLink();
  }

  handleGotoHomeClick() {
    this.router.navigate(['dashboard/home']);
  }

  handleLogoutClick() {
    this.userProfileService.logout();
  }

  constructor(
    private themeService: ThemeService,
    private userProfileService: UserProfileService,
    private customerPortalClient: CustomerPortalClient,
    public keycloak: Keycloak,
    private router: Router) {

    effect(async () => {
      this.userProfile.set(await this.userProfileService.getUserProfile());

      this.userProfileService.userProfile$.subscribe(profile => {
        this.userProfile.set(profile);
      });

      this.roles.set(this.keycloak.realmAccess?.roles || []);

      this.authenticated.set(this.keycloak.authenticated ?? false);
    });
  }

  ngOnInit() {
    this.themeService.applyTheme();
  }

  handleDashboardClick() {
    this.router.navigate(['dashboard/home']);
  }

  handleSettingsClick() {
    this.router.navigate(['dashboard/settings']);
  }

  handleCustomerPortalClick() {
    const customerFullName = this.userProfile()?.firstName + ' ' + this.userProfile()?.lastName
    this.customerPortalClient.createCustomerPortalSession(
      this.userProfile()?.email ?? '',
      customerFullName
    )
      .then((response) => {
        response.subscribe((data) => {
          window.location.href = data.url
        })
      });
  }
}
