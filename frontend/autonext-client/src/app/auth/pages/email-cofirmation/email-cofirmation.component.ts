import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'auth-email-cofirmation',
  imports: [],
  templateUrl: './email-cofirmation.component.html',
  styleUrl: './email-cofirmation.component.css'
})
export class EmailCofirmationComponent implements OnInit {

  private tokenEmail: string = "";

  private route = inject(ActivatedRoute)

  constructor() {}

  ngOnInit(): void {
    this.tokenEmail = this.route.snapshot.paramMap.get("token") || "";
    console.log("Token email: ", this.tokenEmail)
  }

}
