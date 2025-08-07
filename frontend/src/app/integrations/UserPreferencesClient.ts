import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { JobFiltersType } from "../models/types/JobFiltersType";
import { Observable } from "rxjs";
import { environment } from '../environment/environment';
import { UpdateUserPreferencesRequest } from "../models/types/UpdateUserPreferencesRequest";
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

    async getUserPreferences(userId: string): Promise<Observable<JobFiltersTypeJsearch>> {
        const { userPreferencesApiUrl } = environment
        const accessToken = await this.userProfileService.getAccessToken();
        return this.http.get<JobFiltersTypeJsearch>(userPreferencesApiUrl + "/" + userId, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }

    async saveUserPreferences(userPreferences: SaveUserPreferencesJsearchRequest)
        : Promise<Observable<SaveUserPreferencesJsearchRequest>> {
        const { userPreferencesApiUrl } = environment
        const accessToken = await this.userProfileService.getAccessToken();
        return this.http.post<SaveUserPreferencesJsearchRequest>(userPreferencesApiUrl, userPreferences, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }

    async updateUserPreferences(userPreferences: UpdateUserPreferencesJsearchRequest)
        : Promise<Observable<UpdateUserPreferencesJsearchRequest>> {
        const { userPreferencesApiUrl } = environment
        const accessToken = await this.userProfileService.getAccessToken();
        return this.http.put<any>(userPreferencesApiUrl + "/", userPreferences, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + accessToken
            }
        });
    }
}