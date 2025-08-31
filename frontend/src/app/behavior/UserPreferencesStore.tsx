import { Injectable } from "@angular/core";
import { JobFiltersTypeJsearch } from "../models/types/JobFiltersTypeJsearch";
import { BehaviorSubject } from "rxjs/internal/BehaviorSubject";
import { UserPreferencesClient } from "../integrations/UserPreferencesClient";
import { catchError } from "rxjs/operators";
import { of } from "rxjs/internal/observable/of";
import { SaveUserPreferencesJsearchRequest } from "../models/types/SaveUserPreferencesJsearchRequest";

@Injectable({ providedIn: 'root' })
export class UserPreferencesStore {
    private readonly _preferences$ = new BehaviorSubject<JobFiltersTypeJsearch | null>(null);
    readonly preferences$ = this._preferences$.asObservable();

    constructor(private userPreferencesClient: UserPreferencesClient) { }

    loadPreferences(userId: string) {
        this.userPreferencesClient.getUserPreferences(userId).pipe(
            catchError(err => {
                if (err.status === 404) {
                    return this.userPreferencesClient.saveUserPreferences({
                        userId,
                        keywords: '',
                        employmentTypes: '',
                        remoteWork: false,
                        country: 'br',
                        excludeJobPublishers: ''
                    });
                }
                console.error("Erro ao buscar preferências", err);
                return of(null);
            })
        ).subscribe(pref => this._preferences$.next(pref));
    }

    savePreferences(userPreferences: SaveUserPreferencesJsearchRequest) {
        this.userPreferencesClient.saveUserPreferences(userPreferences)
            .subscribe(pref => this._preferences$.next(pref));
    }
}