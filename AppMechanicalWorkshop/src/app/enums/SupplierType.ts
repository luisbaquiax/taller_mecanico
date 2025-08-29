export enum SupplierType{
  PERSON = 'person',
  COMPANY = 'company'
};

export namespace SupplierType {
  export function getName(supplierType: SupplierType): string {
    return supplierType;
  }
}
