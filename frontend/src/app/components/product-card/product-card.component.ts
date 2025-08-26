import { DecimalPipe, NgClass, NgFor, NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UserProfileService } from '../../service/user-profile/user-profile.service';
import { MatRipple } from '@angular/material/core';
import Product from '../../models/types/Product';
import { CheckoutSessionClient } from '../../integrations/CheckoutSessionClient';
@Component({
  selector: 'app-product-card',
  imports: [MatCardModule, NgFor, MatButtonModule, NgClass, MatRipple, DecimalPipe, NgIf],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss'
})
export class ProductCardComponent {
  @Input() product: Product = {} as Product;
  @Input() cssClass: string = '';
  @Input() isAnnual: boolean = false;
  constructor(
    private userProfileService: UserProfileService,
    private checkoutSessionClient: CheckoutSessionClient) { }

  get periodText(): string {
    return this.isAnnual ? '/ano' : '/mês';
  }

  get isPopular(): boolean {
    return this.product.name.toLowerCase().includes('turbo');
  }

  async handleSignatureClick() {
    const isAuthenticated = await this.userProfileService.getAuthenticated();
    if (isAuthenticated) {
      const userProfile = await this.userProfileService.getUserProfile();
      if (userProfile?.email && this.product.defaultPrice) {
        await this.checkoutSessionClient.createCheckoutSession(
          this.product.defaultPrice,
          userProfile.firstName + ' ' + userProfile.lastName,
          userProfile.email)
          .then((observable: any) => {
            observable.subscribe((data: any) => {
              window.location.href = data.url;
            });
          });
      }
    }
  }
}
