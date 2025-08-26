import { Component, effect, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { UserProfileService } from './service/user-profile/user-profile.service';
import Keycloak, { KeycloakProfile } from 'keycloak-js';
import { ThemeService } from './service/theme/theme.service';
import { MatMenuModule } from '@angular/material/menu';
import { NgIf } from '@angular/common';
import { MatRippleModule } from '@angular/material/core';


@Component({
  selector: 'app-root',
  imports: [
    MatToolbarModule, MatSidenavModule, MatButtonModule, MatIconModule, RouterOutlet, MatButtonModule, MatToolbarModule, MatIconModule, MatButtonModule, MatMenuModule, NgIf, MatRippleModule
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

  async handleProfileClick() {
    return this.userProfileService.getProfileManagementLink();
  }

  handleGotoHomeClick() {
    this.router.navigate(['dashboard/']);
  }

  handleLogoutClick() {
    this.userProfileService.logout();
  }

  constructor(
    private themeService: ThemeService,
    private userProfileService: UserProfileService,
    public keycloak: Keycloak,
    private router: Router) {
    effect(async () => {
      this.userProfile.set(this.userProfileService.getUserProfile());

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
    this.router.navigate(['dashboard']);
  }

  handleSettingsClick() {
    this.router.navigate(['dashboard/settings']);
  }
}
