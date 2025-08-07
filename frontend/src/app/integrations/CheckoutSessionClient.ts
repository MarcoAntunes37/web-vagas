import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import CreateCheckoutSessionResponse from "../models/types/CreateCheckoutSessionResponse";
import { environment } from "../environment/environment";
import { UserProfileService } from "../service/user-profile/user-profile.service";

@Injectable({
    providedIn: 'root'
})
export class CheckoutSessionClient {
    constructor(
        private userProfileService: UserProfileService,
        private http: HttpClient) { }

    async createCheckoutSession(price: string, customerName: string, customerEmail: string)
        : Promise<Observable<CreateCheckoutSessionResponse>> {
        const { checkoutSessionApiUrl } = environment
        const accessToken = await this.userProfileService.getAccessToken();
        return this.http.post<CreateCheckoutSessionResponse>(checkoutSessionApiUrl, {
            price, customerName, customerEmail
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }
}