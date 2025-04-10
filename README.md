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
git clone https://github.com/your-repo/your-project.git
cd your-project
./gradlew build
```

2. Run terraform commands 
```
terraform init
terraform plan
terrafrom apply
```

3. Fill following parameter in the `application.propertis` or as Lambda env variables:

`transparency.api.url`,  
`transparency.api.securityToken`

4. Run lambda from AWS Console / Labmda / Function / Test and pass any "string" in body form


5. Check `transparency-paring-results-bucket` and it should contain csv file with date interval
and type from api parameters in the filename.
