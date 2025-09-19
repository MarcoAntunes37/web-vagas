import { Injectable, OnInit } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import Keycloak from 'keycloak-js';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {
  private userProfileSubject = new BehaviorSubject<KeycloakProfile | null>(null);
  userProfile$ = this.userProfileSubject.asObservable();

  constructor(private keycloak: Keycloak) {
    this.initUserProfile();
  }

  private async initUserProfile() {
    if (this.keycloak.authenticated) {
      const profile = await this.keycloak.loadUserProfile();
      this.userProfileSubject.next(profile);
    }
  }

  async getUserProfile(): Promise<KeycloakProfile | null> {
    return this.userProfileSubject.getValue();
  }

  async refreshUserProfile(): Promise<KeycloakProfile | null> {
    const profile = await this.keycloak.loadUserProfile();
    this.userProfileSubject.next(profile);
    return profile;
  }

  async getAuthenticated(): Promise<boolean> {
    return this.keycloak.authenticated ?? false;
  }

  async getProfileManagementLink(): Promise<void> {
    return this.keycloak.accountManagement();
  }

  async logout(): Promise<void> {
    await this.keycloak.logout();
    this.userProfileSubject.next(null);
  }

  async getAccessToken(): Promise<string | undefined> {
    return this.keycloak.token;
  }

  async getRefreshToken(): Promise<string | undefined> {
    return this.keycloak.refreshToken;
  }
}
