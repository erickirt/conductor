# Go SDK

The code for the Golang SDk is available on [Github](https://github.com/conductor-oss/go-sdk). Please feel free to file PRs, issues, etc. there.


## Quick Start

1. [Setup conductor-go package](#Setup-conductor-go-package)
2. [Create and run Task Workers](https://github.com/conductor-oss/go-sdk/blob/main/docs/workers_sdk.md)
3. [Create workflows using Code](https://github.com/conductor-oss/go-sdk/blob/main/docs/workflow_sdk.md)

### Setup conductor go package

Create a folder to build your package:
```shell
mkdir quickstart/
cd quickstart/
go mod init quickstart
```

Get Conductor Go SDK

```shell
go get github.com/conductor-oss/go-sdk
```
## Configuration

### Authentication settings (optional)
Use if your conductor server requires authentication
* keyId: Key
* keySecret: Secret for the Key

```go
authenticationSettings := settings.NewAuthenticationSettings(
  "keyId",
  "keySecret",
)
```

### Access Control Setup
See [Access Control](https://orkes.io/content/docs/getting-started/concepts/access-control) for more details on role based access control with Conductor and generating API keys for your environment.

### Configure API Client
```go

apiClient := client.NewAPIClient(
    settings.NewAuthenticationSettings(
        KEY,
        SECRET,
    ),
    settings.NewHttpSettings(
        "https://developer.orkescloud.com/",
    ),
)
	
```

### Set up Logging
SDK uses [logrus](https://github.com/sirupsen/logrus) for the logging.

```go
func init() {
	log.SetFormatter(&log.TextFormatter{})
	log.SetOutput(os.Stdout)
	log.SetLevel(log.DebugLevel)
}
```

### Next: [Create and run Task Workers](https://github.com/conductor-oss/go-sdk/blob/main/docs/workers_sdk.md)