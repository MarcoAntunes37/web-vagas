import { DecimalPipe, NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { CheckoutSessionClient } from '../../integrations/CheckoutSessionClient';
import { CheckoutSessionStore } from '../../behavior/CheckoutSessionStore';

@Component({
  selector: 'app-cancel-checkout',
  imports: [DecimalPipe, NgIf, MatButtonModule,],
  templateUrl: './cancel-checkout.component.html',
  styleUrl: './cancel-checkout.component.scss'
})
export class CancelCheckoutComponent {
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

  handleGoToPlansClick() {
    this.router.navigate(['/subscription']);
  }
}
