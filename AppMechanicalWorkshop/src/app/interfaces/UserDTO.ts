export interface UserDTO {
  userId: number;
  rolId: number;
  username: string;
  active: boolean;
  email: string;
  phone: string;
  name: string;
  lastName: string;
  twoFactorAuth: boolean;
  typeTwoFactorId: number;
}
