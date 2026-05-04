#!/usr/bin/env sh
set -eu

PORT="${1:-8080}"
APP_PORT="$PORT" docker compose up --build --remove-orphans
