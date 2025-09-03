import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatOption } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatMenu } from '@angular/material/menu';
import { MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';

// Importaciones locales
import { JobDTO } from '../../../../interfaces/JobDTO';
import { JobService } from '../../../../services/job-service/Job.service';
import { AssigmentsJobComponent } from './assigments-job/assigments-job';
import { InvoicesJobComponent } from './invoices-job/invoices-job';
import { PartsJobComponent } from './parts-job/parts-job';

@Component({
  selector: 'app-detail-job',
  imports: [
    CommonModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatTableModule,
    MatMenuModule,
    AssigmentsJobComponent,
  ],
  templateUrl: './detail-job.html',
  styleUrl: './detail-job.css',
})
export class DetailJobComponent implements OnInit {
  job!: JobDTO;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private jobService: JobService
  ) {}

  ngOnInit(): void {
    this.setJobs();
  }

  setJobs(): void {
    const jobId = this.route.snapshot.params['id'];
    const jobIdNumber = parseInt(jobId, 10);
    console.log('JobId:', this.route.snapshot.params['id']);
    if (jobId) {
      this.jobService.getJobById(jobIdNumber).subscribe((data: JobDTO) => {
        this.job = data;
        this.jobService.setJob(this.job);
        console.log('Job cargado:', this.job);
      });
    }
  }

  goBack() {
    window.history.back();
  }
}
