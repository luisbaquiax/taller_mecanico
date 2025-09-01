import { Injectable, signal } from '@angular/core';
import { UserDTO } from '../interfaces/UserDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser = signal<UserDTO | null>(null);

  constructor() {
    this.loadUserFromStorage();
  }

  private loadUserFromStorage() {
    const userData = localStorage.getItem('currentUser');
    if (userData) {
      try {
        const user = JSON.parse(userData);
        this.currentUser.set(user);
      } catch (error) {
        console.error('Error parsing user data:', error);
      }
    }
  }

  getCurrentUser(): UserDTO | null {
    return this.currentUser();
  }

  getCurrentUserId(): number | null {
    const user = this.currentUser();
    return user ? user.userId : null;
  }

  getCurrentUserName(): string {
    const user = this.currentUser();
    return user ? `${user.name} ${user.lastName}` : 'Usuario';
  }

  isAuthenticated(): boolean {
    return this.currentUser() !== null;
  }

  isEmployee(): boolean {
    const user = this.currentUser();
    return user ? user.rolId === 2 : false;
  }

  logout() {
    this.currentUser.set(null);
    localStorage.clear();
  }

  // MÉTODOS NUEVOS PARA EL PERFIL GLOBAL

  /**
   * Update current user information in localStorage and service
   * @param updatedUser
   */
  updateCurrentUser(updatedUser: UserDTO): void {
    // Actualizar en localStorage
    localStorage.setItem('currentUser', JSON.stringify(updatedUser));
    localStorage.setItem('userId', updatedUser.userId?.toString() || '');
    localStorage.setItem('userRole', updatedUser.rolId?.toString() || '');
    this.currentUser.set(updatedUser);
  }

  /**
   * Get current user role name
   * @returns Role name as string
   */
  getCurrentUserRoleName(): string {
    const user = this.getCurrentUser();
    if (!user) return 'Usuario';

    const roleMap: { [key: number]: string } = {
      1: 'Administrador',
      2: 'Empleado',
      3: 'Especialista',
      4: 'Cliente',
      5: 'Proveedor'
    };

    return roleMap[user.rolId] || 'Usuario';
  }

  /**
   * Get dashboard route for current user role
   * @returns Dashboard route as string
   */
  getDashboardRoute(): string {
    const user = this.getCurrentUser();
    if (!user) return '/';

    const routeMap: { [key: number]: string } = {
      1: '/admin',
      2: '/employee/dashboard',
      3: '/specialist/dashboard',
      4: '/client/dashboard',
      5: '/supplier/dashboard'
    };

    return routeMap[user.rolId] || '/';
  }

  /**
   * Check if user has specific role
   * @param roleId Role ID to check
   * @returns boolean
   */
  hasRole(roleId: number): boolean {
    const user = this.getCurrentUser();
    return user ? user.rolId === roleId : false;
  }

  /**
   * Check if user is admin
   * @returns boolean
   */
  isAdmin(): boolean {
    return this.hasRole(1);
  }

  /**
   * Check if user is specialist
   * @returns boolean
   */
  isSpecialist(): boolean {
    return this.hasRole(3);
  }

  /**
   * Check if user is client
   * @returns boolean
   */
  isClient(): boolean {
    return this.hasRole(4);
  }

  /**
   * Check if user is supplier
   * @returns boolean
   */
  isSupplier(): boolean {
    return this.hasRole(5);
  }

  /**
   * Set user after login
   * @param user UserDTO
   */
  setCurrentUser(user: UserDTO): void {
    this.updateCurrentUser(user);
  }
}
