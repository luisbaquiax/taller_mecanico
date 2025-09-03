export enum StatusJobsEnum {
  PENDIENTE = 1,
  EVALUACION = 2,
  AUTORIZADO = 3,
  EN_CURSO = 4,
  PAUSADO = 5,
  FINALIZADO = 6,
  CANCELADO = 7,
  FINALIZADO_SIN_EJECUCION = 8
}

export namespace StatusJobsEnum {
  export function getId(statusJob: StatusJobsEnum): number {
    return statusJob;
  }
}
