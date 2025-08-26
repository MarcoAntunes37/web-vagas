import { DecimalPipe, NgIf } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { CheckoutSessionClient } from '../../integrations/CheckoutSessionClient';

@Component({
  selector: 'app-cancel-checkout',
  imports: [DecimalPipe, NgIf, MatButtonModule,],
  templateUrl: './cancel-checkout.component.html',
  styleUrl: './cancel-checkout.component.scss'
})
export class CancelCheckoutComponent {
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
  
  handleGoToPlansClick() {
    window.location.href = '/subscription';
  }
}
