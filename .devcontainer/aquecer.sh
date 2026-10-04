#!/usr/bin/env bash
# Prepara o Codespace do branch `aula`: dependências do ponto de partida e do último passo,
# e o Android Studio já baixado. Falhas aqui não impedem o Codespace de abrir.
set -uo pipefail
cd "$(dirname "$0")/.."

# O mise.toml só entra no Passo 10; antes disso não há o que instalar.
[ -f mise.toml ] && { mise trust && mise install; } || true

(cd exemplos/tarefas-compose && ./gradlew :composeApp:desktopJar --console=plain) || echo "aviso: Gradle não terminou"

# Compila também o último passo, numa cópia temporária: baixa o que os passos seguintes usam.
git fetch --quiet --tags origin || true
ultimo="$(git tag --list 'passo-*' --sort=v:refname | tail -1)"
if [ -n "$ultimo" ] && git worktree add --quiet --detach /tmp/ultimo-passo "$ultimo"; then
  (cd /tmp/ultimo-passo/exemplos/tarefas-compose && ./gradlew :composeApp:desktopTest --console=plain) || echo "aviso: Gradle (último passo) não terminou"
  git worktree remove --force /tmp/ultimo-passo
fi

# Android Studio (~1,4 GB): baixado agora para abrir na hora com `bash .devcontainer/android-studio.sh`.
if [ ! -x "$HOME/android-studio/bin/studio" ]; then
  url="$(sed -n 's/^VERSAO="\([^"]*\)".*/\1/p' .devcontainer/android-studio.sh)/$(sed -n 's/^ARQUIVO="\([^"]*\)".*/\1/p' .devcontainer/android-studio.sh)"
  curl -fsSL "https://edgedl.me.gvt1.com/android/studio/ide-zips/$url" | tar -xz -C "$HOME" || echo "aviso: Android Studio não baixou"
fi
