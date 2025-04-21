import { Component, Input } from '@angular/core';

@Component({
  selector: 'shared-warning-message',
  imports: [],
  templateUrl: './warning-message.component.html',
  styleUrl: './warning-message.component.css'
})
export class WarningMessageComponent {
    @Input({required: true})
    title!: string ;

    @Input({required: true})
    text!: string ;

    @Input()
    icon: string = "priority_high" ;
}
