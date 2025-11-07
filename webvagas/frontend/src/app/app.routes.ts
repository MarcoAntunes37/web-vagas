import { Routes } from '@angular/router';
import { SuccessCheckoutComponent } from './components/success-checkout/success-checkout.component';
import { CancelCheckoutComponent } from './components/cancel-checkout/cancel-checkout.component';
import { ForbiddenComponent } from './pages/forbidden/forbidden.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { SubscriptionComponent } from './pages/subscription/subscription.component';

export const routes: Routes = [
    {
        path: 'dashboard',
        loadChildren: () => import('./routes/dashboard-routing.module').then(m => m.DashboardRoutingModule),
    },
    { path: 'subscription', component: SubscriptionComponent },
    { path: 'success', component: SuccessCheckoutComponent },
    { path: 'cancel', component: CancelCheckoutComponent },
    { path: 'forbidden', component: ForbiddenComponent },
    { path: '**', component: NotFoundComponent },
];