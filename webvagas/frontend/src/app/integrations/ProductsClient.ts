// import { HttpClient } from "@angular/common/http";
// import { Injectable } from "@angular/core";
// import { Observable } from "rxjs";
// import { environment } from '../environment/environment';
// import { UserProfileService } from "../service/user-profile/user-profile.service";
// import Product from "../models/types/Product";

// @Injectable({
//     providedIn: 'root'
// })
// export class ProductsClient {
//     constructor(private http: HttpClient,
//         private userProfileService: UserProfileService) { }

//     async getProducts(): Promise<Observable<Product[]>> {
//         const { productsApiUrl } = environment
//         const accessToken = await this.userProfileService.getAccessToken();
//         return this.http.get<Product[]>(productsApiUrl, {
//             headers: {
//                 'Content-Type': 'application/json',
//                 'Authorization': 'Bearer ' + accessToken
//             }
//         });
//     }
// }