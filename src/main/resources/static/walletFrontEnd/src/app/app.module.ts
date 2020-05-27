import { AccountService } from './services/account.service';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';

import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { AccountFormComponent } from './components/account-form/account-form.component';
import { ShowAccountComponent } from './components/show-account/show-account.component';
import { TransactionProcessComponent } from './components/transaction-process/transaction-process.component';
import { AccountDetailsComponent } from './components/account-details/account-details.component';
import { PrintTransactionsComponent } from './components/print-transactions/print-transactions.component';
import { AdminAccessComponent } from './components/admin-access/admin-access.component';

const appRoute: Routes = [
    {path: '', component: HomeComponent},
    {path: 'form', component: AccountFormComponent},
    {path: 'show', component: ShowAccountComponent, children: [
        {path: 'account', component: AccountDetailsComponent},
        {path: 'transactions', component: TransactionProcessComponent},
        {path: 'form', component: AccountFormComponent},
        {path: 'printTransactions', component: PrintTransactionsComponent}
    ]},
    {path: 'admin', component: AdminAccessComponent}
];
@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        FormsModule,
        RouterModule.forRoot(appRoute, {onSameUrlNavigation: 'reload'})
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        LoginComponent,
        AccountFormComponent,
        ShowAccountComponent ,
        TransactionProcessComponent ,
        AccountDetailsComponent ,
        PrintTransactionsComponent ,
        AdminAccessComponent],
    providers: [
        AccountService
        ],
    bootstrap: [AppComponent]
})

export class AppModule { }