import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { CheckoutSessionClient } from "../integrations/CheckoutSessionClient";
import GetCheckoutSessionResponse from "../models/types/GetCheckoutSessionResponse";

@Injectable({ providedIn: 'root' })
export class CheckoutSessionStore {
    private readonly _session$ = new BehaviorSubject<GetCheckoutSessionResponse | null>(null);
    readonly session$ = this._session$.asObservable();

    constructor(private checkoutSessionClient: CheckoutSessionClient) { }

    loadSession(sessionId: string): Observable<GetCheckoutSessionResponse> {
        return this.checkoutSessionClient.getCheckoutSession(sessionId).pipe(
            tap(ses => this._session$.next(ses))
        );
    }

    createSession(price: string, customerName: string, customerEmail: string) {
        return this.checkoutSessionClient.createCheckoutSession(price, customerName, customerEmail);
    }
}