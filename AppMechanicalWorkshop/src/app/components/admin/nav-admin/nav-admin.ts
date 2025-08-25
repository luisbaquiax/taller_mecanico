import { Component, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenav } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-admin',
  imports: [MatSidenavModule, MatButtonModule, MatIconModule, RouterModule],
  templateUrl: './nav-admin.html',
  styleUrl: './nav-admin.css',
})
export class NavAdmin {

  @ViewChild(MatSidenav) sidenav!: MatSidenav;

  toggleSidenav() {
    this.sidenav.toggle();
  }
}
