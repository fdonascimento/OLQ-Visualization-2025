
# Visualização do melhor local para construir um empreendimento

Este projeto se divide em dois componentes principais: *locationQuery-rest* e *olq-visualization*.

## locationQuery-rest

O *locationQuery-rest* é um serviço web *RESTFul* que calcula o melhor local para construir um empreendimento. Esse serviço disponibiliza três métodos:

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
