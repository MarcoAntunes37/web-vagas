import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from '../pages/dashboard/dashboard.component';
import { canActivateAuthRole } from '../guards/auth.guard';
import { DashboardHomeComponent } from '../components/dashboard-home/dashboard-home.component';
import { SettingsComponent } from '../components/settings/settings.component';

const routes: Routes = [
    {
        path: '',
        component: DashboardComponent,
        canActivate: [canActivateAuthRole],
        data: { roles: ['plan-start', 'plan-turbo'] },
        children: [
            { path: 'home', component: DashboardHomeComponent },
            { path: 'settings', component: SettingsComponent }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})

export class DashboardRoutingModule { }