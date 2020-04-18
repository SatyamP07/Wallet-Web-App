import { Router } from '@angular/router';
import { AccountService } from './../../service/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account-form',
  templateUrl: './account-form.component.html',
  styleUrls: ['./account-form.component.css']
})
export class AccountFormComponent implements OnInit {
  private customer: CustomerDetails;
  private newCustomer: CustomerDetails;
  private createForm: boolean;
  private flag: number;
  private passwords: String[] = ['', '', ''];
  private transacPins: number[] = [0, 0, 0];
  private changeFlag: number;
  private process: boolean;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.customer = this._accountService.getCustomer();
    this.createForm = this._accountService.getCreateForm();
    this.flag = 0;
    this.changeFlag = 0;
  }

  increamentFlag() {
    this.flag++;
    console.log(this.customer);
  }

  setPassword() {
    if (this.passwords[0] == this.passwords[1]) {
      this.customer.accountPassword = this.passwords[1];
      console.log(this.customer.accountPassword);
      this.flag++;
      console.log(this.flag);
    }
  }

  setTPin() {
    if (this.transacPins[0] == this.transacPins[1]) {
      this.customer.transactionPin = this.transacPins[1];
      this.processForm();
    }
  }

  processForm() {
    if (this.customer.accountId == undefined) {
      this._accountService.createAccount(this.customer).subscribe((customer) => {
        console.log(customer);
      }, (error) => {
        console.log(error);
      });
      setTimeout(() => {
        this._router.navigate(['/show'])
      }, 4000);
    } else {
      this._accountService.updateAccount(this.customer).subscribe((customer) => {
        console.log(customer);
        setTimeout(() => {
          this._accountService.setCustomer(this.customer);
          this._router.navigate(['/show']);
        }, 4000);
      }, (error) => {
        console.log(error);
      });
    }
  }

  changePassword() {
    this.changeFlag = 1;
  }

  confirmPassword() {
    if (this.passwords[2] == this.customer.accountPassword) {
      if (this.passwords[0] == this.passwords[1]) {
        this.customer.accountPassword = this.passwords[1];
        this.processForm();
      }
    }
  }

  changeTPin() {
    this.changeFlag = 2;
  }

  confirmTPin() {
    if (this.transacPins[2] == this.customer.transactionPin) {
      if (this.transacPins[0] == this.transacPins[1]) {
        this.customer.transactionPin = this.transacPins[1];
        this.processForm();
      }
    }
  }
}
