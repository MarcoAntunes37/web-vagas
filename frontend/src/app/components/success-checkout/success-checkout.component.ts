import { Component, inject } from '@angular/core';
import { CheckoutSessionClient } from '../../integrations/CheckoutSessionClient';
import { ActivatedRoute } from '@angular/router';
import { DecimalPipe, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-success-checkout',
  imports: [NgIf, MatButtonModule, DecimalPipe ],
  templateUrl: './success-checkout.component.html',
  styleUrl: './success-checkout.component.scss'
})
export class SuccessCheckoutComponent {
  checkoutServiceClient = inject(CheckoutSessionClient);
  sessionId = '';
  authenticated: boolean = true
  product = {
    name: 'placeholder',
    clientPrice: 0,
  }
  periodText = 'placeholder';

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      this.sessionId = params.get('sessionId') || '';
    });

    this.checkoutServiceClient.getCheckoutSession(this.sessionId).then((response) => {
      response.subscribe((data) => {
        this.product = {
          name: data.productName,
          clientPrice: data.price
        };
        this.periodText = data.date
      })
    })
  }

  handleGoToDashboardClick() {
    window.location.href = '/dashboard';
  }
}
