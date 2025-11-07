import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";
import { countryListOptions } from "../components/shared/CONSTANTS";

export function countryValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const isInCountryArray = (control.value || '');
        if (countryListOptions.map(country => country.code).includes(isInCountryArray)) {
            return null
        }
        return { invalidCountry: true };
    }
} 