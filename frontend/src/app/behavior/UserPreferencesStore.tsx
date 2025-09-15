import { Injectable } from "@angular/core";
import { JobFiltersType } from "../models/types/JobFiltersType";
import { BehaviorSubject } from "rxjs/internal/BehaviorSubject";
import { UserPreferencesClient } from "../integrations/UserPreferencesClient";
import { catchError } from "rxjs/operators";
import { of } from "rxjs/internal/observable/of";
import { SaveUserPreferencesRequest } from "../models/types/SaveUserPreferencesRequest";
import { UpdateUserPreferencesRequest } from "../models/types/UpdateUserPreferencesRequest";

@Injectable({ providedIn: 'root' })
export class UserPreferencesStore {
    private readonly _preferences$ = new BehaviorSubject<JobFiltersType | null>(null);
    readonly preferences$ = this._preferences$.asObservable();

    constructor(private userPreferencesClient: UserPreferencesClient) { }

    loadPreferences(userId: string) {
        this.userPreferencesClient.getUserPreferences(userId).pipe(
            catchError(err => {
                if (err.status === 404) {
                    return this.userPreferencesClient.saveUserPreferences(userId, {
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

    savePreferences(userId: string, userPreferences: SaveUserPreferencesRequest) {
        console.log("userPreferences", userPreferences);
        this._preferences$.next(userPreferences);
        this.userPreferencesClient.saveUserPreferences(userId, userPreferences).pipe(
            catchError(err => {
                console.error("Erro ao salvar preferências", err);
                return of(null);
            })
        ).subscribe(
            pref => console.log("pref", pref),
        )
    }


    updatePreferences(userId: string, userPreferences: UpdateUserPreferencesRequest) {
        console.log("userPreferences", userPreferences);
        this._preferences$.next(userPreferences);
        this.userPreferencesClient.updateUserPreferences(userId, userPreferences).pipe(
            catchError(err => {
                console.error("Erro ao atualizar preferências", err);
                return of(null);
            })
        ).subscribe(
            pref => console.log("pref", pref),
        )
    }
}