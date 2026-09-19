#!/usr/bin/env bash
# Instala e abre o Android Studio na área de trabalho do Codespace (noVNC, porta 6080),
# para usar o @Preview no navegador. Uso: bash .devcontainer/android-studio.sh
set -euo pipefail

VERSAO="2026.1.4.8"   # Android Studio Quail 4 Patch 1
ARQUIVO="android-studio-quail4-patch1-linux.tar.gz"
DESTINO="$HOME/android-studio"

if [ ! -x "$DESTINO/bin/studio" ]; then
  echo "Baixando o Android Studio (~1,4 GB)..."
  curl -fsSL "https://edgedl.me.gvt1.com/android/studio/ide-zips/$VERSAO/$ARQUIVO" | tar -xz -C "$HOME"
fi

PROJETO="$(cd "$(dirname "$0")/.." && pwd)/exemplos/tarefas-compose"
echo "sdk.dir=$ANDROID_HOME" > "$PROJETO/local.properties"

DISPLAY="${DISPLAY:-:1}" setsid nohup "$DESTINO/bin/studio" "$PROJETO" > /tmp/android-studio.log 2>&1 &
echo "Android Studio abrindo na área de trabalho (porta 6080)."
