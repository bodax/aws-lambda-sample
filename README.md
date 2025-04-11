### Prerequiremets: 
- aws cli
- terrafrom cli
- github account
- java17
- gradle or IDE with built in
- api token `transparency.entsoe.eu`

### Deploy Lambda with Terraform
This project deploys an AWS Lambda using Java + Micronaut and Terraform.

---
### How to Run
1. Clone the repository

```bash
git clone https://github.com/bodax/aws-lambda-sample.git
cd your-project
./gradlew build
```

2. Configure aws credentials, by using
``aws configure command`` put api keys from AWS account


3. Run terraform commands to deploy a resources
```
terraform init
terraform plan
terrafrom apply
```

4. Fill the following parameters in the `application.propertis` or as Lambda env variables: 
```
`transparency.api.url`,              `TRANSPARENCY_API_URL` 
`transparency.api.security.token`    `TRANSPARENCY_API_SECURITY_TOKEN`
```


5. Run lambda from AWS Console / Lambda / Function / Test and pass any "string" in json body form


6. Check `transparency-paring-results-bucket` and it should contain csv file with date interval
and type from api parameters in the filename.
