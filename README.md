
# Visualização do melhor local para construir um empreendimento

Este projeto se divide em dois componentes principais: *locationQuery-rest* e *olq-visualization*.

## locationQuery-rest

O *locationQuery-rest* é um serviço web *RESTFul* que calcula o melhor local para construir um empreendimento. Para disponibilizar o serviço basta executar o jar que se encontra na pasta target do projeto.

```
java -jar locationQuery-rest/target/locationQuery-rest-0.0.1-SNAPSHOT.jar
```
O serviço ficará disponibilizado no seguinte endereço:
```
http://localhost:8080
```

Para que o serviço funcione corretamente é necessário possuir um arquivo *settings.yaml* que deverá estar no mesmo diretório do jar. Através desse arquivo é possível configurar os componentes que serão utilizados para obter os dados de entrada.

Além do arquivo de configuração, será necessário possuir um diretório para cada componente de entrada e esses diretórios deverão estar no mesmo diretório do jar. Os diretórios deverão possui os respectivos nomes:

1. CandidateProviders - Os componentes que buscam os candidatos deverão ficar nesse diretório.
2. ClientProviders - Os componentes que buscam os potenciais clientes deverão ficar nesse diretório.
3. FacilityProviders - Os componentes que buscam as instalações deverão ficar nesse diretório.
4. LocationQueries - Os componentes que executam os algoritmos de busca deverão ficar nesse diretório.

Esse serviço disponibiliza três métodos: *input-candidates*, *findBestLocation* e *influenceArea*.

### input-candidates

Recebe as coordenadas que representam os locais a serem analisados na análise. Se esse *input* não for fornecido, serão utilizadas as coordenadas obtidas através do componente *candidate-provider* que será apresentado posteriormente. O método pode ser chamado através de uma requisição *POST* enviando um arquivo *json* com as coordenadas dos pontos candidatos. O arquivo *json* deverá estar no seguinte padrão:

```json
{
    "candidates": [
        {
            "latitude": -12.934240
            "longitude": -38.430540
        }
    ]
}
```

### findBestLocation

Esse método executa o algoritmo implementado no componente *location-query* que será apresentado posteriormente e utiliza os seguintes dados como *input*:
1. Candidatos - Serão obtidos através do método *input-candidates* ou do componente *candidate-provider*.
2. Intalações - Serão obtidas através do componente *facility-provider*.
3. Clientes - Serão obtidos através do componente *client-provider*.

O método pode ser chamado através de uma requisição *GET*.

### *inluenceArea*

Esse método calcula a área de influência de uma determinado ponto. As coordenadas desse ponto devem ser passadas como parâmetro. O método pode ser chamado através de uma requisiço *GET* seguindo o seguinte padrão:

*/influenceArea/{latitude}/{longitude}*

### Configuração do arquivo *settings.yaml*

O arquivo possui apenas três dados de configuração, são eles:

1. clientProvider: informa o nome do jar que será utilizado para obter os clientes.
2. facilityProvider: informa o nome do jar que será utilizado para obter as instalaçes.
3. candidateProvider: informa o nome do jar que será utilizado para obter os candidatos.
4. locationQuery: informa o nome do jar que será utilizado para executar o algoritmo de busca.

O arquivo ficará no seguinto padrão:

```yaml
locationQuery: min-clients-0.0.1-SNAPSHOT
clientProvider: client-provider-txt
facilityProvider: facility-provider-fake-0.0.1-SNAPSHOT
candidateProvider: candidate-provider-fake-0.0.1-SNAPSHOT
```


## olq-visualization

Esse é o componente responsável por visualizar os dados obtidos através do *locationQuery-rest*.
