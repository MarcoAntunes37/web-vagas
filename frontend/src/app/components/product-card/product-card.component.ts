import { DecimalPipe, NgClass, NgFor, NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UserProfileService } from '../../service/user-profile/user-profile.service';
import { MatRipple } from '@angular/material/core';
import Product from '../../models/types/Product';
import { CheckoutSessionStore } from '../../behavior/CheckoutSessionStore';
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
    private checkoutSessionStore: CheckoutSessionStore) { }

  get periodText(): string {
    return this.isAnnual ? '/ano' : '/mês';
  }

  get isPopular(): boolean {
    return this.product.name.toLowerCase().includes('turbo');
  }

  async handleSignatureClick() {
    const isAuthenticated = await this.userProfileService.getAuthenticated();
    if (isAuthenticated) {
      const userProfile = this.userProfileService.getUserProfile();
      if (userProfile?.email && this.product.defaultPrice) {
        this.checkoutSessionStore.createSession(
          this.product.defaultPrice,
          userProfile.firstName + ' ' + userProfile.lastName,
          userProfile.email).subscribe(
            (response) => {
              if (response) {
                window.location.href = response.url;
              }
            }
          );
      }
    }
  }
}
