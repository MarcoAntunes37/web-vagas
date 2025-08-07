import { Component } from '@angular/core';
import { SettingsComponent } from "../settings/settings.component";

@Component({
  selector: 'app-dashboard-home',
  imports: [SettingsComponent],
  templateUrl: './dashboard-home.component.html',
  styleUrl: './dashboard-home.component.scss'
})
export class DashboardHomeComponent {

}
