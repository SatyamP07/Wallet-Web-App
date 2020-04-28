import { CustomerDetails } from './../../customer-details';
import { Router, ActivatedRoute } from '@angular/router';
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
  private customer: CustomerDetails;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this._accountService.setIsSignedIn(true);
    this.customer = this._accountService.getCustomer();
  }

  showDetails() {
    this._accountService.setShowBalance(false);
    this._router.navigate(['/show/account']);
    console.log("details");
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
    this._accountService.setShowBalance(true);
    this._router.navigate(['/show/account']);
    console.log("balance");
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
  printTransactions(){
    this._router.navigate(['show/printTransactions']);
  }
  logOut() {
    let confirmation = confirm('Do you want to logout?');
    if (confirmation) {
      this._accountService.setIsSignedIn(false);
      this._router.navigate(['']);
    }
  }

  deleteAccount() {
    let confirmation = confirm('Do you want to deactivate account?\nNote: Pressing OK will permanently delete the account.');
    if (confirmation) {
      this._accountService.setIsSignedIn(false);
      this._accountService.deleteAccount(this.customer.accountId).subscribe();
      this._router.navigate(['']);
    }
  }
}
