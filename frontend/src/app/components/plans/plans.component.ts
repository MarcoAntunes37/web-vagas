import { Component, inject } from '@angular/core';
import { ProductCardComponent } from '../product-card/product-card.component';
import { NgFor, NgIf } from '@angular/common';
import Product from '../../models/types/Product';
import { ProductsClient } from '../../integrations/ProductsClient';
@Component({
  selector: 'app-plans',
  imports: [ProductCardComponent, NgFor, NgIf],
  templateUrl: './plans.component.html',
  styleUrl: './plans.component.scss'
})
export class PlansComponent {
  products: Product[] = [];
  productsClient = inject(ProductsClient)

  async ngOnInit() {
    (await this.productsClient.getProducts())
      .subscribe((data: any) => {
        this.products = data.products;
      })
  }
  trackByProductId(index: number, product: Product) {
    return product.id;
  }
}