import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { KeycloakProfile } from 'keycloak-js';
import { UserProfileService } from '../../service/user-profile/user-profile.service';

@Component({
  selector: 'app-dashboard',
  imports: [RouterOutlet],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {
  userProfile: KeycloakProfile | null = null
  constructor(private userProfileService: UserProfileService) { }

  async ngOnInit() {
    this.userProfile = await this.userProfileService.getUserProfile(); 
  }
}
