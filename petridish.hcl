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
				image = "docker.io/epsilonator0x11/petridish:latest"
				ports = ["petridish"]
			}
		}
  	}
}