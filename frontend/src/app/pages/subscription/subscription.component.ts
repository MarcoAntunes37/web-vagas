import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import Product from '../../models/types/Product';
import { NgFor, NgIf } from '@angular/common';
import { ProductCardComponent } from '../../components/product-card/product-card.component';
import { productListOptions } from '../../components/shared/CONSTANTS';

@Component({
  selector: 'app-subscription',
  imports: [MatCardModule, MatButtonModule, ProductCardComponent, NgFor, NgIf],
  templateUrl: './subscription.component.html',
  styleUrl: './subscription.component.scss'
})
export class SubscriptionComponent {
  products: Product[] = [];

  async ngOnInit() {
    this.products = productListOptions;
  }

  trackByProductId(index: number, product: Product) {
    return product.id;
  }
}
