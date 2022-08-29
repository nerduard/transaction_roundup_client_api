# transaction_roundup_client_api
This is a simple Starling Bank API client for rounding up all customer transactions going **OUT** over the past 7 days and depositing the total amount into a **Savings Goal** pot.

### How the app works
1. Transactions over the past 7 days are retrieved
2. Roundups for transactions going OUT are summed up
3. User is prompted to create a new Savings Goal
4. Sum of roundups gets added to Savings Goal (or not, if user chooses not to)

### API Setup
In order for the API to function correctly, the following fields need filling out in *RequestHandler.java*:
- **ACCESS_TOKEN** -> to be found in the Sandbox Customer area of the Starling Bank Developer portal.
- **ACCOUNT_HOLDER_UID** -> same as ACCESS_TOKEN.
- **ACCOUNT_UID** -> same as ACCESS_TOKEN but under the Accounts API category.
- **DEFAULT_CATEGORY** -> can be found by using the *Get the accounts associated with an account holder* (GET /api/v2/accounts) API on https://developer.starlingbank.com/docs#api-access-1.

In order to have access to the above, it is assumed that a **Starling Bank API application** and a **Sandbox customer** have been created.

### Dependencies
The dependency management has been handled by Maven, therefore all dependencies can be found in *pom.xml*.
Third-party dependencies used:
- org.apache.httpcomponents v4.5.13
- org.json v20220320

### Additional info
In order to test the App's functionality to the fullest, new transactions can be simulated by using the **Feed API** area in the **Sandbox Customer** portal.
Additionally, Starling Bank's https://developer.starlingbank.com/docs#api-access-1 page allows for all API endpoints to be called, provided the Access Token has been used to authorize the calls.

### About
Version: 0.1
Created by: 
*Eduard Vasilescu*
*eduard@vasy.co*
