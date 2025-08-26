import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import CreateCheckoutSessionResponse from "../models/types/CreateCheckoutSessionResponse";
import { environment } from "../environment/environment";
import { UserProfileService } from "../service/user-profile/user-profile.service";
import GetCheckoutSessionResponse from "../models/types/GetCheckoutSessionResponse";

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
        return this.http.post<CreateCheckoutSessionResponse>(checkoutSessionApiUrl + "/create-checkout-session", {
            price, customerName, customerEmail
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }

    async getCheckoutSession(checkoutSessionId: string): Promise<Observable<GetCheckoutSessionResponse>> {
        const { checkoutSessionApiUrl } = environment
        const accessToken = await this.userProfileService.getAccessToken();
        return this.http.get<GetCheckoutSessionResponse>(checkoutSessionApiUrl + "/get-checkout-session?sessionId=" + checkoutSessionId, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }
}