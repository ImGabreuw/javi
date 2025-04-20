#!/usr/bin/env fish

# Script para instalar o GraalVM Java 21 e configurar native-image para fish shell

# Definir versão do GraalVM
set GRAALVM_VERSION 21
set GRAALVM_DIR $HOME/Programs/graalvm-jdk-$GRAALVM_VERSION

# Criar diretório para o GraalVM se não existir
mkdir -p $HOME/Programs

# Baixar GraalVM
cd $HOME/Programs
echo "Baixando GraalVM JDK 21..."
wget -q --show-progress https://download.oracle.com/graalvm/21/latest/graalvm-jdk-$GRAALVM_VERSION\_linux-x64_bin.tar.gz
echo "Extraindo GraalVM..."
tar -xzf graalvm-jdk-$GRAALVM_VERSION\_linux-x64_bin.tar.gz
mv graalvm-jdk-$GRAALVM_VERSION $GRAALVM_DIR
rm graalvm-jdk-$GRAALVM_VERSION\_linux-x64_bin.tar.gz

# Configurar variáveis de ambiente para o fish
# Remover configurações antigas se existirem
sed -i '/GRAALVM_HOME/d' $HOME/.config/fish/config.fish
sed -i '/fish_add_path.*graalvm/d' $HOME/.config/fish/config.fish

# Adicionar novas configurações
echo "# GraalVM Configuration" >> $HOME/.config/fish/config.fish
echo "set -gx GRAALVM_HOME $GRAALVM_DIR" >> $HOME/.config/fish/config.fish
echo "fish_add_path \$GRAALVM_HOME/bin" >> $HOME/.config/fish/config.fish
echo "set -gx JAVA_HOME \$GRAALVM_HOME" >> $HOME/.config/fish/config.fish

# Carregar as configurações para a sessão atual
set -gx GRAALVM_HOME $GRAALVM_DIR
set -gx JAVA_HOME $GRAALVM_HOME
fish_add_path $GRAALVM_HOME/bin

# Instalar native-image
echo "Instalando native-image..."
gu install native-image

# Verificar instalação
echo "Verificando instalação GraalVM..."
java -version
echo "Verificando instalação native-image..."
native-image --version

echo "GraalVM JDK 21 configurado com sucesso!"
echo "Para aplicar as alterações na sessão atual, você pode executar: source ~/.config/fish/config.fish"
