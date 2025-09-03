export interface JobDTO {
  jobId: number;
  createdBy: number;
  vehicleId: number;
  startedAt: string;
  finishedAt: string;
  typeJobId: number;
  statusJobId: number;
  description: string;
  estimatedHours: number;
}
