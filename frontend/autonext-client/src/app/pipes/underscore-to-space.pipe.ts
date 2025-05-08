import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'underscoreToSpace'
})
export class UnderscoreToSpacePipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    return value ? value.replace(/_/g, ' ') : '';
  }
}
