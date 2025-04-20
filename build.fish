#!/usr/bin/env fish

# Script para compilar o projeto usando GraalVM native-image no fish shell

# Verificar se o GraalVM está configurado
if test -z "$GRAALVM_HOME"
    echo "GRAALVM_HOME não está configurado. Execute primeiro o script setup-graalvm.fish"
    exit 1
end

# Verificar se native-image está instalado
if not command -q native-image
    echo "native-image não encontrado. Instalando..."
    gu install native-image
end

# Compilar o projeto
echo "Compilando o projeto com native-image..."
mvn -Pnative clean package native:compile

echo "Compilação concluída."
