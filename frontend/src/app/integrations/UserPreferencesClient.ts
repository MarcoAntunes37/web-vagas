import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { from, Observable, switchMap } from "rxjs";
import { environment } from '../environment/environment';
import { UserProfileService } from "../service/user-profile/user-profile.service";
import { SaveUserPreferencesJsearchRequest } from "../models/types/SaveUserPreferencesJsearchRequest";
import { UpdateUserPreferencesJsearchRequest } from "../models/types/UpdateUserPreferencesJsearchRequest";
import { JobFiltersTypeJsearch } from "../models/types/JobFiltersTypeJsearch";

@Injectable({
    providedIn: 'root'
})
export class UserPreferencesClient {
    constructor(private http: HttpClient,
        private userProfileService: UserProfileService
    ) { }

    getUserPreferences(userId: string): Observable<JobFiltersTypeJsearch> {
        const { userPreferencesApiUrl } = environment;
        return from(this.userProfileService.getAccessToken()).pipe(
            switchMap(token =>
                this.http.get<JobFiltersTypeJsearch>(`${userPreferencesApiUrl}/${userId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                })
            )
        );
    }

    saveUserPreferences(userPreferences: SaveUserPreferencesJsearchRequest)
        : Observable<SaveUserPreferencesJsearchRequest> {
        const { userPreferencesApiUrl } = environment
        const accessToken = this.userProfileService.getAccessToken();
        return this.http.post<SaveUserPreferencesJsearchRequest>(userPreferencesApiUrl, userPreferences, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }

    updateUserPreferences(userPreferences: UpdateUserPreferencesJsearchRequest)
        : Observable<UpdateUserPreferencesJsearchRequest> {
        const { userPreferencesApiUrl } = environment
        const accessToken = this.userProfileService.getAccessToken();
        return this.http.put<any>(userPreferencesApiUrl + "/", userPreferences, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }
}