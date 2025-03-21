import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthCardComponent } from '../../components/layouts/auth-card/auth-card.component';
import { AuthService } from '@auth/services/auth.service';

@Component({
  selector: 'app-email-confirmation',
  standalone: true,
  imports: [AuthCardComponent],
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css'],
})
export class EmailConfirmationComponent implements OnInit, OnDestroy {
  private tokenEmail: string = '';
  public isConfirmation: boolean;
  private route = inject(ActivatedRoute);
  private closeTimeout: any;
  private authService = inject(AuthService);

  constructor() {
    this.isConfirmation = false;
  }

  ngOnInit(): void {
    this.tokenEmail = this.route.snapshot.paramMap.get('token') || '';

    this.authService.confirmEmail(this.tokenEmail).subscribe({
      next: (message: string) => {
        this.isConfirmation = !!message;
      },
      error: (error: any) => {
        console.error('Error al confirmar email:', error);
        this.isConfirmation = false;
      },
    });

    this.closeTimeout = setTimeout(() => {
      window.close();
    }, 30000);
  }

  ngOnDestroy(): void {
    if (this.closeTimeout) {
      clearTimeout(this.closeTimeout);
    }
  }
}
