#!/usr/bin/env bash
# Instala o mínimo do Android SDK para o Gradle configurar o alvo Android do projeto.
set -euo pipefail

SDK="${ANDROID_HOME:-$HOME/android-sdk}"
FERRAMENTAS="commandlinetools-linux-16111833_latest.zip"

if [ ! -x "$SDK/cmdline-tools/latest/bin/sdkmanager" ]; then
  mkdir -p "$SDK/cmdline-tools"
  curl -fsSL "https://dl.google.com/android/repository/$FERRAMENTAS" -o /tmp/cmdline-tools.zip
  unzip -q /tmp/cmdline-tools.zip -d /tmp/cmdline-tools
  mv /tmp/cmdline-tools/cmdline-tools "$SDK/cmdline-tools/latest"
  rm -rf /tmp/cmdline-tools /tmp/cmdline-tools.zip
fi

# `yes` termina com SIGPIPE quando o sdkmanager fecha a entrada; isso não é erro.
(yes || true) | "$SDK/cmdline-tools/latest/bin/sdkmanager" --sdk_root="$SDK" --licenses > /dev/null
"$SDK/cmdline-tools/latest/bin/sdkmanager" --sdk_root="$SDK" "platform-tools" "platforms;android-37.0" "build-tools;36.0.0"
