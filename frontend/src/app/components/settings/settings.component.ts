import { LiveAnnouncer } from '@angular/cdk/a11y';
import { AsyncPipe, NgFor, NgIf } from '@angular/common';
import { Component, inject, signal, viewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatChipInputEvent, MatChipsModule } from '@angular/material/chips';
import { MatAccordion, MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSnackBar } from '@angular/material/snack-bar';
import { KeycloakProfile } from 'keycloak-js';
import { JobFiltersTypeJsearch } from '../../models/types/JobFiltersTypeJsearch';
import { SaveUserPreferencesJsearchRequest } from '../../models/types/SaveUserPreferencesJsearchRequest';
import { map, Observable, of, startWith } from 'rxjs';
import { UserProfileService } from '../../service/user-profile/user-profile.service';
import { employmentTypeOptions, countryListOptions } from '../shared/CONSTANTS';
import { SnackBarComponent } from '../snack-bar/snack-bar.component';
import { UserPreferencesStore } from '../../behavior/UserPreferencesStore';
@Component({
  selector: 'app-settings',
  imports: [MatFormFieldModule, FormsModule, ReactiveFormsModule, MatButtonModule, MatSelectModule, MatInputModule,
    MatSlideToggleModule, MatExpansionModule, NgIf, MatChipsModule, MatIconModule, MatSlideToggleModule, NgFor,
    MatAutocompleteModule, AsyncPipe],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {
  step = signal(0);

  private _snackBar = inject(MatSnackBar);

  announcer = inject(LiveAnnouncer);

  userProfile: KeycloakProfile | null = null

  jobFilters: JobFiltersTypeJsearch | null = null

  saveUserPreferences: SaveUserPreferencesJsearchRequest | null = null

  userHavePreferences: boolean = false

  employmentTypeOptions = employmentTypeOptions

  countryListOptions = countryListOptions

  filteredOptions: Observable<string[]> = of([])

  accordion = viewChild.required(MatAccordion)

  jobFiltersForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userProfileService: UserProfileService,
    private userPreferencesStore: UserPreferencesStore
  ) { }


  async ngOnInit() {
    this.jobFiltersForm = this.fb.group({
      keywords: [''],
      employmentTypes: [''],
      country: [''],
      remoteWork: [false],
      excludeJobPublishers: ['']
    })

    if (await this.userProfileService.getAuthenticated()) {
      this.userProfile = this.userProfileService.getUserProfile();
    };

    if (!this.userProfile) return;

    const userPreferences = this.userPreferencesStore.loadPreferences(this.userProfile.id ?? '');

    if (userPreferences === null) return;

    this.userPreferencesStore.preferences$.subscribe(pref => {
      if (pref) {
        const countryObj = countryListOptions.find(c => c.code === pref.country);
        this.jobFiltersForm.patchValue({
          keywords: pref.keywords ?? "",
          employmentTypes: pref.employmentTypes ? pref.employmentTypes.split(',') : [],
          country: countryObj?.code ?? "",
          remoteWork: pref.remoteWork ?? false,
          excludeJobPublishers: pref.excludeJobPublishers ? pref.excludeJobPublishers.split(',') : [],
        });
      }
    });

    const countryControl = this.jobFiltersForm.get('country');

    if (countryControl) {
      this.filteredOptions = countryControl.valueChanges.pipe(
        startWith(countryControl.value ?? ''),
        map(value => this._filter(value || ''))
      )
    };
  };

  get selectedEmploymentTypes(): string[] {
    return this.jobFiltersForm?.get('employmentTypes')?.value || [];
  }

  get employerNames(): string[] {
    return this.jobFiltersForm?.get('excludeJobPublishers')?.value ?? [];
  }

  trackByValue(index: number, option: { value: string }): string {
    return option.value;
  }

  getLabel(value: string): string {
    return this.employmentTypeOptions.find(opt => opt.value === value)?.label || value;
  }

  removeEmployerName(employerName: string) {
    const control = this.jobFiltersForm?.get('excludeJobPublishers');

    if (control) {
      const employers = control.value as string[] || [];
      const index = employers.indexOf(employerName);

      if (index >= 0) {
        employers.splice(index, 1);
        control.setValue([...employers]);
        this.announcer.announce(`Empregador ${employerName} removido da lista de exclusão.`);
      }
    }
  }

  addEmployerName(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      const control = this.jobFiltersForm?.get('excludeJobPublishers');

      if (control) {
        const employers = control.value as string[] || [];
        control.setValue([...employers, value]);
        this.announcer.announce(`Empregador ${value} adicionado à lista de exclusão.`);
      }
    }

    event.chipInput!.clear();
  }

  handleJobFiltersFormSubmit() { }
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.countryListOptions.filter(option => option.code
      .toLowerCase()
      .includes(filterValue))
      .map(country => country.code);
  }

  hasError(controlName: string, errorCode: string): boolean {
    const ctrl = this.jobFiltersForm?.get(controlName);
    return !!ctrl && ctrl.hasError(errorCode) && ctrl.touched;
  }

  setStep(index: number) {
    this.step.set(index);
  }

  nextStep() {
    this.step.update(i => i + 1);
  }

  prevStep() {
    this.step.update(i => i - 1);
  }

  openSnackBar() {
    this._snackBar.openFromComponent(SnackBarComponent, {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    })
  }
}
