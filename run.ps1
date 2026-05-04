param(
    [int]$Port = 8080
)

$ErrorActionPreference = "Stop"
$env:APP_PORT = "$Port"

docker compose up --build --remove-orphans
