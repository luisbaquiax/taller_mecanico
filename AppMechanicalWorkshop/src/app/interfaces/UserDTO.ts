export interface UserDTO {
  userId: number;
  rolId: number;
  username: string;
  isActive: boolean;
  email: string;
  phone: string;
  name: string;
  lastName: string;
  twoFactorAuth: boolean;
  typeTwoFactorId: number;
}
