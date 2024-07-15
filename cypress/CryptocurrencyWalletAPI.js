///<reference types ="cypress"/>
describe('CryptocurrencyWalletAPI', () => {
    const baseurl = "https://crypto-wallet-server.mock.beeceptor.com" ;

    it('Register a user', () => {
        // Register a new user using the API endpoint /api/v1/register.
        cy.request({
            // Using post request to fetch the user information
            method: 'POST',
            url : baseurl + "/api/v1/register",
            headers:{
                "content-type":"application/json"
            },
            body:{
                "username": "user123",
                "password": "securepassword",
                "email": "user@example.com"
              }
        }).then((x)=>{
            expect(x.status).to.equal(200);
        })
    });
    //  login a user
    it('Login a user', () => {
        cy.request({
            method: 'POST',
            url: baseurl + "/api/v1/login",
            headers: {
                "content-type": "application/json"
            },
            body: {
                "username": "user123",
                "password": "securepassword"
            }
        }).then((response) => {
            // Assert the status code is 200
            expect(response.status).to.equal(200);
    
            const token = response.body.accessToken;
            cy.log(token);

           // cy.log(JSON.stringify(response.body));
 
            expect(response.body.token_type).to.equal("bearer");
            expect(response.body.expires_in).to.equal(3600);
        });
    });
    
    it('Retrieve the wallet balance', () => {
        cy.request({
            method: 'GET',
            url: baseurl + "/api/v1/balance",
            headers: {
                "content-type": "application/json"
            },
            
        }).then((response) => {
            // Assert the status code is 200
            expect(response.status).to.equal(200);
            expect(response.body.balance).to.equal(100.25);

        })
    });
    it('List all the transactions done by the user', () => {
        cy.request({
            method: 'GET',
            url: baseurl + "/api/v1/transactions",
            headers: {
                "content-type": "application/json"
            },
            
        }).then((response) => {
            // Assert the status code is 200
            expect(response.status).to.equal(200);
            expect(response.body.transactions[0].id).to.equal('12345');
        });
    });

    it('Transfer 5 ETH to a recipient', () => {
        cy.request({
            //Using the post method to  create a  new transaction
            method: 'POST',
            url: baseurl + "/api/v1/transactions",
            headers: {
                "content-type": "application/json"
            },
            body: {
                "recipient_address": "0x1234567890ABCDEF",
                "amount": 5.0,
                "currency": "ETH"
              }
        }).then((response) => {
            // Assert the status code is 200
            expect(response.status).to.equal(200);
            expect(response.body.id).to.equal('98765');
            expect(response.body.currency).to.equal("ETH");
        });
    });
    it('Calculate transaction fees and return estimated cost', () => {
        cy.request({
            method: 'POST',
            url: baseurl + "/api/v1/transaction_fee",
            headers: {
                "content-type": "application/json"
            },
            body: {
                "amount": 2.5,
                "currency": "BTC",
                "recipient_address": "0x1234567890ABCDEF"
              }
        }).then((response) => {
            // Assert the status code is 200
            expect(response.status).to.equal(200);
            expect(response.body.fee).to.equal(0.0005);
            expect(response.body.currency).to.equal("BTC");

        });
    });
    it('Get an object with all available currency exchange rates', () => {
        cy.request({
            method: 'GET',
            url: baseurl + "/api/v1/exchange_rates",
            headers: {
                "content-type": "application/json"
            },
            
        }).then((response) => {
            // Assert the status code is 200
            expect(response.status).to.equal(200);
            expect(response.body.BTC).to.equal(42345.67);
            expect(response.body.ETH).to.equal(2567.89);
            expect(response.body.USD).to.equal(1.0);
        });
 
    });

});    