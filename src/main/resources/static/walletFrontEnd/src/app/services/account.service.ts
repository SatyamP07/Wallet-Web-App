import { CustomerDetails } from './../customer-details';
import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';

import { map, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl: String = 'http://localhost:4444/XYZWallet';
  private headers = new Headers ({'Content-Type': 'application/json'});
  private options = {headers: this.headers};
  private customer: CustomerDetails = JSON.parse(localStorage.getItem('customer') || null);
  private createForm: boolean;
  private signedIn: boolean = JSON.parse(localStorage.getItem('signedIn') || 'false');
  private editType: number;
  private transacType: number;
  private showBalance: boolean;
  constructor(private _http: Http) { }

  getAccounts() {
    return this._http.get(this.baseUrl + '/accounts', this.options).pipe(map((response: Response) => response.json()));
  }

  getAccount(id: Number) {
    return this._http.get(this.baseUrl + '/account/' + id, this.options).pipe(map((response: Response) => response.json())).pipe(
      catchError(this.handleError));
  }

  deleteAccount(id: Number) {
    return this._http.delete(this.baseUrl + '/account/' + id, this.options).pipe(map((response: Response) => response.json()));
  }

  createAccount(customer: CustomerDetails) {
    return this._http.post(this.baseUrl + '/account', JSON.stringify(customer), this.options)
      .pipe(map((response: Response) => response.json()));
  }

  updateAccount(customer: CustomerDetails) {
    return this._http.put(this.baseUrl + '/account', JSON.stringify(customer), this.options)
      .pipe(map((response: Response) => response.json()));
  }

  deposit(accountId: Number, money: Number) {
    return this._http.put(this.baseUrl + '/account/' + accountId + '/deposit/' + money, this.options)
      .pipe(map((response: Response) => response.json()));
  }

  withdraw(accountId: Number, money: Number) {
    return this._http.put(this.baseUrl + '/account/' + accountId + '/withdraw/' + money, this.options)
      .pipe(map((response: Response) => response.json()));
  }

  fundTransfer(accountId: Number, money: Number, receiverId: Number) {
    return this._http.put(this.baseUrl + '/account/' + accountId + '/fundTransfer/' + receiverId + '/' + money,
                         [JSON.stringify(money), JSON.stringify(receiverId)], this.options)
      .pipe(map((response: Response) => response.json()));
  }

  printTransactions(accountId: Number) {
    return this._http.get(this.baseUrl + '/account/' + accountId + '/printTransactions', this.options)
      .pipe(map((response: Response) => response.json()));
  }

  getSignedInAccount() {
    this.setIsSignedIn(true);
    return this._http.get(this.baseUrl + '/account/signedIn', this.options).pipe(map((response: Response) => response.json()));
  }

  checkAccountPassword(password: String, accountId: number) {
    return this._http.post(this.baseUrl + '/account/' + accountId + '/checkAccountPassword', JSON.stringify(password), this.options)
      .pipe(map((response: Response) => response.json())).pipe(catchError(this.handleError));
  }

  checkTransactionPin(password: String, accountId: number) {
    return this._http.post(this.baseUrl + '/account/' + accountId + '/checkTransactionPin', JSON.stringify(password), this.options)
      .pipe(map((response: Response) => response.json())).pipe(catchError(this.handleError));
  }

  setCustomer(customer: CustomerDetails) {
    localStorage.setItem('customer', JSON.stringify(customer));
  }

  getCustomer() {
    return JSON.parse(localStorage.getItem('customer'));
  }

  getIsSignedIn() {
    return JSON.parse(localStorage.getItem('signedIn'));
  }
  setIsSignedIn(flag: boolean) {
    localStorage.setItem('signedIn', JSON.stringify(flag));
  }

  setEditType(editType: number) {
    this.editType = editType;
  }
  getEditType() {
    return this.editType;
  }

  setTransacType(type: number) {
    this.transacType = type;
  }
  getTransacType() {
    return this.transacType;
  }

  setShowBalance(showBalance: boolean) {
    this.showBalance = showBalance;
  }

  getShowBalance() {
    return this.showBalance;
  }

  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
      console.log("from client side");
    } else {
      // server-side error
      errorMessage = `Error: ${error.error.errorMessage}`;
    }
    console.log(error.error.errorMessage);
    return throwError(error);
  }
}

