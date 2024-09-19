job "petridish" {
    type = "service"
    group "petridish" {
		count = 1
		network {
			port "petridish" {
				to = 6379
			}
		}

		service {
			name     = "petridish-svc"
			port     = "petridish"
			provider = "nomad"
		}

		task "petridish-task" {
			driver = "docker"

			config {
				image = "000000000000.dkr.ecr.us-east-1.localhost.localstack.cloud:4566/epsilonator/petridish:latest"
				ports = ["petridish"]
			}
		}
  	}
}