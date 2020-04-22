import { Router } from '@angular/router';
import { AccountService } from './../../services/account.service';
import { Component, OnInit } from '@angular/core';
import { routerNgProbeToken } from '@angular/router/src/router_module';

@Component({
  selector: 'app-show-account',
  templateUrl: './show-account.component.html',
  styleUrls: ['./show-account.component.css']
})
export class ShowAccountComponent implements OnInit {
  private edit: boolean;
  private transac: boolean;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this._accountService.setIsSignedIn(true);
  }

  showDetails() {
    this._router.navigate(['/show/account']);
  }

  editAccount() {
    this.edit = true;
    this.transac = false;
  }

  editAccountDetails() {
    this._accountService.setEditType(1);
    this._router.navigate(['/show/form']);
  }

  editAccountPassword() {
    this._accountService.setEditType(2);
    this._router.navigate(['/show/form']);
  }

  editTransactionPin() {
    this._accountService.setEditType(3);
    this._router.navigate(['/show/form']);
  }

  showBalance() {

  }

  transactions() {
    this.edit = false;
    this.transac = true;
  }

  deposit() {
    this._accountService.setTransacType(1);
    this._router.navigate(['show/transactions']);
  }

  withdraw() {
    this._accountService.setTransacType(2);
    this._router.navigate(['show/transactions']);
  }

  fundTransfer() {
    this._accountService.setTransacType(3);
    this._router.navigate(['show/transactions']);
  }
}
