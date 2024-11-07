# Postal

Exemplo de um Serviço Postal.
Com as funcionalidade de consulta de Código Postal e Cálculo de Frete.

## Endpoints

### Detalhes de um código postal:

GET /postalcode/v1/{countryCode]/{postalCode]/address

Exemplo: /postalcode/v1/BRAZIL/123456000/address

### Cálculo de frete entre dois códigos postais:

POST /shipping/calculator/v1

Exemplo:
```
{
    "countryOrigin": "BRAZIL",
    "postalCodeOrigin": "123456000",
    "countryDestination": "BRAZIL",
    "postalCodeDestination": "98765000",
    "weight": "10"
}
```

## Funcionamento
A consulta dos detalhes do CEP é feita em endpoints externos, como o Correio por exemplo.
Exemplos de chamada pelo postman disponível em: src/test/resources/Postal Code.postman_collection.json

Foi utilizado o wiremock para simular estes endpoints. O mapeamento do mock está disponível em src/test/resources/mappings

O resultado da consulta é salvo na tabela Postal, assim em uma próxima chamada é consultado do banco ao invés de buscar novamente externo.

A simulação de frete tem uma pequena lógica somente pra exemplificar. Se CEP origem e destino forem dentro do mesmo país o custo é menor de entrega.
