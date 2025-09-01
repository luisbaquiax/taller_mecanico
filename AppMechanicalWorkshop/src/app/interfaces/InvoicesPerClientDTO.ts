export interface InvoicesPerClientDTO {



    invoiceId: number,
    jobId: number,
    licencePlate: string,
    vehicleBrand: string,
    vehicleModel: string,
    vehicleYear: string,
    total: number,
    invoiceStatus: string,
    issuedAt: Date,
    jobDescription: string
}
