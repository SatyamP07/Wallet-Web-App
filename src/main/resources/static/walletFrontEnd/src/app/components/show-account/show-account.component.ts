import { Router } from '@angular/router';
import { AccountService } from './../../service/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit, OnChanges } from '@angular/core';

@Component({
  selector: 'app-show-account',
  templateUrl: './show-account.component.html',
  styleUrls: ['./show-account.component.css']
})
export class ShowAccountComponent implements OnInit {
  private customer: CustomerDetails;
  constructor(private _accountService: AccountService, private _router: Router) { }
  private flag: number = 0;

  ngOnInit() {
    if (this._accountService.getCreateForm()) {
      this._accountService.getSignedInAccount().subscribe((customer) => this.customer = customer);
      this._accountService.setCustomer(this.customer);
    } else {
      this.customer = this._accountService.getCustomer();
    this._accountService.getAccount(this.customer.accountId).subscribe((customer) => this.customer = customer );
    }
    console.log("OnInit");
  }

  updateAccount(customer){
    this._accountService.setCustomer(customer);
    this._router.navigate(['/form']);
  }

  showBalance(){
    this.flag = 1;
  }

  doTransactions(customer){
    this._accountService.setCustomer(customer);
    this._router.navigate(['/tp']);
  }

  logout() {
    this._router.navigate(['/']);
  }

  deactivate(){
    this._accountService.deleteAccount(this.customer.accountId).subscribe();
    this._router.navigate(['/']);
  }
}
