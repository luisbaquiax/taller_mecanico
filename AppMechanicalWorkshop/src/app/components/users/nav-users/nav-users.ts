import { Component, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenav } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-nav-users',
  imports: [MatSidenavModule, MatButtonModule, MatIconModule, RouterModule],
  templateUrl: './nav-users.html',
  styleUrl: './nav-users.css'
})
export class NavUsers {

    @ViewChild(MatSidenav) sidenav!: MatSidenav;

  toggleSidenav() {
    this.sidenav.toggle();
  }

}
