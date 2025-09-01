import { Component, OnInit } from '@angular/core';
import { ServiceTypeService } from '../../../services/carServiceType/Service-type.service';
import { ServiceType } from '../../../interfaces/ServiceType';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { RouterModule } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatTableModule } from '@angular/material/table';
import { JobDetailDTO } from '../../../interfaces/JobDetailDTO';
import { JobDetails } from '../../../services/job-details';
import { UserDTO } from '../../../interfaces/UserDTO';
import { QuotationDetailDTO } from '../../../interfaces/QuotationDetailDTO';
import { ClientRepsService } from '../../../services/client-reps-service';
import { InvoicesPerClientDTO } from '../../../interfaces/InvoicesPerClientDTO';
import { PartsPerVehicleDTO } from '../../../interfaces/PartsPerVehicleDTO';


@Component({
  selector: 'app-main-users',
  imports: [CommonModule, FormsModule, MatButtonModule, MatSidenavModule, MatIconModule, RouterModule, MatCardModule, MatListModule, MatTableModule],
  templateUrl: './main-users.html',
  styleUrl: './main-users.css'
})

export class MainUsers {

  displayedColumns: any;
  quotations: QuotationDetailDTO[] = [];
  parts: PartsPerVehicleDTO[] = [];
  invoices: InvoicesPerClientDTO[] = [];


  editService(_t24: ServiceType) {
    console.log('Edit service:', _t24);

  }

  services: ServiceType[] = [];

  jobDetails: JobDetailDTO[] = [];

  user_logged: UserDTO = JSON.parse(localStorage.getItem('user') || '{}');


  constructor(private serviceTypeService: ServiceTypeService, private jobService: JobDetails, private clientRepsService: ClientRepsService) { }

  ngOnInit(): void {
    const user = localStorage.getItem('user');
    if (user) {
      this.user_logged = JSON.parse(user);
      console.log(this.user_logged.name);
    } else {
      console.log('No user logged in');
    }
    this.loadServices();
    this.loadQuotations();
    this.loadParts();
    this.loadInvoices();
  }

  loadServices(): void {

    this.jobService.getJobDetailsByUserId(this.user_logged.userId).subscribe(data => {
      this.jobDetails = data;
      console.log('Job details loaded:', this.jobDetails);
    });

  }

  loadQuotations(): void {
    this.clientRepsService.getQuotationsByUser(this.user_logged.userId).subscribe(data => {
      this.quotations = data;
      console.log('Quotations loaded:', this.quotations);
    });
  }

  loadParts(): void {
    this.clientRepsService.getPartsByUser(this.user_logged.userId).subscribe(data => {
      this.parts = data;
      console.log('Parts loaded:', this.parts);
    });
  }

  loadInvoices(): void {
    this.clientRepsService.getInvoicesByUser(this.user_logged.userId).subscribe(data => {
      this.invoices = data;
      console.log('Invoices loaded:', this.invoices);
    });
  }

}