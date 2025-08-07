import { DecimalPipe, NgClass, NgFor } from '@angular/common';
import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { UserProfileService } from '../../service/user-profile/user-profile.service';
import { MatRipple } from '@angular/material/core';
import Product from '../../models/types/Product';
import { CheckoutSessionClient } from '../../integrations/CheckoutSessionClient';
@Component({
  selector: 'app-product-card',
  imports: [MatCardModule, NgFor, MatButtonModule, NgClass, MatRipple, DecimalPipe],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss'
})
export class ProductCardComponent {
  @Input() product: Product = {} as Product;
  @Input() cssClass: string = '';
  constructor(
    private userProfileService: UserProfileService,
    private checkoutSessionClient: CheckoutSessionClient) { }

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
    } else {
      window.location.href = 'http://localhost:8181/realms/FlashVagas/protocol/openid-connect/auth?client_id=angular&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fhome&response_type=code&code_challenge=T7qQ67Ozi9_qGTRK9g5LMc8mD8Djm2OwYp5attf_r-k&code_challenge_method=S256';
    }
  }
}
