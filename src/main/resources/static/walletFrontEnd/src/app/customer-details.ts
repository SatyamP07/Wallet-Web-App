import { TransactionDetails } from './transaction-details';
export class CustomerDetails {
    accountId: number;
    accountPassword: String;
    name: String;
    eMail: String;
    mobileNumber: String;
    balance: number;
    transactionPin: number;
    transactionDetails: TransactionDetails[];
}
