import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'uppercaseFirstLetter'
})
export class UppercaseFirstLetterPipe implements PipeTransform {

  transform(value: string | undefined): string {
    if(!value) return '';
    
    return value.charAt(0).toUpperCase() + value.slice(1);
  }

}
