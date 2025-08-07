export interface SaveUserPreferencesRequest {
    userId: string,
    keywords: string,
    salaryMin: number,
    salaryMax: number,
    includeUnknown: boolean,
    searchType: string,
    workingDayType: string,
    contractType: string,
    address: string,
    distance: number,
    country: string
}