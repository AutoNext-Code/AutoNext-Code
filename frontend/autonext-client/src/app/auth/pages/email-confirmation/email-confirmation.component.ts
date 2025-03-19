import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthCardComponent } from "../../components/layouts/auth-card/auth-card.component";

@Component({
  imports: [AuthCardComponent],
  templateUrl: './email-confirmation.component.html',
  styleUrl: './email-confirmation.component.css'
})
export class EmailConfirmationComponent implements OnInit {

  private tokenEmail: string = "";

  public isConfirmation: boolean;

  private route = inject(ActivatedRoute)

  constructor() {
    this.isConfirmation = true;
  }

  ngOnInit(): void {
    this.tokenEmail = this.route.snapshot.paramMap.get("token") || "";
    console.log("Token email: ", this.tokenEmail)
  }

}
