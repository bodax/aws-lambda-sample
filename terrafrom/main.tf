# Create the IAM Role for Lambda execution
variable "lambda_role_name" {
  default = ""
}
resource "aws_iam_role" "lambda_role" {
  name = var.lambda_role

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# Attach a basic Lambda execution policy to the role
resource "aws_iam_role_policy_attachment" "lambda_policy_attachment" {
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
  role       = aws_iam_role.lambda_role.name
}

resource "aws_iam_policy" "lambda_s3_policy" {
  name = "lambda-s3-access-policy"

  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = [
          "s3:PutObject"
        ],
        Effect   = "Allow",
        Resource = "${aws_s3_bucket.lambda_results.arn}/*"
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "lambda_s3_attach" {
  role       = aws_iam_role.lambda_role.name
  policy_arn = aws_iam_policy.lambda_s3_policy.arn
}

# Upload the Lambda function (directly from JAR or S3)
resource "aws_lambda_function" "my_lambda" {
  function_name = var.lambda_function_name
  handler       = var.lambda_handler
  runtime       = var.lambda_runtime
  role          = aws_iam_role.lambda_role.arn
  memory_size   = 256
  timeout       = 30

  # If you're using a local file for Lambda deployment:
  filename      = "../build/libs/lambda-transparency-data-parser-0.1-all.jar"
  source_code_hash = filebase64sha256("../build/libs/lambda-transparency-data-parser-0.1-all.jar")

  environment {
    variables = {
      "S3_BUCKET" = aws_s3_bucket.lambda_results.bucket  # Optional environment variables
    }
  }
}

resource "aws_s3_bucket" "lambda_results" {
  bucket = "transparency-paring-results-bucket"  # bucket name must be globally unique

  tags = {
    Name = "LambdaResultsBucket"
  }
}