import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { from, Observable, switchMap } from "rxjs";
import { environment } from '../environment/environment';
import { UserProfileService } from "../service/user-profile/user-profile.service";
import { SaveUserPreferencesRequest } from "../models/types/SaveUserPreferencesRequest";
import { UpdateUserPreferencesRequest } from "../models/types/UpdateUserPreferencesRequest";
import { JobFiltersType } from "../models/types/JobFiltersType";

@Injectable({
    providedIn: 'root'
})
export class UserPreferencesClient {
    constructor(private http: HttpClient,
        private userProfileService: UserProfileService
    ) { }

    getUserPreferences(userId: string): Observable<JobFiltersType> {
        const { userPreferencesApiUrl } = environment;
        return from(this.userProfileService.getAccessToken()).pipe(
            switchMap(accessToken =>
                this.http.get<JobFiltersType>(`${userPreferencesApiUrl}/${userId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${accessToken}`
                    }
                })
            )
        );
    }

    saveUserPreferences(userId: string, userPreferences: SaveUserPreferencesRequest)
        : Observable<SaveUserPreferencesRequest> {
        const { userPreferencesApiUrl } = environment
        return from(this.userProfileService.getAccessToken()).pipe(
            switchMap(accessToken =>
                this.http.post<SaveUserPreferencesRequest>(`${userPreferencesApiUrl}/${userId}`, userPreferences, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${accessToken}`
                    }
                })
            )
        )
    }

    updateUserPreferences(userId: string, userPreferences: UpdateUserPreferencesRequest)
        : Observable<void> {
        const { userPreferencesApiUrl } = environment
        return from(this.userProfileService.getAccessToken()).pipe(
            switchMap(accessToken =>
                this.http.put<void>(`${userPreferencesApiUrl}/${userId}`, userPreferences, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${accessToken}`
                    }
                })
            )
        )
    }
}