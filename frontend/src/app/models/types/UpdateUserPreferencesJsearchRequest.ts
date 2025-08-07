export interface UpdateUserPreferencesJsearchRequest {
    userId: string,
    keywords: string,
    employmentTypes: string,
    remoteWork: boolean,
    country: string,
    excludeJobPublishers: string
}