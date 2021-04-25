# otp-service
Spring Boot Application featuring OTP as a Service
### Features:
1. Create and verify OTP from REST endpoints
2. OTPs are delivered to user via email
3. Set Email Message Template for OTP mail
4. Reject OTP verification for multiple incorrect attempts.

ENDPOINTS:
1. POST `/otp/create`
    1. params: type=email
    2. body:
       
       {
       "emailAddress": "string",
       "messageTemplate": "string",
       "expiryTimeInSeconds": 3600
       }
       
   3. response: 
      1. 200:  `{"otpId": "string"}`
      2. 400: `[
         {
         "fieldName": "string",
         "errorMessage": "string",
         "rejectedValue": "string"
         }]`
      3. 503: `{
         "message": "string"
         }`
         
      4. 500: `{
         "message": "string"
         }`
2. POST `/otp/verify`
   1. body: `{
      "otpId": "string",
      "otpCode": "string"}`
      
   2. response:
      1. 200: `{
         "status": "[VERIFIED,EXPIRED,INVALID]",
         "verified": [true,false],
         "attemptsRemaining": [0-3],
         "message": "string"
         }`
         
      2. 404: `{
         "message": "string"
         }`
         
      3. 503: `{
         "message": "string"
         }`
         
      4. 500: `{
         "message": "string"
         }`
         
3. GET `/otp/status/{otpId}`
   1. response:
      1. 200: {`
         "otpId": "string",
         "currentStatus": "[NEW,PROCESSING,WAITING_FOR_VERIFICATION,VERIFIED,INVALID,EXPIRED]",
         "currentStatusReasonPhrase": "string",
         "totalVerificationAttemptsMade": [0-3]
         }`
         
      2. 404: `{
         "message": "string"
         }`
         

### To Run:
1. Import Project
2. Fill proper credentials in
   1. [application-dev-smtp.yml](src/main/resources/application-dev-smtp.yml)
   2. [application-dev-db.yml](src/main/resources/application-dev-db.yml)
3. Run `mvn spring-boot:run`