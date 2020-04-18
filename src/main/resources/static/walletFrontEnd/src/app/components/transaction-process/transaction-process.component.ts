import { Router } from '@angular/router';
import { AccountService } from './../../service/account.service';
import { CustomerDetails } from './../../customer-details';
import { Component, OnInit } from '@angular/core';
import { TransactionDetails } from 'src/app/transaction-details';

@Component({
  selector: 'app-transaction-process',
  templateUrl: './transaction-process.component.html',
  styleUrls: ['./transaction-process.component.css']
})
export class TransactionProcessComponent implements OnInit {
  private customer: CustomerDetails;
  private transactions: TransactionDetails[];
  private flag = 0;
  private transactionFlag: boolean;
  private money: Number = 0;
  private tPin: Number = 0;
  private receiverId: Number;
  constructor(private _accountService: AccountService, private _router: Router) { }

  ngOnInit() {
    this.transactionFlag = false;
    this.customer = this._accountService.getCustomer();
    this._accountService.printTransactions(this.customer.accountId).subscribe((transactions) => {
      console.log(transactions);
      this.transactions = transactions;
    }, (error) => {
      console.log(error);
    }
    );
  }

  viewAccount() {
    this._router.navigate(['show']);
  }

  showTransactions(){
    if (this.flag != 0) {
      this.flag = 0;
    } else {
    this.flag = 4;
    }
  }

  doDeposit() {
    if (this.flag != 0) {
      this.flag = 0;
    } else {
    this.flag = 1;
    }
  }

  doWithdraw() {
    if (this.flag != 0) {
      this.flag = 0;
    } else {
    this.flag = 2;
    }
  }

  doFundTransfer() {
    if (this.flag != 0) {
      this.flag = 0;
    } else {
    this.flag = 3;
    }
  }

  proceedDeposit() {
    this.transactionFlag = true;
  }

  deposit() {
    if( this.tPin == this.customer.transactionPin) {
       this._accountService.deposit(this.customer.accountId, this.money).subscribe((customer) => {
        console.log(customer);
      }, (error) => {
        console.log(error);
      });
      setTimeout(() => {
        this._accountService.setCustomer(this.customer);
      this._router.navigate(['/show']);
    }, 4000);
    }
  }

  proceedWithdraw() {
    this.transactionFlag = true;
  }

  withdraw() {
    if ( this.tPin == this.customer.transactionPin) {
      this._accountService.withdraw(this.customer.accountId, this.money).subscribe((customer) => {
        console.log(customer);
      }, (error) => {
        console.log(error);
      });
      setTimeout(() => {
          this._accountService.setCustomer(this.customer);
        this._router.navigate(['/show']);
      }, 4000);
    }
  }

  proceedFundTransfer() {
    this.transactionFlag = true;
  }

  transferFund() {
    if( this.tPin == this.customer.transactionPin) {
      this._accountService.fundTransfer(this.customer.accountId, this.money, this.receiverId).subscribe((customer) => {
        console.log(customer);
      }, (error) => {
        console.log(error);
      });
      setTimeout(() => {
        this._accountService.setCustomer(this.customer);
      this._router.navigate(['/show']);
    }, 4000);
    }
  }

  greaterThan(a: number, b: number) {
    return a>b;
  }

  checkPassword(a: number, b: number) {
    return a==b;
  }
}
