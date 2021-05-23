### PayPal API
Auth
````
curl -v https://api.sandbox.paypal.com/v1/oauth2/token \
-H "Accept: application/json" \
-H "Accept-Language: en_US" \
-u "AZLBDto98XnkWuOsGr78XH78ohzsHneaQY9vzVdWu9w5xSKRhv1HQl2KSCBvtIDoEEQpXzLcCvJ8d9BG:EN-cucIq8ETHYA_bFHqVPi2BJ8DhwFQGledPVS370xSucGJi9d64dvozQeQosj6uO8Q6gu6yDwmHvnnt" \
-d "grant_type=client_credentials"
````
#### GET ORDER DETAILS

/v2/checkout/orders/{id}
````
curl -v -X GET https://api.sandbox.paypal.com/v2/checkout/orders/2CV55440UT6646827 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer A21AAGUUf6LqvINaewYHPuyS6QQDW_TMymq1uvqmpwULHQugNg-SdoEQuPXq5JOzhXu9XRliCzpwfFeEyilAgdPNmHjYf6sWA"
````
###
API
````
http GET http://localhost:8080/v1/order/2CV55440UT6646827

HTTP/1.1 200 OK
Content-Length: 893
Content-Type: application/json

{
    "id": "2CV55440UT6646827",
    "intent": "CAPTURE",
    "purchase_units": [
        {
            "amount": {
                "currency_code": "USD",
                "value": "1.00"
            },
            "description": "titsonfire.store Payment for order #401 ",
            "payments": {
                "captures": [
                    {
                        "amount": {
                            "currency_code": "USD",
                            "value": "1.00"
                        },
                        "final_capture": true,
                        "id": "85W962518S850170V",
                        "links": [
                            {
                                "href": "https://api.sandbox.paypal.com/v2/payments/captures/85W962518S850170V",
                                "method": "GET",
                                "rel": "self"
                            },
                            {
                                "href": "https://api.sandbox.paypal.com/v2/payments/captures/85W962518S850170V/refund",
                                "method": "POST",
                                "rel": "refund"
                            },
                            {
                                "href": "https://api.sandbox.paypal.com/v2/checkout/orders/2CV55440UT6646827",
                                "method": "GET",
                                "rel": "up"
                            }
                        ],
                        "seller_receivable_breakdown": {
                            "gross_amount": {
                                "currency_code": "USD",
                                "value": "1.00"
                            },
                            "net_amount": {
                                "currency_code": "USD",
                                "value": "0.66"
                            },
                            "paypal_fee": {
                                "currency_code": "USD",
                                "value": "0.34"
                            }
                        },
                        "status": "COMPLETED"
                    }
                ]
            },
            "reference_id": "default"
        }
    ],
    "status": "COMPLETED"
}

````
#### REFUND
PayPal API
````
curl -v -X POST https://api.sandbox.paypal.com/v2/payments/captures/85W962518S850170V/refund \
-H "Content-Type: application/json" \
-H "Authorization: Bearer A21AAF3-kYOoet64Ya_-HvqwUHgLOmK7yytpeKW2OJO82GPJtoBWfSRbEafTA9lkxxQKz6BeOopGIt13eAWTUhv1MQrdGEF7A"
````
API
````
http POST http://localhost:8080/v1/capture/refund/4CE915409W2428342

HTTP/1.1 200 OK
Content-Length: 47
Content-Type: application/json

{
    "id": "4BH36132M15490109",
    "status": "COMPLETED"
}

````
## Build
````
./mvnw package
````
## Docker
Install

https://docs.docker.com/engine/install/centos/

Build
````
docker build -t maxmorev/paypal-rest-api .
````
Run
````
docker run -i --rm -p 8080:8080 \
-e PAYPAL_CLIENT_ID=$PAYPAL_CLIENT_ID \
-e PAYPAL_SECRET=$PAYPAL_SECRET \
--name paypal-rest-api \
maxmorev/paypal-rest-api
````

### Prepare secrets 
You can, of course, provide the clear text content using the stringData for Secret creation
https://kubernetes.io/docs/concepts/configuration/secret/#basic-authentication-secret
```
echo $PAYPAL_SECRET
echo $PAYPAL_CLIENT_ID
```


