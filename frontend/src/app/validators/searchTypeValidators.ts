import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";
import { SearchType } from "../models/enum/SearchType";

export function searchTypeEnumValueValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
        const isInSearchTypeEnum = (control.value || '');
        if (Object.values(SearchType).includes(isInSearchTypeEnum)) {
            return null
        }
        return { invalidSearchType: true };
    }
} 