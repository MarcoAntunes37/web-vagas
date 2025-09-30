import { Component } from '@angular/core';
import { UserPreferencesStore } from '../../behavior/UserPreferencesStore';
import { UserProfileService } from '../../service/user-profile/user-profile.service';
import { KeycloakProfile } from 'keycloak-js';
import { JobFiltersType } from '../../models/types/JobFiltersType';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackBarComponent } from '../snack-bar/snack-bar.component';

@Component({
  selector: 'app-dashboard-home',
  imports: [MatButtonModule, MatCardModule],
  templateUrl: './dashboard-home.component.html',
  styleUrl: './dashboard-home.component.scss'
})
export class DashboardHomeComponent {
  userProfile: KeycloakProfile | null = null;
  jobFilters: JobFiltersType | null = null;


  constructor(
    private announcer: LiveAnnouncer,
    private snackBar: MatSnackBar,
    private userProfileService: UserProfileService,
    private userPreferencesStore: UserPreferencesStore,
    private router: Router) { }

  async ngOnInit() {
    this.userProfile = await this.userProfileService.getUserProfile();

    if (!this.userProfile) return;

    await this.userPreferencesStore.loadPreferences(this.userProfile.id ?? '', true);

    this.userPreferencesStore.preferences$.subscribe(pref => {
      if (pref) {
        this.jobFilters = pref;
        if (!this.jobFilters.keywords || this.jobFilters.keywords.length === 0) {
          this.openSnackBar();
        }
      }
    })
  }

  handleEditPreferencesClick() {
    this.router.navigate(['dashboard/settings']);
  }

  openSnackBar() {
    this.snackBar.openFromComponent(SnackBarComponent, {
      data: { message: 'Detectamos que não há palavras chave para busca configuradas. Dessa forma o sistema não irá buscar novas vagas.' },
      duration: Infinity,
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    })
  }
}
