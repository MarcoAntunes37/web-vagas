import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DecimalPipe, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { CheckoutSessionStore } from '../../behavior/CheckoutSessionStore';

@Component({
  selector: 'app-success-checkout',
  imports: [NgIf, MatButtonModule, DecimalPipe],
  templateUrl: './success-checkout.component.html',
  styleUrl: './success-checkout.component.scss'
})
export class SuccessCheckoutComponent {
  sessionId = '';
  authenticated: boolean = true
  product = {
    name: '',
    clientPrice: 0,
  }
  periodText = '';

  constructor(
    private route: ActivatedRoute,
    private checkoutSessionStore: CheckoutSessionStore,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      this.sessionId = params.get('session_id') || '';
    });

    this.checkoutSessionStore.loadSession(this.sessionId).subscribe((response) => {
      this.product = {
        name: response.productName,
        clientPrice: response.price
      };
      this.periodText = response.date
    })
  }

  handleGoToDashboardClick() {
    this.router.navigate(['/dashboard/home']);
  }
}
