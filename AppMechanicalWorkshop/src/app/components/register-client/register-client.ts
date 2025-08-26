import { Component, signal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatFormField } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';
import { MatError } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';

// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { FormGroup, Validators, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { F, V } from '@angular/cdk/keycodes';
import { RegisterClient } from '../../interfaces/RegisterClient';
import { Roles } from '../../enums/Roles';

@Component({
  selector: 'app-register-client',
  imports: [
    RouterModule,
    MatFormField,
    MatLabel,
    MatError,
    MatIcon,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
  ],
  standalone: true,
  templateUrl: './register-client.html',
  styleUrl: './register-client.css',
})
export class RegisterClientComponent {
  hide = signal(true);
  registerForm: FormGroup;

  clientNew: RegisterClient = {
    rolId: 0,
    username: '',
    email: '',
    password: '',
    name: '',
    lastName: '',
    phone: '',
    nit: '',
    address: '',
  };

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      name: ['', [Validators.required, Validators.maxLength(100)]],
      last_name: ['', [Validators.required, Validators.maxLength(100)]],
      phone: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(8),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      nit: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(8),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      address: ['', Validators.required],
    });
  }

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.clientNew.rolId = Roles.getId(Roles.CLIENT);
      this.clientNew.username = this.registerForm.get('username')?.value;
      this.clientNew.email = this.registerForm.get('email')?.value;
      this.clientNew.password = this.registerForm.get('password')?.value;
      this.clientNew.name = this.registerForm.get('name')?.value;
      this.clientNew.lastName = this.registerForm.get('last_name')?.value;
      this.clientNew.phone = this.registerForm.get('phone')?.value;
      this.clientNew.nit = this.registerForm.get('nit')?.value;
      this.clientNew.address = this.registerForm.get('address')?.value;
      console.log(this.clientNew);
    }
  }
}
