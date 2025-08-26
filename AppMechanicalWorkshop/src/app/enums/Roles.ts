export enum Roles {
  ADMIN = 1,
  EMPLOYEE = 2,
  ESPECIALIST = 3,
  CLIENT = 4,
}

export namespace Roles {
  export function getId(role: Roles): number {
    return role;
  }
}
