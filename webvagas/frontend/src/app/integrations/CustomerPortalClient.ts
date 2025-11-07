import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../environment/environment";
import { UserProfileService } from "../service/user-profile/user-profile.service";
import CreateCustomerPortalSessionResponse from "../models/types/CreateCustomerPortalSessionResponse";

@Injectable({
    providedIn: 'root'
})
export class CustomerPortalClient {
    constructor(
        private userProfileService: UserProfileService,
        private http: HttpClient) { }

    async createCustomerPortalSession(customerEmail: string, customerName: string)
        : Promise<Observable<CreateCustomerPortalSessionResponse>> {
        const { customerPortalApiUrl } = environment
        const accessToken = await this.userProfileService.getAccessToken();
        return this.http.post<CreateCustomerPortalSessionResponse>(customerPortalApiUrl + "/create-portal-session", {
            customerEmail,
            customerName
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${accessToken}`
            }
        });
    }
}