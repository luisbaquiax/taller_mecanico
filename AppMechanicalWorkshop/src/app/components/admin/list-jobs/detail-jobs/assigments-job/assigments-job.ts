//import { I } from '@angular/cdk/keycodes';
import { Component, Input, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
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
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { DatePipe } from '@angular/common';
import { MatListModule } from '@angular/material/list';

// Importaciones locales
import { JobDTO } from '../../../../../interfaces/JobDTO';
import { UserService } from '../../../../../services/user-service/User.service';
import { UserDTO } from '../../../../../interfaces/UserDTO';
import { JobAssigmentService } from '../../../../../services/job-service/JobAssigment.service';
import { JobAssigmentDTO } from '../../../../../interfaces/JobAssigmentDTO';
import { JobService } from '../../../../../services/job-service/Job.service';

@Component({
  selector: 'app-assigments-job',
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
    MatDividerModule,
    MatListModule,
  ],
  templateUrl: './assigments-job.html',
  styleUrl: './assigments-job.css',
  standalone: true,
})
export class AssigmentsJobComponent implements OnInit {
  @Input() job!: JobDTO;

  users: UserDTO[] = [];
  user!: UserDTO;
  assigments: UserDTO[] = [];
  jobAssigments: JobAssigmentDTO[] = [];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private jobAssigmentService: JobAssigmentService,
    private jobService: JobService
  ) {}

  ngOnInit() {
    this.setUsers();
    this.setAssigmetns();
  }

  setUsers() {
    this.userService.getAllUsers().subscribe((users: UserDTO[]) => {
      this.users = users;
    });
  }

  searchUser(useId: number): UserDTO {
    return this.users.find((user) => user.userId === useId)!;
  }

  setJobs(): void {
    const jobId = this.route.snapshot.params['id'];
    if (!jobId) {
      console.error('No jobId found in route parameters');
      return;
    }
    const jobIdNumber = parseInt(jobId, 10);
    if (isNaN(jobIdNumber)) {
      console.error('Invalid jobId:', jobId);
      return;
    }
    console.log('JobId:', jobId);
    this.jobService.getJobById(jobIdNumber).subscribe({
      next: (data: JobDTO) => {
        this.job = data;
        this.jobService.setJob(this.job);
        console.log('Job cargado:', this.job);
        this.setAssigmetns(); // Call after job is loaded
      },
      error: (error) => {
        console.error('Error fetching job:', error);
      },
    });
  }

  setAssigmetns(): void {
    if (!this.job) {
      console.warn('Job is not loaded yet');
      return;
    }
    this.jobAssigmentService.getJobAssigmentsByJob(this.job.jobId).subscribe({
      next: (data: JobAssigmentDTO[]) => {
        this.jobAssigments = data;
      },
      error: (error) => {
        console.error('Error fetching job assigments:', error);
      },
    });
  }
}
