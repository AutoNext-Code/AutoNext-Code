import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthCardComponent } from "../../components/layouts/auth-card/auth-card.component";

@Component({
  selector: 'app-email-confirmation',
  standalone: true,
  imports: [AuthCardComponent],
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent implements OnInit, OnDestroy {

  private tokenEmail: string = "";
  public isConfirmation: boolean;
  private route = inject(ActivatedRoute);
  private closeTimeout: any;

  constructor() {
    this.isConfirmation = false;
  }

  ngOnInit(): void {
    this.tokenEmail = this.route.snapshot.paramMap.get("token") || "";
    console.log("Token email: ", this.tokenEmail);

    this.closeTimeout = setTimeout(() => {
      window.close();
    }, 1000);
  }

  ngOnDestroy(): void {
    if (this.closeTimeout) {
      clearTimeout(this.closeTimeout);
    }
  }
}
