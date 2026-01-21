#!/bin/bash

# Configurações
JSON_FILE="src/main/resources/pehkui.mixins.json"
LOG_FILE="run/logs/latest.log"

if ! command -v jq &> /dev/null; then
    echo "Erro: 'jq' não encontrado."
    exit 1
fi

echo "--- Auditoria de Mixins (Formato Fabric 2026) ---"

# 1. Extrai o pacote base (ex: virtuoel.pehkui.mixin)
PACKAGE=$(jq -r '.package' "$JSON_FILE")

# 2. Lista todos os mixins do JSON (relativos, ex: compat115minus.EntityMixin)
# Remove espaços em branco e ordena
jq -r '.mixins[]?, .client[]?' "$JSON_FILE" | sed 's/ //g' | sort > /tmp/mixins_in_json.txt

# 3. Extrai o que foi aplicado do log
# O padrão busca "Mixing [Classe] from pehkui.mixins.json"
grep "Mixing.*from pehkui.mixins.json" "$LOG_FILE" | \
sed 's/.*Mixing \(.*\) from pehkui.mixins.json.*/\1/' | \
sort | uniq > /tmp/mixins_actual_applied.txt

# 4. Compara
echo "Classes presentes no JSON mas que NÃO aparecem no log como 'Mixing':"
echo "------------------------------------------------------------------"
# Mostra o que está no arquivo 1 mas não no 2
comm -23 /tmp/mixins_in_json.txt /tmp/mixins_actual_applied.txt

# Limpeza
rm /tmp/mixins_in_json.txt /tmp/mixins_actual_applied.txt
echo "------------------------------------------------------------------"
