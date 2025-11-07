import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import Product from '../../models/types/Product';
import { NgFor, NgIf } from '@angular/common';
import { ProductCardComponent } from '../../components/product-card/product-card.component';
import { productListOptions } from '../../components/shared/CONSTANTS';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-subscription',
  imports: [MatCardModule, MatButtonModule, ProductCardComponent, NgFor, MatTabsModule],
  templateUrl: './subscription.component.html',
  styleUrl: './subscription.component.scss'
})
export class SubscriptionComponent {
  products: Product[] = [];
  anualProducts: Product[] = [];
  mensalProducts: Product[] = [];

  async ngOnInit() {
    this.products = productListOptions;

    this.anualProducts = this.products.filter((product) => product.name.toLowerCase().includes('anual'));
    this.mensalProducts = this.products.filter((product) => product.name.toLowerCase().includes('mensal'));
  }

  trackByProductId(index: number, product: Product) {
    return product.id;
  }
}
