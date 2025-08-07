import { Component, inject } from '@angular/core';
import { UserProfileService } from '../../service/user-profile/user-profile.service';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forbidden',
  imports: [MatButtonModule, NgIf],
  templateUrl: './forbidden.component.html',
  styleUrl: './forbidden.component.scss'
})
export class ForbiddenComponent {
  authenticated: boolean = false;
  route = inject(Router);
  constructor(private userProfileService: UserProfileService, route: Router) {
    this.userProfileService = this.userProfileService
  }

  async ngOnInit() {
    if (await this.userProfileService.getAuthenticated()) {
      this.authenticated = true
    }
  }

  handleGoToPlansClick() {
    this.route.navigate(['subscription']);
  }
}
