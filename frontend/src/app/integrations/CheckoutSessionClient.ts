import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { from, Observable, switchMap } from "rxjs";
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

    createCheckoutSession(price: string, customerName: string, customerEmail: string)
        : Observable<CreateCheckoutSessionResponse> {
        const { checkoutSessionApiUrl } = environment
        return from(this.userProfileService.getAccessToken()).pipe(
            switchMap(token =>
                this.http.post<CreateCheckoutSessionResponse>(checkoutSessionApiUrl + "/create-checkout-session", {
                    price,
                    customerName,
                    customerEmail
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token
                    }
                })
            )
        )
    }

    getCheckoutSession(checkoutSessionId: string): Observable<GetCheckoutSessionResponse> {
        const { checkoutSessionApiUrl } = environment
        return from(this.userProfileService.getAccessToken()).pipe(
            switchMap(accessToken =>
                this.http.get<GetCheckoutSessionResponse>(checkoutSessionApiUrl + "/get-checkout-session?sessionId=" + checkoutSessionId, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + accessToken
                    }
                })
            )
        );
    }
}