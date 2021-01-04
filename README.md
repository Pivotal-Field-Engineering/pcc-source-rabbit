<h1> VMware has ended active development of this project, this repository will no longer be updated.</h1><br># pcc-source-rabbit

SCS source module implementation for PCC. Its built on gemfire SCS and refactored to be compatible with GemFire 9.x

### New Properties

```
gemfire.client.authenticationRequired=true
gemfire.client.userName=
gemfire.client.password=
```

### Example

```
stream create --name pcc-source-mysql-demo --definition "pcc-source --gemfire.pool.subscription-enabled=true --gemfire.pool.connect-type=locator --gemfire.pool.host-addresses=10.0.0.6:55221,10.0.32.7:55221,10.0.64.10:55221 --gemfire.region.region-name=Customer --gemfire.cache-event-expression=newValue --gemfire.client.authenticationRequired=true --gemfire.client.userName=<--username--> --gemfire.client.password=<--password--> --outputType=application/json | log"
```




