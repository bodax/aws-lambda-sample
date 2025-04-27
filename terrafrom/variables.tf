variable "lambda_function_name" {
  default = "my-lambda-function"
}

variable "lambda_handler" {
  default = "com.bodax.home.LambdaMainHandler"  # Your handler class
}

variable "lambda_runtime" {
  default = "java17"
}

variable "lambda_role" {
  default = "lambda-execution-role"
}

variable "s3_transparency_parsing_result_bucket" {
  default = "lambda-execution-role"
}